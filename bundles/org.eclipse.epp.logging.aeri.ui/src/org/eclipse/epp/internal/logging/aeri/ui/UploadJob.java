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
package org.eclipse.epp.internal.logging.aeri.ui;

import static com.google.common.base.Charsets.UTF_8;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.abbreviate;
import static org.eclipse.core.runtime.IStatus.WARNING;
import static org.eclipse.epp.internal.logging.aeri.ui.Constants.PLUGIN_ID;
import static org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies.*;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.GzipCompressingEntity;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseShowRequest;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.Messages;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse.ProblemResolution;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Json;

import com.google.common.base.Optional;
import com.google.common.eventbus.EventBus;

public class UploadJob extends Job {

    private Executor executor;
    private ErrorReport event;
    private URI target;
    private Settings settings;
    private EventBus bus;

    public UploadJob(ErrorReport event, Settings settings, URI target, EventBus bus) {
        super(format(Messages.UPLOADJOB_NAME, target));
        this.event = event;
        this.settings = settings;
        this.target = target;
        this.bus = bus;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        monitor.beginTask(Messages.UPLOADJOB_TASKNAME, 1);
        // max time until a connection to the server has to be established.
        int connectTimeout = (int) TimeUnit.SECONDS.toMillis(3);
        // max time between two packets sent back to the client. 10 seconds of silence will kill the session
        int socketTimeout = (int) TimeUnit.SECONDS.toMillis(10);

        try {
            executor = Executor.newInstance();
            String body = Reports.toJson(event, settings, false);
            StringEntity stringEntity = new StringEntity(body, ContentType.APPLICATION_OCTET_STREAM.withCharset(UTF_8));
            HttpEntity entity = new GzipCompressingEntity(stringEntity);

            Request request = Request.Post(target).viaProxy(getProxyHost(target).orNull()).body(entity).connectTimeout(connectTimeout)
                    .staleConnectionCheck(true).socketTimeout(socketTimeout);
            Response response = proxyAuthentication(executor, target).execute(request);
            HttpResponse httpResponse = response.returnResponse();
            String details = EntityUtils.toString(httpResponse.getEntity());
            int code = httpResponse.getStatusLine().getStatusCode();
            if (code >= 400) {
                return new MultiStatus(PLUGIN_ID, WARNING, new Status[] { new Status(WARNING, PLUGIN_ID, details) },
                        Messages.UPLOADJOB_FAILED, null);
            }
            final ServerResponse05 response05 = Json.deserialize(details, ServerResponse05.class);

            // TODO complete dto
            ServerResponse result = new ServerResponse();
            result.setReportTitle(abbreviate(event.getStatus().getMessage(), 80));
            result.setIncidentId(response05.bugId);
            result.setIncidentUrl(response05.bugUrl);
            result.setResolution(tryParse(response05));
            result.setCommitterMessage(response05.information);

            bus.post(new ServerResponseShowRequest(result));
            return new Status(IStatus.OK, PLUGIN_ID, format(Messages.UPLOADJOB_THANK_YOU, details));
        } catch (Exception e) {
            return new Status(WARNING, PLUGIN_ID, Messages.UPLOADJOB_FAILED, e);
        } finally {
            monitor.done();
        }
    }

    private ProblemResolution tryParse(ServerResponse05 state) {
        try {
            return ProblemResolution.valueOf(state.getResolved().or(ProblemResolution.UNCONFIRMED.name()));
        } catch (Exception e) {
            return ProblemResolution.UNCONFIRMED;
        }
    }

    @SuppressWarnings("unused")
    private static class ServerResponse05 {

        public static final String KEYWORD_NEEDINFO = "needinfo"; //$NON-NLS-1$
        public static final String[] EMPTY_STRINGS = new String[0];
        public static final String FIXED = "FIXED"; //$NON-NLS-1$
        public static final String ASSIGNED = "ASSIGNED"; //$NON-NLS-1$
        public static final String NOT_ECLIPSE = "NOT_ECLIPSE"; //$NON-NLS-1$
        public static final String INVALID = "INVALID"; //$NON-NLS-1$
        public static final String WONTFIX = "WONTFIX"; //$NON-NLS-1$
        public static final String WORKSFORME = "WORKSFORME"; //$NON-NLS-1$
        public static final String MOVED = "MOVED"; //$NON-NLS-1$
        public static final String DUPLICATE = "DUPLICATE"; //$NON-NLS-1$
        public static final String UNKNOWN = "UNKNOWN"; //$NON-NLS-1$
        public static final String CLOSED = "CLOSED"; //$NON-NLS-1$
        public static final String RESOLVED = "RESOLVED"; //$NON-NLS-1$
        public static final String NEW = "NEW"; //$NON-NLS-1$
        public static final String UNCONFIRMED = "UNCONFIRMED"; //$NON-NLS-1$

        private boolean created;
        private String bugId;
        private String bugUrl;
        private String status;
        private String resolved;
        private String information;
        private String[] keywords;

        public boolean isCreated() {
            return created;
        }

        public Optional<String> getBugId() {
            return Optional.fromNullable(bugId);
        }

        public Optional<String> getBugUrl() {
            return Optional.fromNullable(bugUrl);
        }

        public Optional<String> getInformation() {
            return Optional.fromNullable(information);
        }

        public Optional<String[]> getKeywords() {
            return Optional.fromNullable(keywords);
        }

        public Optional<String> getResolved() {
            return Optional.fromNullable(resolved);
        }

        public Optional<String> getStatus() {
            return Optional.fromNullable(status);
        }
    }

}
