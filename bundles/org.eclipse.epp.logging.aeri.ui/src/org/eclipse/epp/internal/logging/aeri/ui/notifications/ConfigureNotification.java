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

import java.util.List;

import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureDialogDisableRequested;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureRequestTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureShowDialogRequest;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

public class ConfigureNotification extends Notification {

    public ConfigureNotification(EventBus bus) {
        super(Constants.NOTIFY_CONFIGURATION, bus);
    }

    @Override
    public List<NoficationAction> getActions() {
        NoficationAction a1 = new NoficationAction("Enable") {

            @Override
            public void execute() {
                fireEvent(new ConfigureShowDialogRequest());
            }

        };
        NoficationAction a2 = new NoficationAction("Disable") {

            @Override
            public void execute() {
                fireEvent(new ConfigureDialogDisableRequested());
            }

        };
        return Lists.newArrayList(a1, a2);
    }

    @Override
    public void unhandled() {
        fireEvent(new ConfigureRequestTimedOut());
    }

    @Override
    public String getTitle() {
        return "Eclipse Error Reporting";
    }

    @Override
    public String getLabel() {
        return "Welcome to the Eclipse Mars Error Reporting Service.\nDo you want to help Eclipse?";
    }

    @Override
    public String getDescription() {
        return "With your permission Eclipse can inspect errors logged inside the IDE and inform project committers about the issues you experienced. Do you want to help and enable the error reporter?";
    }

}
