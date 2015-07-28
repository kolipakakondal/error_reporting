package org.eclipse.epp.internal.logging.aeri.ui.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ReportsToShortClassNameTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
        // @formatter:off
            { null, "" },
            { "  ", "" },
            { "org", "org" },
            { "org.eclipse", "eclipse" },
            { "org.eclipse.epp.ClassName", "ClassName"},
            { "org.eclipse.epp.ClassName$Other", "Other" },
            { "org.eclipse.epp.ClassName$Other$Deeper", "Deeper" },
            { "org.eclipse.epp.internal.logging.aeri.ui.log.StandInExceptionProvider$StandInException", "StandInException" },
        // @formatter:on
        });
    }

    @Parameter(1)
    public String expected;

    @Parameter(0)
    public String value;

    @Test
    public void test() {
        String actual = Reports.toShortClassName(value);
        assertEquals(expected, actual);
    }
}
