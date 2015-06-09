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

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.List;

import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.Events.OpenUrlInBrowserRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseNotificationTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

public class ServerResponseNotification extends Notification {

    private static final int MAX_REPORT_TITLE_CHARACTER_LENGTH = 40;

    private ServerResponse state;

    public ServerResponseNotification(ServerResponse state, EventBus bus) {
        this(Constants.NOTIFY_UPLOAD, state, bus);
    }

    public ServerResponseNotification(String eventId, ServerResponse state, EventBus bus) {
        super(eventId, bus);
        this.state = state;
    }

    @Override
    public String getTitle() {
        String reportTitle = abbreviate(stripToEmpty(state.getReportTitle()), MAX_REPORT_TITLE_CHARACTER_LENGTH);
        switch (state.getResolution()) {
        case FIXED:
            return format("Fixed: {0}", reportTitle);
        case NEEDINFO:
            return format("Your assistance is requested: {0}", reportTitle);
        case NEW:
        case UNCONFIRMED:
            return format("To be confirmed: {0}", reportTitle);
        case WONTFIX:
            return format("Minor issue: {0}", reportTitle);
        case INVALID:
            return format("Log message: {0}", reportTitle);
        default:
            return state.getResolution().name();
        }
    }

    @Override
    public String getLabel() {
        switch (state.getResolution()) {
        case FIXED:
            return format("Your issue appears to be fixed.");
        case INVALID:
            return format("Your report has been marked as a log message.");
        case NEEDINFO:
            return format("Your issue is known but further information is required.");
        case UNCONFIRMED:
        case NEW:
            return format("Your report is now recorded.");
        case WONTFIX:
            return format("Your report has been marked as a minor issue.");
        default:
            return "";

        }
    }

    @Override
    public String getDescription() {
        StringBuilder text = new StringBuilder();
        switch (state.getResolution()) {
        case FIXED: {
            if (state.getBugUrl().isPresent()) {
                text.append("Please visit the bug report for further details.");
            }
            break;
        }
        case NEEDINFO: {
            if (state.getCommitterMessage().isPresent()) {
                return state.getCommitterMessage().get();
            }
            text.append("Please visit the incident and see if you can provide additional information.");
            break;
        }
        case UNCONFIRMED:
        case NEW: {
            text.append(
                    format("It's not yet confirmed as a bug. " + "Please visit your report and see if you can provide more information.",
                            state.getReportTitle()));
            break;
        }
        case INVALID:
        case WONTFIX: {
            text.append("If you think your report is actually an error, please visit your report and leave a comment.");
            break;
        }
        default: {
            break;
        }
        }

        if (state.getCommitterMessage().isPresent()) {
            text.append(format("\nCommitter message: {0}", state.getCommitterMessage().get()));
        }

        text.append("\n\nThank you for your help!");
        return text.toString();
    }

    @Override
    public List<NoficationAction> getActions() {
        List<NoficationAction> actions = Lists.newArrayList();
        if (state.getBugId().isPresent()) {
            NoficationAction a = new NoficationAction("Visit #" + state.getBugId().get()) {

                @Override
                public void execute() {
                    fireEvent(new OpenUrlInBrowserRequest(state.getBugUrl().orNull()));
                }
            };
            actions.add(a);
        }
        NoficationAction a2 = new NoficationAction("Visit Submission") {

            @Override
            public void execute() {
                fireEvent(new OpenUrlInBrowserRequest(state.getIncidentUrl()));
            }
        };
        actions.add(a2);
        return actions;
    }

    @Override
    public void unhandled() {
        fireEvent(new ServerResponseNotificationTimedOut());
    }

}
