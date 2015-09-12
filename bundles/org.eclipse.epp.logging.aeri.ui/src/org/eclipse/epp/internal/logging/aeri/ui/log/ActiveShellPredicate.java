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
package org.eclipse.epp.internal.logging.aeri.ui.log;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Shells;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.google.common.base.Predicate;

public class ActiveShellPredicate implements Predicate<IStatus> {

    private static Shell activeShell;

    public ActiveShellPredicate() {
        Display display = Shells.getDisplay().orNull();
        if (display != null) {
            display.asyncExec(new ActiveShellTracker());
        }
    }

    @Override
    public boolean apply(IStatus input) {
        return activeShell != null;
    }

    private static final class ActiveShellTracker implements Runnable, Listener {
        @Override
        public void run() {
            IWorkbench workbench = PlatformUI.getWorkbench();

            Display display = workbench.getDisplay();
            display.addFilter(SWT.Deactivate, this);
            display.addFilter(SWT.Activate, this);

            IWorkbenchWindow activeWindow = workbench.getActiveWorkbenchWindow();
            if (activeWindow != null) {
                activeShell = activeWindow.getShell();
            }
        }

        @Override
        public void handleEvent(Event event) {
            switch (event.type) {
            case SWT.Activate:
                if (isShell(event)) {
                    activeShell = (Shell) event.widget;
                }
                break;
            case SWT.Deactivate:
                if (isShell(event)) {
                    activeShell = null;
                }
            default:
                break;
            }
        }

        private boolean isShell(Event event) {
            return event.widget instanceof Shell;
        }
    }
}
