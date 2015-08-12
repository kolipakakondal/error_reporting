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

import static com.google.common.collect.Lists.newArrayList;
import static java.util.regex.Pattern.quote;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.epp.internal.logging.aeri.ui.log.ProblemsDatabaseService;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.AcceptOtherPackagesPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.AcceptUiFreezesPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.CompleteUiFreezeReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ProblemDatabaseIgnoredPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ReportsHistoryPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.UnseenErrorReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportPredicates.ValidSizeErrorReportPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelFactory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus.RequiredAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.model.Status;
import org.eclipse.epp.internal.logging.aeri.ui.model.Throwable;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

public class ReportPredicatesTest {

    private ErrorReport report;
    private Predicate<ErrorReport> sut;
    private Settings settings;
    private ServerConfiguration configuration;
    private ModelFactory mf = ModelFactory.eINSTANCE;

    @Before
    public void setUp() {
        report = mock(ErrorReport.class);
        settings = mock(Settings.class);
        configuration = mock(ServerConfiguration.class);
        when(settings.isSkipSimilarErrors()).thenReturn(true);
    }

    @Test
    public void testProblemDatabaseIgnoredPredicateNoSkip() {
        ProblemsDatabaseService service = mock(ProblemsDatabaseService.class);
        when(settings.isSkipSimilarErrors()).thenReturn(false);
        sut = new ProblemDatabaseIgnoredPredicate(service, settings);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testProblemDatabaseIgnoredPredicateNoStatus() {
        ProblemsDatabaseService service = mock(ProblemsDatabaseService.class);
        Optional<ProblemStatus> status = Optional.absent();
        when(service.seen(any(ErrorReport.class))).thenReturn(status);
        sut = new ProblemDatabaseIgnoredPredicate(service, settings);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testProblemDatabaseIgnoredPredicateNone() {
        ProblemsDatabaseService service = mock(ProblemsDatabaseService.class);
        ProblemStatus s = new ProblemStatus();
        s.setAction(RequiredAction.NONE);
        Optional<ProblemStatus> status = Optional.of(s);
        when(service.seen(any(ErrorReport.class))).thenReturn(status);
        sut = new ProblemDatabaseIgnoredPredicate(service, settings);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testProblemDatabaseIgnoredPredicateIgnore() {
        ProblemsDatabaseService service = mock(ProblemsDatabaseService.class);
        ProblemStatus s = new ProblemStatus();
        s.setAction(RequiredAction.IGNORE);
        Optional<ProblemStatus> status = Optional.of(s);
        when(service.seen(any(ErrorReport.class))).thenReturn(status);
        sut = new ProblemDatabaseIgnoredPredicate(service, settings);
        assertFalse(sut.apply(report));
    }

    @Test
    public void testReportsHistoryPredicateNoSkip() {
        ExpiringReportHistory history = mock(ExpiringReportHistory.class);
        sut = new ReportsHistoryPredicate(history, settings);
        when(settings.isSkipSimilarErrors()).thenReturn(false);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testReportsHistoryPredicateNotSeen() {
        ExpiringReportHistory history = mock(ExpiringReportHistory.class);
        when(history.seenExact(any(ErrorReport.class))).thenReturn(false);
        when(history.seenSimilarTrace(any(ErrorReport.class))).thenReturn(false);
        sut = new ReportsHistoryPredicate(history, settings);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testReportsHistoryPredicateSeenExact() {
        ExpiringReportHistory history = mock(ExpiringReportHistory.class);
        when(history.seenExact(any(ErrorReport.class))).thenReturn(true);
        when(history.seenSimilarTrace(any(ErrorReport.class))).thenReturn(false);
        sut = new ReportsHistoryPredicate(history, settings);
        assertFalse(sut.apply(report));
    }

    @Test
    public void testReportsHistoryPredicateSeenSimilar() {
        ExpiringReportHistory history = mock(ExpiringReportHistory.class);
        when(history.seenExact(any(ErrorReport.class))).thenReturn(false);
        when(history.seenSimilarTrace(any(ErrorReport.class))).thenReturn(true);
        sut = new ReportsHistoryPredicate(history, settings);
        assertFalse(sut.apply(report));
    }

    @Test
    public void testUnseenErrorReportPredicateNoSkip() {
        ReportHistory history = createTestHistory(false, false);
        sut = new UnseenErrorReportPredicate(history, settings);
        when(settings.isSkipSimilarErrors()).thenReturn(false);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testUnseenErrorReportPredicateHistoryNotRunning() {
        ReportHistory history = createTestHistory(false, false);
        sut = new UnseenErrorReportPredicate(history, settings);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testUnseenErrorReportPredicateHistoryNotSeen() {
        ReportHistory history = createTestHistory(false, false);
        history.startAsync();
        history.awaitRunning();
        sut = new UnseenErrorReportPredicate(history, settings);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testUnseenErrorReportPredicateHistorySeen() {
        ReportHistory history = createTestHistory(true, false);
        history.startAsync();
        history.awaitRunning();
        sut = new UnseenErrorReportPredicate(history, settings);
        assertFalse(sut.apply(report));
    }

    @Test
    public void testUnseenErrorReportPredicateHistorySeenSimilar() {
        ReportHistory history = createTestHistory(false, true);
        history.startAsync();
        history.awaitRunning();
        sut = new UnseenErrorReportPredicate(history, settings);
        assertFalse(sut.apply(report));
    }

    private static ReportHistory createTestHistory(final boolean seen, final boolean seenSimilar) {
        // isRunning can not be mocked because it is inherited
        // from AbstractIdleService and final
        // TODO consider refactoring ReportHistory to use composition instead of inheritance
        ReportHistory history = new ReportHistory() {
            @Override
            protected Directory createIndexDirectory() throws IOException {
                return new RAMDirectory();
            }

            @Override
            public boolean seen(ErrorReport report) {
                return seen;
            }

            @Override
            public boolean seenSimilar(ErrorReport report) {
                return seenSimilar;
            }
        };
        return history;
    }

    @Test
    public void testCompleteUiFreezeReportPredicateChildrenStatus() {
        Status status = mock(Status.class);
        when(status.getPluginId()).thenReturn("org.eclipse.ui.monitoring");
        BasicEList<Status> list = new BasicEList<Status>();
        list.add(mock(Status.class));
        when(status.getChildren()).thenReturn(list);
        when(report.getStatus()).thenReturn(status);
        sut = new CompleteUiFreezeReportPredicate();
        assertTrue(sut.apply(report));
    }

    @Test
    public void testCompleteUiFreezeReportPredicateEmptyStatus() {
        Status status = mock(Status.class);
        when(status.getPluginId()).thenReturn("org.eclipse.ui.monitoring");
        when(status.getChildren()).thenReturn(new BasicEList<Status>());
        when(report.getStatus()).thenReturn(status);
        sut = new CompleteUiFreezeReportPredicate();
        assertFalse(sut.apply(report));
    }

    @Test
    public void testCompleteUiFreezeReportPredicateEmptyStatusNoUiFreeze() {
        Status status = mock(Status.class);
        when(status.getPluginId()).thenReturn("other.plugin");
        when(status.getChildren()).thenReturn(new BasicEList<Status>());
        when(report.getStatus()).thenReturn(status);
        sut = new CompleteUiFreezeReportPredicate();
        assertTrue(sut.apply(report));
    }

    @Test
    public void testAcceptUiFreezesPredicateAccept() {
        Status status = mock(Status.class);
        when(status.getPluginId()).thenReturn("org.eclipse.ui.monitoring");
        when(report.getStatus()).thenReturn(status);
        when(configuration.isAcceptUiFreezes()).thenReturn(true);
        sut = new AcceptUiFreezesPredicate(configuration);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testAcceptUiFreezesPredicateDiscardAndFreeze() {
        Status status = mock(Status.class);
        when(status.getPluginId()).thenReturn("org.eclipse.ui.monitoring");
        when(report.getStatus()).thenReturn(status);
        when(configuration.isAcceptUiFreezes()).thenReturn(false);
        sut = new AcceptUiFreezesPredicate(configuration);
        assertFalse(sut.apply(report));
    }

    @Test
    public void testAcceptUiFreezesPredicateDiscardAndNoFreeze() {
        Status status = mock(Status.class);
        when(status.getPluginId()).thenReturn("other.plugin");
        when(report.getStatus()).thenReturn(status);
        when(configuration.isAcceptUiFreezes()).thenReturn(false);
        sut = new AcceptUiFreezesPredicate(configuration);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testAcceptOtherPackagesPredicateAccept() {
        when(configuration.isAcceptOtherPackages()).thenReturn(true);
        sut = new AcceptOtherPackagesPredicate(configuration);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testAcceptOtherPackagesPredicateDiscardMatch() {
        when(configuration.isAcceptOtherPackages()).thenReturn(false);
        when(configuration.getAcceptedPackagesPatterns()).thenReturn(newArrayList(Pattern.compile(quote("org.eclipse.plugin"))));
        sut = new AcceptOtherPackagesPredicate(configuration);
        mf = ModelFactory.eINSTANCE;
        ErrorReport report = mf.createErrorReport();
        Status status = mf.createStatus();
        report.setStatus(status);
        assertTrue(sut.apply(report));
    }

    @Test
    public void testAcceptOtherPackagesPredicateDiscardNoMatch() {
        when(configuration.isAcceptOtherPackages()).thenReturn(false);
        when(configuration.getAcceptedPackagesPatterns()).thenReturn(newArrayList(Pattern.compile(quote("org.eclipse.plugin"))));
        sut = new AcceptOtherPackagesPredicate(configuration);
        mf = ModelFactory.eINSTANCE;
        report = mf.createErrorReport();
        Status status = mf.createStatus();
        Throwable throwable = mf.createThrowable();
        throwable.setClassName("other.plugin.Throwable");
        status.setException(throwable);
        report.setStatus(status);
        assertFalse(sut.apply(report));
    }

    @Test
    public void testValidSizeErrorReportPredicate() {
        when(configuration.getMaxReportSize()).thenReturn(1000 * 1000); // ~1MB
        sut = new ValidSizeErrorReportPredicate(configuration);
        byte[] chars = new byte[1000 * 1000 * 10]; // ~10 MB of chars
        // 'a'
        byte b = 97;
        Arrays.fill(chars, b);
        RuntimeException t = new RuntimeException("exception");
        t.fillInStackTrace();
        org.eclipse.core.runtime.Status status = new org.eclipse.core.runtime.Status(IStatus.ERROR,
                "org.eclipse.epp.logging.aeri.rcp.tests", new String(chars), t);
        report = Reports.newErrorReport(status, settings, configuration);
        assertFalse(sut.apply(report));
    }
}
