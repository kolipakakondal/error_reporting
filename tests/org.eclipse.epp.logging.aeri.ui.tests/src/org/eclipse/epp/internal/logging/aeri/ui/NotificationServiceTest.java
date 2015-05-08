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

import static org.hamcrest.CoreMatchers.isA;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureRequestTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportNotificationTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseNotificationTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus.RequiredAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse.ProblemResolution;
import org.eclipse.epp.internal.logging.aeri.ui.notifications.MylynNotificationService;
import org.eclipse.epp.internal.logging.aeri.ui.utils.TestReports;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class NotificationServiceTest {

    private EventBus bus;
    private MylynNotificationService notifications;
    private BlockingQueue<Object> queue = new LinkedBlockingQueue<>();

    @Before
    public void setup() {
        bus = new EventBus();
        notifications = new MylynNotificationService(bus);
        bus.register(this);
    }

    @Test
    public void testWelcome() throws InterruptedException {
        notifications.showWelcomeNotification();
        ConfigureRequestTimedOut event = (ConfigureRequestTimedOut) queue.poll(20, TimeUnit.SECONDS);
        Assert.assertThat(event, isA(ConfigureRequestTimedOut.class));

    }

    @Test
    public void testNewReport() throws InterruptedException {
        notifications.showNewReportsAvailableNotification(TestReports.createTestReport());
        NewReportNotificationTimedOut event = (NewReportNotificationTimedOut) queue.poll(20, TimeUnit.SECONDS);
        Assert.assertThat(event, isA(NewReportNotificationTimedOut.class));
    }

    @Test
    public void testResponseFixed() throws InterruptedException {
        ServerResponse result = createUploadResult();
        notifications.showNewResponseNotification(result);
        ServerResponseNotificationTimedOut event = (ServerResponseNotificationTimedOut) queue.poll(20, TimeUnit.SECONDS);
        Assert.assertThat(event, isA(ServerResponseNotificationTimedOut.class));
    }

    @Test
    public void testResponseInvalid() throws InterruptedException {
        ServerResponse result = createUploadResult();
        result.setResolution(ProblemResolution.INVALID);
        notifications.showNewResponseNotification(result);
        queue.poll(20, TimeUnit.SECONDS);
    }

    @Test
    public void testResponseNeedInfo() throws InterruptedException {
        ServerResponse result = createUploadResult();
        result.setResolution(ProblemResolution.NEEDINFO);
        notifications.showNewResponseNotification(result);
        queue.poll(20, TimeUnit.SECONDS);
    }

    @Test
    public void testResponseNew() throws InterruptedException {
        ServerResponse result = createUploadResult();
        result.setResolution(ProblemResolution.NEW);
        notifications.showNewResponseNotification(result);
        queue.poll(20, TimeUnit.SECONDS);
    }

    @Test
    public void testResponseUnconfirmed() throws InterruptedException {
        ServerResponse result = createUploadResult();
        result.setResolution(ProblemResolution.UNCONFIRMED);
        notifications.showNewResponseNotification(result);
        queue.poll(20, TimeUnit.SECONDS);
    }

    @Test
    public void testResponseWontfix() throws InterruptedException {
        ServerResponse result = createUploadResult();
        result.setResolution(ProblemResolution.WONTFIX);
        notifications.showNewResponseNotification(result);
        queue.poll(20, TimeUnit.SECONDS);
    }

    @Test
    public void testNeedInfo() throws InterruptedException {
        ProblemStatus status = new ProblemStatus();
        status.setAction(RequiredAction.NEEDINFO);
        notifications.showNeedInfoNotification(TestReports.createTestReport(), status);
        queue.poll(20, TimeUnit.SECONDS);
    }

    @Test
    public void testBugFixed() throws InterruptedException {
        ProblemStatus status = new ProblemStatus();
        status.setAction(RequiredAction.FIXED);
        notifications.showBugFixedInfo(TestReports.createTestReport(), status);
        queue.poll(20, TimeUnit.SECONDS);
    }

    private ServerResponse createUploadResult() {
        ServerResponse result = new ServerResponse();
        result.setCommitterMessage(
                "The problem you submitted looks like a bug but needs steps to reproduce it reliably. Please help us spotting the issue and provide further information in bug 460231. Thank you!");
        result.setBugId(460231);
        result.setBugUrl("http://bugzilla.org/");
        result.setIncidentId("0cafebabe0cafebabe0cafebabe0cafebabe");
        result.setIncidentUrl("http://incident.com");
        result.setReportTitle("Report title");
        result.setResolution(ProblemResolution.FIXED);
        return result;
    }

    @Subscribe
    public void on(Object o) {
        queue.add(o);
    }
}
