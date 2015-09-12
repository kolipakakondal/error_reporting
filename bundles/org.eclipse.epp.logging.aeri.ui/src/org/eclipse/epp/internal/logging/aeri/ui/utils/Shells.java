/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Daniel Haftstein - initial API
 */
package org.eclipse.epp.internal.logging.aeri.ui.utils;

import static com.google.common.base.Optional.*;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.google.common.base.Optional;

public class Shells {

    public static Optional<Shell> getWorkbenchWindowShell() {
        IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (workbenchWindow != null) {
            Shell shell = workbenchWindow.getShell();
            if (shell != null) {
                return Optional.of(shell);
            }
        }
        return Optional.absent();
    }

    public static boolean isUIThread() {
        return Display.getCurrent() != null;
    }

    /**
     * Returns the Display of <code>PlatformUI.getWorkbench().getDisplay()</code>. If the workbench is closing or not created or the display
     * is disposed this will return {@link Optional#absent()}.
     */
    public static Optional<Display> getDisplay() {
        IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench == null || workbench.isClosing()) {
            return absent();
        }
        Display display = workbench.getDisplay();
        if (display == null || display.isDisposed()) {
            return absent();
        }
        return of(display);
    }

}
