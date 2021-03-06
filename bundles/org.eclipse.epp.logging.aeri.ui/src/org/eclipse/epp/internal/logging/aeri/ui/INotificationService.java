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

import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus;
import org.eclipse.epp.internal.logging.aeri.ui.model.ServerResponse;

public interface INotificationService {

    void showWelcomeNotification();

    void showNewReportsAvailableNotification(ErrorReport report);

    void showNewResponseNotification(ServerResponse response);

    void showNeedInfoNotification(ErrorReport report, ProblemStatus status);

    void showBugFixedInfo(ErrorReport report, ProblemStatus status);
}
