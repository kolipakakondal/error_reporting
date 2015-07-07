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

import static com.google.common.base.Optional.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;
import static org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies.*;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs;
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
            String etag = getEtag().orNull();
            progress.worked(1);
            if (etag == null) {
                return Logs.toStatus(WARN_FAILED_TO_FETCH_PROBLEM_DB_ETAG);
            }
            try {
                if (isLocalIndexOutdated(etag)) {
                    progress.subTask("Downloading new problem database");
                    File remoteIndexZip = downloadRemoteIndex();
                    progress.worked(1);
                    File tempDir = Files.createTempDir();
                    progress.subTask("Merging problem database");
                    Zips.unzip(remoteIndexZip, tempDir);
                    service.replaceContent(tempDir);
                    progress.worked(1);
                    settings.setProblemsZipEtag(etag);
                    FileUtils.deleteQuietly(tempDir);
                }
            } catch (Exception e) {
                log(WARN_INDEX_UPDATE_FAILED, e);
            }
            return Status.OK_STATUS;
        } finally {
            monitor.done();
        }
    }

    private boolean isLocalIndexOutdated(String etag) {
        return !StringUtils.equals(settings.getProblemsZipEtag(), etag);
    }

    private Optional<String> getEtag() {
        try {
            Executor executor = Executor.newInstance();
            URI target = indexUrl.toURI();
            Request request = Request.Head(target).viaProxy(getProxyHost(target).orNull());
            Response response = proxyAuthentication(executor, target).execute(request);
            // assuming that this cannot be null:
            HttpResponse httpResponse = response.returnResponse();
            // headers, however, may be null:
            Header etagHeader = httpResponse.getFirstHeader("ETAG");
            if (etagHeader == null) {
                return absent();
            }
            return fromNullable(etagHeader.getValue());
        } catch (Exception e) {
            log(WARN_INDEX_UPDATE_FAILED, e);
            return Optional.absent();
        }
    }

    private File downloadRemoteIndex() throws Exception {
        Executor executor = Executor.newInstance();
        URI target = indexUrl.toURI();
        Request request = Request.Get(target).viaProxy(getProxyHost(target).orNull());
        Response response = Proxies.proxyAuthentication(executor, target).execute(request);

        Content content = response.returnContent();
        File temp = File.createTempFile("problems-index", ".zip");
        Files.write(content.asBytes(), temp);
        return temp;
    }
}
