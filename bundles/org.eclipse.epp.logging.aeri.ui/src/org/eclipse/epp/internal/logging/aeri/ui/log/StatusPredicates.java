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
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.eclipse.epp.internal.logging.aeri.ui.Constants.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.ui.IWorkbench;

import com.google.common.base.Predicate;
import com.google.common.base.Throwables;

public class StatusPredicates {

    public static class ReporterNotDisabledPredicate implements Predicate<IStatus> {

        private Settings settings;

        public ReporterNotDisabledPredicate(Settings settings) {
            this.settings = settings;
        }

        @Override
        public boolean apply(IStatus input) {
            return !equal(SendAction.IGNORE, settings.getAction());
        }
    }

    public static class WhitelistedPluginIdPresentPredicate implements Predicate<IStatus> {

        private Settings settings;

        public WhitelistedPluginIdPresentPredicate(Settings settings) {
            this.settings = settings;
        }

        @Override
        public boolean apply(IStatus input) {
            String pluginId = input.getPlugin();
            for (String id : settings.getWhitelistedPluginIds()) {
                if (pluginId.startsWith(id)) {
                    return true;
                }
            }
            return false;
        }

    }

    public static class SkipReportsAbsentPredicate implements Predicate<IStatus> {

        @Override
        public boolean apply(IStatus input) {
            return !Boolean.getBoolean(SYSPROP_SKIP_REPORTS);
        }
    }

    public static class SkipProvisionExceptionsPredicate implements Predicate<IStatus> {

        @Override
        public boolean apply(IStatus input) {
            return !visit(input);
        }

        private boolean visit(IStatus input) {
            Throwable exception = input.getException();
            if (exception != null) {
                for (Throwable t : Throwables.getCausalChain(exception)) {
                    String type = t.getClass().getName();
                    if (equal(PROVISION_EXCEPTION, type)) {
                        return true;
                    }
                }
            }

            for (IStatus child : input.getChildren()) {
                if (visit(child)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class EclipseBuildIdPresentPredicate implements Predicate<IStatus> {

        @Override
        public boolean apply(IStatus input) {
            return !isEmpty(System.getProperty(SYSPROP_ECLIPSE_BUILD_ID));
        }
    }

    public static class ErrorStatusOnlyPredicate implements Predicate<IStatus> {

        @Override
        public boolean apply(IStatus input) {
            return input.matches(IStatus.ERROR);
        }
    }

    public static class WorkbenchRunningPredicate implements Predicate<IStatus> {

        private IWorkbench workbench;

        public WorkbenchRunningPredicate(IWorkbench workbench) {
            this.workbench = workbench;

        }

        @Override
        public boolean apply(IStatus input) {
            return !workbench.isClosing();
        }
    }

    public static class HistoryReadyPredicate implements Predicate<IStatus> {

        private ReportHistory history;

        public HistoryReadyPredicate(ReportHistory history) {
            this.history = history;

        }

        @Override
        public boolean apply(IStatus input) {
            return history.isRunning();
        }
    }
}
