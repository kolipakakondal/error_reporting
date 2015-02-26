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

import java.util.Properties;

import org.junit.rules.ExternalResource;

public class RetainSystemProperties extends ExternalResource {

    private Properties properties;

    @Override
    protected void before() {
        properties = (Properties) System.getProperties().clone();
    }

    @Override
    protected void after() {
        System.setProperties(properties);
    }
}
