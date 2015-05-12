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
package org.eclipse.epp.internal.logging.aeri.ui.l10n;

import static org.eclipse.core.runtime.IStatus.*;

import org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.DefaultLogMessage;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public final class LogMessages extends DefaultLogMessage {

    private static final Bundle BUNDLE = FrameworkUtil.getBundle(LogMessages.class);
    // Unit tests should not fails because OSGI is not yet available:
    private static final String VERSION = "Version: " //$NON-NLS-1$
            + (BUNDLE == null ? "unit-tests" : BUNDLE.getVersion().toString());
    private static int code = 1;

    public static final LogMessages ERROR_FAILED_TO_PARSE_PREFERENCE_VALUE = new LogMessages(ERROR,
            Messages.LOG_ERROR_FAILED_TO_PARSE_PREFERENCE_VALUE);
    public static final LogMessages ERROR_SAVE_PREFERENCES_FAILED = new LogMessages(ERROR, Messages.LOG_ERROR_SAVE_PREFERENCES_FAILED);
    public static final LogMessages INFO_PAUSE_PERIOD_ELAPSED = new LogMessages(INFO, Messages.LOG_INFO_PAUSE_PERIOD_ELAPSED);
    public static final LogMessages INFO_SERVER_NOT_AVAILABLE = new LogMessages(INFO, Messages.LOG_INFO_SERVER_NOT_AVAILABLE);
    public static final LogMessages WARN_CONFIGURATION_TIMED_OUT = new LogMessages(WARNING, Messages.LOG_WARN_CONFIGURATION_TIMED_OUT);
    public static final LogMessages WARN_FAILED_TO_FETCH_PROBLEM_DB_ETAG = new LogMessages(WARNING,
            Messages.LOG_WARN_FAILED_TO_FETCH_PROBLEM_DB_ETAG);
    public static final LogMessages WARN_FAILED_TO_PARSE_REMEMBER_SEND_MODE = new LogMessages(WARNING,
            Messages.LOG_WARN_FAILED_TO_PARSE_REMEMBER_SEND_MODE);
    public static final LogMessages WARN_FAILED_TO_PARSE_SEND_MODE = new LogMessages(WARNING, Messages.LOG_WARN_FAILED_TO_PARSE_SEND_MODE);
    public static final LogMessages WARN_FIRST_CONFIGURATION_FAILED = new LogMessages(WARNING,
            Messages.LOG_WARN_FIRST_CONFIGURATION_FAILED);
    public static final LogMessages WARN_HISTORY_NO_FINGERPRINT = new LogMessages(WARNING, Messages.LOG_WARN_HISTORY_NO_FINGERPRINT);
    public static final LogMessages WARN_HISTORY_NOT_AVAILABLE = new LogMessages(WARNING, Messages.LOG_WARN_HISTORY_NOT_AVAILABLE);
    public static final LogMessages WARN_HISTORY_START_FAILED = new LogMessages(WARNING, Messages.LOG_WARN_HISTORY_START_FAILED);
    public static final LogMessages WARN_HISTORY_STOP_FAILED = new LogMessages(WARNING, Messages.LOG_WARN_HISTORY_STOP_FAILED);
    public static final LogMessages WARN_ILLEGAL_STATE_NO_DISPLAY = new LogMessages(WARNING, Messages.LOG_WARN_ILLEGAL_STATE_NO_DISPLAY);
    public static final LogMessages WARN_INDEX_START_FAILED = new LogMessages(WARNING, Messages.LOG_WARN_INDEX_START_FAILED);
    public static final LogMessages WARN_INDEX_STOP_FAILED = new LogMessages(WARNING, Messages.LOG_WARN_INDEX_STOP_FAILED);
    public static final LogMessages WARN_INDEX_UPDATE_FAILED = new LogMessages(WARNING, Messages.LOG_WARN_INDEX_UPDATE_FAILED);
    public static final LogMessages WARN_NO_INTERNET = new LogMessages(WARNING, Messages.LOG_WARN_NO_INTERNET);
    public static final LogMessages WARN_NOTIFICATION_TIMED_OUT = new LogMessages(WARNING, Messages.LOG_WARN_NOTIFICATION_TIMED_OUT);
    public static final LogMessages WARN_REFLECTION_FAILED = new LogMessages(WARNING, Messages.LOG_WARN_REFLECTION_FAILED);
    public static final LogMessages WARN_REPORTING_ERROR = new LogMessages(WARNING, Messages.LOG_WARN_REPORTING_ERROR);
    public static final LogMessages WARN_SERVER_AVAILABILITY_CHECK_FAILED = new LogMessages(WARNING,
            Messages.LOG_WARN_SERVER_AVAILABILITY_CHECK_FAILED);
    public static final LogMessages WARN_STATUS_INDEX_NOT_AVAILABLE = new LogMessages(WARNING,
            Messages.LOG_WARN_STATUS_INDEX_NOT_AVAILABLE);
    public static final LogMessages WARN_THANK_YOU_DIALOG_ERROR = new LogMessages(WARNING, Messages.LOG_WARN_THANK_YOU_DIALOG_ERROR);
    public static final LogMessages WARN_STACKTRACE_WITH_NULL = new LogMessages(WARNING, Messages.LOG_WARN_STACKTRACE_WITH_NULL);
    public static final LogMessages WARN_CYCLIC_EXCEPTION = new LogMessages(WARNING, Messages.LOG_WARN_CYCLIC_EXCEPTION);
    public static final LogMessages WARN_UPLOADJOB_BAD_RESPONSE = new LogMessages(WARNING, Messages.LOG_WARN_UPLOADJOB_BAD_RESPONSE);

    public LogMessages(int severity, String message) {
        super(severity, code++, String.format("%s %s", message, VERSION)); //$NON-NLS-1$
    }

    @Override
    public Bundle bundle() {
        return BUNDLE;
    }
}
