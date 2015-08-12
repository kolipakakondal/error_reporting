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
import static org.eclipse.epp.internal.logging.aeri.ui.Constants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.AcceptProductPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.EclipseBuildIdPresentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.ErrorStatusOnlyPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.HistoryReadyPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.IgnorePatternPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.ReporterNotDisabledPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.SkipReportsAbsentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.WhitelistedPluginIdPresentPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.WorkbenchRunningPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.utils.RetainSystemProperties;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration.IgnorePattern;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.ui.IWorkbench;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.base.Predicate;

public class StatusPredicatesTest {

    private Settings settings;
    private ServerConfiguration configuration;
    private IStatus status;
    private Predicate<IStatus> sut;

    @Rule
    public RetainSystemProperties retainSystemProperties = new RetainSystemProperties();

    @Before
    public void setUp() {
        settings = mock(Settings.class);
        configuration = mock(ServerConfiguration.class);
        status = mock(IStatus.class);

    }

    @Test
    public void testReporterNotDisabledPredicateIgnore() {
        sut = new ReporterNotDisabledPredicate(settings);
        when(settings.getAction()).thenReturn(SendAction.IGNORE);
        assertFalse(sut.apply(status));
    }

    @Test
    public void testReporterNotDisabledPredicateAsk() {
        sut = new ReporterNotDisabledPredicate(settings);
        when(settings.getAction()).thenReturn(SendAction.ASK);
        assertTrue(sut.apply(status));
    }

    @Test
    public void testWhitelistedPluginIdPresentPredicateNoMatch() {
        when(status.getPlugin()).thenReturn("org.eclipse.plugin");
        when(configuration.getAcceptedPluginsPatterns()).thenReturn(newArrayList(Pattern.compile(quote("other.plugin"))));
        sut = new WhitelistedPluginIdPresentPredicate(configuration);
        assertFalse(sut.apply(status));
    }

    @Test
    public void testWhitelistedPluginIdPresentPredicateMatch() {
        when(status.getPlugin()).thenReturn("org.eclipse.plugin");
        when(configuration.getAcceptedPluginsPatterns()).thenReturn(newArrayList(Pattern.compile(quote("org.eclipse.plugin"))));
        sut = new WhitelistedPluginIdPresentPredicate(configuration);
        assertTrue(sut.apply(status));
    }

    @Test
    public void testSkipReportsAbsentPredicateTrue() {
        System.setProperty(SYSPROP_SKIP_REPORTS, "true");
        sut = new SkipReportsAbsentPredicate();
        assertFalse(sut.apply(status));
    }

    @Test
    public void testSkipReportsAbsentPredicateFalse() {
        System.setProperty(SYSPROP_SKIP_REPORTS, "false");
        sut = new SkipReportsAbsentPredicate();
        assertTrue(sut.apply(status));
    }

    @Test
    public void testSkipReportsAbsentPredicateEmpty() {
        System.clearProperty(SYSPROP_SKIP_REPORTS);
        sut = new SkipReportsAbsentPredicate();
        assertTrue(sut.apply(status));
    }

    @Test
    public void testIgnorePatternPredicateOkStatus() {
        IgnorePattern pattern = IgnorePattern.fromString("org.eclipse.equinox.p2.ui:^org.eclipse.equinox.p2.core.ProvisionException:*");
        status = Status.OK_STATUS;
        sut = new IgnorePatternPredicate(newArrayList(pattern));
        assertTrue(sut.apply(status));
    }

    @Test
    public void testIgnorePatternPredicateIgnore() {
        IgnorePattern pattern = IgnorePattern.fromString("org.eclipse.equinox.p2.ui:^org.eclipse.equinox.p2.core.ProvisionException:*");
        status = new Status(IStatus.ERROR, "org.eclipse.equinox.p2.ui", "some", new ProvisionException(""));
        sut = new IgnorePatternPredicate(newArrayList(pattern));
        assertFalse(sut.apply(status));
    }

    @Test
    public void testIgnorePatternPredicateMultiStatusAny() {
        IgnorePattern anyPattern = IgnorePattern.fromString("plugin.id::");
        Status childStatus = new Status(IStatus.ERROR, "plugin.id", "message", new RuntimeException());
        status = new MultiStatus("other.plugin", IStatus.ERROR, new Status[] { childStatus }, "message", new Throwable());
        sut = new IgnorePatternPredicate(newArrayList(anyPattern));
        assertFalse(sut.apply(status));
    }

    @Test
    public void testIgnorePatternPredicateMultiStatusFirst() {
        IgnorePattern firstOnlyPattern = IgnorePattern.fromString("^plugin.id::");
        Status childStatus = new Status(IStatus.ERROR, "plugin.id", "message", new RuntimeException());
        status = new MultiStatus("other.plugin", IStatus.ERROR, new Status[] { childStatus }, "message", new Throwable());
        sut = new IgnorePatternPredicate(newArrayList(firstOnlyPattern));
        assertTrue(sut.apply(status));
    }

