/**
 * Copyright (c) 2014 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Marcel Bruch - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui.model.impl;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Settings</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getName
 * <em>Name</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getEmail
 * <em>Email</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#isAnonymizeMessages
 * <em>Anonymize Messages</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#isAnonymizeStrackTraceElements
 * <em>Anonymize Strack Trace Elements</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#isConfigured
 * <em>Configured</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#isSkipSimilarErrors
 * <em>Skip Similar Errors</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getAction
 * <em>Action</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getRememberSendAction
 * <em>Remember Send Action</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getRememberSendActionPeriodStart
 * <em>Remember Send Action Period Start</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getServerUrl
 * <em>Server Url</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getWhitelistedPluginIds
 * <em>Whitelisted Plugin Ids</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getWhitelistedPackages
 * <em>Whitelisted Packages</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SettingsImpl extends MinimalEObjectImpl.Container implements Settings {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getEmail()
     * @generated
     * @ordered
     */
    protected static final String EMAIL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEmail() <em>Email</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getEmail()
     * @generated
     * @ordered
     */
    protected String email = EMAIL_EDEFAULT;

    /**
     * The default value of the '{@link #isAnonymizeMessages()
     * <em>Anonymize Messages</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #isAnonymizeMessages()
     * @generated
     * @ordered
     */
    protected static final boolean ANONYMIZE_MESSAGES_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isAnonymizeMessages()
     * <em>Anonymize Messages</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #isAnonymizeMessages()
     * @generated
     * @ordered
     */
    protected boolean anonymizeMessages = ANONYMIZE_MESSAGES_EDEFAULT;

    /**
     * The default value of the '{@link #isAnonymizeStrackTraceElements()
     * <em>Anonymize Strack Trace Elements</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #isAnonymizeStrackTraceElements()
     * @generated
     * @ordered
     */
    protected static final boolean ANONYMIZE_STRACK_TRACE_ELEMENTS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isAnonymizeStrackTraceElements()
     * <em>Anonymize Strack Trace Elements</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #isAnonymizeStrackTraceElements()
     * @generated
     * @ordered
     */
    protected boolean anonymizeStrackTraceElements = ANONYMIZE_STRACK_TRACE_ELEMENTS_EDEFAULT;

    /**
     * The default value of the '{@link #isConfigured() <em>Configured</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isConfigured()
     * @generated
     * @ordered
     */
    protected static final boolean CONFIGURED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isConfigured() <em>Configured</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isConfigured()
     * @generated
     * @ordered
     */
    protected boolean configured = CONFIGURED_EDEFAULT;

    /**
     * The default value of the '{@link #isSkipSimilarErrors()
     * <em>Skip Similar Errors</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #isSkipSimilarErrors()
     * @generated
     * @ordered
     */
    protected static final boolean SKIP_SIMILAR_ERRORS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isSkipSimilarErrors()
     * <em>Skip Similar Errors</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #isSkipSimilarErrors()
     * @generated
     * @ordered
     */
    protected boolean skipSimilarErrors = SKIP_SIMILAR_ERRORS_EDEFAULT;

    /**
     * The default value of the '{@link #getAction() <em>Action</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getAction()
     * @generated
     * @ordered
     */
    protected static final SendAction ACTION_EDEFAULT = SendAction.ASK;

    /**
     * The cached value of the '{@link #getAction() <em>Action</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getAction()
     * @generated
     * @ordered
     */
    protected SendAction action = ACTION_EDEFAULT;

    /**
     * The default value of the '{@link #getRememberSendAction()
     * <em>Remember Send Action</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getRememberSendAction()
     * @generated
     * @ordered
     */
    protected static final RememberSendAction REMEMBER_SEND_ACTION_EDEFAULT = RememberSendAction.HOURS_24;

    /**
     * The cached value of the '{@link #getRememberSendAction()
     * <em>Remember Send Action</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getRememberSendAction()
     * @generated
     * @ordered
     */
    protected RememberSendAction rememberSendAction = REMEMBER_SEND_ACTION_EDEFAULT;

    /**
     * The default value of the '{@link #getRememberSendActionPeriodStart()
     * <em>Remember Send Action Period Start</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getRememberSendActionPeriodStart()
     * @generated
     * @ordered
     */
    protected static final long REMEMBER_SEND_ACTION_PERIOD_START_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getRememberSendActionPeriodStart()
     * <em>Remember Send Action Period Start</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getRememberSendActionPeriodStart()
     * @generated
     * @ordered
     */
    protected long rememberSendActionPeriodStart = REMEMBER_SEND_ACTION_PERIOD_START_EDEFAULT;

    /**
     * The default value of the '{@link #getServerUrl() <em>Server Url</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getServerUrl()
     * @generated
     * @ordered
     */
    protected static final String SERVER_URL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getServerUrl() <em>Server Url</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getServerUrl()
     * @generated
     * @ordered
     */
    protected String serverUrl = SERVER_URL_EDEFAULT;

    /**
     * The cached value of the '{@link #getWhitelistedPluginIds()
     * <em>Whitelisted Plugin Ids</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getWhitelistedPluginIds()
     * @generated
     * @ordered
     */
    protected List<String> whitelistedPluginIds;

    /**
     * The cached value of the '{@link #getWhitelistedPackages()
     * <em>Whitelisted Packages</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getWhitelistedPackages()
     * @generated
     * @ordered
     */
    protected List<String> whitelistedPackages;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected SettingsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ModelPackage.Literals.SETTINGS;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getEmail() {
        return email;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setEmail(String newEmail) {
        String oldEmail = email;
        email = newEmail;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__EMAIL, oldEmail, email));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isAnonymizeMessages() {
        return anonymizeMessages;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setAnonymizeMessages(boolean newAnonymizeMessages) {
        boolean oldAnonymizeMessages = anonymizeMessages;
        anonymizeMessages = newAnonymizeMessages;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__ANONYMIZE_MESSAGES, oldAnonymizeMessages,
                    anonymizeMessages));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isAnonymizeStrackTraceElements() {
        return anonymizeStrackTraceElements;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setAnonymizeStrackTraceElements(boolean newAnonymizeStrackTraceElements) {
        boolean oldAnonymizeStrackTraceElements = anonymizeStrackTraceElements;
        anonymizeStrackTraceElements = newAnonymizeStrackTraceElements;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__ANONYMIZE_STRACK_TRACE_ELEMENTS,
                    oldAnonymizeStrackTraceElements, anonymizeStrackTraceElements));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isConfigured() {
        return configured;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setConfigured(boolean newConfigured) {
        boolean oldConfigured = configured;
        configured = newConfigured;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__CONFIGURED, oldConfigured, configured));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSkipSimilarErrors() {
        return skipSimilarErrors;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setSkipSimilarErrors(boolean newSkipSimilarErrors) {
        boolean oldSkipSimilarErrors = skipSimilarErrors;
        skipSimilarErrors = newSkipSimilarErrors;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__SKIP_SIMILAR_ERRORS, oldSkipSimilarErrors,
                    skipSimilarErrors));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public SendAction getAction() {
        return action;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setAction(SendAction newAction) {
        SendAction oldAction = action;
        action = newAction == null ? ACTION_EDEFAULT : newAction;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__ACTION, oldAction, action));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public RememberSendAction getRememberSendAction() {
        return rememberSendAction;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setRememberSendAction(RememberSendAction newRememberSendAction) {
        RememberSendAction oldRememberSendAction = rememberSendAction;
        rememberSendAction = newRememberSendAction == null ? REMEMBER_SEND_ACTION_EDEFAULT : newRememberSendAction;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__REMEMBER_SEND_ACTION, oldRememberSendAction,
                    rememberSendAction));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public long getRememberSendActionPeriodStart() {
        return rememberSendActionPeriodStart;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setRememberSendActionPeriodStart(long newRememberSendActionPeriodStart) {
        long oldRememberSendActionPeriodStart = rememberSendActionPeriodStart;
        rememberSendActionPeriodStart = newRememberSendActionPeriodStart;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__REMEMBER_SEND_ACTION_PERIOD_START,
                    oldRememberSendActionPeriodStart, rememberSendActionPeriodStart));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getServerUrl() {
        return serverUrl;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setServerUrl(String newServerUrl) {
        String oldServerUrl = serverUrl;
        serverUrl = newServerUrl;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__SERVER_URL, oldServerUrl, serverUrl));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public List<String> getWhitelistedPluginIds() {
        return whitelistedPluginIds;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setWhitelistedPluginIds(List<String> newWhitelistedPluginIds) {
        List<String> oldWhitelistedPluginIds = whitelistedPluginIds;
        whitelistedPluginIds = newWhitelistedPluginIds;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__WHITELISTED_PLUGIN_IDS, oldWhitelistedPluginIds,
                    whitelistedPluginIds));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public List<String> getWhitelistedPackages() {
        return whitelistedPackages;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setWhitelistedPackages(List<String> newWhitelistedPackages) {
        List<String> oldWhitelistedPackages = whitelistedPackages;
        whitelistedPackages = newWhitelistedPackages;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__WHITELISTED_PACKAGES, oldWhitelistedPackages,
                    whitelistedPackages));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case ModelPackage.SETTINGS__NAME:
            return getName();
        case ModelPackage.SETTINGS__EMAIL:
            return getEmail();
        case ModelPackage.SETTINGS__ANONYMIZE_MESSAGES:
            return isAnonymizeMessages();
        case ModelPackage.SETTINGS__ANONYMIZE_STRACK_TRACE_ELEMENTS:
            return isAnonymizeStrackTraceElements();
        case ModelPackage.SETTINGS__CONFIGURED:
            return isConfigured();
        case ModelPackage.SETTINGS__SKIP_SIMILAR_ERRORS:
            return isSkipSimilarErrors();
        case ModelPackage.SETTINGS__ACTION:
            return getAction();
        case ModelPackage.SETTINGS__REMEMBER_SEND_ACTION:
            return getRememberSendAction();
        case ModelPackage.SETTINGS__REMEMBER_SEND_ACTION_PERIOD_START:
            return getRememberSendActionPeriodStart();
        case ModelPackage.SETTINGS__SERVER_URL:
            return getServerUrl();
        case ModelPackage.SETTINGS__WHITELISTED_PLUGIN_IDS:
            return getWhitelistedPluginIds();
        case ModelPackage.SETTINGS__WHITELISTED_PACKAGES:
            return getWhitelistedPackages();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case ModelPackage.SETTINGS__NAME:
            setName((String) newValue);
            return;
        case ModelPackage.SETTINGS__EMAIL:
            setEmail((String) newValue);
            return;
        case ModelPackage.SETTINGS__ANONYMIZE_MESSAGES:
            setAnonymizeMessages((Boolean) newValue);
            return;
        case ModelPackage.SETTINGS__ANONYMIZE_STRACK_TRACE_ELEMENTS:
            setAnonymizeStrackTraceElements((Boolean) newValue);
            return;
        case ModelPackage.SETTINGS__CONFIGURED:
            setConfigured((Boolean) newValue);
            return;
        case ModelPackage.SETTINGS__SKIP_SIMILAR_ERRORS:
            setSkipSimilarErrors((Boolean) newValue);
            return;
        case ModelPackage.SETTINGS__ACTION:
            setAction((SendAction) newValue);
            return;
        case ModelPackage.SETTINGS__REMEMBER_SEND_ACTION:
            setRememberSendAction((RememberSendAction) newValue);
            return;
        case ModelPackage.SETTINGS__REMEMBER_SEND_ACTION_PERIOD_START:
            setRememberSendActionPeriodStart((Long) newValue);
            return;
        case ModelPackage.SETTINGS__SERVER_URL:
            setServerUrl((String) newValue);
            return;
        case ModelPackage.SETTINGS__WHITELISTED_PLUGIN_IDS:
            setWhitelistedPluginIds((List<String>) newValue);
            return;
        case ModelPackage.SETTINGS__WHITELISTED_PACKAGES:
            setWhitelistedPackages((List<String>) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case ModelPackage.SETTINGS__NAME:
            setName(NAME_EDEFAULT);
            return;
        case ModelPackage.SETTINGS__EMAIL:
            setEmail(EMAIL_EDEFAULT);
            return;
        case ModelPackage.SETTINGS__ANONYMIZE_MESSAGES:
            setAnonymizeMessages(ANONYMIZE_MESSAGES_EDEFAULT);
            return;
        case ModelPackage.SETTINGS__ANONYMIZE_STRACK_TRACE_ELEMENTS:
            setAnonymizeStrackTraceElements(ANONYMIZE_STRACK_TRACE_ELEMENTS_EDEFAULT);
            return;
        case ModelPackage.SETTINGS__CONFIGURED:
            setConfigured(CONFIGURED_EDEFAULT);
            return;
        case ModelPackage.SETTINGS__SKIP_SIMILAR_ERRORS:
            setSkipSimilarErrors(SKIP_SIMILAR_ERRORS_EDEFAULT);
            return;
        case ModelPackage.SETTINGS__ACTION:
            setAction(ACTION_EDEFAULT);
            return;
        case ModelPackage.SETTINGS__REMEMBER_SEND_ACTION:
            setRememberSendAction(REMEMBER_SEND_ACTION_EDEFAULT);
            return;
        case ModelPackage.SETTINGS__REMEMBER_SEND_ACTION_PERIOD_START:
            setRememberSendActionPeriodStart(REMEMBER_SEND_ACTION_PERIOD_START_EDEFAULT);
            return;
        case ModelPackage.SETTINGS__SERVER_URL:
            setServerUrl(SERVER_URL_EDEFAULT);
            return;
        case ModelPackage.SETTINGS__WHITELISTED_PLUGIN_IDS:
            setWhitelistedPluginIds((List<String>) null);
            return;
        case ModelPackage.SETTINGS__WHITELISTED_PACKAGES:
            setWhitelistedPackages((List<String>) null);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case ModelPackage.SETTINGS__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
        case ModelPackage.SETTINGS__EMAIL:
            return EMAIL_EDEFAULT == null ? email != null : !EMAIL_EDEFAULT.equals(email);
        case ModelPackage.SETTINGS__ANONYMIZE_MESSAGES:
            return anonymizeMessages != ANONYMIZE_MESSAGES_EDEFAULT;
        case ModelPackage.SETTINGS__ANONYMIZE_STRACK_TRACE_ELEMENTS:
            return anonymizeStrackTraceElements != ANONYMIZE_STRACK_TRACE_ELEMENTS_EDEFAULT;
        case ModelPackage.SETTINGS__CONFIGURED:
            return configured != CONFIGURED_EDEFAULT;
        case ModelPackage.SETTINGS__SKIP_SIMILAR_ERRORS:
            return skipSimilarErrors != SKIP_SIMILAR_ERRORS_EDEFAULT;
        case ModelPackage.SETTINGS__ACTION:
            return action != ACTION_EDEFAULT;
        case ModelPackage.SETTINGS__REMEMBER_SEND_ACTION:
            return rememberSendAction != REMEMBER_SEND_ACTION_EDEFAULT;
        case ModelPackage.SETTINGS__REMEMBER_SEND_ACTION_PERIOD_START:
            return rememberSendActionPeriodStart != REMEMBER_SEND_ACTION_PERIOD_START_EDEFAULT;
        case ModelPackage.SETTINGS__SERVER_URL:
            return SERVER_URL_EDEFAULT == null ? serverUrl != null : !SERVER_URL_EDEFAULT.equals(serverUrl);
        case ModelPackage.SETTINGS__WHITELISTED_PLUGIN_IDS:
            return whitelistedPluginIds != null;
        case ModelPackage.SETTINGS__WHITELISTED_PACKAGES:
            return whitelistedPackages != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: ");
        result.append(name);
        result.append(", email: ");
        result.append(email);
        result.append(", anonymizeMessages: ");
        result.append(anonymizeMessages);
        result.append(", anonymizeStrackTraceElements: ");
        result.append(anonymizeStrackTraceElements);
        result.append(", configured: ");
        result.append(configured);
        result.append(", skipSimilarErrors: ");
        result.append(skipSimilarErrors);
        result.append(", action: ");
        result.append(action);
        result.append(", rememberSendAction: ");
        result.append(rememberSendAction);
        result.append(", rememberSendActionPeriodStart: ");
        result.append(rememberSendActionPeriodStart);
        result.append(", serverUrl: ");
        result.append(serverUrl);
        result.append(", whitelistedPluginIds: ");
        result.append(whitelistedPluginIds);
        result.append(", whitelistedPackages: ");
        result.append(whitelistedPackages);
        result.append(')');
        return result.toString();
    }

} // SettingsImpl
