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
package org.eclipse.epp.internal.logging.aeri.ui.log;

import static org.eclipse.epp.internal.logging.aeri.ui.utils.TestReports.createStacktraceForClasses;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelFactory;
import org.eclipse.epp.internal.logging.aeri.ui.model.Status;
import org.eclipse.epp.internal.logging.aeri.ui.utils.TestReports;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportHistoryTest {
    private static ModelFactory factory = ModelFactory.eINSTANCE;
    private ReportHistory sut;

    @Before
    public void setUp() throws Exception {
        sut = new ReportHistory() {
            @Override
            protected Directory createIndexDirectory() throws IOException {
                return new RAMDirectory();
            }
        };
        sut.startUp();
    }

    @After
    public void tearDown() throws Exception {
        sut.shutDown();
    }

    @Test
    public void testRememberEmptyReport() {
        ErrorReport report = factory.createErrorReport();
        sut.remember(report);
        assertThat(sut.seen(report), is(true));
    }

    @Test
    public void testBug458792() {
        ErrorReport report = factory.createErrorReport();
        Status status = factory.createStatus();
        report.setStatus(status);
        assertThat(sut.seen(report), is(false));
        sut.remember(report);
        assertThat(sut.seen(report), is(true));
        status.getChildren();
        assertThat(sut.seen(report), is(true));
    }

    @Test
    public void testRememberSameStacktrace() {
        ErrorReport report = factory.createErrorReport();
        Throwable throwable = new Throwable();
        throwable.setStackTrace(createStacktraceForClasses("any.Class1", "any.Class2"));
        Status status = TestReports.createStatus(IStatus.ERROR, "plugin.id", "a message", throwable);
        report.setStatus(status);
        sut.remember(report);

        ErrorReport report2 = factory.createErrorReport();
        Throwable throwable2 = new Throwable();
        throwable2.setStackTrace(createStacktraceForClasses("any.Class1", "any.Class2"));
        Status status2 = TestReports.createStatus(IStatus.ERROR, "plugin.id", "a message", throwable2);
        report2.setStatus(status2);

        assertThat(sut.seen(report2), is(true));
    }

    @Test
    public void testDifferentPlugin() {
        ErrorReport report = factory.createErrorReport();
        Throwable throwable = new Throwable();
        throwable.setStackTrace(createStacktraceForClasses("any.Class1", "any.Class2"));
        Status status = TestReports.createStatus(IStatus.ERROR, "plugin.id.1", "a message", throwable);
        report.setStatus(status);
        sut.remember(report);

        ErrorReport report2 = factory.createErrorReport();
        Throwable throwable2 = new Throwable();
        throwable2.setStackTrace(createStacktraceForClasses("any.Class1", "any.Class2"));
        Status status2 = TestReports.createStatus(IStatus.ERROR, "plugin.id.2", "a message", throwable2);
        report2.setStatus(status2);

        assertThat(sut.seen(report2), is(false));
    }

    @Test
    public void testDifferentMessage() {
        ErrorReport report = factory.createErrorReport();
        Throwable throwable = new Throwable("message-1");
        throwable.setStackTrace(createStacktraceForClasses("any.Class1", "any.Class2"));
        Status status = TestReports.createStatus(IStatus.ERROR, "plugin.id", "a message", throwable);
        report.setStatus(status);
        sut.remember(report);

        ErrorReport report2 = factory.createErrorReport();
        Throwable throwable2 = new Throwable("message-2");
        throwable2.setStackTrace(createStacktraceForClasses("any.Class1", "any.Class2"));
        Status status2 = TestReports.createStatus(IStatus.ERROR, "plugin.id", "a message", throwable2);
        report2.setStatus(status2);

        assertThat(sut.seen(report2), is(false));
    }

    @Test
    public void testDifferentStacktrace() {
        ErrorReport report = factory.createErrorReport();
        Throwable throwable = new Throwable("message");
        throwable.setStackTrace(createStacktraceForClasses("any.Class1", "any.Class2", "any.other.Class"));
        Status status = TestReports.createStatus(IStatus.ERROR, "plugin.id", "a message", throwable);
        report.setStatus(status);
        sut.remember(report);

        ErrorReport report2 = factory.createErrorReport();
        Throwable throwable2 = new Throwable("message");
        throwable2.setStackTrace(createStacktraceForClasses("any.Class1", "any.Class2"));
        Status status2 = TestReports.createStatus(IStatus.ERROR, "plugin.id", "a message", throwable2);
        report2.setStatus(status2);

        assertThat(sut.seen(report2), is(false));
    }

    @Test
    public void testRequestOnEmptyHistory() {
        ErrorReport report = factory.createErrorReport();
        assertThat(sut.seen(report), is(false));
    }
}
