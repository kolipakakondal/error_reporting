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
package org.eclipse.epp.internal.logging.aeri.ui.model;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Settings</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getName
 * <em>Name</em>}</li>
 * <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getEmail
 * <em>Email</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeMessages
 * <em>Anonymize Messages</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeStrackTraceElements
 * <em>Anonymize Strack Trace Elements</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isConfigured
 * <em>Configured</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isSkipSimilarErrors
 * <em>Skip Similar Errors</em>}</li>
 * <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getAction
 * <em>Action</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendAction
 * <em>Remember Send Action</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendActionPeriodStart
 * <em>Remember Send Action Period Start</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getServerUrl
 * <em>Server Url</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getWhitelistedPluginIds
 * <em>Whitelisted Plugin Ids</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getWhitelistedPackages
 * <em>Whitelisted Packages</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings()
 * @model
 * @generated
 */
public interface Settings extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
     * should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_Name()
     * @model unique="false"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getName
     * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Email</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Email</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Email</em>' attribute.
     * @see #setEmail(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_Email()
     * @model unique="false"
     * @generated
     */
    String getEmail();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getEmail
     * <em>Email</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Email</em>' attribute.
     * @see #getEmail()
     * @generated
     */
    void setEmail(String value);

    /**
     * Returns the value of the '<em><b>Anonymize Messages</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Anonymize Messages</em>' attribute isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Anonymize Messages</em>' attribute.
     * @see #setAnonymizeMessages(boolean)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_AnonymizeMessages()
     * @model unique="false"
     * @generated
     */
    boolean isAnonymizeMessages();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeMessages
     * <em>Anonymize Messages</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Anonymize Messages</em>' attribute.
     * @see #isAnonymizeMessages()
     * @generated
     */
    void setAnonymizeMessages(boolean value);

    /**
     * Returns the value of the '<em><b>Anonymize Strack Trace Elements</b></em>
     * ' attribute. <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Anonymize Strack Trace Elements</em>'
     * attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Anonymize Strack Trace Elements</em>'
     *         attribute.
     * @see #setAnonymizeStrackTraceElements(boolean)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_AnonymizeStrackTraceElements()
     * @model unique="false"
     * @generated
     */
    boolean isAnonymizeStrackTraceElements();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeStrackTraceElements
     * <em>Anonymize Strack Trace Elements</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Anonymize Strack Trace Elements</em>
     *            ' attribute.
     * @see #isAnonymizeStrackTraceElements()
     * @generated
     */
    void setAnonymizeStrackTraceElements(boolean value);

    /**
     * Returns the value of the '<em><b>Configured</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Configured</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Configured</em>' attribute.
     * @see #setConfigured(boolean)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_Configured()
     * @model unique="false"
     * @generated
     */
    boolean isConfigured();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isConfigured
     * <em>Configured</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Configured</em>' attribute.
     * @see #isConfigured()
     * @generated
     */
    void setConfigured(boolean value);

    /**
     * Returns the value of the '<em><b>Skip Similar Errors</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Skip Similar Errors</em>' attribute isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Skip Similar Errors</em>' attribute.
     * @see #setSkipSimilarErrors(boolean)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_SkipSimilarErrors()
     * @model unique="false"
     * @generated
     */
    boolean isSkipSimilarErrors();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isSkipSimilarErrors
     * <em>Skip Similar Errors</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Skip Similar Errors</em>' attribute.
     * @see #isSkipSimilarErrors()
     * @generated
     */
    void setSkipSimilarErrors(boolean value);

    /**
     * Returns the value of the '<em><b>Action</b></em>' attribute. The literals
     * are from the enumeration
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.SendAction}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Action</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Action</em>' attribute.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.SendAction
     * @see #setAction(SendAction)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_Action()
     * @model unique="false"
     * @generated
     */
    SendAction getAction();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getAction
     * <em>Action</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @param value
     *            the new value of the '<em>Action</em>' attribute.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.SendAction
     * @see #getAction()
     * @generated
     */
    void setAction(SendAction value);

    /**
     * Returns the value of the '<em><b>Remember Send Action</b></em>'
     * attribute. The literals are from the enumeration
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction}
     * . <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Remember Send Action</em>' attribute isn't
     * clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Remember Send Action</em>' attribute.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction
     * @see #setRememberSendAction(RememberSendAction)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_RememberSendAction()
     * @model unique="false"
     * @generated
     */
    RememberSendAction getRememberSendAction();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendAction
     * <em>Remember Send Action</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Remember Send Action</em>'
     *            attribute.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction
     * @see #getRememberSendAction()
     * @generated
     */
    void setRememberSendAction(RememberSendAction value);

    /**
     * Returns the value of the '
     * <em><b>Remember Send Action Period Start</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Remember Send Action Period Start</em>'
     * attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Remember Send Action Period Start</em>'
     *         attribute.
     * @see #setRememberSendActionPeriodStart(long)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_RememberSendActionPeriodStart()
     * @model unique="false"
     * @generated
     */
    long getRememberSendActionPeriodStart();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendActionPeriodStart
     * <em>Remember Send Action Period Start</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '
     *            <em>Remember Send Action Period Start</em>' attribute.
     * @see #getRememberSendActionPeriodStart()
     * @generated
     */
    void setRememberSendActionPeriodStart(long value);

    /**
     * Returns the value of the '<em><b>Server Url</b></em>' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc --> <!-- begin-model-doc --> The
     * remote address where error events are send to. <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Server Url</em>' attribute.
     * @see #setServerUrl(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_ServerUrl()
     * @model unique="false"
     * @generated
     */
    String getServerUrl();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getServerUrl
     * <em>Server Url</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Server Url</em>' attribute.
     * @see #getServerUrl()
     * @generated
     */
    void setServerUrl(String value);

    /**
     * Returns the value of the '<em><b>Whitelisted Plugin Ids</b></em>'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> A list of prefixes a plug-in ID is matched against
     * using String#startsWith (e.g., 'com.codetrails.' <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Whitelisted Plugin Ids</em>' attribute.
     * @see #setWhitelistedPluginIds(List)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_WhitelistedPluginIds()
     * @model unique="false" dataType=
     *        "org.eclipse.recommenders.internal.stacktraces.rcp.model.ListOfStrings"
     * @generated
     */
    List<String> getWhitelistedPluginIds();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getWhitelistedPluginIds
     * <em>Whitelisted Plugin Ids</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Whitelisted Plugin Ids</em>'
     *            attribute.
     * @see #getWhitelistedPluginIds()
     * @generated
     */
    void setWhitelistedPluginIds(List<String> value);

    /**
     * Returns the value of the '<em><b>Whitelisted Packages</b></em>'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
     * begin-model-doc --> A list of prefixes a class name is matched against
     * using String#startsWith (e.g., 'com.codetrails.' <!-- end-model-doc -->
     * 
     * @return the value of the '<em>Whitelisted Packages</em>' attribute.
     * @see #setWhitelistedPackages(List)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_WhitelistedPackages()
     * @model unique="false" dataType=
     *        "org.eclipse.recommenders.internal.stacktraces.rcp.model.ListOfStrings"
     * @generated
     */
    List<String> getWhitelistedPackages();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getWhitelistedPackages
     * <em>Whitelisted Packages</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Whitelisted Packages</em>'
     *            attribute.
     * @see #getWhitelistedPackages()
     * @generated
     */
    void setWhitelistedPackages(List<String> value);

} // Settings
