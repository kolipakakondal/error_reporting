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
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.INFO_SERVER_NOT_AVAILABLE;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;
import static org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
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
import org.eclipse.epp.internal.logging.aeri.ui.log.NetworkCommunicationTestJob;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse.ProblemResolution;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Json;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies;

public class AeriServer {

    public static ServerConfiguration download(URI target, Executor executor)
            throws HttpResponseException, UnknownHostException, Exception {
        Response response = request(target, executor);
        String content = response.returnContent().asString();
        ServerConfiguration config = Json.deserialize(content, ServerConfiguration.class);
        config.setTimestamp(System.currentTimeMillis());
        return config;
    }

    public static ServerConfiguration loadFromFile(File jsonFile) {
        return Json.deserialize(jsonFile, ServerConfiguration.class);
    }

    public static void saveToFile(File file, ServerConfiguration configuration) {
        Json.serialize(configuration, file);
    }

    private static Response request(URI target, Executor executor) throws ClientProtocolException, IOException {
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
    private Settings settings;

    // max time until a connection to the server has to be established.
    private int connectTimeout = (int) TimeUnit.SECONDS.toMillis(3);
    // max time between two packets sent back to the client. 10 seconds of silence will kill the session
    private int socketTimeout = (int) TimeUnit.SECONDS.toMillis(10);

    public AeriServer(Executor executor, ServerConfiguration configuration, Settings settings) {
        this.executor = executor;
        this.configuration = configuration;
        this.settings = settings;
    }

    public ServerConfiguration getConfiguration() {
        return configuration;
    }

    public ServerResponse upload(ErrorReport report) throws URISyntaxException, ClientProtocolException, IOException {
        String body = Reports.toJson(report, false);
        StringEntity stringEntity = new StringEntity(body, ContentType.APPLICATION_OCTET_STREAM.withCharset(UTF_8));
        HttpEntity entity = new GzipCompressingEntity(stringEntity);

        String submitUrl = configuration.getSubmitUrl();
        URI target = new URI(submitUrl);
        Request request = Request.Post(target).viaProxy(getProxyHost(target).orNull()).body(entity).connectTimeout(connectTimeout)
                .staleConnectionCheck(true).socketTimeout(socketTimeout);
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

    public boolean downloadDatabase(File destination) throws IOException, URISyntaxException {
        URI target = new URI(configuration.getProblemsUrl());
        Date lastmodified = new Date(parseLastmodified());
        // @formatter:off
        Request request = Request.Get(target)
                .viaProxy(getProxyHost(target).orNull())
                .connectTimeout(connectTimeout)
                .staleConnectionCheck(true)
                .socketTimeout(socketTimeout)
                .setIfModifiedSince(lastmodified);
        // @formatter:on

        Response response = Proxies.proxyAuthentication(executor, target).execute(request);
        HttpResponse returnResponse = response.returnResponse();
        int statusCode = returnResponse.getStatusLine().getStatusCode();

        if (statusCode == HttpStatus.SC_OK) {
            Header lastModified = returnResponse.getFirstHeader(HttpHeaders.LAST_MODIFIED);
            if (lastModified != null) {
                String value = lastModified.getValue();
                settings.setProblemsZipEtag(value);
            }
            try (FileOutputStream out = new FileOutputStream(destination)) {
                returnResponse.getEntity().writeTo(out);
            }
            return true;
        } else if (statusCode == HttpStatus.SC_NOT_MODIFIED) {
            return false;
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
        }
        return false;
    }

    private long parseLastmodified() {
        try {
            String value = settings.getProblemsZipEtag();
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    private ProblemResolution tryParse(ServerResponse05 state) {
        try {
            return ProblemResolution.valueOf(state.getResolved().or(ProblemResolution.UNCONFIRMED.name()));
        } catch (Exception e) {
            return ProblemResolution.UNCONFIRMED;
        }
    }
}
