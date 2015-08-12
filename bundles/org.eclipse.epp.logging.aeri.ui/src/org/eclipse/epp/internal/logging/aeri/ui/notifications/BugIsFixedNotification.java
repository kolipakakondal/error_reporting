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

import static org.apache.commons.lang3.StringUtils.abbreviate;

import java.util.List;

import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.Events;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportShowDetailsRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.OpenUrlInBrowserRequest;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

public class BugIsFixedNotification extends Notification {

    private static Image IMG = AbstractUIPlugin.imageDescriptorFromPlugin(Constants.PLUGIN_ID, "icons/obj16/database_refresh.png")
            .createImage();

    private ProblemStatus state;
    private ErrorReport report;

    public BugIsFixedNotification(ErrorReport report, ProblemStatus state, EventBus bus) {
        super(Constants.NOTIFY_BUG_FIXED, bus);
        this.state = state;
        this.report = report;
    }

    @Override
    public Image getNotificationImage() {
        return IMG;
    }

    @Override
    public String getTitle() {
        return "You hit an already fixed issue. Time to update?";
    }

    @Override
    public String getLabel() {
        return String.format("'%s' was marked as FIXED.", abbreviate(report.getStatus().getMessage(), 60));
    }

    @Override
    public String getDescription() {
        String message = state.getMessage().or("Please visit Eclipse Bugzilla for details.");
        return abbreviate(message, 200);
    }

    @Override
    public List<NotificationAction> getActions() {
        List<NotificationAction> actions = Lists.newArrayList();

        NotificationAction a1 = new NotificationAction("View Details") {
            @Override
            public void execute() {
                closeWithEvent(new NewReportShowDetailsRequest(report));
            }
        };
        actions.add(a1);

        if (state.hasBugId()) {
            NotificationAction a = new NotificationAction("Visit #" + state.getBugId()) {

                @Override
                public void execute() {
                    closeWithEvent(new OpenUrlInBrowserRequest(state.getBugUrl().get()));
                }
            };
            actions.add(a);
        }
        return actions;
    }

    @Override
    public void unhandled() {
        closeWithEvent(new Events.BugIsFixedNotificationTimedOut());
    }
}
