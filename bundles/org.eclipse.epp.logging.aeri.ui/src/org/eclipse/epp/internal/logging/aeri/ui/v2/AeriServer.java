/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui.v2;

import static com.google.common.base.Charsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.abbreviate;
import static org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.GzipCompressingEntity;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse.ProblemResolution;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Json;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Responses;

import com.google.common.annotations.VisibleForTesting;

public class AeriServer {

    @VisibleForTesting
    protected static Response request(URI target, Executor executor) throws ClientProtocolException, IOException {
        // max time until a connection to the server has to be established.
        int connectTimeout = (int) TimeUnit.SECONDS.toMillis(3);
        // max time between two packets sent back to the client. 10 seconds of silence will kill the session
        int socketTimeout = (int) TimeUnit.SECONDS.toMillis(10);
        Request request = Request.Get(target).viaProxy(getProxyHost(target).orNull()).connectTimeout(connectTimeout)
                .staleConnectionCheck(true).socketTimeout(socketTimeout);
        return proxyAuthentication(executor, target).execute(request);
    }

    private Executor executor;
    private ServerConfiguration configuration;

    private File configurationFile;

    public AeriServer(Executor executor, File configurationFile) {
        this.executor = executor;
        this.configurationFile = configurationFile;
    }

    // TODO test all remote cases for exceptions
    public void refreshConfiguration(String serverUrl, IProgressMonitor monitor)
            throws HttpResponseException, UnknownHostException, Exception {
        Response response = request(newURI(serverUrl), executor);
        String content = Responses.getContentWithProgress(response, monitor);
        configuration = Json.deserialize(content, ServerConfiguration.class);
        configuration.setTimestamp(System.currentTimeMillis());
    }

    public void loadConfiguration() {
        configuration = Json.deserialize(configurationFile, ServerConfiguration.class);
    }

    public void saveConfiguration() {
        Json.serialize(configuration, configurationFile);
    }

    public ServerConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ServerConfiguration configuration) {
        this.configuration = configuration;
    }

    public ServerResponse upload(ErrorReport report, IProgressMonitor monitor) throws IOException {
        String body = Reports.toJson(report, false);
        StringEntity stringEntity = new StringEntity(body, ContentType.APPLICATION_OCTET_STREAM.withCharset(UTF_8));
        // length of zipped conent is unknown, using the progress of the string-stream instead.
        // download progress percentage will be accurate, download progress size will be too large by the compression factor
        HttpEntity entity = new GzipCompressingEntity(Responses.decorateForProgressMonitoring(stringEntity, monitor));

        String submitUrl = configuration.getSubmitUrl();
        URI target = newURI(submitUrl);
        // @formatter:off
        Request request = Request.Post(target)
                .viaProxy(getProxyHost(target).orNull())
                .body(entity)
                .connectTimeout(configuration.getConnectTimeoutMs())
                .staleConnectionCheck(true)
                .socketTimeout(configuration.getSocketTimeoutMs());
        // @formatter:on
        String response = proxyAuthentication(executor, target).execute(request).returnContent().asString();
        ServerResponse05 response05 = Json.deserialize(response, ServerResponse05.class);

        // TODO complete dto
        ServerResponse result = new ServerResponse();
        result.setReportTitle(abbreviate(report.getStatus().getMessage(), 80));
        result.setIncidentId(response05.getBugId().orNull());
        result.setIncidentUrl(response05.getBugUrl().orNull());
        result.setResolution(tryParse(response05));
        result.setCommitterMessage(response05.getInformation().orNull());
        return result;
    }

    private static URI newURI(String uri) throws IOException {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new IOException("invalid server url", e);
        }
    }

    /**
     *
     * @param monitor
     * @return the {@link HttpStatus}
     */
    public int downloadDatabase(File destination, IProgressMonitor monitor) throws IOException {
        URI target = newURI(configuration.getProblemsUrl());
        // @formatter:off
        Request request = Request.Get(target)
                .viaProxy(getProxyHost(target).orNull())
                .connectTimeout(configuration.getConnectTimeoutMs())
                .staleConnectionCheck(true)
                .socketTimeout(configuration.getSocketTimeoutMs());
        // @formatter:on

        Response response = Proxies.proxyAuthentication(executor, target).execute(request);

        HttpResponse returnResponse = Responses.getResponseWithProgress(response, monitor);
        int statusCode = returnResponse.getStatusLine().getStatusCode();

        if (statusCode == HttpStatus.SC_OK) {
            configuration.setProblemsZipLastDownloadTimestamp(System.currentTimeMillis());
            saveConfiguration();
            try (FileOutputStream out = new FileOutputStream(destination)) {
                returnResponse.getEntity().writeTo(out);
            }
        }
        return statusCode;
    }

    private ProblemResolution tryParse(ServerResponse05 state) {
        try {
            return ProblemResolution.valueOf(state.getResolved().or(ProblemResolution.UNCONFIRMED.name()));
        } catch (Exception e) {
            return ProblemResolution.UNCONFIRMED;
        }
    }

    public boolean isProblemsDatabaseOutdated() {
        return System.currentTimeMillis() - configuration.getProblemsZipLastDownloadTimestamp() > configuration.getProblemsTtlMs();
    }

    public boolean isConfigurationOutdated() {
        return System.currentTimeMillis() - configuration.getTimestamp() > configuration.getTtlMs();
    }

}
