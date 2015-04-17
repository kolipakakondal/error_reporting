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

import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.REPORTING_ERROR;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;
import static org.eclipse.epp.internal.logging.aeri.ui.model.Reports.*;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportLogged;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;

import com.google.common.base.Predicate;
import com.google.common.eventbus.EventBus;

public class LogListener implements ILogListener {

    private StandInStacktraceProvider stacktraceProvider = new StandInStacktraceProvider();
    private Predicate<IStatus> statusFilters;
    private Predicate<ErrorReport> reportFilters;
    private EventBus bus;
    private Settings settings;

    public LogListener(Predicate<IStatus> statusFilters, Predicate<ErrorReport> reportFilters, Settings settings, EventBus bus) {
        this.statusFilters = statusFilters;
        this.reportFilters = reportFilters;
        this.settings = settings;
        this.bus = bus;
    }

    @Override
    public void logging(final IStatus status, String nouse) {
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
            log(REPORTING_ERROR, e);
        }
    }

    private ErrorReport createErrorReport(final IStatus status) {
        ErrorReport report = newErrorReport(status, settings);
        insertStandinStacktrace(report);
        guessInvolvedPlugins(report);
        return report;
    }

    private void insertStandinStacktrace(final ErrorReport report) {
        stacktraceProvider.insertStandInStacktraceIfEmpty(report.getStatus(), settings);
    }
}
