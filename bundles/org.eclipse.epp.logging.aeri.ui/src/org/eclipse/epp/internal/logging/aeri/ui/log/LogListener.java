/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch, Daniel Haftstein - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui.log;

import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.WARN_REPORTING_ERROR;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;
import static org.eclipse.epp.internal.logging.aeri.ui.model.Reports.*;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportLogged;
import org.eclipse.epp.internal.logging.aeri.ui.ExpiringReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.CompleteErrorReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ReportsHistoryPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.UnseenErrorReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.EclipseBuildIdPresentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.ErrorStatusOnlyPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.HistoryReadyPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.IgnorePatternPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.ReporterNotDisabledPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.SkipReportsAbsentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.WhitelistedPluginIdPresentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.WorkbenchRunningPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;
import org.eclipse.ui.PlatformUI;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.eventbus.EventBus;

public class LogListener implements ILogListener {

    @SuppressWarnings("unchecked")
    @VisibleForTesting
    public static LogListener createLogListener(Settings settings, ServerConfiguration configuration, ReportHistory history, EventBus bus,
            ExpiringReportHistory expiringReportHistory, ProblemsDatabaseService serverProblemsStatusIndex) {
        Predicate<IStatus> statusFilters = Predicates.and(new ReporterNotDisabledPredicate(settings),
                new WhitelistedPluginIdPresentPredicate(configuration), new SkipReportsAbsentPredicate(),
                new EclipseBuildIdPresentPredicate(), new ErrorStatusOnlyPredicate(),
                new WorkbenchRunningPredicate(PlatformUI.getWorkbench()), new HistoryReadyPredicate(history),
                new IgnorePatternPredicate(configuration.getIgnoredPluginMessagesPatterns()));
        Predicate<ErrorReport> reportFilters = Predicates.and(new UnseenErrorReportPredicate(history, settings),
                new CompleteErrorReportPredicate(), new ReportsHistoryPredicate(expiringReportHistory, settings),
                new ReportPredicates.ProblemDatabaseIgnoredPredicate(serverProblemsStatusIndex, settings));
        org.eclipse.epp.internal.logging.aeri.ui.log.LogListener listener = new org.eclipse.epp.internal.logging.aeri.ui.log.LogListener(
                statusFilters, reportFilters, settings, configuration, bus);
        return listener;
    }

    private StandInStacktraceProvider stacktraceProvider = new StandInStacktraceProvider();
    private Predicate<IStatus> statusFilters;
    private Predicate<ErrorReport> reportFilters;
    private EventBus bus;
    private Settings settings;
    private ServerConfiguration configuration;

    public LogListener(Predicate<IStatus> statusFilters, Predicate<ErrorReport> reportFilters, Settings settings,
            ServerConfiguration configuration, EventBus bus) {
        this.statusFilters = statusFilters;
        this.reportFilters = reportFilters;
        this.settings = settings;
        this.configuration = configuration;
        this.bus = bus;
    }

    @Override
    public void logging(IStatus status, String nouse) {
        assert status != null;
        try {
            if (!statusFilters.apply(status)) {
                return;
            }
            ErrorReport report = createErrorReport(status);
            if (!reportFilters.apply(report)) {
                return;
            }
            bus.post(new NewReportLogged(report));
        } catch (Exception e) {
            log(WARN_REPORTING_ERROR, e);
        }
    }

    private ErrorReport createErrorReport(final IStatus status) {
        ErrorReport report = newErrorReport(status, settings, configuration);
        insertStandinStacktrace(report);
        guessInvolvedPlugins(report);
        insertLinkageErrorComment(report);
        return report;
    }

    private void insertStandinStacktrace(final ErrorReport report) {
        stacktraceProvider.insertStandInStacktraceIfEmpty(report.getStatus(), configuration);
    }
}
