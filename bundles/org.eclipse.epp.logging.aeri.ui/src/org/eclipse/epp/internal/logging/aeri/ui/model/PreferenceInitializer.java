/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import static org.eclipse.epp.internal.logging.aeri.ui.Constants.PLUGIN_ID;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;
import static org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction.NONE;
import static org.eclipse.epp.internal.logging.aeri.ui.model.SendAction.ASK;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

public class PreferenceInitializer extends AbstractPreferenceInitializer {
    public static final ModelPackage PKG = ModelPackage.eINSTANCE;
    public static final String PROP_ANONYMIZE_STACKTRACES = PKG.getSettings_AnonymizeStrackTraceElements().getName();
    public static final String PROP_ANONYMIZE_MESSAGES = PKG.getSettings_AnonymizeMessages().getName();
    public static final String PROP_CONFIGURED = PKG.getSettings_Configured().getName();
    public static final String PROP_EMAIL = PKG.getSettings_Email().getName();
    public static final String PROP_NAME = PKG.getSettings_Name().getName();
    public static final String PROP_REMEMBER_SEND_ACTION = PKG.getSettings_RememberSendAction().getName();
    public static final String PROP_REMEMBER_SETTING_PERIOD_START = PKG.getSettings_RememberSendActionPeriodStart().getName();
    public static final String PROP_SEND_ACTION = PKG.getSettings_Action().getName();
    public static final String PROP_SERVER = PKG.getSettings_ServerUrl().getName();
    public static final String PROP_SKIP_SIMILAR_ERRORS = PKG.getSettings_SkipSimilarErrors().getName();
    public static final String PROP_SERVER_CONFIGURATION_LOCAL_FILE = PKG.getSettings_ServerConfigurationLocalFile().getName();

    private static String getServerUrl() {
        return System.getProperty(PLUGIN_ID + "." + PROP_SERVER, "https://dev.eclipse.org/recommenders/community/confess/v2/discovery");
    }

    public static final String SERVER_URL = getServerUrl();

    public static final String SERVER_CONFIGURATION_FILE;

    static {
        Bundle bundle = FrameworkUtil.getBundle(PreferenceInitializer.class);
        IPath stateLocation = Platform.getStateLocation(bundle);
        File indexDirectory = new File(stateLocation.toFile(), Constants.SERVER_CONFIGURATION_FILE);
        SERVER_CONFIGURATION_FILE = indexDirectory.getAbsolutePath();
    }

    private static long MS_PER_DAY = TimeUnit.DAYS.toMillis(1);
    private static Settings settings;

