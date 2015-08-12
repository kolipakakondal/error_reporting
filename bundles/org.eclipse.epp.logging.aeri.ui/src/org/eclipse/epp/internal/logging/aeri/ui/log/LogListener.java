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

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportLogged;
import org.eclipse.epp.internal.logging.aeri.ui.ExpiringReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.AcceptOtherPackagesPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.AcceptUiFreezesPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.CompleteUiFreezeReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ProblemDatabaseIgnoredPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ReportsHistoryPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.UnseenErrorReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ValidSizeErrorReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.AcceptProductPredicate;
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
import org.osgi.framework.FrameworkUtil;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.eventbus.EventBus;

public class LogListener implements ILogListener {

    @SuppressWarnings("unchecked")
    @VisibleForTesting
    public static LogListener createLogListener(Settings settings, ServerConfiguration configuration, ReportHistory history, EventBus bus,
            ExpiringReportHistory expiringReportHistory, ProblemsDatabaseService serverProblemsStatusIndex) {
        Predicate<? super IStatus>[] statusPredicates = decorateForDebug(
                // @formatter:off
                new AcceptProductPredicate(configuration),
                new EclipseBuildIdPresentPredicate(),
                new ErrorStatusOnlyPredicate(),
                new HistoryReadyPredicate(history),
                new IgnorePatternPredicate(configuration.getIgnoredPluginMessagesPatterns()),
                new ReporterNotDisabledPredicate(settings),
                new SkipReportsAbsentPredicate(),
                new WhitelistedPluginIdPresentPredicate(configuration),
                new WorkbenchRunningPredicate(PlatformUI.getWorkbench()));
                // @formatter:on
        Predicate<IStatus> statusFilters = Predicates.and(statusPredicates);

        Predicate<? super ErrorReport>[] reportPredicates = decorateForDebug(
                // @formatter:off
                new AcceptOtherPackagesPredicate(configuration),
                new AcceptUiFreezesPredicate(configuration),
                new CompleteUiFreezeReportPredicate(),
                new ProblemDatabaseIgnoredPredicate(serverProblemsStatusIndex, settings),
                new ReportsHistoryPredicate(expiringReportHistory, settings),
                new UnseenErrorReportPredicate(history, settings),
                new ValidSizeErrorReportPredicate(configuration));
                 // @formatter:on
        Predicate<ErrorReport> reportFilters = Predicates.and(reportPredicates);
        LogListener listener = new LogListener(statusFilters, reportFilters, settings, configuration, bus);
        return listener;
    }

    @SuppressWarnings("unchecked")
    private static <T> Predicate<? super T>[] decorateForDebug(Predicate<? super T>... components) {
        ILog log = Platform.getLog(FrameworkUtil.getBundle(LogListener.class));
        Predicate<? super T>[] predicates = new Predicate[components.length];
        for (int i = 0; i < components.length; i++) {
            predicates[i] = new PredicateDebugDecorator<>(components[i], log);
        }
        return predicates;
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
        insertErrorAnalyseComment(report);
        return report;
    }

    private void insertStandinStacktrace(final ErrorReport report) {
        stacktraceProvider.insertStandInStacktraceIfEmpty(report.getStatus(), configuration);
    }

}
