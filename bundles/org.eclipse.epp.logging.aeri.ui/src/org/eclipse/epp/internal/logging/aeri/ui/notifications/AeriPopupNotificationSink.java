/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Daniel Haftstein - initial API and implementation based on the work 
 *                       in the Eclipse Mylyn project.
 */
package org.eclipse.epp.internal.logging.aeri.ui.notifications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Shells;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.commons.notifications.core.AbstractNotification;
import org.eclipse.mylyn.commons.notifications.core.NotificationSink;
import org.eclipse.mylyn.commons.notifications.core.NotificationSinkEvent;
import org.eclipse.mylyn.internal.commons.notifications.ui.popup.PopupNotificationSink;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;

@SuppressWarnings("restriction")
public class AeriPopupNotificationSink extends NotificationSink {

    private static final long DELAY_OPEN = 1 * 1000;

    private static final boolean RUN_AS_SYSTEM_JOB = true;

    private final WeakHashMap<Object, Object> cancelledTokens = new WeakHashMap<Object, Object>();

    private final Set<AeriNotification> notifications = new HashSet<AeriNotification>();

    private final Set<AeriNotification> currentlyNotifying = Collections.synchronizedSet(notifications);

    private AeriPopupNotification popup;

    private final Job openJob = new Job("") {
        @Override
        protected IStatus run(IProgressMonitor monitor) {
            Display display = Shells.getDisplay().orNull();
            if (display == null || !Platform.isRunning()) {
                return Status.CANCEL_STATUS;
            }
            try {
                display.asyncExec(new Runnable() {

                    @Override
                    public void run() {
                        // TODO needs cleanup
                        if (popup != null && popup.getReturnCode() == Window.CANCEL) {
                            AeriNotification notification = popup.getNotification();
                            if (notification.getToken() != null) {
                                cancelledTokens.put(notification.getToken(), null);
                            }
                        }
                        for (Iterator<AeriNotification> it = currentlyNotifying.iterator(); it.hasNext();) {
                            AeriNotification notification = it.next();
                            if (notification.getToken() != null && cancelledTokens.containsKey(notification.getToken())) {
                                it.remove();
                            }
                        }
                        synchronized (PopupNotificationSink.class) {
                            if (currentlyNotifying.size() > 0) {
                                showPopup();
                            }
                        }
                    }
                });
            } finally {
                if (popup != null) {
                    schedule(popup.getDelayClose() / 2);
                }
            }

            if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
            }

            return Status.OK_STATUS;
        }
    };

    public AeriPopupNotificationSink() {
        openJob.setSystem(RUN_AS_SYSTEM_JOB);
    }

    private void cleanNotified() {
        currentlyNotifying.clear();
    }

    public boolean isAnimationsEnabled() {
        IPreferenceStore store = PlatformUI.getPreferenceStore();
        return store.getBoolean(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS);
    }

    @Override
    public void notify(NotificationSinkEvent event) {
        // TODO how to handle multiple events? (maybe: accumulate all in one
        // message?)
        for (AbstractNotification notification : event.getNotifications()) {
            if (notification instanceof AeriNotification) {
                currentlyNotifying.add((AeriNotification) notification);
            }
        }

        if (!openJob.cancel()) {
            try {
                openJob.join();
            } catch (InterruptedException e) {
                // ignore
            }
        }
        openJob.schedule(DELAY_OPEN);
    }

    public void showPopup() {
        if (popup != null) {
            popup.close();
        }

        Shell shell = new Shell(PlatformUI.getWorkbench().getDisplay());
        popup = new AeriPopupNotification(shell.getDisplay());
        popup.setFadingEnabled(isAnimationsEnabled());
        List<AeriNotification> toDisplay = new ArrayList<AeriNotification>(currentlyNotifying);
        Collections.sort(toDisplay);
        // TODO handle all
        AeriNotification notification = toDisplay.get(0);
        popup.setNotification(notification);
        notification.setPopup(popup);
        cleanNotified();
        popup.setBlockOnOpen(false);
        popup.open();
    }
}
