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
package org.eclipse.epp.internal.logging.aeri.ui;

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.v2.AeriServer;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;

import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;

public class UploadHandler extends JobChangeAdapter {

    private final Settings settings;
    private final EventBus bus;
    private final Set<ErrorReport> events = Sets.newConcurrentHashSet();

    volatile Job scheduledJob;
    private ReportHistory history;
    private AeriServer server;
    private ServerConfiguration configuration;

    public UploadHandler(Settings settings, ServerConfiguration configuration, AeriServer server, ReportHistory history, EventBus bus) {
        this.settings = settings;
        this.configuration = configuration;
        this.server = server;
        this.history = history;
        this.bus = bus;
    }

    public void scheduleForSending(final List<ErrorReport> reports) {
        new Job("Schedule Reports for sending") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                monitor.beginTask("Scheduling...", reports.size());
                for (ErrorReport report : reports) {
                    history.remember(report);
                    addForSending(report);
                    monitor.worked(1);
                    if (monitor.isCanceled()) {
                        break;
                    }
                }
                monitor.done();
                return Status.OK_STATUS;
            }
        }.schedule();
    }

    private void addForSending(ErrorReport report) {
        boolean add = events.add(report);
        if (add && scheduledJob == null) {
            scheduleJob();
        }
    }

    @Override
    public void done(IJobChangeEvent event) {
        scheduledJob = null;
        if (!events.isEmpty()) {
            scheduleJob();
        }
    }

    private void scheduleJob() {
        scheduledJob = new UploadJob(events, settings, configuration, server, bus);
        scheduledJob.addJobChangeListener(this);
    }
}
