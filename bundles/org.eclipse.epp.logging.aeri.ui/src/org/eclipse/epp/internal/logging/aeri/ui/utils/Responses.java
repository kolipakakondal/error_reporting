/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Daniel Haftstein - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.concurrent.CancellationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

public class Responses {

    private static class ProgressMonitorResponseHandler implements ResponseHandler<HttpResponse> {

        private IProgressMonitor monitor;

        public ProgressMonitorResponseHandler(IProgressMonitor monitor) {
            this.monitor = monitor;
        }

        @Override
        public HttpResponse handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            final HttpEntity entity = decorateForProgressMonitoring(response.getEntity(), monitor);
            if (response.getEntity() != null) {
                final ByteArrayEntity byteArrayEntity = new ByteArrayEntity(EntityUtils.toByteArray(entity));
                final ContentType contentType = ContentType.getOrDefault(entity);
                byteArrayEntity.setContentType(contentType.toString());
                response.setEntity(byteArrayEntity);
            }
            return response;
        }
    }

    private static class ProgressMonitorResponseStringHandler implements ResponseHandler<String> {
        private IProgressMonitor monitor;

        public ProgressMonitorResponseStringHandler(IProgressMonitor monitor) {
            this.monitor = monitor;
        }

        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            HttpResponse handled = new ProgressMonitorResponseHandler(monitor).handleResponse(response);
            return IOUtils.toString(handled.getEntity().getContent());
        }
    }

    private static class HttpEntityProgressDecorator implements HttpEntity {
        HttpEntity entity;
        private IProgressMonitor monitor;

        public HttpEntityProgressDecorator(HttpEntity entity, IProgressMonitor monitor) {
            this.entity = entity;
            this.monitor = monitor;
        }

        @Override
        public boolean isRepeatable() {
            return entity.isRepeatable();
        }

        @Override
        public boolean isChunked() {
            return entity.isChunked();
        }

        @Override
        public long getContentLength() {
            return entity.getContentLength();
        }

        @Override
        public Header getContentType() {
            return entity.getContentType();
        }

        @Override
        public Header getContentEncoding() {
            return entity.getContentEncoding();
        }

        @Override
        public InputStream getContent() throws IOException, IllegalStateException {
            return new ProgressMonitorInputStream(entity.getContent(), monitor, (int) getContentLength());
        }

        @Override
        public void writeTo(OutputStream outstream) throws IOException {
            entity.writeTo(new ProgressMonitorOutputStream(outstream, monitor, (int) getContentLength()));
        }

        @Override
        public boolean isStreaming() {
            return entity.isStreaming();
        }

        @SuppressWarnings("deprecation")
        @Override
        public void consumeContent() throws IOException {
            entity.consumeContent();
        }
    }

    private static class ByteTransferProgress {
        private static final int PROGRESS_STEPS = 100;

        public ByteTransferProgress(IProgressMonitor monitor, int byteLength) {
            this.totalBytes = byteLength;
            this.currentBytes = 0;
            this.monitorProgress = 0;
            this.monitor = SubMonitor.convert(monitor, PROGRESS_STEPS);
        }

        private IProgressMonitor monitor;
        private int monitorProgress;
        private int totalBytes;
        private int currentBytes;

        public void transferred(int numBytes) {
            currentBytes += numBytes;
            int currentProgress = (int) (PROGRESS_STEPS * ((double) currentBytes / totalBytes));
            int newProgress = currentProgress - monitorProgress;
            if (newProgress > 0) {
                monitor.worked(newProgress);
                monitorProgress += newProgress;
                monitor.subTask(createProgressMessage(currentBytes, totalBytes));
            }
        }

        private String createProgressMessage(long transferredBytes, long totalBytes) {
            return MessageFormat.format("{0}/{1}", FileUtils.byteCountToDisplaySize(transferredBytes),
                    FileUtils.byteCountToDisplaySize(totalBytes));
        }

        public boolean isMonitorCanceled() {
            return monitor.isCanceled();
        }
    }

    private static class ProgressMonitorOutputStream extends OutputStream {
        private OutputStream stream;
        private ByteTransferProgress progress;

        public ProgressMonitorOutputStream(OutputStream stream, IProgressMonitor monitor, int byteLength) {
            this.stream = stream;
            this.progress = new ByteTransferProgress(monitor, byteLength);
        }

        @Override
        public void write(int b) throws IOException {
            if (progress.isMonitorCanceled()) {
                throw new CancellationException();
            }
            progress.transferred(1);
            stream.write(b);
        }

        @Override
        public int hashCode() {
            return stream.hashCode();
        }

        @Override
        public void write(byte[] b) throws IOException {
            // no progress, calls write()
            super.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            // no progress, calls write()
            super.write(b, off, len);
        }

        @Override
        public boolean equals(Object obj) {
            return stream.equals(obj);
        }

        @Override
        public void flush() throws IOException {
            stream.flush();
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }

        @Override
        public String toString() {
            return stream.toString();
        }
    }

    private static class ProgressMonitorInputStream extends InputStream {

        private InputStream stream;
        private ByteTransferProgress progress;

        public ProgressMonitorInputStream(InputStream stream, IProgressMonitor monitor, int byteLength) {
            this.stream = stream;
            this.progress = new ByteTransferProgress(monitor, byteLength);
        }

        @Override
        public int read() throws IOException {
            if (progress.isMonitorCanceled()) {
                throw new CancellationException();
            }
            progress.transferred(1);
            return stream.read();
        }

        @Override
        public int hashCode() {
            return stream.hashCode();
        }

        @Override
        public int read(byte[] b) throws IOException {
            // no progress, calls read()
            return super.read(b);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            // no progress, calls read()
            return super.read(b, off, len);
        }

        @Override
        public boolean equals(Object obj) {
            return stream.equals(obj);
        }

        @Override
        public long skip(long n) throws IOException {
            progress.transferred((int) n);
            return stream.skip(n);
        }

        @Override
        public String toString() {
            return stream.toString();
        }

        @Override
        public int available() throws IOException {
            return stream.available();
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }

        @Override
        public void mark(int readlimit) {
            stream.mark(readlimit);
        }

        @Override
        public void reset() throws IOException {
            stream.reset();
        }

        @Override
        public boolean markSupported() {
            return stream.markSupported();
        }

    }

    /**
     * Returns a decorating {@link HttpEntity} which tracks progress of {@link HttpEntity#getContent()} and
     * {@link HttpEntity#writeTo(OutputStream)} based on {@link HttpEntity#getContentLength()}
     *
     * @param entity
     *            The entity to decorate, needs to return a positive {@link HttpEntity#getContentLength()}
     * @param monitor
     *            The monitor to use for progress tracking.
     * @return The decorated entity.
     */
    public static HttpEntity decorateForProgressMonitoring(HttpEntity entity, IProgressMonitor monitor) {
        return new HttpEntityProgressDecorator(entity, monitor);
    }

    public static HttpResponse getResponseWithProgress(Response response, IProgressMonitor monitor)
            throws ClientProtocolException, IOException {
        return response.handleResponse(new ProgressMonitorResponseHandler(monitor));
    }

    public static String getContentWithProgress(Response response, IProgressMonitor monitor) throws ClientProtocolException, IOException {
        return response.handleResponse(new ProgressMonitorResponseStringHandler(monitor));
    }
}
