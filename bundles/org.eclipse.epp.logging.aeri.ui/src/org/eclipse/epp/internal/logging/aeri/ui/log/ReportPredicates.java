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
package org.eclipse.epp.internal.logging.aeri.ui.log;

import static com.google.common.base.Objects.equal;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.WARN_HISTORY_NOT_AVAILABLE;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;

import org.eclipse.epp.internal.logging.aeri.ui.ExpiringReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.model.Bundle;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus.RequiredAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement;
import org.eclipse.epp.internal.logging.aeri.ui.model.Status;
import org.eclipse.epp.internal.logging.aeri.ui.model.Throwable;
import org.eclipse.epp.internal.logging.aeri.ui.model.util.ModelSwitch;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

public class ReportPredicates {

    public static class ProblemDatabaseIgnoredPredicate implements Predicate<ErrorReport> {

        private ProblemsDatabaseService index;
        private Settings settings;

        public ProblemDatabaseIgnoredPredicate(ProblemsDatabaseService index, Settings settings) {
            this.index = index;
            this.settings = settings;
        }

        @Override
        public boolean apply(ErrorReport report) {
            if (!settings.isSkipSimilarErrors()) {
                return true;
            }
            Optional<ProblemStatus> status = index.seen(report);
            if (status.isPresent()) {
                if (status.get().getAction() == RequiredAction.IGNORE) {
                    return false;
                }
            }
            return true;
        }
    }

    public static class ReportsHistoryPredicate implements Predicate<ErrorReport> {

        private ExpiringReportHistory history;
        private Settings settings;

        public ReportsHistoryPredicate(ExpiringReportHistory history, Settings settings) {
            this.history = history;
            this.settings = settings;
        }

        @Override
        public boolean apply(ErrorReport report) {
            if (!settings.isSkipSimilarErrors()) {
                return true;
            }
            boolean seen =
            // exact identity
            history.seenExact(report) ||
                    // similar trace
                    history.seenSimilarTrace(report);
            if (!seen) {
                history.add(report);
            }
            return !seen;
        }

    }

    public static class UnseenErrorReportPredicate implements Predicate<ErrorReport> {

        private ReportHistory history;
        private Settings settings;

        public UnseenErrorReportPredicate(ReportHistory history, Settings settings) {
            this.history = history;
            this.settings = settings;
        }

        @Override
        public boolean apply(ErrorReport report) {
            if (!settings.isSkipSimilarErrors()) {
                return true;
            }
            if (!history.isRunning()) {
                log(WARN_HISTORY_NOT_AVAILABLE);
                return true;
            }

            return
            // did we send a similar error before?
            !(history.seenSimilar(report) ||
                    // did we send exactly this error before?
                    history.seen(report));
        }
    }

    public static class CompleteUiFreezeReportPredicate implements Predicate<ErrorReport> {

        @Override
        public boolean apply(ErrorReport report) {
            Status status = report.getStatus();
            return isUiMonitoring(status) ? !status.getChildren().isEmpty() : true;
        }

    }

    public static class AcceptUiFreezesPredicate implements Predicate<ErrorReport> {

        private ServerConfiguration configuration;

        public AcceptUiFreezesPredicate(ServerConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public boolean apply(ErrorReport report) {
            if (!configuration.isAcceptUiFreezes() && isUiMonitoring(report.getStatus())) {
                return false;
            }
            return true;
        }

    }

    public static class AcceptOtherPackagesPredicate implements Predicate<ErrorReport> {

        private ServerConfiguration configuration;

        public AcceptOtherPackagesPredicate(ServerConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public boolean apply(ErrorReport report) {
            if (!configuration.isAcceptOtherPackages() && Reports.containsNotWhitelistedClasses(report, configuration)) {
                return false;
            }
            return true;
        }

    }

    private static boolean isUiMonitoring(Status status) {
        return equal("org.eclipse.ui.monitoring", status.getPluginId());
    }

    public static class ValidSizeErrorReportPredicate implements Predicate<ErrorReport> {

        private static final class EstimateSizeVisitor extends ModelSwitch<Object> {

            int estimatedCharacters = 0;

            @Override
            public Object caseStatus(Status status) {
                if (status != null) {
                    estimatedCharacters += status.getPluginId().length();
                    estimatedCharacters += status.getMessage().length();
                }
                return null;
            }

            @Override
            public Object caseThrowable(Throwable throwable) {
                if (throwable.getMessage() != null) {
                    estimatedCharacters += throwable.getMessage().length();
                }
                return null;
            }

            @Override
            public Object caseStackTraceElement(StackTraceElement element) {
                estimatedCharacters += element.getClassName().length();
                estimatedCharacters += element.getFileName().length();
                estimatedCharacters += element.getMethodName().length();
                return null;
            }

            @Override
            public Object caseBundle(Bundle bundle) {
                estimatedCharacters += bundle.getName().length();
                estimatedCharacters += bundle.getVersion().length();
                return null;
            }
        }

        private int maxReportByteSize;

        public ValidSizeErrorReportPredicate(int maxReportByteSize) {
            this.maxReportByteSize = maxReportByteSize;
        }

        @Override
        public boolean apply(ErrorReport report) {
            // in case of very large reports (~50+ MB), toJson or String.getBytes may be too expensive
            EstimateSizeVisitor visitor = new EstimateSizeVisitor();
            Reports.visit(report, visitor);
            // assume 16 bit chars
            int estimatedByteLength = visitor.estimatedCharacters * 2;
            return estimatedByteLength < maxReportByteSize;
        }

    }
}
