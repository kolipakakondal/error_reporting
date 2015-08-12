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
package org.eclipse.epp.internal.logging.aeri.ui.notifications;

import static org.eclipse.epp.internal.logging.aeri.ui.model.Reports.toShortClassName;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportNotificationTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportShowDetailsRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.SendReportsRequest;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.Throwable;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

public class NewErrorNotification extends Notification {

    private final ErrorReport report;

    public NewErrorNotification(ErrorReport report, EventBus bus) {
        super(Constants.NOTIFY_REPORT, bus);
        this.report = report;
    }

    @Override
    public String getDescription() {
        StringBuilder b = new StringBuilder();
        Throwable exception = report.getStatus().getException();
        if (exception != null) {

            String message = toShortClassName(exception.getClassName()) + ": " + Optional.fromNullable(exception.getMessage()).or("");
            b.append(StringUtils.abbreviate(message, 120));
        }
        b.append("\n\nDo you want to report this error?");
        return b.toString();
    }

    @Override
    public String getLabel() {
        return "Message: " + report.getStatus().getMessage();
    }

    public ErrorReport getReport() {
        return report;
    }

    @Override
    public List<NotificationAction> getActions() {
        NotificationAction a1 = new NotificationAction("View Details") {

            @Override
            public void execute() {
                closeWithEvent(new NewReportShowDetailsRequest(report));
            }
        };
        NotificationAction a2 = new NotificationAction("Send") {

            @Override
            public void execute() {
                closeWithEvent(new SendReportsRequest());
            }
        };
        return Lists.newArrayList(a1, a2);
    }

    @Override
    public String getTitle() {
        return "Error logged in " + getReport().getStatus().getPluginId();
    }

    @Override
    public void unhandled() {
        closeWithEvent(new NewReportNotificationTimedOut(report));
    }
}
