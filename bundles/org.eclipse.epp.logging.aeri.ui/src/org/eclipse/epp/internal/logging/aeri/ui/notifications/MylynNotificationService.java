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
package org.eclipse.epp.internal.logging.aeri.ui.notifications;

import static com.google.common.collect.Lists.newArrayList;

import org.eclipse.epp.internal.logging.aeri.ui.INotificationService;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse;
import org.eclipse.mylyn.commons.notifications.ui.NotificationsUi;

import com.google.common.eventbus.EventBus;

@SuppressWarnings({ "restriction" })
public class MylynNotificationService implements INotificationService {

    private EventBus bus;

    public MylynNotificationService(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public void showWelcomeNotification() {
        ConfigureNotification notification = new ConfigureNotification(bus);
        NotificationsUi.getService().notify(newArrayList(notification));
    }

    @Override
    public void showNewReportsAvailableNotification(final ErrorReport e) {
        NotificationsUi.getService().notify(newArrayList(new NewErrorNotification(e, bus)));
    }

    @Override
    public void showNewResponseNotification(ServerResponse state) {
        NotificationsUi.getService().notify(newArrayList(new ServerResponseNotification(state, bus)));
    }

}
