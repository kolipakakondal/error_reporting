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
package org.eclipse.epp.internal.logging.aeri.ui.utils;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class WildcardRegexConverterTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            // @formatter:off
                { "org.eclipse.*", "org.eclipse.epp.logging", true },
                { "org.eclipse.", "org.eclipse.epp.logging", false },
                { "org.eclipse.*.foo", "org.eclipse.epp.logging", false },
                { "org.eclipse.*.foo", "org.eclipse.epp.logging.foo", true },
                { "*.test", "org.eclipse.epp.logging.test", true },
                { "*.test", "org.eclipse.epp.logging.test.other", false },
                { "*.test.*", "org.eclipse.epp.logging.test.other", true },
                //escape other regex
                { ".", "a", false },
                //no match for whitespace
                { "*", "a b c", true },
                { "*", "abc", true },
                //? wildcard not supported
                { "?", "a", false },
                { "?", "?", true },
            // @formatter:on
        });
    }

    @Parameter(0)
    public String wildcardString;

    @Parameter(1)
    public String input;

    @Parameter(2)
    public boolean expectedResult;

    @Test
    public void test() {
        assertThat(WildcardRegexConverter.convert(wildcardString).matcher(input).matches(), is(expectedResult));
    }

}
