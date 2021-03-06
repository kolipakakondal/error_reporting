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

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
    private static final String BUNDLE_NAME = "org.eclipse.epp.internal.logging.aeri.ui.l10n.messages"; //$NON-NLS-1$
    public static String CONFIGURATIONDIALOG_ACTION_TOOLTIP;
    public static String CONFIGURATIONDIALOG_ANONYMIZATION;
    public static String CONFIGURATIONDIALOG_DISABLE;
    public static String CONFIGURATIONDIALOG_ENABLE;
    public static String CONFIGURATIONDIALOG_INFO;
    public static String CONFIGURATIONDIALOG_PLEASE_TAKE_MOMENT_TO_CONFIGURE;
    public static String CONFIGURATIONDIALOG_PREFERENCE_PAGE_LINK;
    public static String CONFIGURATIONDIALOG_REPORTING_STARTED_FIRST_TIME;
    public static String ERRORREPORTWIZARD_WE_NOTICED_ERROR;
    public static String FIELD_LABEL_SERVER;
    public static String FIELD_LABEL_ACTION;
    public static String FIELD_LABEL_ACTION_REPORT_ASK;
    public static String FIELD_LABEL_ACTION_REPORT_NEVER;
    public static String FIELD_LABEL_ACTION_REPORT_ALWAYS;
    public static String FIELD_LABEL_ACTION_REPORT_PAUSE_DAY;
    public static String FIELD_LABEL_ACTION_REPORT_PAUSE_RESTART;
    public static String FIELD_LABEL_IGNORE_SIMILAR_ERRORS_IN_FUTURE;
    public static String FIELD_LABEL_NOT_AN_ERROR;
    public static String FIELD_LABEL_SKIP_SIMILAR_ERRORS;
    public static String FIELD_LABEL_ANONYMIZE_STACKTRACES;
    public static String FIELD_LABEL_ANONYMIZE_MESSAGES;
    public static String FIELD_LABEL_NAME;
    public static String FIELD_DESC_NAME;
    public static String FIELD_MESSAGE_NAME;
    public static String FIELD_LABEL_EMAIL;
    public static String FIELD_DESC_EMAIL;
    public static String FIELD_MESSAGE_EMAIL;
    public static String SETTINGSPAGE_TITLE;
    public static String SETTINGSPAGE_DESC;
    public static String SETTINGSPAGE_GROUPLABEL_PERSONAL;
    public static String LINK_LEARN_MORE;
    public static String LINK_PROVIDE_FEEDBACK;
    public static String PREFERENCEPAGE_ASK_LABEL;
    public static String PREVIEWPAGE_DESC;
    public static String PREVIEWPAGE_LABEL_COMMENT;
    public static String PREVIEWPAGE_LABEL_MESSAGE;
    public static String PREVIEWPAGE_TITLE;
    public static String THANKYOUDIALOG_COMMITTER_MESSAGE;
    public static String THANKYOUDIALOG_INVALID_SERVER_RESPONSE;
    public static String THANKYOUDIALOG_MARKED_DUPLICATE;
    public static String THANKYOUDIALOG_MARKED_FIXED;
    public static String THANKYOUDIALOG_MARKED_MOVED;
    public static String THANKYOUDIALOG_MARKED_NORMAL;
    public static String THANKYOUDIALOG_MARKED_UNKNOWN;
    public static String THANKYOUDIALOG_MATCHED_EXISTING_BUG;
    public static String THANKYOUDIALOG_COMMITTER_MESSAGE_EMPTY;
    public static String THANKYOUDIALOG_MARKED_WORKSFORME;
    public static String THANKYOUDIALOG_RECEIVED_AND_TRACKED;
    public static String THANKYOUDIALOG_RECEIVED_UNKNOWN_SERVER_RESPONSE;
    public static String THANKYOUDIALOG_THANK_YOU;
    public static String THANKYOUDIALOG_THANK_YOU_FOR_HELP;
    public static String THANKYOUDIALOG_NEW;
    public static String THANKYOUDIALOG_ADDITIONAL_INFORMATION;
    public static String TOOLTIP_MAKE_STACKTRACE_ANONYMOUS;
    public static String TOOLTIP_MAKE_MESSAGES_ANONYMOUS;
    public static String TOOLTIP_SKIP_SIMILAR;
    public static String TOOLTIP_IGNORE_SIMILAR_ERRORS_IN_FUTURE;
    public static String TOOLTIP_NOT_AN_ERROR;
    public static String UPLOADJOB_ALREADY_FIXED_UPDATE;
    public static String LOG_WARN_UPLOADJOB_BAD_RESPONSE;
    public static String UPLOADJOB_FAILED;
    public static String UPLOADJOB_NAME;
    public static String UPLOADJOB_NEED_FURTHER_INFORMATION;
    public static String UPLOADJOB_TASKNAME;
    public static String UPLOADJOB_THANK_YOU;
    public static String LOG_ERROR_FAILED_TO_PARSE_PREFERENCE_VALUE;
    public static String LOG_ERROR_SAVE_PREFERENCES_FAILED;
    public static String LOG_INFO_PAUSE_PERIOD_ELAPSED;
    public static String LOG_INFO_SERVER_NOT_AVAILABLE;
    public static String LOG_WARN_CONFIGURATION_DOWNLOAD_FAILED;
    public static String LOG_WARN_CONFIGURATION_TIMED_OUT;
    public static String LOG_WARN_FAILED_TO_FETCH_PROBLEM_DB_ETAG;
    public static String LOG_WARN_FAILED_TO_LOAD_DEFAULT_PREFERENCES;
    public static String LOG_WARN_FAILED_TO_PARSE_REMEMBER_SEND_MODE;
    public static String LOG_WARN_FAILED_TO_PARSE_SEND_MODE;
    public static String LOG_WARN_FIRST_CONFIGURATION_FAILED;
    public static String LOG_WARN_HISTORY_NO_FINGERPRINT;
    public static String LOG_WARN_HISTORY_NOT_AVAILABLE;
    public static String LOG_WARN_HISTORY_START_FAILED;
    public static String LOG_WARN_HISTORY_STOP_FAILED;
    public static String LOG_WARN_ILLEGAL_STATE_NO_DISPLAY;
    public static String LOG_WARN_INDEX_START_FAILED;
    public static String LOG_WARN_INDEX_STOP_FAILED;
    public static String LOG_WARN_INDEX_UPDATE_FAILED;
    public static String LOG_WARN_INVALID_PATTERN;
    public static String LOG_WARN_STACKTRACE_WITH_NULL;
    public static String LOG_WARN_NO_INTERNET;
    public static String LOG_WARN_NOTIFICATION_TIMED_OUT;
    public static String LOG_WARN_OPEN_BROSER_FAILED;
    public static String LOG_WARN_REFLECTION_FAILED;
    public static String LOG_WARN_REPORTING_ERROR;
    public static String LOG_WARN_SERVER_AVAILABILITY_CHECK_FAILED;
    public static String LOG_WARN_STATUS_INDEX_NOT_AVAILABLE;
    public static String LOG_WARN_STARTUP_FAILED;
    public static String LOG_WARN_THANK_YOU_DIALOG_ERROR;
    public static String LOG_WARN_CYCLIC_EXCEPTION;
    public static String LOG_ERROR_LISTENER_NULL;
    public static String LOG_ERROR_NETWORK_COMMUNICATION_URL_PARSING_FAILED;
    public static String LOG_ERROR_ON_PROXY_AUTHENTICATION_TEST;
    public static String LOG_ERROR_ON_APACHE_HEAD_REQUEST;
    public static String LOG_ERROR_ON_P2_HEAD_REQUEST;

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages() {
    }
}
