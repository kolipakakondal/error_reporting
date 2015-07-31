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

import static com.google.common.base.Optional.absent;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;
import static org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies.getProxyHost;

import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Zips;

import com.google.common.base.Optional;
import com.google.common.io.Files;

public class ProblemsDatabaseUpdateJob extends Job {

    private ProblemsDatabaseService service;
    private URL indexUrl;
    private Settings settings;

    public ProblemsDatabaseUpdateJob(ProblemsDatabaseService service, URL indexUrl, Settings settings) {
        super("Updating Error Reports Database");
        this.service = service;
        this.indexUrl = indexUrl;
        this.settings = settings;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        SubMonitor progress = SubMonitor.convert(monitor, 3);
        progress.beginTask("Checking...", 3);
        try {
            progress.subTask("Checking for new problem database");
            File tempRemoteIndexZip = downloadNewRemoteIndex(progress.newChild(1)).orNull();
            if (tempRemoteIndexZip == null) {
                return Status.OK_STATUS;
            }

            File tempDir = Files.createTempDir();
            progress.subTask("Merging problem database");
            Zips.unzip(tempRemoteIndexZip, tempDir);
            service.replaceContent(tempDir);
            progress.worked(1);

            // cleanup files
            tempRemoteIndexZip.delete();
            FileUtils.deleteDirectory(tempDir);
            progress.worked(1);

            return Status.OK_STATUS;
        } catch (Exception e) {
            log(WARN_INDEX_UPDATE_FAILED, e);
            return Status.OK_STATUS;
        } finally {
            monitor.done();
        }
    }

    private Optional<File> downloadNewRemoteIndex(SubMonitor progress) throws Exception {

        // max time until a connection to the server has to be established.
        int connectTimeout = (int) TimeUnit.SECONDS.toMillis(10);
        // max time between two packets sent back to the client. 10 seconds of silence will kill the session
        int socketTimeout = (int) TimeUnit.SECONDS.toMillis(10);

        try {
            URI target = indexUrl.toURI();
            Request request = Request.Get(target).viaProxy(getProxyHost(target).orNull()).connectTimeout(connectTimeout)
                    .staleConnectionCheck(true).socketTimeout(socketTimeout);
            if (StringUtils.isNotBlank(settings.getProblemsZipEtag())) {
                request.setHeader(HttpHeaders.IF_NONE_MATCH, settings.getProblemsZipEtag());
            }

            Executor executor = Executor.newInstance();
            Response response = Proxies.proxyAuthentication(executor, target).execute(request);
            HttpResponse returnResponse = response.returnResponse();
            int statusCode = returnResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                Header etagHeader = returnResponse.getFirstHeader(HttpHeaders.ETAG);
                if (etagHeader == null) {
                    return absent();
                }
                settings.setProblemsZipEtag(etagHeader.getValue());

                File temp = File.createTempFile("problems-index", ".zip");
                try (OutputStream out = Files.newOutputStreamSupplier(temp).getOutput()) {
                    returnResponse.getEntity().writeTo(out);
                }
                return Optional.of(temp);
            } else if (statusCode == HttpStatus.SC_NOT_MODIFIED) {
                return absent();
            } else {
                // Could not access problems.zip for whatever reason; switch off error reporting until restart.
                settings.setAction(SendAction.IGNORE);
                settings.setRememberSendAction(RememberSendAction.RESTART);
                log(INFO_SERVER_NOT_AVAILABLE);
                if (statusCode != HttpStatus.SC_NOT_FOUND) {
                    // NOT_FOUND (404) would mean that network communication works correctly.
                    // We are only interested in cases where the communication failed.
                    new NetworkCommunicationTestJob().schedule();
                }
                return absent();
            }
        } finally {
            progress.done();
        }
    }
}
