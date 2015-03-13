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

import static org.eclipse.epp.internal.logging.aeri.ui.model.Reports.computeFingerprintFor;

import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.model.Status;

public class StandInStacktraceProvider {

    public static class StandInException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public StandInException(String message) {
            super(message);
        }
    }

    private static final String STAND_IN_MESSAGE = "Stand-In Stacktrace supplied by the Eclipse Error Reporting";

    public void insertStandInStacktraceIfEmpty(final Status status, Settings settings) {
        if (requiresStandInStacktrace(status)) {
            Throwable syntetic = new StandInException(STAND_IN_MESSAGE);
            syntetic.fillInStackTrace();
            StackTraceElement[] stacktrace = syntetic.getStackTrace();
            StackTraceElement[] clearedStacktrace = clearBlacklistedTopStackframes(stacktrace, Constants.STAND_IN_STACKTRACE_BLACKLIST);
            syntetic.setStackTrace(clearedStacktrace);
            status.setException(Reports.newThrowable(syntetic));
            status.setFingerprint(computeFingerprintFor(status, settings));
        }
    }

    private boolean requiresStandInStacktrace(final Status status) {
        if (status.getException() != null) {
            return false;
        }
        return true;
    }

    public StackTraceElement[] clearBlacklistedTopStackframes(StackTraceElement[] stackframes, Set<String> blacklistedClassNames) {
        if (ArrayUtils.isEmpty(stackframes)) {
            return stackframes;
        }
        int index = findCutOffIndex(stackframes, blacklistedClassNames);
        return ArrayUtils.subarray(stackframes, index, stackframes.length);
    }

    private int findCutOffIndex(StackTraceElement[] stackframes, Set<String> blacklistedClassNames) {
        for (int i = 0; i < stackframes.length; i++) {
            StackTraceElement current = stackframes[i];
            if (!blacklistedClassNames.contains(current.getClassName())) {
                return i;
            }
        }
        return 0;
    }
}
