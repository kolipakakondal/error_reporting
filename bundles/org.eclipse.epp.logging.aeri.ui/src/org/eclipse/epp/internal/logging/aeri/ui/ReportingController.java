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
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;
import static org.eclipse.epp.internal.logging.aeri.ui.utils.Shells.isUIThread;
import static org.eclipse.jface.window.Window.*;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.property.Properties;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.Events.BugIsFixedInfo;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureDialogCanceled;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureDialogCompleted;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigurePopupDisableRequested;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureRequestTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ConfigureShowDialogRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NeedInfoRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportLogged;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportNotificationSkipped;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportNotificationTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportShowDetailsRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportShowNotificationRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.OpenUrlInBrowserRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.SendReportsRequest;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseNotificationTimedOut;
import org.eclipse.epp.internal.logging.aeri.ui.Events.ServerResponseShowRequest;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.Messages;
import org.eclipse.epp.internal.logging.aeri.ui.log.ProblemsDatabaseService;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Browsers;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Jobs;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Shells;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class ReportingController {

    private static final long NOTIFICATION_IDLE_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(30);
    private static final long CONFIGURATION_PROCESS_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(5);
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
    private ProblemsDatabaseService problemsDb;

    public ReportingController(EventBus bus, Settings settings, INotificationService notifications,
            ReportHistory history, ProblemsDatabaseService problemsDb) {
        this.bus = bus;
        this.settings = settings;
        this.notifications = notifications;
        this.history = history;
        this.problemsDb = problemsDb;
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

        addToQueue(report);

        if (!isConfigured()) {
            if (!isConfigureInProgress()) {
                requestShowConfigureDialog();
            }
            // we can't do anything else yet, wait for configuration
            return;
        }

        ProblemStatus status = problemsDb.seen(report).orNull();
        if (status != null) {
            switch (status.getAction()) {
            case NEEDINFO:
                requestShowNeedInfoRequest(report, status);
                // TODO if this popu dialog fades out, what should we do?
                // handling this case is not very clean yet
                // we also do not store that this problem was seen, right?
                // needs intensive unit tests...
                return;
            case FIXED:
                requestShowFixedInfo(report, status);
                // TODO if this popu dialog fades out, what should we do?
                // handling this case is not very clean yet
                // we also do not store that this problem was seen, right?
                // needs intensive unit tests...
                return;
            default:
                return;
            }
        }

        if (isSentSilently()) {
            requestSendSilently();
        } else if (!isNotificationInProgress()) {
            requestShowNewReportNotification(report);
        } else {
            // if something is going on already, don't notify
        }
    }

    private void requestShowNeedInfoRequest(ErrorReport report, ProblemStatus seen) {
        bus.post(new NeedInfoRequest(report, seen));
    }

    private void requestShowFixedInfo(ErrorReport report, ProblemStatus status) {
        bus.post(new Events.BugIsFixedInfo(report, status));
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
            Optional<Display> display = Shells.getDisplay();
            if (display.isPresent()) {
                display.get().asyncExec(run);
            } else {
                log(LogMessages.ILLEGAL_STATE_NO_DISPLAY);
            }
        }
    }

    private boolean isConfigured() {
        return settings.isConfigured();
    }

    private boolean isConfigureInProgress() {
        // after some timeout we assume that there is no
        // configuration in progress. E.g., if Mylyn notifications as disabled,
        // we'll never know...
        if (isConfigurationProcessTimedOut()) {
            resetConfigureRequestTimeout();
            setConfigureInProgress(false);
            log(LogMessages.CONFIGURATION_TIMED_OUT);
        }
        return configureInProgress;
    }

    private boolean isConfigurationProcessTimedOut() {
        return System.currentTimeMillis() > configureInProgressTimeout;
    }

    private long resetConfigureRequestTimeout() {
        return configureInProgressTimeout = Long.MAX_VALUE;
    }

    private void setConfigureInProgress(boolean configureInProgress) {
        this.configureInProgress = configureInProgress;
    }

    private void requestShowConfigureDialog() {
        setConfigureInProgress(true);
        configureInProgressTimeout = System.currentTimeMillis() + CONFIGURATION_PROCESS_TIMEOUT_MS;
        notifications.showWelcomeNotification();
    }

    private void requestShowNewReportNotification(ErrorReport e) {
        setNotificationInProgress(true);
        setNotificationInProgressTimeout();
        notifications.showNewReportsAvailableNotification(e);
    }

    private boolean isNotificationInProgress() {
        if (isNotificationTimedOut()) {
            resetNotificationInProgress();
            setNotificationInProgress(false);
            log(LogMessages.NOTIFICATION_TIMED_OUT);
        }
        return notificationInProgress;
    }

    private boolean isNotificationTimedOut() {
        return System.currentTimeMillis() > notificationInProgressTimeout;
    }

    private void setNotificationInProgress(boolean newValue) {
        notificationInProgress = newValue;
    }

    private void setNotificationInProgressTimeout() {
        notificationInProgressTimeout = System.currentTimeMillis() + NOTIFICATION_IDLE_TIMEOUT_MS;
    }

    private long resetNotificationInProgress() {
        return notificationInProgressTimeout = Long.MAX_VALUE;
    }

    @VisibleForTesting
    public void scheduleForSending(final List<ErrorReport> reports) {
        final URI target = URI.create(settings.getServerUrl());
        new Job("Schedule Reports for sending") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {
                monitor.beginTask("Scheduling...", reports.size());
                List<Job> jobs = Lists.newLinkedList();
                for (ErrorReport report : reports) {
                    history.remember(report);
                    UploadJob job = new UploadJob(report, settings, target, bus);
                    jobs.add(job);
                    monitor.worked(1);
                    if (monitor.isCanceled()) {
                        break;
                    }
                }
                monitor.done();
                Jobs.sequential(format(Messages.UPLOADJOB_NAME, target), jobs);
                return Status.OK_STATUS;
            }
        }.schedule();
    }

    @Subscribe
    public void on(ConfigureShowDialogRequest e) {
        Shell shell = Shells.getWorkbenchWindowShell().orNull();
        ConfigurationDialog dialog = new ConfigurationDialog(shell, settings);
        dialog.setBlockOnOpen(true);
        int status = dialog.open();
        switch (status) {
        case Window.OK: {
            bus.post(new ConfigureDialogCompleted(SendAction.ASK));
            break;
        }
        case Window.CANCEL: {
            bus.post(new ConfigureDialogCompleted(SendAction.IGNORE));
            break;
        }
        case ConfigurationDialog.ESC_CANCEL: {
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
        settings.setAction(e.selectedAction);
        settings.setConfigured(true);
        setConfigureInProgress(false);
        if (e.selectedAction == SendAction.IGNORE) {
            // the user disabled the system
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
        settings.setAction(SendAction.IGNORE);
        settings.setRememberSendAction(RememberSendAction.RESTART);
        settings.setConfigured(false);
        setConfigureInProgress(false);
    }

    @Subscribe
    public void on(ConfigurePopupDisableRequested e) {
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
    public void on(final NewReportShowDetailsRequest e) {
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
                    for (Object report : queueUI) {
                        bus.post(new NewReportNotificationSkipped((ErrorReport) report));
                    }
                    break;
                }
                case ESC_CANCEL: {
                    // TODO: better behaviour than review the configuration on
                    // the next event?
                    settings.setConfigured(false);
                    for (Object report : queueUI) {
                        bus.post(new NewReportNotificationSkipped((ErrorReport) report));
                    }
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
    public void on(NeedInfoRequest e) {
        setNotificationInProgress(true);
        setNotificationInProgressTimeout();
        notifications.showNeedInfoNotification(e.report, e.status);
    }

    @Subscribe
    public void on(BugIsFixedInfo e) {
        setNotificationInProgress(true);
        setNotificationInProgressTimeout();
        notifications.showBugFixedInfo(e.report, e.status);
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
    public void on(OpenUrlInBrowserRequest e) {
        setNotificationInProgress(false);
        if (!isEmpty(e.url)) {
            Browsers.openInExternalBrowser(e.url);
        }
    }

}
