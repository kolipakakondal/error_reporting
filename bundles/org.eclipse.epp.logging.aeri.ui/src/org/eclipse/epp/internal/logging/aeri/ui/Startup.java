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

import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.log.LogListener;
import org.eclipse.epp.internal.logging.aeri.ui.log.ProblemsDatabaseService;
import org.eclipse.epp.internal.logging.aeri.ui.log.ProblemsDatabaseUpdateJob;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.CompleteErrorReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ReportsHistoryPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.UnseenErrorReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.EclipseBuildIdPresentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.ErrorStatusOnlyPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.HistoryReadyPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.ReporterNotDisabledPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.SkipReportsAbsentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.WhitelistedPluginIdPresentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.WorkbenchRunningPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.PreferenceInitializer;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.notifications.MylynNotificationService;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
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
                initializeHistory();
                initializeExpiringHistory();
                initializeProblemsDatabase();
                initalizeSettings();
                initalizeEventBus();
                initalizeController();
                initalizeLogListener();
                scheduleJobs();
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
                        log(HISTORY_STOP_FAILED);
                    }
                    return true;
                }

                @Override
                public void postShutdown(IWorkbench workbench) {
                }
            });
        } catch (Exception e1) {
            log(HISTORY_START_FAILED);
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
                        log(INDEX_STOP_FAILED);
                    }
                    return true;
                }

                @Override
                public void postShutdown(IWorkbench workbench) {
                }
            });
        } catch (Exception e1) {
            log(INDEX_START_FAILED);
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
        org.eclipse.epp.internal.logging.aeri.ui.log.LogListener listener = createLogListener(settings, history, bus,
                expiringReportHistory, problemsDb);
        Platform.addLogListener(listener);
    }

    private void scheduleJobs() {
        try {
            URL indexZipUrl = new URL(Constants.PROBLEMS_STATUS_INDEX_ZIP_URL);
            new ProblemsDatabaseUpdateJob(problemsDb, indexZipUrl, settings)
                    .schedule(TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
        } catch (MalformedURLException e) {
            Throwables.propagate(e);
        }
    }

    @SuppressWarnings("unchecked")
    @VisibleForTesting
    public static LogListener createLogListener(Settings settings, ReportHistory history, EventBus bus,
            ExpiringReportHistory expiringReportHistory, ProblemsDatabaseService serverProblemsStatusIndex) {
        Predicate<IStatus> statusFilters = Predicates.and(new ReporterNotDisabledPredicate(settings),
                new WhitelistedPluginIdPresentPredicate(settings), new SkipReportsAbsentPredicate(),
                new EclipseBuildIdPresentPredicate(), new ErrorStatusOnlyPredicate(),
                new WorkbenchRunningPredicate(PlatformUI.getWorkbench()), new HistoryReadyPredicate(history));
        Predicate<ErrorReport> reportFilters = Predicates.and(new UnseenErrorReportPredicate(history, settings),
                new CompleteErrorReportPredicate(), new ReportsHistoryPredicate(expiringReportHistory, settings),
                new ReportPredicates.ProblemDatabaseIgnoredPredicate(serverProblemsStatusIndex));
        org.eclipse.epp.internal.logging.aeri.ui.log.LogListener listener = new org.eclipse.epp.internal.logging.aeri.ui.log.LogListener(
                statusFilters, reportFilters, settings, bus);
        return listener;
    }
}
