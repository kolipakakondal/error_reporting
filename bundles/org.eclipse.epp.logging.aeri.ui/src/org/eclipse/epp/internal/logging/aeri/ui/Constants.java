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

import java.util.Set;

import org.eclipse.epp.internal.logging.aeri.ui.log.LogListener;
import org.eclipse.epp.internal.logging.aeri.ui.log.StandInStacktraceProvider;

import com.google.common.collect.ImmutableSet;

public final class Constants {

    private Constants() {
        throw new IllegalStateException("Not meant to be instantiated"); //$NON-NLS-1$
    }

    public static final String PLUGIN_ID = "org.eclipse.epp.logging.aeri.ui";
    public static final String AERI_NAMESPACE = "org.eclipse.epp.logging.aeri";

    public static final String PREF_PAGE_ID = "org.eclipse.epp.logging.aeri.ui.preferencePages.preference";

    /**
     * Specifying '-Dorg.eclipse.epp.logging.aeri.rcp.skipReports=true' as vmarg in eclipse launch configurations lets the log listener skip
     * automated error reporting.
     */
    public static final String SYSPROP_SKIP_REPORTS = PLUGIN_ID + ".skipReports";
    public static final String SYSPROP_ECLIPSE_BUILD_ID = "eclipse.buildId";

    public static final String PROVISION_EXCEPTION = "org.eclipse.equinox.p2.core.ProvisionException";

    // values for notifications
    public static final String NOTIFY_CONFIGURATION = AERI_NAMESPACE + ".notifications.event.configure";
    public static final String NOTIFY_REPORT = AERI_NAMESPACE + ".notifications.event.newReport";
    public static final String NOTIFY_UPLOAD = AERI_NAMESPACE + ".notifications.event.response";
    public static final String NOTIFY_MORE_INFO = AERI_NAMESPACE + ".notifications.event.moreinfo";
    public static final String NOTIFY_NEED_INFO = AERI_NAMESPACE + ".notifications.event.needinfo";
    public static final String NOTIFY_BUG_FIXED = AERI_NAMESPACE + ".notifications.event.fixed";

    public static final String HELP_URL = "https://dev.eclipse.org/recommenders/community/confess/";
    public static final String FEEDBACK_FORM_URL = "https://docs.google.com/a/codetrails.com/forms/d/1wd9AzydLv_TMa7ZBXHO7zQIhZjZCJRNMed-6J4fVNsc/viewform";

    // Cache
    public static final int PREVIOUS_ERROR_CACHE_MAXIMUM_SIZE = 30;
    public static final int PREVIOUS_ERROR_CACHE_EXPIRE_AFTER_ACCESS_MINUTES = 10;

    // Whitelist for sending
    public static final String WHITELISTED_PLUGINS = "org.eclipse.;org.apache.log4j;com.codetrails;";
    public static final String WHITELISTED_PACKAGES = "org.eclipse.;;;org.apache.;java.;javax.;javafx.;sun.;com.sun.;com.codetrails.;org.osgi.;com.google.;ch.qos.;org.slf4j.;";

    // Classes removed from top of stand-in-stacktrace
    public static final Set<String> STAND_IN_STACKTRACE_BLACKLIST = ImmutableSet.of("java.security.AccessController",
            "org.eclipse.core.internal.runtime.Log", "org.eclipse.core.internal.runtime.RuntimeLog",
            "org.eclipse.core.internal.runtime.PlatformLogWriter", "org.eclipse.osgi.internal.log.ExtendedLogReaderServiceFactory",
            "org.eclipse.osgi.internal.log.ExtendedLogReaderServiceFactory$3", "org.eclipse.osgi.internal.log.ExtendedLogServiceFactory",
            "org.eclipse.osgi.internal.log.ExtendedLogServiceImpl", "org.eclipse.osgi.internal.log.LoggerImpl",
            StandInStacktraceProvider.class.getName(), LogListener.class.getName());

    // values for anonymization
    public static final String HIDDEN = "HIDDEN";
    public static final String SOURCE_BEGIN_MESSAGE = "----------------------------------- SOURCE BEGIN -------------------------------------";
    public static final String SOURCE_FILE_REMOVED = "source file contents removed";

    public static final String F_VERSION = "version";
    public static final String VERSION = "0.6";
    public static final String SERVER_PROBLEMS_SERVICE_INDEX_DIR = "remote-index";
    public static final String PROBLEMS_STATUS_INDEX_ZIP_URL = "https://dev.eclipse.org/recommenders/community/confess/problems.zip";
    public static final String MISSING = "MISSING";

}
