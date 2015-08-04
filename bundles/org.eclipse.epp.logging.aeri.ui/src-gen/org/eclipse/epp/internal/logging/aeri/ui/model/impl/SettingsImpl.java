/**
 */
package org.eclipse.epp.internal.logging.aeri.ui.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Settings</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#isAnonymizeMessages <em>Anonymize Messages</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#isAnonymizeStrackTraceElements <em>Anonymize Strack Trace Elements</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#isConfigured <em>Configured</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#isSkipSimilarErrors <em>Skip Similar Errors</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getAction <em>Action</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getRememberSendAction <em>Remember Send Action</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getRememberSendActionPeriodStart <em>Remember Send Action Period Start</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getServerConfigurationUrl <em>Server Configuration Url</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getProblemsZipEtag <em>Problems Zip Etag</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getServerConfigurationLocalFile <em>Server Configuration Local File</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl#getProblemsZipLastDownloadTimestamp <em>Problems Zip Last Download Timestamp</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SettingsImpl extends MinimalEObjectImpl.Container implements Settings {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getEmail() <em>Email</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getEmail()
     * @generated
     * @ordered
     */
    protected static final String EMAIL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getEmail() <em>Email</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getEmail()
     * @generated
     * @ordered
     */
    protected String email = EMAIL_EDEFAULT;

    /**
     * The default value of the '{@link #isAnonymizeMessages() <em>Anonymize Messages</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #isAnonymizeMessages()
     * @generated
     * @ordered
     */
    protected static final boolean ANONYMIZE_MESSAGES_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isAnonymizeMessages() <em>Anonymize Messages</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #isAnonymizeMessages()
     * @generated
     * @ordered
     */
    protected boolean anonymizeMessages = ANONYMIZE_MESSAGES_EDEFAULT;

    /**
     * The default value of the '{@link #isAnonymizeStrackTraceElements() <em>Anonymize Strack Trace Elements</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isAnonymizeStrackTraceElements()
     * @generated
     * @ordered
     */
    protected static final boolean ANONYMIZE_STRACK_TRACE_ELEMENTS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isAnonymizeStrackTraceElements() <em>Anonymize Strack Trace Elements</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #isAnonymizeStrackTraceElements()
     * @generated
     * @ordered
     */
    protected boolean anonymizeStrackTraceElements = ANONYMIZE_STRACK_TRACE_ELEMENTS_EDEFAULT;

    /**
     * The default value of the '{@link #isConfigured() <em>Configured</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isConfigured()
     * @generated
     * @ordered
     */
    protected static final boolean CONFIGURED_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isConfigured() <em>Configured</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #isConfigured()
     * @generated
     * @ordered
     */
    protected boolean configured = CONFIGURED_EDEFAULT;

    /**
     * The default value of the '{@link #isSkipSimilarErrors() <em>Skip Similar Errors</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #isSkipSimilarErrors()
     * @generated
     * @ordered
     */
    protected static final boolean SKIP_SIMILAR_ERRORS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isSkipSimilarErrors() <em>Skip Similar Errors</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #isSkipSimilarErrors()
     * @generated
     * @ordered
     */
    protected boolean skipSimilarErrors = SKIP_SIMILAR_ERRORS_EDEFAULT;

    /**
     * The default value of the '{@link #getAction() <em>Action</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAction()
     * @generated
     * @ordered
     */
    protected static final SendAction ACTION_EDEFAULT = SendAction.ASK;

    /**
     * The cached value of the '{@link #getAction() <em>Action</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #getAction()
     * @generated
     * @ordered
     */
    protected SendAction action = ACTION_EDEFAULT;

    /**
     * The default value of the '{@link #getRememberSendAction() <em>Remember Send Action</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getRememberSendAction()
     * @generated
     * @ordered
     */
    protected static final RememberSendAction REMEMBER_SEND_ACTION_EDEFAULT = RememberSendAction.HOURS_24;

    /**
     * The cached value of the '{@link #getRememberSendAction() <em>Remember Send Action</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getRememberSendAction()
     * @generated
     * @ordered
     */
    protected RememberSendAction rememberSendAction = REMEMBER_SEND_ACTION_EDEFAULT;

    /**
     * The default value of the '{@link #getRememberSendActionPeriodStart() <em>Remember Send Action Period Start</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getRememberSendActionPeriodStart()
     * @generated
     * @ordered
     */
    protected static final long REMEMBER_SEND_ACTION_PERIOD_START_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getRememberSendActionPeriodStart() <em>Remember Send Action Period Start</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getRememberSendActionPeriodStart()
     * @generated
     * @ordered
     */
    protected long rememberSendActionPeriodStart = REMEMBER_SEND_ACTION_PERIOD_START_EDEFAULT;

    /**
     * The default value of the '{@link #getServerConfigurationUrl() <em>Server Configuration Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServerConfigurationUrl()
     * @generated
     * @ordered
     */
    protected static final String SERVER_CONFIGURATION_URL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getServerConfigurationUrl() <em>Server Configuration Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServerConfigurationUrl()
     * @generated
     * @ordered
     */
    protected String serverConfigurationUrl = SERVER_CONFIGURATION_URL_EDEFAULT;

    /**
     * The default value of the '{@link #getProblemsZipEtag() <em>Problems Zip Etag</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getProblemsZipEtag()
     * @generated
     * @ordered
     */
    protected static final String PROBLEMS_ZIP_ETAG_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getProblemsZipEtag() <em>Problems Zip Etag</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getProblemsZipEtag()
     * @generated
     * @ordered
     */
    protected String problemsZipEtag = PROBLEMS_ZIP_ETAG_EDEFAULT;

    /**
     * The default value of the '{@link #getServerConfigurationLocalFile() <em>Server Configuration Local File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServerConfigurationLocalFile()
     * @generated
     * @ordered
     */
    protected static final String SERVER_CONFIGURATION_LOCAL_FILE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getServerConfigurationLocalFile() <em>Server Configuration Local File</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServerConfigurationLocalFile()
     * @generated
     * @ordered
     */
    protected String serverConfigurationLocalFile = SERVER_CONFIGURATION_LOCAL_FILE_EDEFAULT;

    /**
     * The default value of the '{@link #getProblemsZipLastDownloadTimestamp() <em>Problems Zip Last Download Timestamp</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProblemsZipLastDownloadTimestamp()
     * @generated
     * @ordered
     */
    protected static final long PROBLEMS_ZIP_LAST_DOWNLOAD_TIMESTAMP_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getProblemsZipLastDownloadTimestamp() <em>Problems Zip Last Download Timestamp</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getProblemsZipLastDownloadTimestamp()
     * @generated
     * @ordered
     */
    protected long problemsZipLastDownloadTimestamp = PROBLEMS_ZIP_LAST_DOWNLOAD_TIMESTAMP_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected SettingsImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ModelPackage.Literals.SETTINGS;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @generated
     */
    public String getEmail() {
        return email;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @generated
     */
    public boolean isAnonymizeMessages() {
        return anonymizeMessages;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setAnonymizeMessages(boolean newAnonymizeMessages) {
        boolean oldAnonymizeMessages = anonymizeMessages;
        anonymizeMessages = newAnonymizeMessages;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__ANONYMIZE_MESSAGES, oldAnonymizeMessages, anonymizeMessages));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isAnonymizeStrackTraceElements() {
        return anonymizeStrackTraceElements;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setAnonymizeStrackTraceElements(boolean newAnonymizeStrackTraceElements) {
        boolean oldAnonymizeStrackTraceElements = anonymizeStrackTraceElements;
        anonymizeStrackTraceElements = newAnonymizeStrackTraceElements;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__ANONYMIZE_STRACK_TRACE_ELEMENTS, oldAnonymizeStrackTraceElements, anonymizeStrackTraceElements));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public boolean isConfigured() {
        return configured;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @generated
     */
    public boolean isSkipSimilarErrors() {
        return skipSimilarErrors;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setSkipSimilarErrors(boolean newSkipSimilarErrors) {
        boolean oldSkipSimilarErrors = skipSimilarErrors;
        skipSimilarErrors = newSkipSimilarErrors;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__SKIP_SIMILAR_ERRORS, oldSkipSimilarErrors, skipSimilarErrors));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public SendAction getAction() {
        return action;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
     * @generated
     */
    public RememberSendAction getRememberSendAction() {
        return rememberSendAction;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setRememberSendAction(RememberSendAction newRememberSendAction) {
        RememberSendAction oldRememberSendAction = rememberSendAction;
        rememberSendAction = newRememberSendAction == null ? REMEMBER_SEND_ACTION_EDEFAULT : newRememberSendAction;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__REMEMBER_SEND_ACTION, oldRememberSendAction, rememberSendAction));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public long getRememberSendActionPeriodStart() {
        return rememberSendActionPeriodStart;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setRememberSendActionPeriodStart(long newRememberSendActionPeriodStart) {
        long oldRememberSendActionPeriodStart = rememberSendActionPeriodStart;
        rememberSendActionPeriodStart = newRememberSendActionPeriodStart;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__REMEMBER_SEND_ACTION_PERIOD_START, oldRememberSendActionPeriodStart, rememberSendActionPeriodStart));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getServerConfigurationUrl() {
        return serverConfigurationUrl;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setServerConfigurationUrl(String newServerConfigurationUrl) {
        String oldServerConfigurationUrl = serverConfigurationUrl;
        serverConfigurationUrl = newServerConfigurationUrl;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__SERVER_CONFIGURATION_URL, oldServerConfigurationUrl, serverConfigurationUrl));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getProblemsZipEtag() {
        return problemsZipEtag;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setProblemsZipEtag(String newProblemsZipEtag) {
        String oldProblemsZipEtag = problemsZipEtag;
        problemsZipEtag = newProblemsZipEtag;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__PROBLEMS_ZIP_ETAG, oldProblemsZipEtag, problemsZipEtag));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getServerConfigurationLocalFile() {
        return serverConfigurationLocalFile;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setServerConfigurationLocalFile(String newServerConfigurationLocalFile) {
        String oldServerConfigurationLocalFile = serverConfigurationLocalFile;
        serverConfigurationLocalFile = newServerConfigurationLocalFile;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__SERVER_CONFIGURATION_LOCAL_FILE, oldServerConfigurationLocalFile, serverConfigurationLocalFile));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public long getProblemsZipLastDownloadTimestamp() {
        return problemsZipLastDownloadTimestamp;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setProblemsZipLastDownloadTimestamp(long newProblemsZipLastDownloadTimestamp) {
        long oldProblemsZipLastDownloadTimestamp = problemsZipLastDownloadTimestamp;
        problemsZipLastDownloadTimestamp = newProblemsZipLastDownloadTimestamp;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SETTINGS__PROBLEMS_ZIP_LAST_DOWNLOAD_TIMESTAMP, oldProblemsZipLastDownloadTimestamp, problemsZipLastDownloadTimestamp));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
            case ModelPackage.SETTINGS__SERVER_CONFIGURATION_URL:
                return getServerConfigurationUrl();
            case ModelPackage.SETTINGS__PROBLEMS_ZIP_ETAG:
                return getProblemsZipEtag();
            case ModelPackage.SETTINGS__SERVER_CONFIGURATION_LOCAL_FILE:
                return getServerConfigurationLocalFile();
            case ModelPackage.SETTINGS__PROBLEMS_ZIP_LAST_DOWNLOAD_TIMESTAMP:
                return getProblemsZipLastDownloadTimestamp();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ModelPackage.SETTINGS__NAME:
                setName((String)newValue);
                return;
            case ModelPackage.SETTINGS__EMAIL:
                setEmail((String)newValue);
                return;
            case ModelPackage.SETTINGS__ANONYMIZE_MESSAGES:
                setAnonymizeMessages((Boolean)newValue);
                return;
            case ModelPackage.SETTINGS__ANONYMIZE_STRACK_TRACE_ELEMENTS:
                setAnonymizeStrackTraceElements((Boolean)newValue);
                return;
            case ModelPackage.SETTINGS__CONFIGURED:
                setConfigured((Boolean)newValue);
                return;
            case ModelPackage.SETTINGS__SKIP_SIMILAR_ERRORS:
                setSkipSimilarErrors((Boolean)newValue);
                return;
            case ModelPackage.SETTINGS__ACTION:
                setAction((SendAction)newValue);
                return;
            case ModelPackage.SETTINGS__REMEMBER_SEND_ACTION:
                setRememberSendAction((RememberSendAction)newValue);
                return;
            case ModelPackage.SETTINGS__REMEMBER_SEND_ACTION_PERIOD_START:
                setRememberSendActionPeriodStart((Long)newValue);
                return;
            case ModelPackage.SETTINGS__SERVER_CONFIGURATION_URL:
                setServerConfigurationUrl((String)newValue);
                return;
            case ModelPackage.SETTINGS__PROBLEMS_ZIP_ETAG:
                setProblemsZipEtag((String)newValue);
                return;
            case ModelPackage.SETTINGS__SERVER_CONFIGURATION_LOCAL_FILE:
                setServerConfigurationLocalFile((String)newValue);
                return;
            case ModelPackage.SETTINGS__PROBLEMS_ZIP_LAST_DOWNLOAD_TIMESTAMP:
                setProblemsZipLastDownloadTimestamp((Long)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
            case ModelPackage.SETTINGS__SERVER_CONFIGURATION_URL:
                setServerConfigurationUrl(SERVER_CONFIGURATION_URL_EDEFAULT);
                return;
            case ModelPackage.SETTINGS__PROBLEMS_ZIP_ETAG:
                setProblemsZipEtag(PROBLEMS_ZIP_ETAG_EDEFAULT);
                return;
            case ModelPackage.SETTINGS__SERVER_CONFIGURATION_LOCAL_FILE:
                setServerConfigurationLocalFile(SERVER_CONFIGURATION_LOCAL_FILE_EDEFAULT);
                return;
            case ModelPackage.SETTINGS__PROBLEMS_ZIP_LAST_DOWNLOAD_TIMESTAMP:
                setProblemsZipLastDownloadTimestamp(PROBLEMS_ZIP_LAST_DOWNLOAD_TIMESTAMP_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
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
            case ModelPackage.SETTINGS__SERVER_CONFIGURATION_URL:
                return SERVER_CONFIGURATION_URL_EDEFAULT == null ? serverConfigurationUrl != null : !SERVER_CONFIGURATION_URL_EDEFAULT.equals(serverConfigurationUrl);
            case ModelPackage.SETTINGS__PROBLEMS_ZIP_ETAG:
                return PROBLEMS_ZIP_ETAG_EDEFAULT == null ? problemsZipEtag != null : !PROBLEMS_ZIP_ETAG_EDEFAULT.equals(problemsZipEtag);
            case ModelPackage.SETTINGS__SERVER_CONFIGURATION_LOCAL_FILE:
                return SERVER_CONFIGURATION_LOCAL_FILE_EDEFAULT == null ? serverConfigurationLocalFile != null : !SERVER_CONFIGURATION_LOCAL_FILE_EDEFAULT.equals(serverConfigurationLocalFile);
            case ModelPackage.SETTINGS__PROBLEMS_ZIP_LAST_DOWNLOAD_TIMESTAMP:
                return problemsZipLastDownloadTimestamp != PROBLEMS_ZIP_LAST_DOWNLOAD_TIMESTAMP_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

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
        result.append(", serverConfigurationUrl: ");
        result.append(serverConfigurationUrl);
        result.append(", problemsZipEtag: ");
        result.append(problemsZipEtag);
        result.append(", serverConfigurationLocalFile: ");
        result.append(serverConfigurationLocalFile);
        result.append(", problemsZipLastDownloadTimestamp: ");
        result.append(problemsZipLastDownloadTimestamp);
        result.append(')');
        return result.toString();
    }

} // SettingsImpl
