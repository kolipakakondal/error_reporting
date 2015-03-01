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

import static com.google.common.base.Objects.equal;
import static com.google.common.collect.ImmutableList.copyOf;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.eclipse.epp.internal.logging.aeri.ui.ConfigurationDialog.ESC_CANCEL;
import static org.eclipse.epp.internal.logging.aeri.ui.utils.Shells.isUIThread;
import static org.eclipse.jface.window.Window.*;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureDialogCanceled;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureDialogCompleted;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureDialogDisableRequested;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureRequestTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureShowDialogRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportLogged;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportNotificationSkipped;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportNotificationTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportShowDetailsRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportShowNotificationRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.SendReportsRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseNotificationTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseOpenBugzillaRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseOpenIncidentRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseShowRequest;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.Messages;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Browsers;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Jobs;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Shells;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class ReportingController {

    private IObservableList queueUI;
    // careful! do never make any modifications to this list! It's a means to
    // access the queued reports outside the UI
    // thread. TODO is there a better way?
    private LinkedList<ErrorReport> queueRO;

    private EventBus bus;
    private Settings settings;
    private INotificationService notifications;

    private boolean configureInProgress;
    private long configureInProgressTimeout = Long.MAX_VALUE;

    private boolean notificationInProgress;
    private long notificationInProgressTimeout = Long.MAX_VALUE;
    private ReportHistory history;

    public ReportingController(EventBus bus, Settings settings, INotificationService notifications,
            ReportHistory history) {
        this.bus = bus;
        this.settings = settings;
        this.notifications = notifications;
        this.history = history;
        initalizeLists();
    }

    protected void initalizeLists() {
        Display.getDefault().syncExec(new Runnable() {

            @Override
            public void run() {
                queueRO = Lists.newLinkedList();
                queueUI = Properties.selfList(ErrorReport.class).observe(queueRO);
            }
        });
    }

    @Subscribe
    public synchronized void on(NewReportLogged e) {
        ErrorReport report = e.report;
        if (isIgnoreReports()) {
            return;
        }
        if (isSentSilently()) {
            scheduleForSending(report);
            return;
        } else {
            addToQueue(report);
        }

        if (isConfigured()) {
            if (!isConfigureInProgress()) {
                requestShowConfigureDialog();
            }
            // we can't do anything else yet:
            return;
        } else {
            if (isSentSilently()) {
                requestSendSilently();
                return;
            } else if (!isNotificationInProgress()) {
                requestShowNewReportNotification(report);
            }
            // if something is going on already, don't notify
            return;
        }
    }

    private void requestSendSilently() {
        bus.post(new SendReportsRequest());
    }

    private boolean isSentSilently() {
        return equal(settings.getAction(), SendAction.SILENT);
    }

    private void addToQueue(final ErrorReport report) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                queueUI.add(report);
            }
        };
        runAsync(run);
    }

    private void clearIncoming() {
        Runnable run = new Runnable() {

            @Override
            public void run() {
                queueUI.clear();
            }
        };
        runAsync(run);
    }

    private void runAsync(Runnable run) {
        if (isUIThread()) {
            run.run();
        } else {
            Shells.getDisplay().orNull().asyncExec(run);
        }
    }

    private boolean isIgnoreReports() {
        return settings.getAction() == SendAction.IGNORE;
    }

    private boolean isConfigured() {
        return !settings.isConfigured();
    }

    private boolean isConfigureInProgress() {
        // after some timeout we assume that there is no
        // configuration in progress. E.g., if Mylyn notifications as disabled,
        // we'll never know...
        if (System.currentTimeMillis() > configureInProgressTimeout) {
            // TODO log that situation as WARNING
            resetConfigureRequestTimeout();
            setConfigureInProgress(false);
        }
        return configureInProgress;
    }

    private long resetConfigureRequestTimeout() {
        return configureInProgressTimeout = Long.MAX_VALUE;
    }

    private void setConfigureInProgress(boolean configureInProgress) {
        this.configureInProgress = configureInProgress;
    }

    private void requestShowConfigureDialog() {
        setConfigureInProgress(true);
        configureInProgressTimeout = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
        notifications.showWelcomeNotification();
    }

    private void requestShowNewReportNotification(ErrorReport e) {
        setNotificationInProgress(true);
        setNotificationInProgressTimeout();
        notifications.showNewReportsAvailableNotification(e);
    }

    private boolean isNotificationInProgress() {
        if (System.currentTimeMillis() > notificationInProgressTimeout) {
            // TODO log that situation as WARNING
            resetNotificationInProgress();
            setNotificationInProgress(false);
        }
        return notificationInProgress;
    }

    private void setNotificationInProgress(boolean newValue) {
        notificationInProgress = newValue;
    }

    private void setNotificationInProgressTimeout() {
        notificationInProgressTimeout = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30);
    }

    private long resetNotificationInProgress() {
        return notificationInProgressTimeout = Long.MAX_VALUE;
    }

    private void scheduleForSending(ErrorReport report) {
        scheduleForSending(Lists.newArrayList(report));
    }

    private void scheduleForSending(List<ErrorReport> reports) {
        URI target = URI.create(settings.getServerUrl());
        List<Job> jobs = Lists.newLinkedList();
        history.remember(reports);
        for (ErrorReport report : reports) {
            UploadJob job = new UploadJob(report, settings, target, bus);
            jobs.add(job);
            System.out.println("scheduled " + report.hashCode() + ": " + report.getStatus().getMessage());
        }
        Jobs.sequential(format(Messages.UPLOADJOB_NAME, target), jobs);
    }

    @Subscribe
    public void on(ConfigureShowDialogRequest e) {
        Shell shell = Shells.getWorkbenchWindowShell().orNull();
        ConfigurationDialog dialog = new ConfigurationDialog(shell, settings);
        dialog.setBlockOnOpen(true);
        int status = dialog.open();
        switch (status) {
        case Window.OK: {
            settings.setAction(SendAction.ASK);
            settings.setConfigured(true);
            bus.post(new ConfigureDialogCompleted());
            break;
        }
        case Window.CANCEL: {
            settings.setAction(SendAction.IGNORE);
            settings.setConfigured(true);
            bus.post(new ConfigureDialogCompleted());
            break;
        }
        case ConfigurationDialog.ESC_CANCEL: {
            settings.setAction(SendAction.IGNORE);
            settings.setRememberSendAction(RememberSendAction.RESTART);
            settings.setConfigured(false);
            bus.post(new ConfigureDialogCanceled());
            break;
        }
        default:
            // nothing
            bus.post(new ConfigureDialogCanceled());
        }
    }

    @Subscribe
    public void on(ConfigureDialogCompleted e) {
        setConfigureInProgress(false);
        if (isIgnoreReports()) {
            clearIncoming();
        } else {
            if (!queueRO.isEmpty()) {
                ErrorReport peek = queueRO.peek();
                bus.post(new NewReportShowNotificationRequest(peek));
            }
        }
    }

    @Subscribe
    public void on(ConfigureDialogCanceled e) {
        setConfigureInProgress(false);
    }

    @Subscribe
    public void on(ConfigureDialogDisableRequested e) {
        setConfigureInProgress(false);
        settings.setConfigured(true);
        settings.setAction(SendAction.IGNORE);
    }

    @Subscribe
    public void on(ConfigureRequestTimedOut e) {
        setConfigureInProgress(false);
        resetConfigureRequestTimeout();
    }

    // Report UI Events

    @Subscribe
    public void on(NewReportShowNotificationRequest e) {
        setNotificationInProgress(true);
        notifications.showNewReportsAvailableNotification(e.report);
    }

    @Subscribe
    public void on(NewReportShowDetailsRequest e) {
        // show
        Runnable run = new Runnable() {

            @Override
            public void run() {
                ReportDialog d = new ReportDialog(Shells.getWorkbenchWindowShell().orNull(), settings, queueUI, bus);
                d.setBlockOnOpen(true);
                int status = d.open();
                setNotificationInProgress(false);

                switch (status) {
                case OK: {
                    requestSendSilently();
                    break;
                }
                case CANCEL: {
                    bus.post(new NewReportNotificationSkipped());
                    break;
                }
                case ESC_CANCEL: {
                    // TODO: better behaviour than review the configuration on the next event?
                    settings.setConfigured(false);
                    bus.post(new NewReportNotificationSkipped());
                    break;
                }
                default:
                    // nothing
                }
            }
        };
        runAsync(run);
    }

    @Subscribe
    public void on(NewReportNotificationSkipped e) {
        setNotificationInProgress(false);
        history.remember(e.report);
        clearIncoming();
    }

    @Subscribe
    public void on(NewReportNotificationTimedOut e) {
        setNotificationInProgress(false);
        clearIncoming();
    }

    @Subscribe
    public void on(SendReportsRequest e) {
        ImmutableList<ErrorReport> tmp = copyOf(queueRO);
        clearIncoming();
        setNotificationInProgress(false);
        scheduleForSending(tmp);
    }

    @Subscribe
    public void on(ServerResponseShowRequest e) {
        if (isNotificationInProgress()) {
            return;
        }
        setNotificationInProgressTimeout();
        setNotificationInProgress(true);
        notifications.showNewResponseNotification(e.response);
    }

    @Subscribe
    public void on(ServerResponseNotificationTimedOut e) {
        setNotificationInProgress(false);
    }

    @Subscribe
    public void on(ServerResponseOpenBugzillaRequest e) {
        setNotificationInProgress(false);
        String url = e.state.getBugUrl().orNull();
        if (!isEmpty(url)) {
            Browsers.openInExternalBrowser(url);
        }
    }

    @Subscribe
    public void on(ServerResponseOpenIncidentRequest e) {
        setNotificationInProgress(false);
        String url = e.state.getIncidentUrl();
        if (!isEmpty(url)) {
            Browsers.openInExternalBrowser(url);
        }
    }
}
