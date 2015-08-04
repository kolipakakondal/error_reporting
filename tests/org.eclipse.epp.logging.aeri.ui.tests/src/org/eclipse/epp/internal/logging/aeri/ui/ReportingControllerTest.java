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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.eclipse.epp.internal.logging.aeri.ui.Events.BugIsFixedInfo;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureDialogCanceled;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureDialogCompleted;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigurePopupDisableRequested;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureRequestTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NeedInfoRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportLogged;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportShowNotificationRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.SendReportsRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseShowRequest;
import org.eclipse.epp.internal.logging.aeri.ui.log.ProblemsDatabaseService;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelFactory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus.RequiredAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.notifications.MylynNotificationService;
import org.eclipse.epp.internal.logging.aeri.ui.v2.AeriServer;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import com.google.common.base.Optional;
import com.google.common.eventbus.EventBus;

public class ReportingControllerTest {

    protected class TestProblemsDatabaseService extends ProblemsDatabaseService {
        private TestProblemsDatabaseService(File indexDirectory) {
            super(indexDirectory);
        }

        @Override
        protected Directory createIndexDirectory() throws IOException {
            return new RAMDirectory();
        }
    }

    private EventBus bus;
    private Settings settings;
    private ServerConfiguration configuration;
    private AeriServer server;
    private MylynNotificationService notifications;
    private ReportingController sut;
    private ReportHistory history;
    private ProblemsDatabaseService problems;

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

        problems = spy(new TestProblemsDatabaseService(null));
        bus = spy(new EventBus());
        sut = spy(new ReportingController(bus, settings, configuration, server, notifications, history, problems));
        bus.register(sut);
        doNothing().when(sut).scheduleForSending(anyListOfReports());
    }

    @Test
    public void testUnconfiguredShowsConfigure() {
        settings.setConfigured(false);

        sut.on(newEvent());

        verify(notifications).showWelcomeNotification();
        verify(notifications, never()).showNewReportsAvailableNotification(anyReport());
    }

    @Test
    public void testUnconfiguredShowsConfigureOnce() {
        settings.setConfigured(false);

        sut.on(newEvent());
        sut.on(newEvent());
        sut.on(newEvent());

        verify(notifications, times(1)).showWelcomeNotification();
        verify(notifications, never()).showNewReportsAvailableNotification(anyReport());
    }

    @Test
    public void testConfigured() {
        settings.setConfigured(true);

        sut.on(newEvent());
        sut.on(newEvent());
        sut.on(newEvent());

        verify(notifications, never()).showWelcomeNotification();
        verify(notifications, times(1)).showNewReportsAvailableNotification(anyReport());
    }

    @Test
    public void testNeedInfoStatus() {
        settings.setConfigured(true);

        ErrorReport report = createTestReport();

        ProblemStatus status = new ProblemStatus();
        status.setAction(RequiredAction.NEEDINFO);
        when(problems.seen(report)).thenReturn(Optional.of(status));

        sut.on(new NewReportLogged(report));

        verify(bus).post(isA(NeedInfoRequest.class));
        verifyNoReportSendOrShown();
    }

    @Test
    public void testIgnoreStatus() {
        settings.setConfigured(true);

        ErrorReport report = createTestReport();

        ProblemStatus status = new ProblemStatus();
        status.setAction(RequiredAction.IGNORE);
        when(problems.seen(report)).thenReturn(Optional.of(status));

        sut.on(new NewReportLogged(report));

        verify(bus, never()).post(any());
        verifyNoReportSendOrShown();
    }

    @Test
    public void testFixedStatus() {
        settings.setConfigured(true);

        ErrorReport report = createTestReport();

        ProblemStatus status = new ProblemStatus();
        status.setAction(RequiredAction.FIXED);
        when(problems.seen(report)).thenReturn(Optional.of(status));

        sut.on(new NewReportLogged(report));

        verify(bus).post(isA(BugIsFixedInfo.class));
        verifyNoReportSendOrShown();
    }

    @Test
    public void testSendSilently() {
        settings.setConfigured(true);
        settings.setAction(SendAction.SILENT);

        sut.on(newEvent());

        verify(sut).scheduleForSending(anyListOfReports());
    }

    @Test
    public void testShowReportAfterConfiguration() {
        settings.setConfigured(false);

        sut.on(newEvent());

        // fake configuration event
        sut.on(new ConfigureDialogCompleted(SendAction.ASK));

        verify(sut).on(new NewReportShowNotificationRequest(anyReport()));
    }

    @Test
    public void testConfigurationCompletedUpdatesSettingsASK() {
        // inverse values
        settings.setConfigured(false);
        settings.setAction(SendAction.IGNORE);

        sut.on(new ConfigureDialogCompleted(SendAction.ASK));

        assertThat(settings.isConfigured(), is(true));
        assertThat(settings.getAction(), is(SendAction.ASK));
    }

    @Test
    public void testConfigurationCompletedUpdatesSettingsIGNORE() {
        // inverse values
        settings.setConfigured(false);
        settings.setAction(SendAction.ASK);

        sut.on(new ConfigureDialogCompleted(SendAction.IGNORE));

        assertThat(settings.isConfigured(), is(true));
        assertThat(settings.getAction(), is(SendAction.IGNORE));
    }

    @Test
    public void testConfigurationCancelUpdatesSettings() {
        // inverse values
        settings.setConfigured(true);
        settings.setAction(SendAction.ASK);
        settings.setRememberSendAction(RememberSendAction.NONE);

        sut.on(new ConfigureDialogCanceled());

        assertThat(settings.isConfigured(), is(false));
        assertThat(settings.getAction(), is(SendAction.IGNORE));
        assertThat(settings.getRememberSendAction(), is(RememberSendAction.RESTART));
    }

    @Test
    public void testDisableRequestUpdatesSettings() {
        // inverse values
        settings.setConfigured(false);
        settings.setAction(SendAction.ASK);

        sut.on(new ConfigurePopupDisableRequested());

        assertThat(settings.isConfigured(), is(true));
        assertThat(settings.getAction(), is(SendAction.IGNORE));
    }

    @Test
    public void testConfigureTimeoutNoSettingsChange() {
        // values should not change on timeout
        settings.setConfigured(false);
        settings.setAction(SendAction.ASK);

        sut.on(new ConfigureRequestTimedOut());

        assertThat(settings.isConfigured(), is(false));
        assertThat(settings.getAction(), is(SendAction.ASK));
    }

    @Test
    public void testShowOnlyOneServerResponseRequest() {
        sut.on(new ServerResponseShowRequest(mock(ServerResponse.class)));
        sut.on(new ServerResponseShowRequest(mock(ServerResponse.class)));

        verify(notifications).showNewResponseNotification(any(ServerResponse.class));
    }

    @Test
    public void testShowNeedInfoRequest() {
        sut.on(new NeedInfoRequest(mock(ErrorReport.class), mock(ProblemStatus.class)));

        verify(notifications).showNeedInfoNotification(any(ErrorReport.class), any(ProblemStatus.class));
    }

    private void verifyNoReportSendOrShown() {
        verify(bus, never()).post(isA(SendReportsRequest.class));
        verify(notifications, never()).showNewReportsAvailableNotification(anyReport());
    }

    private NewReportLogged newEvent() {
        return new NewReportLogged(createTestReport());
    }

    private ErrorReport anyReport() {
        return Matchers.any();
    }

    private List<ErrorReport> anyListOfReports() {
        return anyListOf(ErrorReport.class);
    }

}