    @Override
    public void initializeDefaultPreferences() {
        IEclipsePreferences s = DefaultScope.INSTANCE.getNode(PLUGIN_ID);
        String name = "";
        String email = "";
        boolean anonymizeStacktraces = true;
        boolean anonymizeMessages = false;
        try {
            final ScopedPreferenceStore store = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, PLUGIN_ID);
            name = store.getString(PROP_NAME);
            email = store.getString(PROP_EMAIL);
            if (store.contains(PROP_ANONYMIZE_STACKTRACES)) {
                anonymizeStacktraces = store.getBoolean(PROP_ANONYMIZE_STACKTRACES);
            }
            if (store.contains(PROP_ANONYMIZE_MESSAGES)) {
                anonymizeMessages = store.getBoolean(PROP_ANONYMIZE_MESSAGES);
            }
        } catch (Exception e) {
            log(WARN_FAILED_TO_LOAD_DEFAULT_PREFERENCES, e);
        }
        s.put(PROP_SERVER, SERVER_URL);
        s.put(PROP_SERVER_CONFIGURATION_LOCAL_FILE, SERVER_CONFIGURATION_FILE);
        s.put(PROP_NAME, name);
        s.put(PROP_EMAIL, email);
        s.putBoolean(PROP_ANONYMIZE_STACKTRACES, anonymizeStacktraces);
        s.putBoolean(PROP_ANONYMIZE_MESSAGES, anonymizeMessages);
        s.putBoolean(PROP_SKIP_SIMILAR_ERRORS, true);
        s.putBoolean(PROP_CONFIGURED, false);
        s.putLong(PROP_REMEMBER_SETTING_PERIOD_START, 0L);
        s.put(PROP_SEND_ACTION, SendAction.ASK.name());
        s.put(PROP_REMEMBER_SEND_ACTION, RememberSendAction.NONE.name());
    }

    public static Settings getDefault() {
        if (settings == null) {
            settings = ModelFactory.eINSTANCE.createSettings();
            final EClass eClass = settings.eClass();
            final ScopedPreferenceStore instanceStore = new ScopedPreferenceStore(InstanceScope.INSTANCE, PLUGIN_ID);
            loadFromPreferences(instanceStore, settings, eClass);
            registerPreferenceStoreChangeListener(instanceStore, settings, eClass);
            // register a listener that sends all changes to the instance scope store:
            registerSettingsChangeListener(instanceStore, settings, new HashSet<String>());

            // register a listener that sends selected changes to the configuration scope store:
            final ScopedPreferenceStore configurationStore = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, PLUGIN_ID);
            registerSettingsChangeListener(configurationStore, settings,
                    ImmutableSet.of(PROP_NAME, PROP_EMAIL, PROP_ANONYMIZE_STACKTRACES, PROP_ANONYMIZE_MESSAGES));
            handleRestart24hSendAction(settings);
        }
        return settings;
    }

    private static void registerSettingsChangeListener(final ScopedPreferenceStore store, final Settings settings,
            final Set<String> allowedKeys) {
        settings.eAdapters().add(new AdapterImpl() {
            @Override
            public void notifyChanged(Notification msg) {
                Object feature = msg.getFeature();
                if (!(feature instanceof EAttribute)) {
                    return;
                }
                EAttribute attr = (EAttribute) feature;
                String key = attr.getName();
                EDataType type = attr.getEAttributeType();
                Object value = msg.getNewValue();
                String data = EcoreUtil.convertToString(type, value);

                // if empty all keys are allowed:
                if (allowedKeys.isEmpty() || allowedKeys.contains(key)) {
                    try {
                        store.putValue(key, data);
                        store.save();
                    } catch (Exception e) {
                        log(ERROR_SAVE_PREFERENCES_FAILED, e);
                    }
                }
            }
        });
    }

    private static void registerPreferenceStoreChangeListener(final ScopedPreferenceStore store, final Settings settings,
            final EClass eClass) {
        store.addPropertyChangeListener(new IPropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent event) {
                String property = event.getProperty();
                EStructuralFeature feature = eClass.getEStructuralFeature(property);
                if (feature != null && feature instanceof EAttribute) {
                    EAttribute attr = (EAttribute) feature;
                    EDataType type = attr.getEAttributeType();
                    String string = EcoreUtil.convertToString(type, event.getNewValue());
                    Object value = EcoreUtil.createFromString(type, string);
                    settings.eSet(feature, value);
                }
            }
        });
    }

    private static void loadFromPreferences(final ScopedPreferenceStore store, final Settings settings, final EClass eClass) {
        settings.eSetDeliver(false);
        for (EAttribute attr : eClass.getEAllAttributes()) {
            EDataType type = attr.getEAttributeType();
            String key = attr.getName();
            String value = store.getString(key);
            try {
                Object data = EcoreUtil.createFromString(type, value);
                settings.eSet(attr, data);
            } catch (Exception e) {
                log(ERROR_FAILED_TO_PARSE_PREFERENCE_VALUE, attr, value);
            }
        }
        settings.eSetDeliver(true);
    }

    static void handleRestart24hSendAction(Settings settings) {
        switch (settings.getRememberSendAction()) {
        case RESTART:
            settings.setAction(ASK);
            settings.setRememberSendAction(NONE);
            break;
        case HOURS_24:
            long elapsedTime = System.currentTimeMillis() - settings.getRememberSendActionPeriodStart();
            boolean isDayElapsed = elapsedTime >= MS_PER_DAY;
            if (isDayElapsed) {
                log(INFO_PAUSE_PERIOD_ELAPSED);
                settings.setAction(ASK);
                settings.setRememberSendAction(NONE);
            }
            break;
        default:
            // do nothing
        }
    }

    static List<String> convert(String string) {
        return Splitter.on(';').omitEmptyStrings().trimResults().splitToList(string);

    }

    static String convert(List<String> strings) {
        return Joiner.on(";").skipNulls().join(strings);
    }
}
