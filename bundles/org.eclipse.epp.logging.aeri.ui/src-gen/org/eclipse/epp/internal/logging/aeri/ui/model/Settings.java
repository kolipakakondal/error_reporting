/**
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Settings</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getEmail <em>Email</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeMessages <em>Anonymize Messages</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeStrackTraceElements <em>Anonymize Strack Trace Elements</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isConfigured <em>Configured</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isSkipSimilarErrors <em>Skip Similar Errors</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getAction <em>Action</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendAction <em>Remember Send Action</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendActionPeriodStart <em>Remember Send Action Period Start</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getServerUrl <em>Server Url</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getProblemsZipEtag <em>Problems Zip Etag</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getServerConfigurationLocalFile <em>Server Configuration Local File</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getProblemsZipLastDownloadTimestamp <em>Problems Zip Last Download Timestamp</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings()
 * @model
 * @generated
 */
public interface Settings extends EObject {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getName <em>Name</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Email</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Email</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Email</em>' attribute.
     * @see #setEmail(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_Email()
     * @model
     * @generated
     */
    String getEmail();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getEmail <em>Email</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
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
     * If the meaning of the '<em>Anonymize Messages</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Anonymize Messages</em>' attribute.
     * @see #setAnonymizeMessages(boolean)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_AnonymizeMessages()
     * @model
     * @generated
     */
    boolean isAnonymizeMessages();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeMessages <em>Anonymize Messages</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Anonymize Messages</em>' attribute.
     * @see #isAnonymizeMessages()
     * @generated
     */
    void setAnonymizeMessages(boolean value);

    /**
     * Returns the value of the '<em><b>Anonymize Strack Trace Elements</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Anonymize Strack Trace Elements</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Anonymize Strack Trace Elements</em>' attribute.
     * @see #setAnonymizeStrackTraceElements(boolean)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_AnonymizeStrackTraceElements()
     * @model
     * @generated
     */
    boolean isAnonymizeStrackTraceElements();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeStrackTraceElements <em>Anonymize Strack Trace Elements</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Anonymize Strack Trace Elements</em>' attribute.
     * @see #isAnonymizeStrackTraceElements()
     * @generated
     */
    void setAnonymizeStrackTraceElements(boolean value);

    /**
     * Returns the value of the '<em><b>Configured</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Configured</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Configured</em>' attribute.
     * @see #setConfigured(boolean)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_Configured()
     * @model
     * @generated
     */
    boolean isConfigured();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isConfigured <em>Configured</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Configured</em>' attribute.
     * @see #isConfigured()
     * @generated
     */
    void setConfigured(boolean value);

    /**
     * Returns the value of the '<em><b>Skip Similar Errors</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Skip Similar Errors</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Skip Similar Errors</em>' attribute.
     * @see #setSkipSimilarErrors(boolean)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_SkipSimilarErrors()
     * @model
     * @generated
     */
    boolean isSkipSimilarErrors();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isSkipSimilarErrors <em>Skip Similar Errors</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Skip Similar Errors</em>' attribute.
     * @see #isSkipSimilarErrors()
     * @generated
     */
    void setSkipSimilarErrors(boolean value);

    /**
     * Returns the value of the '<em><b>Action</b></em>' attribute.
     * The literals are from the enumeration {@link org.eclipse.epp.internal.logging.aeri.ui.model.SendAction}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Action</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Action</em>' attribute.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.SendAction
     * @see #setAction(SendAction)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_Action()
     * @model
     * @generated
     */
    SendAction getAction();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getAction <em>Action</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Action</em>' attribute.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.SendAction
     * @see #getAction()
     * @generated
     */
    void setAction(SendAction value);

    /**
     * Returns the value of the '<em><b>Remember Send Action</b></em>' attribute.
     * The literals are from the enumeration {@link org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Remember Send Action</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Remember Send Action</em>' attribute.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction
     * @see #setRememberSendAction(RememberSendAction)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_RememberSendAction()
     * @model
     * @generated
     */
    RememberSendAction getRememberSendAction();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendAction <em>Remember Send Action</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Remember Send Action</em>' attribute.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction
     * @see #getRememberSendAction()
     * @generated
     */
    void setRememberSendAction(RememberSendAction value);

    /**
     * Returns the value of the '<em><b>Remember Send Action Period Start</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Remember Send Action Period Start</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Remember Send Action Period Start</em>' attribute.
     * @see #setRememberSendActionPeriodStart(long)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_RememberSendActionPeriodStart()
     * @model
     * @generated
     */
    long getRememberSendActionPeriodStart();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendActionPeriodStart <em>Remember Send Action Period Start</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Remember Send Action Period Start</em>' attribute.
     * @see #getRememberSendActionPeriodStart()
     * @generated
     */
    void setRememberSendActionPeriodStart(long value);

    /**
     * Returns the value of the '<em><b>Server Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Url</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Url</em>' attribute.
     * @see #setServerUrl(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_ServerUrl()
     * @model
     * @generated
     */
    String getServerUrl();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getServerUrl <em>Server Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server Url</em>' attribute.
     * @see #getServerUrl()
     * @generated
     */
    void setServerUrl(String value);

    /**
     * Returns the value of the '<em><b>Problems Zip Etag</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Problems Zip Etag</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Problems Zip Etag</em>' attribute.
     * @see #setProblemsZipEtag(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_ProblemsZipEtag()
     * @model
     * @generated
     */
    String getProblemsZipEtag();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getProblemsZipEtag <em>Problems Zip Etag</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Problems Zip Etag</em>' attribute.
     * @see #getProblemsZipEtag()
     * @generated
     */
    void setProblemsZipEtag(String value);

    /**
     * Returns the value of the '<em><b>Server Configuration Local File</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server Configuration Local File</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server Configuration Local File</em>' attribute.
     * @see #setServerConfigurationLocalFile(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_ServerConfigurationLocalFile()
     * @model
     * @generated
     */
    String getServerConfigurationLocalFile();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getServerConfigurationLocalFile <em>Server Configuration Local File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server Configuration Local File</em>' attribute.
     * @see #getServerConfigurationLocalFile()
     * @generated
     */
    void setServerConfigurationLocalFile(String value);

    /**
     * Returns the value of the '<em><b>Problems Zip Last Download Timestamp</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Problems Zip Last Download Timestamp</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Problems Zip Last Download Timestamp</em>' attribute.
     * @see #setProblemsZipLastDownloadTimestamp(long)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSettings_ProblemsZipLastDownloadTimestamp()
     * @model
     * @generated
     */
    long getProblemsZipLastDownloadTimestamp();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getProblemsZipLastDownloadTimestamp <em>Problems Zip Last Download Timestamp</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Problems Zip Last Download Timestamp</em>' attribute.
     * @see #getProblemsZipLastDownloadTimestamp()
     * @generated
     */
    void setProblemsZipLastDownloadTimestamp(long value);

} // Settings
