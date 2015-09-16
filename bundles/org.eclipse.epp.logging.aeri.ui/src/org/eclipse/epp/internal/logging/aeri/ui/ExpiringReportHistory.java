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

import static org.eclipse.epp.internal.logging.aeri.ui.model.Reports.*;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.Reports;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ExpiringReportHistory {
    private static final int EXPIRES_ON_IN_MINUTES = 60;

    private DelayQueue<HistoryEntry> history = new DelayQueue<HistoryEntry>();

    public void add(ErrorReport report) {
        removeOutdated();
        HistoryEntry entry = new HistoryEntry(report, expiresOn());
        history.add(entry);
    }

    public ImmutableList<HistoryEntry> elements() {
        removeOutdated();
        return ImmutableList.copyOf(history);
    }

    private long expiresOn() {
        return System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(EXPIRES_ON_IN_MINUTES);
    }

    private void removeOutdated() {
        history.drainTo(Lists.newLinkedList());
    }

    private static class HistoryEntry implements Delayed {

        private long expiresOn;
        private final String reportExactIndentityHash;
        private final String reportTraceIdentityHash;

        public HistoryEntry(ErrorReport report, long expiresOn) {
            reportExactIndentityHash = Reports.exactIdentityHash(report);
            reportTraceIdentityHash = Reports.traceIdentityHash(report);
            this.expiresOn = expiresOn;
        }

        public String getReportExactIndentityHash() {
            return reportExactIndentityHash;
        }

        public String getReportTraceIdentityHash() {
            return reportTraceIdentityHash;
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expiresOn - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    public boolean seenExact(ErrorReport report) {
        String hash = exactIdentityHash(report);
        for (HistoryEntry e : elements()) {
            if (e.getReportExactIndentityHash().equals(hash)) {
                return true;
            }
        }
        return false;
    }

    public boolean seenSimilarTrace(ErrorReport report) {
        String hash = traceIdentityHash(report);
        for (HistoryEntry e : elements()) {
            if (e.getReportTraceIdentityHash().equals(hash)) {
                return true;
            }
        }
        return false;
    }

}
