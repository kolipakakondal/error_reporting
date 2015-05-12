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
package org.eclipse.epp.internal.logging.aeri.ui.utils;

import static com.google.common.collect.Iterables.size;
import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;

import com.google.common.base.Throwables;

public final class Jobs {

    private Jobs() {
        throw new IllegalStateException("Not meant to be instantiated"); //$NON-NLS-1$
    }

    public static final ISchedulingRule EXCLUSIVE = new SequentialSchedulingRule();

    public static IProgressMonitor getProgressGroup() {
        return Job.getJobManager().createProgressGroup();
    }

    public static IProgressMonitor parallel(String task, Job... jobs) {
        return parallel(task, asList(jobs));
    }

    public static IProgressMonitor parallel(String task, Iterable<Job> jobs) {
        IProgressMonitor group = getProgressGroup();
        try {
            group.beginTask(task, size(jobs));
            for (Job job : jobs) {
                job.setProgressGroup(group, 1);
                job.schedule();
            }
            for (Job job : jobs) {
                job.join();
            }
        } catch (InterruptedException e) {
            // ignore
        } finally {
            group.done();
        }
        return group;
    }

    public static IProgressMonitor sequential(String task, Job... jobs) {
        return sequential(task, Arrays.asList(jobs));
    }

    public static IProgressMonitor sequential(String task, Iterable<Job> jobs) {
        ISchedulingRule rule = new SequentialSchedulingRule();
        IProgressMonitor group = getProgressGroup();
        try {
            group.beginTask(task, size(jobs));
            for (Job job : jobs) {
                job.setRule(rule);
                job.setProgressGroup(group, 1);
                job.schedule();
                job.join();
            }
        } catch (InterruptedException e) {
            // ignore

        } finally {
            group.done();
        }
        return group;
    }

    public static final class SequentialSchedulingRule implements ISchedulingRule {
        @Override
        public boolean isConflicting(ISchedulingRule rule) {
            return rule == this;
        }

        @Override
        public boolean contains(ISchedulingRule rule) {
            return rule == this;
        }
    }

    public static void wait(IProgressMonitor group, List<Job> jobs) {
        for (Job job : jobs) {
            try {
                job.join();
            } catch (InterruptedException e) {
                Throwables.propagate(e);
            }
        }
        group.done();
    }

}
