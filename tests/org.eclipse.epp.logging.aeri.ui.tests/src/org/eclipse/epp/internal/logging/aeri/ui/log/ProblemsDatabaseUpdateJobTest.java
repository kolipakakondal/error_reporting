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
package org.eclipse.epp.internal.logging.aeri.ui.log;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpStatus;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.v2.AeriServer;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ProblemsDatabaseUpdateJobTest {

    private ProblemsDatabaseService service;
    private AeriServer server;
    private ServerConfiguration configuration;
    private Settings settings;

    private ProblemsDatabaseUpdateJob sut;

    @Before
    public void setUp() {
        service = mock(ProblemsDatabaseService.class);
        configuration = mock(ServerConfiguration.class);
        server = mock(AeriServer.class);
        Mockito.doCallRealMethod().when(server).setConfiguration(any(ServerConfiguration.class));
        server.setConfiguration(configuration);
        when(server.isProblemsDatabaseOutdated()).thenCallRealMethod();
        settings = mock(Settings.class);
        sut = new ProblemsDatabaseUpdateJob(service, server, settings);
    }

    @Test
    public void testNoRemoteInteractionsIfUpToDate() {
        mockDatabaseUpToDate();

        IStatus status = runJob();

        assertThat(status, is(Status.OK_STATUS));
        verify(server).isProblemsDatabaseOutdated();
        verify(server).setConfiguration(any(ServerConfiguration.class));
        verifyNoMoreInteractions(server);
    }

    @Test
    public void testDownloadFails() throws IOException, URISyntaxException {
        mockDatabaseOutdated();
        when(server.downloadDatabase(any(File.class), any(IProgressMonitor.class))).thenReturn(HttpStatus.SC_NOT_FOUND);

        IStatus status = runJob();

        assertThat(status, is(Status.OK_STATUS));
        verifyZeroInteractions(service);
        verify(server).isProblemsDatabaseOutdated();
        verify(server).setConfiguration(any(ServerConfiguration.class));
        verify(server).downloadDatabase(any(File.class), any(IProgressMonitor.class));
        verifyNoMoreInteractions(server);
    }

    @Test
    public void testDownloadSuccess() throws IOException, URISyntaxException {
        mockDatabaseOutdated();
        // the job will try to unzip the index for the service
        when(server.downloadDatabase(any(File.class), any(IProgressMonitor.class))).thenAnswer(new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                File file = (File) invocation.getArguments()[0];
                createEmptyZip(file);
                return HttpStatus.SC_OK;
            }
        });

        IStatus status = runJob();

        assertThat(status, is(Status.OK_STATUS));
        verify(server).isProblemsDatabaseOutdated();
        verify(server).setConfiguration(any(ServerConfiguration.class));
        verify(server).downloadDatabase(any(File.class), any(IProgressMonitor.class));
        verify(service).replaceContent(any(File.class));
        verifyNoMoreInteractions(server);
    }

    @Test
    public void testDownloadException() throws IOException, URISyntaxException {
        mockDatabaseOutdated();
        when(server.downloadDatabase(any(File.class), any(IProgressMonitor.class))).thenThrow(new RuntimeException());

        IStatus status = runJob();

        assertThat(status, is(Status.OK_STATUS));

    }

    private IStatus runJob() {
        return sut.run(mock(IProgressMonitor.class));
    }

    private void mockDatabaseOutdated() {
        // timestamp long ago
        when(configuration.getProblemsZipLastDownloadTimestamp()).thenReturn(42L);
        // with very short time to live
        when(configuration.getProblemsTtlMs()).thenReturn(1L);
    }

    private void mockDatabaseUpToDate() {
        // downloaded now
        when(configuration.getProblemsZipLastDownloadTimestamp()).thenReturn(System.currentTimeMillis());
        // long time to live
        when(configuration.getProblemsTtlMs()).thenReturn(TimeUnit.MILLISECONDS.convert(10, TimeUnit.DAYS));
    }

    private static final byte[] MINIMAL_ZIP_FILE = { 80, 75, 05, 06, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
            00 };

    private static void createEmptyZip(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(MINIMAL_ZIP_FILE, 0, 22);
        fos.flush();
        fos.close();
    }

}
