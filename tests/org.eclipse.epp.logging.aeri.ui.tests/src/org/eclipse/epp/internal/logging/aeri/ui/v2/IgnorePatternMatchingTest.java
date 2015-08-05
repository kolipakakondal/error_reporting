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
package org.eclipse.epp.internal.logging.aeri.ui.v2;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration.IgnorePattern;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class IgnorePatternMatchingTest {

    private static final Throwable T_RE = new RuntimeException();
    private static final Throwable T_RE_NPE = new RuntimeException(new NullPointerException());

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // @formatter:off
                    //single fields + wildcards rest
                    { "org.eclipse.epp.logging.aeri.ui:*:*", "org.eclipse.epp.logging.aeri.ui", T_RE, "any message", true },
                    { "*:java.lang.RuntimeException:*", "org.eclipse.epp.logging.aeri.ui", T_RE, "any message", true },
                    { "*:*:special message with wildcard *", "org.eclipse.epp.logging.aeri.ui", T_RE, "special message with wildcard and more text", true },
                    //single fields +  no wildcards rest
                    { "org.eclipse.epp.logging.aeri.ui::", "org.eclipse.epp.logging.aeri.ui", T_RE, "any message", true },
                    { ":java.lang.RuntimeException:", "org.eclipse.epp.logging.aeri.ui", T_RE, "any message", true },
                    { "::special message with wildcard *", "org.eclipse.epp.logging.aeri.ui", T_RE, "special message with wildcard and more text", true },
                    //exception patterns
                    { "*:java.lang.NullPointerException:*", "org.eclipse.epp.logging.aeri.ui", T_RE_NPE, "any message", true },
                    { "*:^java.lang.NullPointerException:*", "org.eclipse.epp.logging.aeri.ui", T_RE_NPE, "any message", false },
                    { "*:$java.lang.NullPointerException:*", "org.eclipse.epp.logging.aeri.ui", T_RE_NPE, "any message", true },
                    { "*:java.lang.NullPointerException:*", "org.eclipse.epp.logging.aeri.ui", T_RE, "any message", false },
                    { "*:$java.lang.NullPointerException:*", "org.eclipse.epp.logging.aeri.ui", T_RE, "any message", false },
                    //exception patterns + wildcard
                    { "*:^*NullPointer*:*", "org.eclipse.epp.logging.aeri.ui", T_RE_NPE, "any message", false },
                    { "*:^*Runtime*:*", "org.eclipse.epp.logging.aeri.ui", T_RE_NPE, "any message", true },
                    { "*:$*NullPointer*:*", "org.eclipse.epp.logging.aeri.ui", T_RE_NPE, "any message", true },
                    { "*:*NullPointer*:*", "org.eclipse.epp.logging.aeri.ui", T_RE_NPE, "any message", true },
                    //plugin wildcard
                    { "org.eclipse.*:*:*", "org.eclipse.epp.logging.aeri.ui", T_RE, "any message", true },
                    { "org.eclipse.*:*:*", "not.org.eclipse.epp.logging.aeri.ui", T_RE, "any message", false },
                    { "*.aeri.ui:*:*", "org.eclipse.epp.logging.aeri.ui", T_RE, "any message", true },
                    { "*.aeri.ui:*:*", "org.eclipse.epp.logging.aeri.ui.view", T_RE, "any message", false },
                    //plugin or message not matching
                    { "foo:*:bar", "not.foo", T_RE, "not bar", false },
                    { "foo:*:bar", "foo", T_RE, "not bar", false },
                // @formatter:on
        });
    }

    @Parameter(0)
    public String input;

    @Parameter(1)
    public String pluginId;

    @Parameter(2)
    public Throwable exception;

    @Parameter(3)
    public String message;

    @Parameter(4)
    public boolean expected;

    @Test
    public void test() {
        IgnorePattern pattern = IgnorePattern.fromString(input);
        assertThat(pattern.matches(pluginId, exception, message), is(expected));
    }
}
