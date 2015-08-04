/**
 * Copyright (c) 2014 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Daniel Haftstein - initial tests.
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.emf.ecore.util.EcoreUtil.getAllContents;
import static org.eclipse.epp.internal.logging.aeri.ui.model.Reports.*;
import static org.eclipse.epp.internal.logging.aeri.ui.utils.TestReports.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports.AnonymizeStacktraceVisitor;
import org.eclipse.epp.internal.logging.aeri.ui.model.util.ModelSwitch;
import org.eclipse.epp.internal.logging.aeri.ui.utils.TestReports;
import org.eclipse.epp.internal.logging.aeri.ui.utils.WildcardRegexConverter;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportsTest {
    private static final List<Pattern> PREFIX_WHITELIST = WildcardRegexConverter
            .convert(Arrays.asList("sun.*", "java.*", "javax.*", "org.eclipse.*"));

    private static final String WHITELISTED_CLASSNAME = "java.lang.RuntimeException";
    private static final String NOT_WHITELISTED_CLASSNAME = "foo.bar.FooBarException";

    private static final String WHITELISTED_CLASSNAME_2 = "java.lang.String";
    private static final String WHITELISTED_METHODNAME_2 = "trim";

    private static final String NOT_WHITELISTED_CLASSNAME_2 = "foo.bar.AnyClass";
    private static final String NOT_WHITELISTED_METHODNAME_2 = "foo";

    private static String ANONYMIZED_TAG = "HIDDEN";

    private Settings settings;
    private ServerConfiguration configuration;

    @Before
    public void before() {
        settings = ModelFactory.eINSTANCE.createSettings();
        exCounter = 1;
        stCounter = 1;
        configuration = new ServerConfiguration();
        configuration.setAcceptedPackages(newArrayList("org.*"));
    }

    @Test
    public void testClearEventMessage() {
        ErrorReport event = createTestReport();

        Reports.clearMessages(event);

        assertThat(event.getStatus().getMessage(), is(ANONYMIZED_TAG));
    }

    @Test
    public void testDeclaringClassToNull()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        java.lang.Throwable some = new java.lang.Throwable("contains null");
        java.lang.StackTraceElement ste = new java.lang.StackTraceElement("class", "method", null, 1);
        Field field = java.lang.StackTraceElement.class.getDeclaredField("declaringClass");
        field.setAccessible(true);
        field.set(ste, null);
        some.setStackTrace(new java.lang.StackTraceElement[] { ste });
        Throwable sut = Reports.newThrowable(some);
        Assert.assertEquals("MISSING", sut.getStackTrace().get(0).getClassName());
    }

    @Test
    public void testClearThrowableMessage() {
        ErrorReport event = createTestReport();
        Reports.clearMessages(event);
        assertThat(event.getStatus().getException().getMessage(), is(ANONYMIZED_TAG));
    }

    @Test
    public void testAnonymizeThrowableDtoClassname() {
        Throwable throwable = createThrowable(NOT_WHITELISTED_CLASSNAME);
        visit(throwable, new AnonymizeStacktraceVisitor(PREFIX_WHITELIST));
        assertThat(throwable.getClassName(), is(ANONYMIZED_TAG));
    }

    @Test
    public void testAnonymizeThrowableDtoWhitelistedClassname() {
        Throwable throwable = createThrowable(WHITELISTED_CLASSNAME);
        visit(throwable, new AnonymizeStacktraceVisitor(PREFIX_WHITELIST));
        assertThat(throwable.getClassName(), is(WHITELISTED_CLASSNAME));
    }

    @Test
    public void testAnonymizeStackTraceElementDtoClassnames() {
        StackTraceElement element = createStackTraceElement(NOT_WHITELISTED_CLASSNAME_2, NOT_WHITELISTED_METHODNAME_2);
        visit(element, new AnonymizeStacktraceVisitor(PREFIX_WHITELIST));
        assertThat(element.getClassName(), is(ANONYMIZED_TAG));
    }

    @Test
    public void testAnonymizeStackTraceElementDtoWhitelistedClassnames() {
        StackTraceElement element = createStackTraceElement(WHITELISTED_CLASSNAME, "");
        visit(element, new AnonymizeStacktraceVisitor(PREFIX_WHITELIST));
        assertThat(element.getClassName(), is(WHITELISTED_CLASSNAME));
    }

    @Test
    public void testAnonymizeStackTraceElementMethodname() {
        StackTraceElement element = createStackTraceElement(NOT_WHITELISTED_CLASSNAME_2, NOT_WHITELISTED_METHODNAME_2);
        visit(element, new AnonymizeStacktraceVisitor(PREFIX_WHITELIST));
        assertThat(element.getMethodName(), is(ANONYMIZED_TAG));
    }

    @Test
    public void testAnonymizeStackTraceElementWhitelistedMethodname() {
        StackTraceElement element = createStackTraceElement(WHITELISTED_CLASSNAME_2, WHITELISTED_METHODNAME_2);
        visit(element, new AnonymizeStacktraceVisitor(PREFIX_WHITELIST));
        assertThat(element.getMethodName(), is(WHITELISTED_METHODNAME_2));
    }

    @Test
    public void testFingerprint() {

        Exception cause = new RuntimeException("cause");
        Exception r1 = new RuntimeException("exception message");

        r1.fillInStackTrace();
        Exception r2 = new RuntimeException("exception message", cause);
        r2.fillInStackTrace();

        IStatus s1 = new Status(IStatus.ERROR, "org.eclipse.epp.logging.aeri", "some error message", r1);
        IStatus s2 = new Status(IStatus.ERROR, "org.eclipse.epp.logging.aeri", "some error message", r2);

        org.eclipse.epp.internal.logging.aeri.ui.model.Status noCause = Reports.newStatus(s1, configuration);
        org.eclipse.epp.internal.logging.aeri.ui.model.Status withCause = Reports.newStatus(s2, configuration);

        Assert.assertNotEquals(noCause.getFingerprint(), withCause.getFingerprint());
    }

    @Test
    public void testFingerprintNested() {
        Exception root = new RuntimeException("root");
        IStatus s1 = new Status(IStatus.ERROR, "org.eclipse.epp.logging.aeri", "some error message", root);
        IStatus s2 = new MultiStatus("org.eclipse.epp.logging.aeri", 0, new IStatus[] { s1 }, "some error message", root);

        org.eclipse.epp.internal.logging.aeri.ui.model.Status normal = Reports.newStatus(s1, configuration);
        org.eclipse.epp.internal.logging.aeri.ui.model.Status multi = Reports.newStatus(s2, configuration);

        Assert.assertNotEquals(normal.getFingerprint(), multi.getFingerprint());
    }

    @Test
    public void testCoreExceptionHandling() {
        IStatus causingStatus = new Status(IStatus.ERROR, "the.causing.plugin", "first message");
        java.lang.Throwable causingException = new CoreException(causingStatus);
        IStatus causedStatus = new Status(IStatus.WARNING, "some.calling.plugin", "any other message", causingException);
        java.lang.Throwable rootException = new CoreException(causedStatus);
        IStatus rootEvent = new Status(IStatus.ERROR, "org.eclipse.epp.logging.aeri", "someErrorMessage", rootException);

        org.eclipse.epp.internal.logging.aeri.ui.model.Status rootStatus = Reports.newStatus(rootEvent, configuration);

        org.eclipse.epp.internal.logging.aeri.ui.model.Status child = rootStatus.getChildren().get(0);
        org.eclipse.epp.internal.logging.aeri.ui.model.Status leaf = child.getChildren().get(0);
        assertThat(child.getPluginId(), is("some.calling.plugin"));
        assertThat(leaf.getPluginId(), is("the.causing.plugin"));
    }

    @Test
    public void testMultistatusDuplicateChildFiltering() {

        Exception e1 = new Exception("Stack Trace");
        e1.setStackTrace(createStacktraceForClasses("java.lang.Object", "org.eclipse.core.internal.jobs.WorkerPool",
                "org.eclipse.core.internal.jobs.WorkerPool", "org.eclipse.core.internal.jobs.Worker"));
        IStatus s1 = new Status(IStatus.ERROR, "org.eclipse.ui.monitoring",
                "Thread 'Worker-3' tid=39 (TIMED_WAITING)\n" + "Waiting for: org.eclipse.core.internal.jobs.WorkerPool@416dc7fc", e1);

        Exception e2 = new Exception("Stack Trace");
        e2.setStackTrace(TestReports.createStacktraceForClasses("java.lang.Object", "org.eclipse.core.internal.jobs.WorkerPool",
                "org.eclipse.core.internal.jobs.WorkerPool", "org.eclipse.core.internal.jobs.Worker"));
        IStatus s2 = new Status(IStatus.ERROR, "org.eclipse.ui.monitoring",
                "Thread 'Worker-2' tid=36 (TIMED_WAITING)\n" + "Waiting for: org.eclipse.core.internal.jobs.WorkerPool@416dc7fc", e2);

        IStatus multi = new MultiStatus("org.eclipse.ui.monitoring", 0, new IStatus[] { s1, s2 }, "UI freeze of 10s at 08:09:02.936",
                new RuntimeException("stand-in-stacktrace"));
        org.eclipse.epp.internal.logging.aeri.ui.model.Status newStatus = Reports.newStatus(multi, configuration);
        assertThat(newStatus.getChildren().size(), is(1));
        assertThat(newStatus.getMessage(), is("UI freeze of 10s at 08:09:02.936 [1 child-status duplicates removed by Error Reporting]"));
    }

    @Test
    public void testMultistatusChildFilteringHandlesEmptyStacktrace() {

        Exception e1 = new Exception("Stack Trace 1");
        e1.setStackTrace(new java.lang.StackTraceElement[0]);
        IStatus s1 = new Status(IStatus.ERROR, "org.eclipse.ui.monitoring", "Thread 'Signal Dispatcher' tid=4 (RUNNABLE)", e1);

        IStatus multi = new MultiStatus("org.eclipse.ui.monitoring", 0, new IStatus[] { s1 }, "UI freeze of 10s at 08:09:02.936",
                new RuntimeException("stand-in-stacktrace"));
        org.eclipse.epp.internal.logging.aeri.ui.model.Status newStatus = Reports.newStatus(multi, configuration);
        assertThat(newStatus.getChildren().size(), is(0));
    }

    @Test
    public void testMultistatus() {
        MultiStatus mst = mst(ex(), st(ex()), st(ex()), st(ex()), st(ex()), st(ex()));
        org.eclipse.epp.internal.logging.aeri.ui.model.Status res = Reports.newStatus(mst, configuration);
        assertThat(numberOfStatusObjects(res), is(6));
    }

    @Test
    public void testCausedByContainsCoreExceptionWithStatus() {
        Status st = st(ex(cex(st(ex()))));
        org.eclipse.epp.internal.logging.aeri.ui.model.Status res = newStatus(st, configuration);
        assertThat(numberOfStatusObjects(res), is(2));
    }

    @Test
    public void testCausedByContainsCoreExceptionWithMultistatus() {
        MultiStatus mst = mst(ex(), st(ex()), st(ex()), st(ex()), st(ex()), st(ex()));
        Status st = st(ex(cex(mst)));
        org.eclipse.epp.internal.logging.aeri.ui.model.Status res = Reports.newStatus(st, configuration);
        assertThat(numberOfStatusObjects(res), is(7));
    }

    @Test
    public void testDetachMultistatusFromCoreException() {
        // #input
        // st4
        // -ex4
        // --cex
        // ---mst3
        // ----ex1
        // ----st1
        // -----ex2
        // ----st2
        // -----ex3
        Status st = st(ex(cex(mst(ex(), st(ex()), st(ex())))));
        // #expected output
        // st4
        // -ex4
        // --cex(mst3)
        // ---ex1
        // -mst3 (moved from cex to st4.children)
        // --ex1
        // --st1
        // ---ex2
        // --st2
        // ---ex3
        org.eclipse.epp.internal.logging.aeri.ui.model.Status st4 = Reports.newStatus(st, configuration);
        Throwable ex4 = st4.getException();
        Throwable cex = ex4.getCause();
        Throwable ex1 = cex.getCause();
        org.eclipse.epp.internal.logging.aeri.ui.model.Status mst3 = st4.getChildren().get(0);
        Throwable ex1b = mst3.getException();
        org.eclipse.epp.internal.logging.aeri.ui.model.Status st1 = mst3.getChildren().get(0);
        Throwable ex2 = st1.getException();
        org.eclipse.epp.internal.logging.aeri.ui.model.Status st2 = mst3.getChildren().get(1);
        Throwable ex3 = st2.getException();

        assertThat(st4.getMessage(), is("st4"));
        assertThat(ex4.getMessage(), is("ex4"));
        assertThat(cex.getMessage(), is("mst3"));
        assertThat(ex1.getMessage(), is("ex1"));
        assertThat(mst3.getMessage(), is("mst3 [detached from CoreException of Status 'st4' by Error Reporting]"));
        assertThat(ex1b.getMessage(), is("ex1"));
        assertThat(st1.getMessage(), is("st1"));
        assertThat(ex2.getMessage(), is("ex2"));
        assertThat(st2.getMessage(), is("st2"));
        assertThat(ex3.getMessage(), is("ex3"));
    }

    @Test
    public void testDetachMultipleMultistatusFromCoreException() {
        // #input
        // st9
        // -ex6
        // --cex
        // ---st8
        // ----cex
        // -----mst7
        // ------ex1
        // ------st4
        // -------cex
        // --------mst3
        // ---------ex2
        // ---------st1
        // ----------ex3
        // ---------st2
        // ----------ex4
        // ------st6
        // -------cex
        // --------st5
        // ---------ex5
        Status st = st(ex(cex(st(cex(mst(ex(), st(cex(mst(ex(), st(ex()), st(ex())))), st(cex(st(ex())))))))));
        // #expected output
        // st9
        // -st8
        // --mst7
        // ---ex1
        // ---st4
        // ----mst3
        // -----ex2
        // ----st1
        // -----ex3
        // ----st2
        // -----ex4
        // ---st6
        // ----st5
        // -----ex5
        org.eclipse.epp.internal.logging.aeri.ui.model.Status st9 = Reports.newStatus(st, configuration);
        Throwable ex6 = st9.getException();
        org.eclipse.epp.internal.logging.aeri.ui.model.Status st8 = st9.getChildren().get(0);
        org.eclipse.epp.internal.logging.aeri.ui.model.Status mst7 = st8.getChildren().get(0);
        org.eclipse.epp.internal.logging.aeri.ui.model.Status st4 = mst7.getChildren().get(0);
        org.eclipse.epp.internal.logging.aeri.ui.model.Status mst3 = st4.getChildren().get(0);
        org.eclipse.epp.internal.logging.aeri.ui.model.Status st2 = mst3.getChildren().get(1);
        Throwable ex4 = st2.getException();
        org.eclipse.epp.internal.logging.aeri.ui.model.Status st6 = mst7.getChildren().get(1);
        org.eclipse.epp.internal.logging.aeri.ui.model.Status st5 = st6.getChildren().get(0);
        Throwable ex5 = st5.getException();

        assertThat(st9.getMessage(), is("st9"));
        assertThat(ex6.getMessage(), is("ex6"));
        assertThat(st8.getMessage(), is("st8 [detached from CoreException of Status 'st9' by Error Reporting]"));
        assertThat(mst7.getMessage(), is("mst7 [detached from CoreException of Status 'st8' by Error Reporting]"));
        assertThat(st4.getMessage(), is("st4"));
        assertThat(mst3.getMessage(), is("mst3 [detached from CoreException of Status 'st4' by Error Reporting]"));
        assertThat(st2.getMessage(), is("st2"));
        assertThat(ex4.getMessage(), is("ex4"));
        assertThat(st6.getMessage(), is("st6"));
        assertThat(st5.getMessage(), is("st5 [detached from CoreException of Status 'st6' by Error Reporting]"));
        assertThat(ex5.getMessage(), is("ex5"));
    }

    @Test
    public void testPrettyPrintNullSafe1() {
        ModelFactory mf = ModelFactory.eINSTANCE;
        ErrorReport report = mf.createErrorReport();
        Reports.prettyPrint(report, settings, configuration);
    }

    @Test
    public void testPrettyPrintNullSafe2() {
        ModelFactory mf = ModelFactory.eINSTANCE;
        ErrorReport report = mf.createErrorReport();
        report.setStatus(mf.createStatus());
        Reports.prettyPrint(report, settings, configuration);
    }

    @Test
    public void testPrettyPrintNullSafe3() {
        ModelFactory mf = ModelFactory.eINSTANCE;

        ErrorReport report = mf.createErrorReport();
        report.setStatus(mf.createStatus());
        Throwable t = mf.createThrowable();
        t.setClassName("org.test");
        report.getStatus().setException(t);
        Reports.prettyPrint(report, settings, configuration);
    }

    @Test
    public void testPrettyPrintSkipsNullException() {
        ModelFactory mf = ModelFactory.eINSTANCE;

        ErrorReport report = mf.createErrorReport();
        report.setStatus(mf.createStatus());
        String prettyPrint = Reports.prettyPrint(report, settings, configuration);
        assertThat(prettyPrint, not(containsString("Exception")));
    }

    @Test
    public void testMultistatusMainStacktracesNotFiltered() {

        Exception e1 = new Exception("Stack Trace");
        java.lang.StackTraceElement[] stackTrace = createStacktraceForClasses("java.lang.Thread",
                "org.eclipse.epp.logging.aeri.ui.actions.UiFreezeAction", "org.eclipse.ui.internal.PluginAction",
                "org.eclipse.ui.internal.WWinPluginAction", "org.eclipse.jface.action.ActionContributionItem",
                "org.eclipse.jface.action.ActionContributionItem", "org.eclipse.jface.action.ActionContributionItem",
                "org.eclipse.swt.widgets.EventTable", "org.eclipse.swt.widgets.Display", "org.eclipse.swt.widgets.Widget",
                "org.eclipse.swt.widgets.Display", "org.eclipse.swt.widgets.Display",
                "org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine", "org.eclipse.core.databinding.observable.Realm",
                "org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine", "org.eclipse.e4.ui.internal.workbench.E4Workbench",
                "org.eclipse.ui.internal.Workbench", "org.eclipse.core.databinding.observable.Realm", "org.eclipse.ui.internal.Workbench",
                "org.eclipse.ui.PlatformUI", "org.eclipse.ui.internal.ide.application.IDEApplication",
                "org.eclipse.equinox.internal.app.EclipseAppHandle", "org.eclipse.core.runtime.internal.adaptor.EclipseAppLauncher",
                "org.eclipse.core.runtime.internal.adaptor.EclipseAppLauncher", "org.eclipse.core.runtime.adaptor.EclipseStarter",
                "org.eclipse.core.runtime.adaptor.EclipseStarter", "sun.reflect.NativeMethodAccessorImpl",
                "sun.reflect.NativeMethodAccessorImpl", "sun.reflect.DelegatingMethodAccessorImpl", "java.lang.reflect.Method",
                "org.eclipse.equinox.launcher.Main", "org.eclipse.equinox.launcher.Main", "org.eclipse.equinox.launcher.Main",
                "org.eclipse.equinox.launcher.Main", "org.eclipse.equinox.launcher.Main");
        e1.setStackTrace(stackTrace);
        IStatus s1 = new Status(IStatus.ERROR, "org.eclipse.ui.monitoring",
                "Sample at 11:25:04.447 (+1,331s)\n" + "Thread 'main' tid=1 (TIMED_WAITING)", e1);

        IStatus multi = new MultiStatus("org.eclipse.ui.monitoring", 0, new IStatus[] { s1 }, "UI freeze of 6,0s at 11:24:59.108",
                new RuntimeException("stand-in-stacktrace"));
        org.eclipse.epp.internal.logging.aeri.ui.model.Status newStatus = Reports.newStatus(multi, configuration);
        assertThat(newStatus.getChildren().size(), is(1));
        assertThat(newStatus.getChildren().get(0).getException().getStackTrace().size(), is(stackTrace.length));
    }

    private static int numberOfStatusObjects(org.eclipse.epp.internal.logging.aeri.ui.model.Status status) {
        final AtomicInteger counter = new AtomicInteger();
        ModelSwitch<AtomicInteger> visitor = new ModelSwitch<AtomicInteger>() {

            @Override
            public AtomicInteger caseStatus(org.eclipse.epp.internal.logging.aeri.ui.model.Status object) {
                counter.incrementAndGet();
                return counter;
            }

            @Override
            public AtomicInteger caseThrowable(Throwable object) {
                return counter;
            }
        };

        visitor.doSwitch(status);
        for (TreeIterator<EObject> it = getAllContents(status, true); it.hasNext();) {
            visitor.doSwitch(it.next());
        }
        return counter.get();
    }

    private static int exCounter = 1;

    private static Exception ex() {
        return ex(null);
    }

    private static Exception cex(IStatus st) {
        return new CoreException(st);
    }

    private static Exception ex(Exception cause) {
        return new Exception("ex" + exCounter++, cause);
    }

    private static int stCounter = 1;

    private static Status st(Exception ex) {
        return new Status(IStatus.ERROR, "org.eclipse", "st" + stCounter++, ex);
    }

    private static MultiStatus mst(Exception ex, Status... status) {
        return new MultiStatus("org.eclipse", IStatus.ERROR, status, "mst" + stCounter++, ex);
    }

}
