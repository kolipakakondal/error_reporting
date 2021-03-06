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

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.epp.internal.logging.aeri.ui.Constants.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.epp.internal.logging.aeri.ui.Events.NewReportLogged;
import org.eclipse.epp.internal.logging.aeri.ui.log.LogListener;
import org.eclipse.epp.internal.logging.aeri.ui.log.ProblemsDatabaseService;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.AcceptOtherPackagesPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.AcceptUiFreezesPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.CompleteUiFreezeReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ProblemDatabaseIgnoredPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ReportsHistoryPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.UnseenErrorReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ValidSizeErrorReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.AcceptProductPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.EclipseBuildIdPresentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.ErrorStatusOnlyPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.HistoryReadyPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.IgnorePatternPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.ReporterNotDisabledPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.WhitelistedPluginIdPresentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.WorkbenchRunningPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelFactory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus.RequiredAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.utils.RetainSystemProperties;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;
import org.eclipse.ui.PlatformUI;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Throwables;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class LogListenerTest {

    private static final String TEST_PLUGIN_ID = "org.eclipse.epp.logging.aeri.rcp.tests";
    private static final String ANY_THIRD_PARTY_PLUGIN_ID = "any.third.party.plugin.id";
    private EventBus bus;
    private BlockingQueue<Object> queue;
    private Settings settings;
    private ServerConfiguration configuration;
    private LogListener sut;
    private ReportHistory history;
    private ExpiringReportHistory expiringHistory;
    private ProblemsDatabaseService problemStatusIndex;

    @Rule
    public RetainSystemProperties retainSystemProperties = new RetainSystemProperties();

    private static class TestHistory extends ReportHistory {
        @Override
        protected Directory createIndexDirectory() throws IOException {
            return new RAMDirectory();
        }
    }

    @Before
    public void setup() {
        System.setProperty(SYSPROP_ECLIPSE_BUILD_ID, "unit-tests");
        System.setProperty(SYSPROP_ECLIPSE_PRODUCT, "org.eclipse.sdk.ide");
        settings = ModelFactory.eINSTANCE.createSettings();
        settings.setConfigured(true);
        configuration = new ServerConfiguration();
        configuration.setAcceptedPlugins(newArrayList(TEST_PLUGIN_ID));
        configuration.setAcceptedPackages(newArrayList("java.*"));
        configuration.setAcceptedProducts(newArrayList("org.eclipse.*"));
        configuration.setAcceptOtherPackages(true);
        configuration.setIgnoredStatuses(new ArrayList<String>());
        configuration.setMaxReportSize(1000 * 1000);
        settings.setAction(SendAction.SILENT);
        settings.setSkipSimilarErrors(true);

        history = new TestHistory();
        history.startAsync();
        history.awaitRunning();

        queue = new LinkedBlockingQueue<>();
        bus = new EventBus();
        bus.register(this);

        expiringHistory = new ExpiringReportHistory();

        problemStatusIndex = mock(ProblemsDatabaseService.class);
        Optional<ProblemStatus> noStatus = Optional.absent();

        when(problemStatusIndex.seen(org.mockito.Matchers.any(ErrorReport.class))).thenReturn(noStatus);

        sut = createLogListener(settings, configuration, history, bus, expiringHistory, problemStatusIndex);
    }

    // TODO Local tests need to be executed without the activeShellPredicate
    // The predicates from LogListener.createLogListener should be moved to Constants in a later change
    @SuppressWarnings("unchecked")
    public static LogListener createLogListener(Settings settings, ServerConfiguration configuration, ReportHistory history, EventBus bus,
            ExpiringReportHistory expiringReportHistory, ProblemsDatabaseService serverProblemsStatusIndex) {
        Predicate<? super IStatus>[] statusPredicates = new Predicate[] {
                // @formatter:off
                new AcceptProductPredicate(configuration),
                new EclipseBuildIdPresentPredicate(),
                new ErrorStatusOnlyPredicate(),
                new HistoryReadyPredicate(history),
                new IgnorePatternPredicate(configuration.getIgnoredPluginMessagesPatterns()),
                new ReporterNotDisabledPredicate(settings),
                new WhitelistedPluginIdPresentPredicate(configuration),
                new WorkbenchRunningPredicate(PlatformUI.getWorkbench())};
                // @formatter:on
        Predicate<IStatus> statusFilters = Predicates.and(statusPredicates);

        Predicate<? super ErrorReport>[] reportPredicates = new Predicate[] {
                // @formatter:off
                new AcceptOtherPackagesPredicate(configuration),
                new AcceptUiFreezesPredicate(configuration),
                new CompleteUiFreezeReportPredicate(),
                new ProblemDatabaseIgnoredPredicate(serverProblemsStatusIndex, settings),
                new ReportsHistoryPredicate(expiringReportHistory, settings),
                new UnseenErrorReportPredicate(history, settings),
                new ValidSizeErrorReportPredicate(configuration)};
                 // @formatter:on
        Predicate<ErrorReport> reportFilters = Predicates.and(reportPredicates);
        LogListener listener = new LogListener(statusFilters, reportFilters, settings, configuration, bus);
        return listener;
    }

    @Subscribe
    public void on(Object o) {
        queue.add(o);
    }

    private NewReportLogged pollEvent() {
        return pollEvent(20, TimeUnit.SECONDS);
    }

    private NewReportLogged pollEvent(int timout, TimeUnit unit) {
        try {
            Object event = queue.poll(timout, unit);
            if (event == null) {
                return null;
            }
            return (NewReportLogged) event;
        } catch (InterruptedException e) {
            throw Throwables.propagate(e);
        }
    }

    private void verifyNoErrorReportLogged() {
        try {
            // logging is done async, wait for system to handle events
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Throwables.propagate(e);
        }
        assertThat(queue.isEmpty(), is(true));
    }

    private void verifyExactOneErrorReportLogged() {
        try {
            // logging is done async, wait for system to handle events
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Throwables.propagate(e);
        }
        assertThat(queue.size(), is(1));
        NewReportLogged event1 = pollEvent();
        assertThat(event1, is(not(nullValue())));
        assertThat(queue.isEmpty(), is(true));
    }

    @Test
    public void testStatusUnmodified() {
        Status empty = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "has no stacktrace");
        Status empty2 = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "has no stacktrace");

        sut.logging(empty, "");
        assertTrue(EqualsBuilder.reflectionEquals(empty, empty2));
    }

    @Test
    public void testNoInsertDebugStacktraceOnIgnoreMode() {
        Status empty = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "has no stacktrace");
        Assert.assertThat(empty.getException(), nullValue());

        sut.logging(empty, "");

        assertThat(empty.getException(), nullValue());
    }

    @Test
    public void testInsertDebugStacktrace() {
        Status empty = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "has no stacktrace");

        sut.logging(empty, "");

        NewReportLogged event = pollEvent();

        assertThat(event.report.getStatus().getException(), not(nullValue()));
    }

    @Test
    public void testInsertedDebugStacktraceHasFingerprint() {
        Status empty = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "has no stacktrace");

        sut.logging(empty, "");

        NewReportLogged event = pollEvent();
        // the fingerprint should not be a string of only 0-values, which
        // indicates a missing fingerprint
        assertThat(event.report.getStatus().getFingerprint().matches("[0]*"), is(false));
    }

    @Test
    public void testBundlesAddedToDebugStacktrace() {
        Status empty = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "has no stacktrace");

        sut.logging(empty, "");

        NewReportLogged event = pollEvent();

        assertThat(event.report.getPresentBundles(), not(Matchers.empty()));
    }

    @Test
    public void testIgnoreInfo() {
        Status status = new Status(IStatus.INFO, TEST_PLUGIN_ID, "a message");

        sut.logging(status, "");

        verifyNoErrorReportLogged();
    }

    @Test
    public void testNoReportIfBuildIdUnknown() {
        System.clearProperty(SYSPROP_ECLIPSE_BUILD_ID);
        Status status = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "a message");

        sut.logging(status, "");

        verifyNoErrorReportLogged();
    }

    @Test
    public void testIgnoreCancel() {
        Status status = new Status(IStatus.CANCEL, TEST_PLUGIN_ID, "a message");

        sut.logging(status, "");

        verifyNoErrorReportLogged();
    }

    @Test
    public void testIgnoreOk() {
        Status status = new Status(IStatus.OK, TEST_PLUGIN_ID, "a message");

        sut.logging(status, "");

        verifyNoErrorReportLogged();
    }

    @Test
    public void testIgnoreWarning() {
        Status status = new Status(IStatus.WARNING, TEST_PLUGIN_ID, "a message");

        sut.logging(status, "");

        verifyNoErrorReportLogged();
    }

    @Test
    public void testUnknownPluginsIgnored() {
        Status status = new Status(IStatus.ERROR, ANY_THIRD_PARTY_PLUGIN_ID, "any message");

        sut.logging(status, "");

        verifyNoErrorReportLogged();
    }

    @Test
    public void testIgnore() {
        Status status = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "test message");
        settings.setAction(SendAction.IGNORE);

        sut.logging(status, "");

        verifyNoErrorReportLogged();
    }

    @Test
    public void testSkipSimilarErrors() {
        settings.setSkipSimilarErrors(true);

        Throwable t1 = new Throwable();
        Status s1 = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "test message", t1);
        Status s2 = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "test message", t1);

        sut.logging(s1, "");
        NewReportLogged event1 = pollEvent(1, TimeUnit.SECONDS);
        history.remember(event1.report);

        sut.logging(s2, "");

        NewReportLogged event2 = pollEvent(1, TimeUnit.SECONDS);
        assertThat(event2, is(nullValue()));
    }

    @Test
    public void testNoSkippingSimilarErrors() {
        settings.setSkipSimilarErrors(false);

        Throwable t1 = new Throwable();
        Status s1 = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "test message", t1);
        Status s2 = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "test message", t1);

        sut.logging(s1, "");
        NewReportLogged event1 = pollEvent();
        history.remember(event1.report);
        sut.logging(s2, "");

        NewReportLogged event2 = pollEvent();
        assertThat(event2, is(not(nullValue())));
    }

    @Test
    public void testNoReportOfSourceFiles() {
        String sourceDataMessage = "Exception occurred during compilation unit conversion:\n"
                + "----------------------------------- SOURCE BEGIN -------------------------------------\n" + "package some.package;\n"
                + "\n" + "import static some.import.method;\n" + "import static some.other.import;\n";
        Status status = new Status(IStatus.ERROR, TEST_PLUGIN_ID, sourceDataMessage, new RuntimeException());

        sut.logging(status, "");

        NewReportLogged event = pollEvent();

        assertThat(event.report.getStatus().getMessage(), is("source file contents removed"));
    }

    @Test
    public void testMonitoringStatusWithNoChildsFiltered()
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        MultiStatus multi = new MultiStatus("org.eclipse.ui.monitoring", 0, "UI freeze of 6,0s at 11:24:59.108",
                new RuntimeException("stand-in-stacktrace"));
        Method method = Status.class.getDeclaredMethod("setSeverity", Integer.TYPE);
        method.setAccessible(true);
        method.invoke(multi, IStatus.ERROR);
        sut.logging(multi, "");
        verifyNoErrorReportLogged();
    }

    @Test
    public void testMultipleSimilarErrors() {
        for (int i = 0; i < 10; i++) {
            Status status = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "Error Message", new RuntimeException());
            sut.logging(status, "");
        }
        // only one event should be logged
        verifyExactOneErrorReportLogged();
    }

    @Test
    public void testNoAcceptingOtherPackages() {
        Throwable t = new RuntimeException();
        t.fillInStackTrace();
        StackTraceElement[] stackTrace = t.getStackTrace();
        stackTrace[0] = new StackTraceElement("any.third.party.Clazz", "thirdPartyMethod", "Clazz.java", 42);
        t.setStackTrace(stackTrace);
        Status status = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "Error Message", t);

        configuration.setAcceptOtherPackages(false);

        sut.logging(status, "");
        verifyNoErrorReportLogged();
    }

    @Test
    public void testNoAcceptingUiFreezes() {
        Status status = new Status(IStatus.ERROR, "org.eclipse.ui.monitoring", "Error Message", new RuntimeException());

        configuration.setAcceptUiFreezes(false);

        sut.logging(status, "");
        verifyNoErrorReportLogged();
    }

    @Test
    public void testNoAcceptingOtherProduct() {
        Status status = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "Error Message", new RuntimeException());

        System.setProperty(SYSPROP_ECLIPSE_PRODUCT, "other.eclipse.product");
        sut.logging(status, "");
        verifyNoErrorReportLogged();
    }

    @Test
    public void testAcceptOtherProduct() {
        Status status = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "Error Message", new RuntimeException());
        System.setProperty(SYSPROP_ECLIPSE_PRODUCT, "other.eclipse.product");
        configuration.setAcceptedProducts(newArrayList("other.eclipse.product"));
        sut.logging(status, "");
        verifyExactOneErrorReportLogged();
    }

    @Test
    public void testMultipleErrorsWithDifferentMessages() {
        for (int i = 0; i < 10; i++) {
            Status status = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "Error Message Number " + i, new RuntimeException());
            sut.logging(status, "");
        }
        // only one event should be logged
        verifyExactOneErrorReportLogged();
    }

    @Test
    public void testServerProblemIgnoreHandled() {
        Throwable t1 = new Throwable();
        t1.fillInStackTrace();
        Status s1 = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "test message", t1);
        ErrorReport report = Reports.newErrorReport(s1, settings, configuration);

        ProblemStatus status = new ProblemStatus();
        status.setIncidentFingerprint(Reports.traceIdentityHash(report));
        status.setBugUrl("http://the.url");
        status.setAction(RequiredAction.IGNORE);

        when(problemStatusIndex.seen(org.mockito.Matchers.any(ErrorReport.class))).thenReturn(Optional.of(status));

        sut.logging(s1, "");

        verifyNoErrorReportLogged();
    }

    @Test
    public void testLinkageErrorCommentAdded() {
        Throwable e3 = new RuntimeException();
        e3.fillInStackTrace();
        ClassNotFoundException e2 = new ClassNotFoundException(StringUtils.class.getName(), e3);
        Throwable e1 = new Throwable("test", e2);
        Status status = new Status(IStatus.ERROR, TEST_PLUGIN_ID, "test message", e1);

        sut.logging(status, "");

        ErrorReport report = pollEvent().report;
        assertThat(report.getComment(), not(isEmptyOrNullString()));
    }

}
