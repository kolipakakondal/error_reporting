package org.eclipse.epp.internal.logging.aeri.ui.model;

import static org.eclipse.epp.internal.logging.aeri.ui.model.Reports.newThrowable;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class LinkageErrorAnalyserMessageParserTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays
                .asList(new Object[][] {
        // @formatter:off
            { "org.eclipse.ui.views.tasklist", new NoClassDefFoundError("org/eclipse/ui/views/tasklist/ITaskListResourceAdapter") },
            { "org.eclipse.egit.ui", new NoClassDefFoundError("org/eclipse/egit/ui/Activator$1") },
            { "org.eclipse.egit.ui", new NoClassDefFoundError("org/eclipse/egit/ui/Activator$1$1") },
            { null, new NoClassDefFoundError("Test") },
            { null, new NoClassDefFoundError("Test$1") },
            { "some", new NoClassDefFoundError("some/Test") },
            {"org.eclipse.ui.views.tasklist", new ClassNotFoundException("org.eclipse.ui.views.tasklist.ITaskListResourceAdapter cannot be found by org.eclipse.ui.ide_3.11.0.v20150309-2044")},
            {"org.eclipse.mylyn.internal.provisional.commons.ui", new ClassNotFoundException("org.eclipse.mylyn.internal.provisional.commons.ui.CommonImages")},
            {"org.apache.http.params", new LinkageError("org/apache/http/params/HttpParams")},

        // @formatter:on
        });
    }

    @Parameter(0)
    public String expected;

    @Parameter(1)
    public java.lang.Throwable exception;

    @Test
    public void test() {
        Throwable throwable = newThrowable(exception);
        String actual = LinkageErrorAnalyser.extractProblematicPackage(throwable).orNull();
        assertEquals(expected, actual);
    }
}
