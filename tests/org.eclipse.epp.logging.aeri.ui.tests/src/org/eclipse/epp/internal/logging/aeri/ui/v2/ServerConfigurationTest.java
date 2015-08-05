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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Executor;
import org.eclipse.epp.internal.logging.aeri.ui.utils.WildcardRegexConverter;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration.IgnorePattern;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ServerConfigurationTest {

    private static final String SERVER_URL = "https://dev.eclipse.org/recommenders/community/confess/v2/discovery";
    private static final String UNKNOWN_SERVER_URL = "https://no-route-to-host12.com/discovery-not";
    private static final String BAD_RESPONSE_SERVER_URL = "https://dev.eclipse.org/recommenders/community/confess/v2/discovery-fail";

    private Executor executor = Executor.newInstance();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testDownloadDiscovery() throws Exception {
        URI target = new URI(SERVER_URL);
        ServerConfiguration configuration = AeriServer.download(target, executor);
        long timestamp = configuration.getTimestamp();
        assertThat(timestamp, greaterThan(0L));
        assertThat(timestamp, lessThanOrEqualTo(System.currentTimeMillis()));
    }

    @Test
    public void testPatternsInitialized() throws Exception {
        URI target = new URI(SERVER_URL);
        ServerConfiguration configuration = AeriServer.download(target, executor);
        // packages
        for (String p : configuration.getAcceptedPackages()) {
            Pattern pattern = WildcardRegexConverter.convert(p);
            assertThat(containsPatternFromSameString(configuration.getAcceptedPackagesPatterns(), pattern), is(true));
        }
        // plugins
        for (String p : configuration.getAcceptedPlugins()) {
            Pattern pattern = WildcardRegexConverter.convert(p);
            assertThat(containsPatternFromSameString(configuration.getAcceptedPluginsPatterns(), pattern), is(true));
        }
        // products
        for (String p : configuration.getAcceptedProducts()) {
            Pattern pattern = WildcardRegexConverter.convert(p);
            assertThat(containsPatternFromSameString(configuration.getAcceptedProductsPatterns(), pattern), is(true));
        }
        // ignore patterns
        for (String p : configuration.getIgnoredPluginMessages()) {
            IgnorePattern pattern = IgnorePattern.fromString(p);
            assertThat(containsIgnorePatternFromSameString(configuration.getIgnoredPluginMessagesPatterns(), pattern), is(true));
        }
    }

    private static boolean containsPatternFromSameString(List<Pattern> patterns, Pattern p) {
        for (Pattern pp : patterns) {
            if (pp.toString().equals(p.toString())) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsIgnorePatternFromSameString(List<IgnorePattern> patterns, IgnorePattern p) {
        for (IgnorePattern ip : patterns) {
            boolean ep = ip.getExceptionPattern().toString().equals(p.getExceptionPattern().toString());
            boolean mp = ip.getMessagePattern().toString().equals(p.getMessagePattern().toString());
            boolean pp = ip.getPluginPattern().toString().equals(p.getPluginPattern().toString());
            boolean epos = ip.getExceptionPosition().equals(p.getExceptionPosition());
            if (ep && mp && pp && epos) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testSaveAndLoad() throws Exception {
        URI target = new URI(SERVER_URL);
        ServerConfiguration originalConfig = AeriServer.download(target, executor);
        File file = folder.newFile();
        AeriServer.saveToFile(file, originalConfig);
        ServerConfiguration loadedConfig = AeriServer.loadFromFile(file);

        assertThat(EqualsBuilder.reflectionEquals(originalConfig, loadedConfig), is(true));
    }

    @Test(expected = UnknownHostException.class)
    public void testUnknownHostException() throws Exception {
        URI target = new URI(UNKNOWN_SERVER_URL);
        AeriServer.download(target, executor);
    }

    // TODO we may use some test urls on the server side to make sure they work as expected - including timeouts=
    @Test(expected = HttpResponseException.class)
    public void testHttpResponseExceptionNotFound() throws Exception {
        URI target = new URI(BAD_RESPONSE_SERVER_URL);
        AeriServer.download(target, executor);
    }
}
