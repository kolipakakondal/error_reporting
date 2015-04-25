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
import static org.eclipse.epp.internal.logging.aeri.ui.utils.Proxies.proxy;

import java.net.URI;
import java.net.URL;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;

public class CheckServerAvailabilityJob extends Job {

    private Settings settings;

    public CheckServerAvailabilityJob(Settings settings) {
        super("Checking Server Availability");
        this.settings = settings;
    }

    @Override
    protected IStatus run(IProgressMonitor monitor) {
        try {
            URI target = new URL(Constants.PROBLEMS_STATUS_INDEX_ZIP_URL).toURI();
            Request request = Request.Head(target);
            Executor executor = Executor.newInstance();
            Response response = proxy(executor, target).execute(request);
            if (response.returnResponse().getStatusLine().getStatusCode() != 200) {
                settings.setAction(SendAction.IGNORE);
                settings.setRememberSendAction(RememberSendAction.RESTART);
                log(INFO_SERVER_NOT_AVAILABLE);
            }
        } catch (Exception e) {
            log(WARN_SERVER_AVAILABILITY_CHECK_FAILED, e);
        }
        return Status.OK_STATUS;
    }

}