    @Test
    public void testEclipseBuildIdPresentPredicateTrue() {
        System.setProperty(SYSPROP_ECLIPSE_BUILD_ID, "build.id");
        sut = new EclipseBuildIdPresentPredicate();
        assertTrue(sut.apply(status));
    }

    @Test
    public void testEclipseBuildIdPresentPredicateFalse() {
        System.clearProperty(SYSPROP_ECLIPSE_BUILD_ID);
        sut = new EclipseBuildIdPresentPredicate();
        assertFalse(sut.apply(status));
    }

    @Test
    public void testSkipNonErrorStatusPredicateOk() {
        when(status.matches(IStatus.OK)).thenReturn(true);
        Predicate<IStatus> sut = new ErrorStatusOnlyPredicate();
        assertFalse(sut.apply(status));
    }

    @Test
    public void testSkipNonErrorStatusPredicateCancel() {
        when(status.matches(IStatus.CANCEL)).thenReturn(true);
        Predicate<IStatus> sut = new ErrorStatusOnlyPredicate();
        assertFalse(sut.apply(status));
    }

    @Test
    public void testSkipNonErrorStatusPredicateInfo() {
        when(status.matches(IStatus.INFO)).thenReturn(true);
        Predicate<IStatus> sut = new ErrorStatusOnlyPredicate();
        assertFalse(sut.apply(status));
    }

    @Test
    public void testSkipNonErrorStatusPredicateWarning() {
        when(status.matches(IStatus.WARNING)).thenReturn(true);
        Predicate<IStatus> sut = new ErrorStatusOnlyPredicate();
        assertFalse(sut.apply(status));
    }

    @Test
    public void testSkipNonErrorStatusPredicateError() {
        when(status.matches(IStatus.ERROR)).thenReturn(true);
        Predicate<IStatus> sut = new ErrorStatusOnlyPredicate();
        assertTrue(sut.apply(status));
    }

    @Test
    public void testWorkbenchRunningPredicateTrue() {
        IWorkbench wb = mock(IWorkbench.class);
        when(wb.isClosing()).thenReturn(true);
        sut = new WorkbenchRunningPredicate(wb);
        assertFalse(sut.apply(status));
    }

    @Test
    public void testWorkbenchRunningPredicateFalse() {
        IWorkbench wb = mock(IWorkbench.class);
        when(wb.isClosing()).thenReturn(false);
        sut = new WorkbenchRunningPredicate(wb);
        assertTrue(sut.apply(status));
    }

    @Test
    public void testHistoryRunningPredicateTrue() {
        ReportHistory history = createTestHistory();
        history.startAsync();
        history.awaitRunning();
        sut = new HistoryReadyPredicate(history);
        assertTrue(sut.apply(status));
    }

    @Test
    public void testHistoryRunningPredicateFalse() {
        ReportHistory history = createTestHistory();
        // history not running
        sut = new HistoryReadyPredicate(history);
        assertFalse(sut.apply(status));
    }

    private static ReportHistory createTestHistory() {
        // isRunning can not be mocked because it is inherited
        // from AbstractIdleService and final
        // TODO consider refactoring ReportHistory to use composition instead of inheritance
        ReportHistory history = new ReportHistory() {
            @Override
            protected Directory createIndexDirectory() throws IOException {
                return new RAMDirectory();
            }
        };
        return history;
    }

    @Test
    public void testAcceptProductPredicateTrue() {
        System.setProperty(SYSPROP_ECLIPSE_PRODUCT, "the.eclipse.product.id");
        when(configuration.getAcceptedProductsPatterns()).thenReturn(newArrayList(Pattern.compile(quote("the.eclipse.product.id"))));
        sut = new AcceptProductPredicate(configuration);
        assertTrue(sut.apply(status));
    }

    @Test
    public void testAcceptProductPredicateFalse() {
        System.setProperty(SYSPROP_ECLIPSE_PRODUCT, "the.eclipse.product.id");
        when(configuration.getAcceptedProductsPatterns()).thenReturn(newArrayList(Pattern.compile(quote("other.product.id"))));
        sut = new AcceptProductPredicate(configuration);
        assertFalse(sut.apply(status));
    }

    @Test
    public void testAcceptProductPredicateNull() {
        System.clearProperty(SYSPROP_ECLIPSE_PRODUCT);
        when(configuration.getAcceptedProductsPatterns()).thenReturn(newArrayList(Pattern.compile(quote("other.product.id"))));
        sut = new AcceptProductPredicate(configuration);
        assertFalse(sut.apply(status));
    }
}
