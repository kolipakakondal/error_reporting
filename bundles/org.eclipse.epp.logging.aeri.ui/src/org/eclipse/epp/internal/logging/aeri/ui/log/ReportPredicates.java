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

import org.eclipse.epp.internal.logging.aeri.ui.ExpiringReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.model.Status;

import com.google.common.base.Predicate;

public class ReportPredicates {

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
            return
            // did we send a similar error before?
            !(history.seenSimilar(report) ||
            // did we send exactly this error before?
            history.seen(report));
        }
    }

    public static class CompleteErrorReportPredicate implements Predicate<ErrorReport> {

        @Override
        public boolean apply(ErrorReport report) {
            Status status = report.getStatus();
            return equal("org.eclipse.ui.monitoring", status.getPluginId()) ? !status.getChildren().isEmpty() : true;
        }
    }

}
