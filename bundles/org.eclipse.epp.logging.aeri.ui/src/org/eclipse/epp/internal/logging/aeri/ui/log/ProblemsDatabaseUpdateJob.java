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

import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;

import java.io.File;
import java.util.concurrent.CancellationException;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.utils.Zips;
import org.eclipse.epp.internal.logging.aeri.ui.v2.AeriServer;

import com.google.common.io.Files;

public class ProblemsDatabaseUpdateJob extends Job {

    private ProblemsDatabaseService service;
    private AeriServer server;
    private Settings settings;

    public ProblemsDatabaseUpdateJob(ProblemsDatabaseService service, AeriServer server, Settings settings) {
        super("Updating Error Reports Database");
        this.service = service;
        this.server = server;
        this.settings = settings;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        SubMonitor progress = SubMonitor.convert(monitor, 3);
        progress.beginTask("Checking...", 1000);
        if (!server.isProblemsDatabaseOutdated()) {
            return Status.OK_STATUS;
        }
        try {
            progress.subTask("Checking remote database");
            File tempRemoteIndexZip = File.createTempFile("problems-index", ".zip");
            int downloadStatus = server.downloadDatabase(tempRemoteIndexZip, progress);
            if (downloadStatus == HttpStatus.SC_NOT_MODIFIED) {
                return Status.OK_STATUS;
            } else if (downloadStatus != HttpStatus.SC_OK) {
                // Could not access problems.zip for whatever reason; switch off error reporting until restart.
                settings.setAction(SendAction.IGNORE);
                settings.setRememberSendAction(RememberSendAction.RESTART);
                log(INFO_SERVER_NOT_AVAILABLE);
                return Status.OK_STATUS;
            }

            progress.worked(1);
            File tempDir = Files.createTempDir();
            progress.subTask("Replacing local database");
            Zips.unzip(tempRemoteIndexZip, tempDir);
            service.replaceContent(tempDir);
            progress.worked(1);

            // cleanup files
            tempRemoteIndexZip.delete();
            FileUtils.deleteDirectory(tempDir);
            progress.worked(1);

            return Status.OK_STATUS;
        } catch (CancellationException e) {
            return Status.CANCEL_STATUS;
        } catch (Exception e) {
            log(WARN_INDEX_UPDATE_FAILED, e);
            return Status.OK_STATUS;
        } finally {
            monitor.done();
        }
    }

}
