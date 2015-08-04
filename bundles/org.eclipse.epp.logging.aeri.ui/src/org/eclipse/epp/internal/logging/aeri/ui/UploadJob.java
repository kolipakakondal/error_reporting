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

import static java.text.MessageFormat.format;
import static org.eclipse.core.runtime.IStatus.WARNING;
import static org.eclipse.epp.internal.logging.aeri.ui.Constants.PLUGIN_ID;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseShowRequest;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.Messages;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.v2.AeriServer;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;

import com.google.common.eventbus.EventBus;

public class UploadJob extends Job {

    private Set<ErrorReport> events;
    private Settings settings;
    private EventBus bus;
    private AeriServer server;
    private ServerConfiguration configuration;

    public UploadJob(Set<ErrorReport> events, Settings settings, ServerConfiguration configuration, AeriServer server, EventBus bus) {
        super(format(Messages.UPLOADJOB_NAME, server.getConfiguration().getSubmitUrl()));
        this.events = events;
        this.settings = settings;
        this.configuration = configuration;
        this.server = server;
        this.bus = bus;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        monitor.beginTask(Messages.UPLOADJOB_TASKNAME, 1);
        try {
            while (!events.isEmpty()) {
                ErrorReport event = poll(events);
                ErrorReport reportToSend = Reports.createAnonymizedSendCopy(event, settings, configuration);
                ServerResponse result = server.upload(reportToSend);
                bus.post(new ServerResponseShowRequest(result));
            }
            return new Status(IStatus.OK, PLUGIN_ID, Messages.UPLOADJOB_THANK_YOU);
        } catch (Exception e) {
            return new Status(WARNING, PLUGIN_ID, Messages.UPLOADJOB_FAILED, e);
        } finally {
            monitor.done();
        }

    }

    private ErrorReport poll(Set<ErrorReport> events) {
        Iterator<ErrorReport> iterator = events.iterator();
        ErrorReport event = iterator.next();
        iterator.remove();
        return event;
    }
}
