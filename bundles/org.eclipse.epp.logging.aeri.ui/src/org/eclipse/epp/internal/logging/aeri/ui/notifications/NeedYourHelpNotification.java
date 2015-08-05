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
import org.eclipse.epp.internal.logging.aeri.ui.Events.NeedInfoRequestdTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportShowDetailsRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.OpenUrlInBrowserRequest;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

public class NeedYourHelpNotification extends Notification {

    private static Image IMG = AbstractUIPlugin.imageDescriptorFromPlugin(Constants.PLUGIN_ID, "icons/obj16/database_error.png")
            .createImage();

    private ProblemStatus state;
    private ErrorReport report;

    public NeedYourHelpNotification(ErrorReport report, ProblemStatus state, EventBus bus) {
        super(Constants.NOTIFY_NEED_INFO, bus);
        this.state = state;
        this.report = report;
    }

    @Override
    public Image getNotificationImage() {
        return IMG;
    }

    @Override
    public String getTitle() {
        return "You hit a known issue in Eclipse.";
    }

    @Override
    public String getLabel() {
        return String.format("'%s' appears to be hard to reproduce. Please help us fixing it by providing further details.",
                abbreviate(report.getStatus().getMessage(), 60));
    }

    @Override
    public String getDescription() {
        return abbreviate(state.getMessage().orNull(), 200);
    }

    @Override
    public List<NoficationAction> getActions() {
        List<NoficationAction> actions = Lists.newArrayList();

        NoficationAction a1 = new NoficationAction("View Details") {
            @Override
            public void execute() {
                closeWithEvent(new NewReportShowDetailsRequest(report));
            }
        };
        actions.add(a1);

        if (state.hasBugId()) {
            NoficationAction a = new NoficationAction("Visit #" + state.getBugId()) {

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
        closeWithEvent(new NeedInfoRequestdTimedOut());
    }
}
