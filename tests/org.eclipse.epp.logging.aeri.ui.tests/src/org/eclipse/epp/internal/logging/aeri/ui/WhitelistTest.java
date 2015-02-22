package org.eclipse.epp.internal.logging.aeri.ui;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;

import java.util.Collection;

import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.junit.Assert;
import org.junit.Test;

public class WhitelistTest {

    Settings s = PreferenceInitializer.getDefault();

    @Test
    public void testWhitelistPackages() throws Exception {
        Collection<String> sut = s.getWhitelistedPackages();
        Assert.assertThat(sut, not(hasItems("")));
        Assert.assertThat(
                sut,
                hasItems("org.eclipse.", "org.apache.", "ch.qos.", "org.slf4j.", "java.", "javax.", "javafx.", "sun.", "com.sun.",
                        "com.codetrails.", "org.osgi.", "com.google."));

    }

    @Test
    public void testWhitelistPlugins() throws Exception {
        Collection<String> sut = s.getWhitelistedPluginIds();
        Assert.assertThat(sut, not(hasItems("")));
        Assert.assertThat(sut, hasItems("org.eclipse.", "org.apache.log4j", "com.codetrails"));

    }

}
