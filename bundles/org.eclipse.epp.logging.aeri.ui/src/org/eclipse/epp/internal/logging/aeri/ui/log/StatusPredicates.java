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

import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration.IgnorePattern;
import org.eclipse.ui.IWorkbench;

import com.google.common.base.Predicate;

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

        private ServerConfiguration configuration;

        public WhitelistedPluginIdPresentPredicate(ServerConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public boolean apply(IStatus input) {
            String pluginId = input.getPlugin();
            for (Pattern id : configuration.getAcceptedPluginsPatterns()) {
                if (id.matcher(pluginId).matches()) {
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

    public static class IgnorePatternPredicate implements Predicate<IStatus> {

        private List<IgnorePattern> patterns;

        public IgnorePatternPredicate(List<IgnorePattern> patterns) {
            this.patterns = patterns;
        }

        @Override
        public boolean apply(IStatus input) {
            for (IgnorePattern pattern : patterns) {
                if (pattern.matches(input)) {
                    return false;
                }
            }
            return true;
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

    public static class AcceptProductPredicate implements Predicate<IStatus> {

        private ServerConfiguration configuration;

        public AcceptProductPredicate(ServerConfiguration configuration) {
            this.configuration = configuration;
        }

        @Override
        public boolean apply(IStatus input) {
            String product = System.getProperty(SYSPROP_ECLIPSE_PRODUCT);
            return product == null ? false : isAccepted(product);
        }

        private boolean isAccepted(String product) {
            for (Pattern acceptedProductPattern : configuration.getAcceptedProductsPatterns()) {
                if (acceptedProductPattern.matcher(product).matches()) {
                    return true;
                }
            }
            return false;
        }

    }
}
