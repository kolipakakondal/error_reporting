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
package org.eclipse.epp.internal.logging.aeri.ui.log;

import static org.eclipse.epp.internal.logging.aeri.ui.Constants.DEBUG;

import java.text.MessageFormat;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;

import com.google.common.base.Predicate;

class PredicateDebugDecorator<T> implements Predicate<T> {

    private Predicate<T> predicate;
    private ILog log;

    private static final int DEBUG_STATUS_SEVERITY = IStatus.INFO;
    private static final String DEBUG_STATUS_PLUGIN_ID = Constants.PLUGIN_ID;

    public PredicateDebugDecorator(Predicate<T> predicate, ILog log) {
        this.predicate = predicate;
        this.log = log;
    }

    @Override
    public boolean apply(T input) {
        boolean apply = predicate.apply(input);
        if (!apply && DEBUG) {
            if (input instanceof IStatus) {
                debugStatus((IStatus) input);
            } else if (input instanceof ErrorReport) {
                debugReport((ErrorReport) input);
            }
        }
        return apply;
    }

    private void debugStatus(IStatus inputStatus) {
        // avoid recursion with own debug status logging
        if (inputStatus.getSeverity() == DEBUG_STATUS_SEVERITY && inputStatus.getPlugin().equals(DEBUG_STATUS_PLUGIN_ID)) {
            return;
        }
        String message = MessageFormat.format("Debug: predicate {0} filtered status:\n {1}", predicate.getClass().getName(),
                inputStatus.toString());
        logDebugMessageStatus(message);
    }

    private void debugReport(ErrorReport report) {
        String message = MessageFormat.format("Debug: predicate {0} filtered report:\n {1}", predicate.getClass().getName(),
                report.getStatus().getMessage());
        logDebugMessageStatus(message);
    }

    private void logDebugMessageStatus(String message) {
        IStatus status = new Status(DEBUG_STATUS_SEVERITY, DEBUG_STATUS_PLUGIN_ID, message);
        log.log(status);
    }

    @Override
    public boolean equals(Object object) {
        return predicate.equals(object);
    }

    @Override
    public int hashCode() {
        return predicate.hashCode();
    }

    @Override
    public String toString() {
        return predicate.toString();
    }

}
