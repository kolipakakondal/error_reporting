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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.eclipse.epp.internal.logging.aeri.ui.Events;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Optional;
import com.google.common.eventbus.EventBus;

public class NotificationsTest {

    private EventBus bus;
    private ArgumentCaptor<Object> captor;

    @Before
    public void setUp() {
        bus = mock(EventBus.class);
        captor = ArgumentCaptor.forClass(Object.class);
    }

    @Test
    public void testBugIsFixedNotificationUnhandled() {
        Notification notification = new BugIsFixedNotification(mock(ErrorReport.class), mock(ProblemStatus.class), bus);
        notification.unhandled();

        verifyEventPosted(Events.BugIsFixedNotificationTimedOut.class);
    }

    @Test
    public void testBugIsFixedNotificationViewDetailsAction() {
        Notification notification = new BugIsFixedNotification(mock(ErrorReport.class), mock(ProblemStatus.class), bus);

        assertThat(notification.getActions().size(), is(1));
        notification.getActions().get(0).execute();
        verifyEventPosted(Events.NewReportShowDetailsRequest.class);
    }

    @Test
    public void testBugIsFixedNotificationVisitBugAction() {
        ProblemStatus status = mock(ProblemStatus.class);
        when(status.getBugId()).thenReturn(42);
        when(status.hasBugId()).thenReturn(true);
        Optional<String> url = Optional.of("mock-url");
        when(status.getBugUrl()).thenReturn(url);

        Notification notification = new BugIsFixedNotification(mock(ErrorReport.class), status, bus);

        assertThat(notification.getActions().size(), is(2));
        notification.getActions().get(1).execute();
        verifyEventPosted(Events.OpenUrlInBrowserRequest.class);
    }

    @Test
    public void testConfigureNotificationUnhandled() {
        Notification notification = new ConfigureNotification(bus);
        notification.unhandled();

        verifyEventPosted(Events.ConfigureRequestTimedOut.class);
    }

    @Test
    public void testConfigureNotificationEnableAction() {
        Notification notification = new ConfigureNotification(bus);
        NotificationAction action = notification.getActions().get(0);
        assertThat(action.getName(), is("Enable"));
        action.execute();

        verifyEventPosted(Events.ConfigureShowDialogRequest.class);
    }

    @Test
    public void testConfigureNotificationDisableAction() {
        Notification notification = new ConfigureNotification(bus);
        NotificationAction action = notification.getActions().get(1);
        assertThat(action.getName(), is("Disable"));
        action.execute();

        verifyEventPosted(Events.ConfigurePopupDisableRequested.class);
    }

    @Test
    public void testNeedYourHelpNotificationUnhandled() {
        Notification notification = new NeedYourHelpNotification(mock(ErrorReport.class), mock(ProblemStatus.class), bus);
        notification.unhandled();

        verifyEventPosted(Events.NeedInfoRequestdTimedOut.class);
    }

    @Test
    public void testNeedYourHelpNotificationViewDetailsAction() {
        Notification notification = new NeedYourHelpNotification(mock(ErrorReport.class), mock(ProblemStatus.class), bus);

        assertThat(notification.getActions().size(), is(1));
        notification.getActions().get(0).execute();
        verifyEventPosted(Events.NewReportShowDetailsRequest.class);
    }

    @Test
    public void testNeedYourHelpNotificationVisitBugAction() {
        ProblemStatus status = mock(ProblemStatus.class);
        when(status.getBugId()).thenReturn(42);
        when(status.hasBugId()).thenReturn(true);
        Optional<String> url = Optional.of("mock-url");
        when(status.getBugUrl()).thenReturn(url);

        Notification notification = new NeedYourHelpNotification(mock(ErrorReport.class), status, bus);

        assertThat(notification.getActions().size(), is(2));
        notification.getActions().get(1).execute();
        verifyEventPosted(Events.OpenUrlInBrowserRequest.class);
    }

    @Test
    public void testNewErrorNotificationUnhandled() {
        Notification notification = new NewErrorNotification(mock(ErrorReport.class), bus);
        notification.unhandled();

        verifyEventPosted(Events.NewReportNotificationTimedOut.class);
    }

    // TODO NewErrorNotification
    // TODO ServerResponseNotification

    private <T> void verifyEventPosted(Class<T> clazz) {
        verify(bus).post(captor.capture());
        assertTrue(clazz.isInstance(captor.getValue()));
    }

}
