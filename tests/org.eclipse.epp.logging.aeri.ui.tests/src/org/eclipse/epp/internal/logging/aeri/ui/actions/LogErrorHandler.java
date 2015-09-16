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
package org.eclipse.epp.internal.logging.aeri.ui.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobFunction;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Shells;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.framework.FrameworkUtil;

import com.google.common.base.Throwables;

public class LogErrorHandler extends AbstractHandler {

    private ILog log = Platform.getLog(FrameworkUtil.getBundle(getClass()));

    @Override
    public Object execute(final ExecutionEvent event) throws ExecutionException {
        Job job = new Job("test exceptions") {

            @Override
            public IStatus run(IProgressMonitor monitor) {
                System.setProperty("eclipse.buildId", "unit-tests");
                String parameter = event.getParameter("org.eclipse.epp.logging.aeri.ui.tests.errorCommand.param");
                switch (Integer.valueOf(parameter)) {
                case 0: {
                    logError();
                    break;
                }
                case 1: {
                    logVerifyError();
                    break;
                }
                case 2: {
                    logMultiStatus();
                    break;
                }
                case 3: {
                    uiFreeze();
                    break;
                }
                case 4: {
                    logCoreExceptionError();
                    break;
                }
                case 5: {
                    Display.getDefault().asyncExec(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                testUiDeadlock(event);
                            } catch (ExecutionException e) {
                                Throwables.propagate(e);
                            }
                        }

                    });
                }
                default:
                    break;
                }
                return Status.OK_STATUS;
            }

        };
        job.schedule();
        return null;
    }

    private void logError() {
        Throwable t = new RuntimeException();
        t.fillInStackTrace();
        StackTraceElement[] stackTrace = t.getStackTrace();
        stackTrace[1] = new StackTraceElement("any.third.party.Clazz", "thirdPartyMethod", "Clazz.java", 42);
        t.setStackTrace(stackTrace);
        log.log(new Status(IStatus.ERROR, "org.eclipse.epp.logging.aeri", "Test Error", t));
    }

    private void logVerifyError() {
        log.log(new Status(IStatus.ERROR, "org.eclipse.epp.logging.aeri", "Test Error without exception", new VerifyError(
                "Bad type on operand stack Exception Details: Location: org/eclipse/recommenders/internal/snipmatch/rcp/EclipseGitSnippetRepository.push(Lorg/eclipse/jgit/lib/Repository;Lorg/eclipse/swt/widgets/Shell;)V @47: invokevirtual Reason: Type 'org/eclipse/egit/ui/internal/credentials/EGitCredentialsProvider' (current frame, stack[1]) is not assignable to 'org/eclipse/jgit/transport/CredentialsProvider' Current Frame: bci: @47 flags: { } locals: { 'org/eclipse/recommenders/internal/snipmatch/rcp/EclipseGitSnippetRepository', 'org/eclipse/jgit/lib/Repository', 'org/eclipse/swt/widgets/Shell', 'org/eclipse/jgit/transport/RemoteConfig', integer, 'org/eclipse/egit/core/op/PushOperation' } stack: { 'org/eclipse/egit/core/op/PushOperation', 'org/eclipse/egit/ui/internal/credentials/EGitCredentialsProvider' } Bytecode: 0x0000000: 2bb8 032b 4eb8 0316 b603 1713 0146 b903 0x0000010: 7f02 0036 04bb 016f 592b 2db6 0338 0315 0x0000020: 04b7 0315 3a05 1905 bb01 7359 b703 1db6 0x0000030: 0314 bb01 8459 2ab2 02ec 1905 b703 513a 0x0000040: 0619 062a b402 e2b6 030e 1906 bb01 8559 0x0000050: 2a2c b703 52b6 030d 1906 b603 0cb1")));
    }

    private void logMultiStatus() {
        int counter = 0;
        IStatus[] children = new IStatus[3];
        for (int i = 0; i < children.length; i++) {
            RuntimeException cause = new IllegalArgumentException("cause" + i);
            cause.setStackTrace(createTrace(3));
            Exception exception = new RuntimeException("exception message", cause);
            exception.setStackTrace(createTrace(3));
            children[i] = new Status(IStatus.ERROR, "org.eclipse.epp.logging.aeri", "status error message " + ++counter, exception);
        }
        log.log(new MultiStatus("org.eclipse.epp.logging.aeri", IStatus.ERROR, children, "status error message",
                new RuntimeException("Processing the user query failed due to an incompatible query string format.")));
    }

    private StackTraceElement[] createTrace(int k) {
        StackTraceElement[] trace = new StackTraceElement[k];
        for (int j = k; j-- > 0;) {
            trace[j] = new StackTraceElement("org.eclipse.M", "method" + j, "", 1);
        }
        return trace;
    }

    private void uiFreeze() {
        Shells.getDisplay().get().syncExec(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                }
            }
        });

    }

    private void logCoreExceptionError() {
        Status s = new Status(IStatus.ERROR, "org.eclipse.epp.logging.aeri", "Core-Exception inner status");
        log.log(new Status(IStatus.ERROR, "org.eclipse.epp.logging.aeri", "Core-Exception outer status", new CoreException(s)));
    }

    private void testUiDeadlock(ExecutionEvent event) throws ExecutionException {

        final Object lock = new Object();
        IWorkbenchWindow window;
        window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        MessageDialog.openInformation(window.getShell(), "TestLogingLock", "Test lock");
        Job longRunningJob = Job.create("Test Log running job", new IJobFunction() {

            @Override
            public IStatus run(IProgressMonitor monitor) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //
                }
                Platform.getLog(FrameworkUtil.getBundle(getClass()))
                        .log(new Status(IStatus.ERROR, "org.eclipse.epp.logging.aeri", "Test logging errors"));
                synchronized (lock) {
                    lock.notifyAll();
                }
                return Status.OK_STATUS;
            }
        });
        longRunningJob.schedule();
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                Platform.getLog(FrameworkUtil.getBundle(getClass())).log(Status.CANCEL_STATUS);
            }
        }
        MessageDialog.openInformation(window.getShell(), "TestLogingLock", "No lock.");
    }

}
