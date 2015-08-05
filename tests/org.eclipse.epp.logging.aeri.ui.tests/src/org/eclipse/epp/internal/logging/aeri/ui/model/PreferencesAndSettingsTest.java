/**
 * Copyright (c) 2014 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Daniel Haftstein - initial tests.
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import static org.eclipse.epp.internal.logging.aeri.ui.Constants.PLUGIN_ID;
import static org.eclipse.epp.internal.logging.aeri.ui.model.PreferenceInitializer.PROP_SEND_ACTION;
import static org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction.*;
import static org.eclipse.epp.internal.logging.aeri.ui.model.SendAction.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.junit.Before;
import org.junit.Test;

public class PreferencesAndSettingsTest {

    private Settings settings;
    private ScopedPreferenceStore store;

    @Before
    public void setUp() {
        new PreferenceInitializer().initializeDefaultPreferences();
        settings = PreferenceInitializer.getDefault();
        store = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGIN_ID);
    }

    @Test
    public void testDefaultActions() {
        assertThat(settings.getAction(), is(SendAction.ASK));
        assertThat(settings.getRememberSendAction(), is(NONE));
    }

    @Test
    public void testSettingsUpdateStore() {
        String sendActionBefore = "any other value";
        store.putValue(PROP_SEND_ACTION, sendActionBefore);
        settings.setAction(SendAction.IGNORE);
        assertThat(store.getString(PROP_SEND_ACTION), not(is(sendActionBefore)));
    }

    @Test
    public void testStoreUpdateSettings() {
        SendAction sendActionBefore = settings.getAction();
        store.setValue(PROP_SEND_ACTION, SendAction.IGNORE.name());
        assertThat(settings.getAction(), not(is(sendActionBefore)));
    }

    @Test
    public void test24hReset() throws Exception {
        Field settingsField = PreferenceInitializer.class.getDeclaredField("settings");
        Field msPerDayField = PreferenceInitializer.class.getDeclaredField("MS_PER_DAY");
        settingsField.setAccessible(true);
        msPerDayField.setAccessible(true);

        msPerDayField.set(null, 1000);

        settings = PreferenceInitializer.getDefault();
        settings.setAction(IGNORE);
        settings.setRememberSendAction(HOURS_24);
        settings.setRememberSendActionPeriodStart(System.currentTimeMillis());

        // clear to enforce reload
        settingsField.set(null, null);

        settings = PreferenceInitializer.getDefault();
        // time not elapsed
        assertThat(settings.getAction(), is(IGNORE));

        // clear again for another reload
        settingsField.set(null, null);

        // now wait for reset-time
        Thread.sleep(1500);

        settings = PreferenceInitializer.getDefault();
        // time elapsed, fallback to ASK
        assertThat(settings.getAction(), is(ASK));
    }

    @Test
    public void testRestartReset() throws Exception {
        Field settingsField = PreferenceInitializer.class.getDeclaredField("settings");
        settingsField.setAccessible(true);

        settings = PreferenceInitializer.getDefault();
        settings.setAction(IGNORE);
        settings.setRememberSendAction(RESTART);

        // clear to enforce reload
        settingsField.set(null, null);

        settings = PreferenceInitializer.getDefault();

        // settings are reloaded, fallback to ASK
        assertThat(settings.getAction(), is(ASK));
    }

    @Test
    public void testSameSettingsInstance() {
        assertThat(settings, is(sameInstance(PreferenceInitializer.getDefault())));
    }

}
