/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Johannes Dorn - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui.log;

import static java.lang.System.lineSeparator;
import static java.net.URLEncoder.encode;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.eclipse.core.internal.net.ProxySelector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies;
import org.eclipse.equinox.internal.p2.transport.ecf.RepositoryTransport;

import com.google.common.collect.Lists;

@SuppressWarnings("restriction")
public class NetworkCommunicationTestJob extends Job {

    private static final String PROXY_TEST_URL = "http://download.eclipse.org/stats/technology/epp/logging/network-communication-test/proxy/authentication/"; //$NON-NLS-1$
    private static final String REQUEST_URL = "http://download.eclipse.org/stats/technology/epp/logging/network-communication-test/{0}/java-{1}/{2}-{3}/{4}-{5}/{6}/{7}-{8}/"; //$NON-NLS-1$
    private static final String APACHE_HTTP_REQUEST_PART = "apache"; //$NON-NLS-1$
    private static final String P2_HTTP_REQUEST_PART = "p2"; //$NON-NLS-1$
    private static final String UNKNOWN = "unknown"; //$NON-NLS-1$
    private static final String NO_PROXY_AUTHENTICATION = "none"; //$NON-NLS-1$
    private static final String BUGZILLA_URL = "https://bugs.eclipse.org/bugs/enter_bug.cgi?product=EPP&component=logging"; //$NON-NLS-1$

    public NetworkCommunicationTestJob() {
        super("Network Communication Test"); //$NON-NLS-1$
        setSystem(true);
        setPriority(Job.DECORATE);
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        SubMonitor progress = SubMonitor.convert(monitor, 3);

        String javaVersion = getUrlSafeProperty("java.version"); //$NON-NLS-1$
        String operatingSystem = getUrlSafeProperty("os.name"); //$NON-NLS-1$
        String operatingSystemVersion = getUrlSafeProperty("os.version"); //$NON-NLS-1$
        String proxyProvider = ProxySelector.getDefaultProvider();

        try {
            URI proxyTestUri = new URL(PROXY_TEST_URL).toURI();
            String proxyHostConfigured = getProxyHost(proxyTestUri).isPresent() ? "proxyHost" : "noProxyHost"; //$NON-NLS-1$ //$NON-NLS-2$

            String proxyUsernameConfigured = Proxies.getProxyUser(proxyTestUri).isPresent() ? "proxyUsername" //$NON-NLS-1$
                    : "noProxyUsername"; //$NON-NLS-1$

            String proxyPasswordConfigured = Proxies.getProxyPassword(proxyTestUri).isPresent() ? "proxyPassword" //$NON-NLS-1$
                    : "noProxyPassword"; //$NON-NLS-1$

            String authentication = getRequiredAuthentication(proxyTestUri, progress.newChild(1));

            doApacheHeadRequest(toUri(format(REQUEST_URL, APACHE_HTTP_REQUEST_PART, javaVersion, operatingSystem, operatingSystemVersion,
                    proxyProvider, authentication, proxyHostConfigured, proxyUsernameConfigured, proxyPasswordConfigured)),
                    progress.newChild(1));
            doP2HeadRequest(toUri(format(REQUEST_URL, P2_HTTP_REQUEST_PART, javaVersion, operatingSystem, operatingSystemVersion,
                    proxyProvider, authentication, proxyHostConfigured, proxyUsernameConfigured, proxyPasswordConfigured)),
                    progress.newChild(1));
        } catch (Exception e) {
            Logs.log(LogMessages.ERROR_NETWORK_COMMUNICATION_URL_PARSING_FAILED, e);
        }
        return Status.OK_STATUS;
    }

    private static URI toUri(String url) throws MalformedURLException, URISyntaxException {
        return new URL(url).toURI();
    }

    private String getRequiredAuthentication(URI uri, SubMonitor progress) {
        try {
            Executor executor = Executor.newInstance();
            Request request = Request.Head(uri).viaProxy(getProxyHost(uri).orNull());
            Response response = executor.execute(request);
            String authentication = response.handleResponse(new ResponseHandler<String>() {

                @Override
                public String handleResponse(HttpResponse response) throws IOException {
                    Header[] headers = response.getHeaders("Proxy-Authenticate"); //$NON-NLS-1$
                    if (ArrayUtils.isEmpty(headers)) {
                        return NO_PROXY_AUTHENTICATION;
                    }
                    List<String> authenticationSchemes = Lists.newArrayList();
                    for (int i = 0; i < headers.length; i++) {
                        Header header = headers[i];
                        String value = header.getValue();
                        if (value == null) {
                            continue;
                        }
                        authenticationSchemes.add(StringUtils.substringBefore(header.getValue(), " ")); //$NON-NLS-1$
                    }
                    return StringUtils.join(authenticationSchemes, ';');
                }
            });
            return authentication;
        } catch (Exception e) {
            Logs.log(LogMessages.ERROR_ON_PROXY_AUTHENTICATION_TEST, e, uri);
            return UNKNOWN;
        } finally {
            progress.done();
        }
    }

    private void doApacheHeadRequest(URI uri, SubMonitor progress) {
        Executor executor = Executor.newInstance();
        Request request = Request.Head(uri).viaProxy(getProxyHost(uri).orNull());
        try {
            Response response = proxyAuthentication(executor, uri).execute(request);
            HttpResponse httpResponse = response.returnResponse();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_NOT_FOUND || statusCode != HttpStatus.SC_OK) {
                StringBuilder sb = new StringBuilder();
                sb.append(lineSeparator());
                sb.append("Communication URL: ").append(uri).append(lineSeparator()); //$NON-NLS-1$
                sb.append("Response Code: ").append(statusCode).append(" - ").append(httpResponse.getStatusLine().getReasonPhrase())
                        .append(lineSeparator());
                sb.append("Response Headers:").append(lineSeparator()); //$NON-NLS-1$
                for (Header header : httpResponse.getAllHeaders()) {
                    sb.append(header.getName()).append(": ").append(header.getValue()).append(lineSeparator()); //$NON-NLS-1$
                }
                Logs.log(LogMessages.ERROR_ON_APACHE_HEAD_REQUEST, BUGZILLA_URL, sb);
            }
        } catch (Exception e) {
            Logs.log(LogMessages.ERROR_ON_APACHE_HEAD_REQUEST, e, BUGZILLA_URL, uri);
        }
        progress.done();
    }

    private void doP2HeadRequest(URI uri, SubMonitor progress) {
        try {
            RepositoryTransport transport = new RepositoryTransport();
            transport.getLastModified(uri, progress);
        } catch (FileNotFoundException e) {
            // Expected exception.
        } catch (Exception e) {
            Logs.log(LogMessages.ERROR_ON_P2_HEAD_REQUEST, e, uri);
        }
    }

    private static String getUrlSafeProperty(String key) {
        try {
            return encode(System.getProperty(key, UNKNOWN), UTF_8);
        } catch (UnsupportedEncodingException e) {
            return UNKNOWN;
        }
    }
}
