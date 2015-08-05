package org.eclipse.epp.internal.logging.aeri.ui.model;

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.epp.internal.logging.aeri.ui.model.Reports.newThrowable;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ErrorAnalyserMessageParserTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
        // @formatter:off
            { newArrayList("org.eclipse.ui.views.tasklist"), new NoClassDefFoundError("org/eclipse/ui/views/tasklist/ITaskListResourceAdapter") },
            { newArrayList("org.eclipse.egit.ui"), new NoClassDefFoundError("org/eclipse/egit/ui/Activator$1") },
            { newArrayList("org.eclipse.egit.ui"), new NoClassDefFoundError("org/eclipse/egit/ui/Activator$1$1") },
            { newArrayList(), new NoClassDefFoundError("Test") },
            { newArrayList(), new NoClassDefFoundError("Test$1") },
            { newArrayList("some"), new NoClassDefFoundError("some/Test") },
            { newArrayList("org.eclipse.ui.views.tasklist"), new ClassNotFoundException("org.eclipse.ui.views.tasklist.ITaskListResourceAdapter cannot be found by org.eclipse.ui.ide_3.11.0.v20150309-2044")},
            { newArrayList("org.eclipse.mylyn.internal.provisional.commons.ui"), new ClassNotFoundException("org.eclipse.mylyn.internal.provisional.commons.ui.CommonImages")},
            { newArrayList("org.apache.http.params"), new LinkageError("org/apache/http/params/HttpParams")},
            { newArrayList("org.eclipse.recommenders.utils"), new NoSuchMethodError("org.eclipse.recommenders.utils.Checks.anyIsNull([Ljava/lang/Object;)")},
            { newArrayList(), new NoSuchMethodError("HIDDEN")},
            { newArrayList(
                    "org.eclipse.egit.ui.internal.credentials",
                    "org.eclipse.jgit.transport",
                    "org.eclipse.recommenders.internal.snipmatch.rcp",
                    "org.eclipse.jgit.lib",
                    "org.eclipse.swt.widgets",
                    "org.eclipse.egit.core.op"),
                new VerifyError("Bad type on operand stack Exception Details: Location: org/eclipse/recommenders/internal/snipmatch/rcp/EclipseGitSnippetRepository.push(Lorg/eclipse/jgit/lib/Repository;Lorg/eclipse/swt/widgets/Shell;)V @47: invokevirtual Reason: Type 'org/eclipse/egit/ui/internal/credentials/EGitCredentialsProvider' (current frame, stack[1]) is not assignable to 'org/eclipse/jgit/transport/CredentialsProvider' Current Frame: bci: @47 flags: { } locals: { 'org/eclipse/recommenders/internal/snipmatch/rcp/EclipseGitSnippetRepository', 'org/eclipse/jgit/lib/Repository', 'org/eclipse/swt/widgets/Shell', 'org/eclipse/jgit/transport/RemoteConfig', integer, 'org/eclipse/egit/core/op/PushOperation' } stack: { 'org/eclipse/egit/core/op/PushOperation', 'org/eclipse/egit/ui/internal/credentials/EGitCredentialsProvider' } Bytecode: 0x0000000: 2bb8 032b 4eb8 0316 b603 1713 0146 b903 0x0000010: 7f02 0036 04bb 016f 592b 2db6 0338 0315 0x0000020: 04b7 0315 3a05 1905 bb01 7359 b703 1db6 0x0000030: 0314 bb01 8459 2ab2 02ec 1905 b703 513a 0x0000040: 0619 062a b402 e2b6 030e 1906 bb01 8559 0x0000050: 2a2c b703 52b6 030d 1906 b603 0cb1")},
        // @formatter:on
        });
    }

    @Parameter(0)
    public List<String> expected;

    @Parameter(1)
    public java.lang.Throwable exception;

    @Test
    public void test() {
        Throwable throwable = newThrowable(exception);
        List<String> actual = ErrorAnalyser.extractProblematicPackage(throwable);
        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }
}
