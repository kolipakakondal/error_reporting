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

import org.eclipse.epp.internal.logging.aeri.ui.utils.WildcardRegexConverter;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration.IgnorePattern;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration.IgnorePattern.ExceptionStacktracePosition;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration.IgnorePattern.StatusChainPosition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class IgnorePatternFromStringTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            // @formatter:off
                { "foo:bar:baz", "foo", "bar", "baz", ExceptionStacktracePosition.ANY, StatusChainPosition.ANY },
                { "foo:^bar:baz", "foo", "bar", "baz", ExceptionStacktracePosition.TOP, StatusChainPosition.ANY },
                { "^foo:$bar:baz", "foo", "bar", "baz", ExceptionStacktracePosition.BOTTOM, StatusChainPosition.FIRST },
                { "^f*oo:*bar:ba*z", "f*oo", "*bar", "ba*z", ExceptionStacktracePosition.ANY, StatusChainPosition.FIRST },
                { "foo:^*bar:baz", "foo", "*bar", "baz", ExceptionStacktracePosition.TOP, StatusChainPosition.ANY },
                { "^foo:$*bar:baz", "foo", "*bar", "baz", ExceptionStacktracePosition.BOTTOM, StatusChainPosition.FIRST },
            // @formatter:on
        });
    }

    @Parameter(0)
    public String input;

    @Parameter(1)
    public String pluginPattern;

    @Parameter(2)
    public String exceptionPattern;

    @Parameter(3)
    public String messagePattern;

    @Parameter(4)
    public ExceptionStacktracePosition exceptionStacktracePosition;

    @Parameter(5)
    public StatusChainPosition statusChainPosition;

    @Test
    public void test() {
        IgnorePattern pattern = IgnorePattern.fromString(input);
        assertThat(pattern.getPluginPattern().toString(), is(WildcardRegexConverter.convert(pluginPattern).toString()));
        assertThat(pattern.getExceptionPattern().toString(), is(WildcardRegexConverter.convert(exceptionPattern).toString()));
        assertThat(pattern.getMessagePattern().toString(), is(WildcardRegexConverter.convert(messagePattern).toString()));
        assertThat(pattern.getExceptionPosition(), is(exceptionStacktracePosition));
        assertThat(pattern.getStatusChainPosition(), is(statusChainPosition));
    }
}
