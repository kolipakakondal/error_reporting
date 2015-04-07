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

import static org.eclipse.core.runtime.Status.*;
import static org.eclipse.epp.internal.logging.aeri.ui.Constants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.epp.internal.logging.aeri.ui.log.ReportHistory;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.HistoryReadyPredicate;
import org.eclipse.epp.internal.logging.aeri.ui.log.StatusPredicates.WorkbenchRunningPredicate;
import org.eclipse.ui.IWorkbench;
import org.junit.Test;

import com.google.common.base.Predicate;

public class FiltersTest {

    private static final Status WARNING = new Status(IStatus.WARNING, "some", "some");
    private static final Status ERROR = new Status(IStatus.ERROR, "some", "some");

    @Test
    public void testEclipseBuildIdPredicate() {
        Predicate<IStatus> sut = new StatusPredicates.EclipseBuildIdPresentPredicate();
        assertThat(sut.apply(null), is(false));
        System.setProperty(SYSPROP_ECLIPSE_BUILD_ID, "some-value");
        assertThat(sut.apply(null), is(true));
        System.clearProperty(SYSPROP_ECLIPSE_BUILD_ID);
    }

    @Test
    public void testSkipReportsPredicateTest() {
        Predicate<IStatus> sut = new StatusPredicates.SkipReportsAbsentPredicate();
        assertThat(sut.apply(null), is(true));

        System.setProperty(SYSPROP_SKIP_REPORTS, "true");
        assertThat(sut.apply(null), is(false));

        System.setProperty(SYSPROP_SKIP_REPORTS, "false");
        assertThat(sut.apply(null), is(true));

        System.clearProperty(SYSPROP_SKIP_REPORTS);
    }

    @Test
    public void testSkipNonErrorStatusPredicateTest() {
        Predicate<IStatus> sut = new StatusPredicates.ErrorStatusOnlyPredicate();
        assertThat(sut.apply(OK_STATUS), is(false));
        assertThat(sut.apply(CANCEL_STATUS), is(false));
        assertThat(sut.apply(WARNING), is(false));
        assertThat(sut.apply(ERROR), is(true));
    }

    @Test
    public void testWorkbenchRunningPredicate() {
        IWorkbench wb = mock(IWorkbench.class);
        when(wb.isClosing()).thenReturn(true, false);
        WorkbenchRunningPredicate sut = new StatusPredicates.WorkbenchRunningPredicate(wb);

        assertThat(sut.apply(null), is(false));
        assertThat(sut.apply(null), is(true));
    }

    @Test
    public void testHistoryRunningPredicate() {
        ReportHistory history = new ReportHistory() {
            @Override
            protected Directory createIndexDirectory() throws IOException {
                return new RAMDirectory();
            }
        };
        HistoryReadyPredicate sut = new StatusPredicates.HistoryReadyPredicate(history);

        assertThat(sut.apply(null), is(false));
        history.startAsync();
        history.awaitRunning();
        assertThat(sut.apply(null), is(true));
    }
}