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

import org.eclipse.epp.internal.logging.aeri.ui.model.impl.VisitorImpl;
import org.junit.Test;

public class VisitorTest {

    VisitorImpl someVisitor = new VisitorImpl() {
    };

    @Test
    public void testNullSafe() {
        eINSTANCE.createStatus().accept(someVisitor);
        eINSTANCE.createErrorReport().accept(someVisitor);
        eINSTANCE.createStackTraceElement().accept(someVisitor);
        eINSTANCE.createThrowable().accept(someVisitor);
        eINSTANCE.createBundle().accept(someVisitor);
    }
}
