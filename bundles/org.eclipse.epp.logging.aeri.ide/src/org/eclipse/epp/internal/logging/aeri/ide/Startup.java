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
package org.eclipse.epp.internal.logging.aeri.ide;

import static java.util.concurrent.TimeUnit.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.ExpiringReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.ReportingController;
import org.eclipse.epp.internal.logging.aeri.ui.log.LogListener;
import org.eclipse.epp.internal.logging.aeri.ui.log.ProblemsDatabaseService;
import org.eclipse.epp.internal.logging.aeri.ui.log.ProblemsDatabaseUpdateJob;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.model.PreferenceInitializer;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.notifications.MylynNotificationService;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.common.base.Throwables;
import com.google.common.eventbus.EventBus;

public class Startup implements IStartup {

    private ReportHistory history;
    private Settings settings;
    private ReportingController controller;
    private EventBus bus;
    private ExpiringReportHistory expiringReportHistory;
    private ProblemsDatabaseService problemsDb;

    @Override
    public void earlyStartup() {
        new Job("Initializing Error Reporting System") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                SubMonitor progress = SubMonitor.convert(monitor, "Initializing error reporting", 8);
                progress.subTask("history");
                initializeHistory();
                progress.worked(1);
                progress.subTask("expiring history");
                initializeExpiringHistory();
                progress.worked(1);
                progress.subTask("problem database");
                initializeProblemsDatabase();
                progress.worked(1);
                progress.subTask("settings");
                initalizeSettings();
                progress.worked(1);
                progress.subTask("eventbus");
                initalizeEventBus();
                progress.worked(1);
                progress.subTask("controller");
                initalizeController();
                progress.worked(1);
                progress.subTask("log listener");
                initalizeLogListener();
                progress.worked(1);
                progress.subTask("jobs");
                scheduleJobs();
                progress.worked(1);
                monitor.done();
                return Status.OK_STATUS;
            }

        }.schedule();
    }

    private void initializeHistory() {
        try {
            history = new ReportHistory();
            history.startAsync();
            PlatformUI.getWorkbench().addWorkbenchListener(new IWorkbenchListener() {

                @Override
                public boolean preShutdown(IWorkbench workbench, boolean forced) {
                    try {
                        history.stopAsync();
                        history.awaitTerminated();
                    } catch (Exception e) {
                        log(WARN_HISTORY_STOP_FAILED);
                    }
                    return true;
                }

                @Override
                public void postShutdown(IWorkbench workbench) {
                }
            });
        } catch (Exception e) {
            log(WARN_HISTORY_START_FAILED, e);
        }
    }

    private void initializeProblemsDatabase() {
        try {
            Bundle bundle = FrameworkUtil.getBundle(getClass());
            IPath stateLocation = Platform.getStateLocation(bundle);
            File indexDirectory = new File(stateLocation.toFile(), Constants.SERVER_PROBLEMS_SERVICE_INDEX_DIR);

            problemsDb = new ProblemsDatabaseService(indexDirectory);
            problemsDb.startAsync();
            PlatformUI.getWorkbench().addWorkbenchListener(new IWorkbenchListener() {

                @Override
                public boolean preShutdown(IWorkbench workbench, boolean forced) {
                    try {
                        problemsDb.stopAsync();
                        problemsDb.awaitTerminated();
                    } catch (Exception e) {
                        log(WARN_INDEX_STOP_FAILED);
                    }
                    return true;
                }

                @Override
                public void postShutdown(IWorkbench workbench) {
                }
            });
        } catch (Exception e) {
            log(WARN_INDEX_START_FAILED, e);
        }
    }

    private void initializeExpiringHistory() {
        expiringReportHistory = new ExpiringReportHistory();
    }

    private void initalizeSettings() {
        settings = PreferenceInitializer.getDefault();
    }

    private void initalizeEventBus() {
        bus = new EventBus("Error Reporting Bus");
    }

    private void initalizeController() {
        controller = new ReportingController(bus, settings, new MylynNotificationService(bus), history, problemsDb);
        bus.register(controller);
    }

    private void initalizeLogListener() {
        org.eclipse.epp.internal.logging.aeri.ui.log.LogListener listener = LogListener.createLogListener(settings, history, bus,
                expiringReportHistory, problemsDb);
        Platform.addLogListener(listener);
    }

    private void scheduleJobs() {
        try {
            URL indexZipUrl = new URL(Constants.PROBLEMS_STATUS_INDEX_ZIP_URL);
            new ProblemsDatabaseUpdateJob(problemsDb, indexZipUrl, settings).schedule(MILLISECONDS.convert(10, SECONDS));
        } catch (Exception e) {
            Throwables.propagate(e);
        }
    }

}
