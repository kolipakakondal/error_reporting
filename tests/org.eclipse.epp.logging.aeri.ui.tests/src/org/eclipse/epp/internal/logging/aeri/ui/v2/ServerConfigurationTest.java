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

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Executor;
import org.eclipse.epp.internal.logging.aeri.ui.utils.WildcardRegexConverter;
import org.eclipse.epp.internal.logging.aeri.ui.v2.ServerConfiguration.IgnorePattern;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.GsonBuilder;

public class ServerConfigurationTest {

    private static final String SERVER_URL = "https://dev.eclipse.org/recommenders/community/confess/v2/discovery";
    private static final String UNKNOWN_SERVER_URL = "https://no-route-to-host12.com/discovery-not";
    private static final String BAD_RESPONSE_SERVER_URL = "https://dev.eclipse.org/recommenders/community/confess/v2/discovery-fail";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testDownloadDiscovery() throws Exception {
        AeriServer server = new AeriServer(Executor.newInstance(), folder.newFile());
        server.refreshConfiguration(SERVER_URL, null);
        ServerConfiguration configuration = server.getConfiguration();
        long timestamp = configuration.getTimestamp();
        assertThat(timestamp, greaterThan(0L));
        assertThat(timestamp, lessThanOrEqualTo(System.currentTimeMillis()));
    }

    @Test
    public void testPatternsInitialized() throws Exception {
        AeriServer server = new AeriServer(Executor.newInstance(), folder.newFile());
        server.refreshConfiguration(SERVER_URL, null);
        ServerConfiguration configuration = server.getConfiguration();
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
        for (String p : configuration.getIgnoredStatuses()) {
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
        AeriServer server = new AeriServer(Executor.newInstance(), folder.newFile());
        server.refreshConfiguration(SERVER_URL, null);
        ServerConfiguration configuration = server.getConfiguration();
        server.saveConfiguration();
        server.setConfiguration(null);
        server.loadConfiguration();

        assertThat(EqualsBuilder.reflectionEquals(configuration, server.getConfiguration()), is(true));
    }

    @Test(expected = UnknownHostException.class)
    public void testUnknownHostException() throws Exception {
        AeriServer server = new AeriServer(Executor.newInstance(), folder.newFile());
        server.refreshConfiguration(UNKNOWN_SERVER_URL, null);
    }

    // TODO we may use some test urls on the server side to make sure they work as expected - including timeouts=
    @Test(expected = HttpResponseException.class)
    public void testHttpResponseExceptionNotFound() throws Exception {
        AeriServer server = new AeriServer(Executor.newInstance(), folder.newFile());
        server.refreshConfiguration(BAD_RESPONSE_SERVER_URL, null);
    }

    @Test
    public void testCompatibility() throws ClientProtocolException, IOException {
        // validates, that the client and the server share exactly the same fields in configuration
        // additional fields on the client have to be transient or must be excluded from the test
        String asString = AeriServer.request(URI.create(SERVER_URL), Executor.newInstance()).returnContent().asString();
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) new GsonBuilder().create().fromJson(asString, Object.class);
        Set<String> serverFields = map.keySet();
        map.remove("id");
        List<Field> classFieldsAll = Lists.newArrayList(ServerConfiguration.class.getDeclaredFields());
        Set<String> classFields = Sets.newHashSet();
        for (int i = classFieldsAll.size() - 1; i >= 0; i--) {
            Field field = classFieldsAll.get(i);
            if (Modifier.isTransient(field.getModifiers())) {
                // local helper fields
                continue;
            }
            if (field.getName().equals("problemsZipLastDownloadTimestamp") || field.getName().equals("timestamp")) {
                // not transient, but will be created and persisted on the client
                continue;
            }
            classFields.add(field.getName());
        }
        for (String s : serverFields) {
            assertThat("Missing value in client ServerConfiguration", classFields, hasItem(s));
        }
        for (String s : classFields) {
            assertThat("Missing value on remote server", serverFields, hasItem(s));
        }
    }
}
