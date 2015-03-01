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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epp.internal.logging.aeri.ui.log.LogListener;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.eventbus.EventBus;

public class Startup implements IStartup {

    private ReportHistory history;
    private Settings settings;
    private ReportingController controller;
    private EventBus bus;

    @Override
    public void earlyStartup() {
        initializeHistory();
        initalizeSettings();
        initalizeEventBus();
        initalizeController();
        initalizeLogListener();
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

    private void initalizeSettings() {
        settings = PreferenceInitializer.getDefault();
    }

    private void initalizeEventBus() {
        bus = new EventBus("Error Reporting Bus");
    }

    private void initalizeController() {
        controller = new ReportingController(bus, settings, new MylynNotificationService(bus), history);
        bus.register(controller);
    }

    private void initalizeLogListener() {
        org.eclipse.epp.internal.logging.aeri.ui.log.LogListener listener = createLogListener(settings, history, bus);
        Platform.addLogListener(listener);

    }

    @SuppressWarnings("unchecked")
    @VisibleForTesting
    public static LogListener createLogListener(Settings settings, ReportHistory history, EventBus bus) {
        Predicate<IStatus> statusFilters = Predicates.and(new ReporterNotDisabledPredicate(settings),
                new WhitelistedPluginIdPresentPredicate(settings), new SkipReportsAbsentPredicate(), new EclipseBuildIdPresentPredicate(),
                new ErrorStatusOnlyPredicate(), new WorkbenchRunningPredicate(PlatformUI.getWorkbench()),
                new HistoryReadyPredicate(history));
        Predicate<ErrorReport> reportFilters = Predicates.and(new UnseenErrorReportPredicate(history, settings),
                new CompleteErrorReportPredicate(), new ReportsHistoryPredicate(new ExpiringReportHistory(), settings));
        org.eclipse.epp.internal.logging.aeri.ui.log.LogListener listener = new org.eclipse.epp.internal.logging.aeri.ui.log.LogListener(
                statusFilters, reportFilters, settings, bus);
        return listener;
    }
}
