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

import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse;

public class Events {
    public static class NewReportLogged {

        public final ErrorReport report;

        public NewReportLogged(ErrorReport report) {
            this.report = report;
        }

    }

    public static class ConfigureShowDialogRequest {
    }

    public static class ConfigureDialogCompleted {

        public final SendAction selectedAction;

        public ConfigureDialogCompleted(SendAction selectedAction) {
            this.selectedAction = selectedAction;
        }
    }

    public static class ConfigureDialogCanceled {
    }

    public static class ConfigurePopupDisableRequested {
    }

    public static class ConfigureRequestTimedOut {
    }

    public static class NewReportShowNotificationRequest {

        public final ErrorReport report;

        public NewReportShowNotificationRequest(ErrorReport report) {
            this.report = report;
        }
    }

    /**
     * Fired when the user explicitly dismissed the details dialog w/o sending the report.
     */
    public static class NewReportNotificationSkipped {

        public final ErrorReport report;

        public NewReportNotificationSkipped(ErrorReport report) {
            this.report = report;
        }
    }

    /**
     * Fired when the popup notification closed w/o any action (no matter it was dismissed by pressing on the x or timed out)
     */
    public static class NewReportNotificationTimedOut {

        public final ErrorReport report;

        public NewReportNotificationTimedOut(ErrorReport report) {
            this.report = report;
        }
    }

    public static class NewReportShowDetailsRequest {

        public final ErrorReport report;

        public NewReportShowDetailsRequest(ErrorReport report) {
            this.report = report;
        }
    }

    public static class SendReportsRequest {
    }

    public static class ServerResponseShowRequest {

        public final ServerResponse response;

        public ServerResponseShowRequest(ServerResponse response) {
            this.response = response;
        }
    }

    public static class ServerResponseNotificationTimedOut {

    }

    public static class NeedInfoRequest {

        public final ErrorReport report;
        public final ProblemStatus status;

        public NeedInfoRequest(ErrorReport report, ProblemStatus status) {
            this.report = report;
            this.status = status;
        }
    }

    public static class BugIsFixedInfo {

        public final ErrorReport report;
        public final ProblemStatus status;

        public BugIsFixedInfo(ErrorReport report, ProblemStatus status) {
            this.report = report;
            this.status = status;
        }
    }

    public static class NeedInfoRequestdTimedOut {

    }

    public static class OpenUrlInBrowserRequest {

        public String url;

        public OpenUrlInBrowserRequest(String url) {
            this.url = url;
        }

    }
}
