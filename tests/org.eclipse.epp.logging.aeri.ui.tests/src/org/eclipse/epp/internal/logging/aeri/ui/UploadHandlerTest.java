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
package org.eclipse.epp.internal.logging.aeri.ui;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.v2.AeriServer;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

public class UploadHandlerTest {

    protected class LocalUploadHandler extends UploadHandler {
        private LocalUploadHandler(Settings settings, ServerConfiguration configuration, AeriServer server, ReportHistory history,
                EventBus bus) {
            super(settings, configuration, server, history, bus);
        }

        @Override
        protected void scheduleJob() {
            // dummy for UploadJob
            this.scheduledJob = new Job("dummy") {

                @Override
                protected IStatus run(IProgressMonitor monitor) {
                    return Status.OK_STATUS;
                }
            };
            this.scheduledJob.schedule();
        }
    }

    private UploadHandler sut;
    private Settings settings;
    private ServerConfiguration configuration;
    private AeriServer server;
    private ReportHistory history;
    private EventBus bus;

    @Before
    public void setUp() {
        settings = mock(Settings.class);
        configuration = mock(ServerConfiguration.class);
        server = mock(AeriServer.class);
        history = mock(ReportHistory.class);
        bus = mock(EventBus.class);
        sut = spy(new LocalUploadHandler(settings, configuration, server, history, bus));
    }

    @Test
    public void testScheduleJobOnFirstReports() {
        ArrayList<ErrorReport> list = Lists.newArrayList(mock(ErrorReport.class));
        sut.scheduleForSending(list);
        verify(sut, times(1)).scheduleForSending(list);
    }

    @Test
    public void testOnlyOneJobForUpload() {
        ArrayList<ErrorReport> list = Lists.newArrayList(mock(ErrorReport.class));
        sut.scheduleForSending(list);
        sut.scheduleForSending(Lists.newArrayList(mock(ErrorReport.class)));
        verify(sut, times(1)).scheduleJob();
    }

    @Test
    public void testRestartUploadJobIfDone() {
        ArrayList<ErrorReport> list = Lists.newArrayList(mock(ErrorReport.class));
        sut.scheduleForSending(list);
        sut.done(mock(IJobChangeEvent.class));
        sut.scheduleForSending(Lists.newArrayList(mock(ErrorReport.class)));
        verify(sut, times(2)).scheduleJob();
    }

}
