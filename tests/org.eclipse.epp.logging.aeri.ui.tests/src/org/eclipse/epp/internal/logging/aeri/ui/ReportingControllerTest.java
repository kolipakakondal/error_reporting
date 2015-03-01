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

import static org.eclipse.epp.internal.logging.aeri.ui.utils.TestReports.createTestReport;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportLogged;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelFactory;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.notifications.MylynNotificationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import com.google.common.eventbus.EventBus;

public class ReportingControllerTest {

    private EventBus bus;
    private Settings settings;
    private MylynNotificationService notifications;
    private ReportingController sut;
    private ReportHistory history;

    @Before
    public void setup() {
        notifications = mock(MylynNotificationService.class);
        settings = ModelFactory.eINSTANCE.createSettings();
        history = new ReportHistory() {
            @Override
            protected Directory createIndexDirectory() throws IOException {
                return new RAMDirectory();
            }
        };
        bus = new EventBus();
        sut = new ReportingController(bus, settings, notifications, history);
    }

    @Test
    public void testUnconfiguredShowsConfigure() {
        settings.setConfigured(false);
        //
        sut.on(newEvent());
        //
        verify(notifications).showWelcomeNotification();
        verify(notifications, never()).showNewReportsAvailableNotification(anyReport());
    }

    private ErrorReport anyReport() {
        return Matchers.any();
    }

    @Test
    public void testUnconfiguredShowsConfigureOnce() {
        settings.setConfigured(false);
        //
        sut.on(newEvent());
        sut.on(newEvent());
        sut.on(newEvent());
        //
        verify(notifications, times(1)).showWelcomeNotification();
        verify(notifications, never()).showNewReportsAvailableNotification(anyReport());
    }

    @Test
    public void testConfigured() {
        settings.setConfigured(true);
        //
        sut.on(newEvent());
        sut.on(newEvent());
        sut.on(newEvent());
        //
        verify(notifications, never()).showWelcomeNotification();
        verify(notifications, times(1)).showNewReportsAvailableNotification(anyReport());
    }

    private NewReportLogged newEvent() {
        return new NewReportLogged(createTestReport());
    }

}
