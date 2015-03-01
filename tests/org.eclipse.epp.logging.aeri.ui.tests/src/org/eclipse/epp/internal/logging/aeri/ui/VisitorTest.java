/**
 * Copyright (c) 2014 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui;

import static org.eclipse.epp.internal.logging.aeri.ui.model.ModelFactory.eINSTANCE;
import static org.eclipse.epp.internal.logging.aeri.ui.model.Reports.visit;

import org.eclipse.epp.internal.logging.aeri.ui.model.util.ModelSwitch;
import org.junit.Test;

public class VisitorTest {

    ModelSwitch<Void> someVisitor = new ModelSwitch<Void>() {
    };

    @Test
    public void testNullSafe() {
        visit(eINSTANCE.createStatus(), someVisitor);
        visit(eINSTANCE.createStackTraceElement(), someVisitor);
        visit(eINSTANCE.createErrorReport(), someVisitor);
        visit(eINSTANCE.createThrowable(), someVisitor);
        visit(eINSTANCE.createBundle(), someVisitor);
    }
}
