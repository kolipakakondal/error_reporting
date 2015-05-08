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
package org.eclipse.epp.internal.logging.aeri.ui.log;

import static org.hamcrest.Matchers.*;

import java.util.Collection;

import org.eclipse.epp.internal.logging.aeri.ui.model.PreferenceInitializer;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.junit.Assert;
import org.junit.Test;

public class WhitelistTest {

    Settings s = PreferenceInitializer.getDefault();

    @Test
    public void testWhitelistPackages() throws Exception {
        Collection<String> sut = s.getWhitelistedPackages();
        Assert.assertThat(sut, not(hasItems("")));
        Assert.assertThat(sut, hasItems("org.eclipse.", "org.apache.", "ch.qos.", "org.slf4j.", "java.", "javax.", "javafx.", "sun.",
                "com.sun.", "com.codetrails.", "org.osgi.", "com.google."));

    }

    @Test
    public void testWhitelistPlugins() throws Exception {
        Collection<String> sut = s.getWhitelistedPluginIds();
        Assert.assertThat(sut, not(hasItems("")));
        Assert.assertThat(sut, hasItems("org.eclipse.", "org.apache.log4j", "com.codetrails"));

    }

}
