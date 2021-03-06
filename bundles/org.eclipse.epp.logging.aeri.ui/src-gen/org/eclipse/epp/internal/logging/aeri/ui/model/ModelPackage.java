/**
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "model";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://eclipse.org/epp/logging/aeri";

    /**
     * The package namespace name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "aeri";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    ModelPackage eINSTANCE = org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.ErrorReportImpl <em>Error Report</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ErrorReportImpl
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getErrorReport()
     * @generated
     */
    int ERROR_REPORT = 0;

    /**
     * The feature id for the '<em><b>Anonymous Id</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__ANONYMOUS_ID = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__NAME = 1;

    /**
     * The feature id for the '<em><b>Email</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__EMAIL = 2;

    /**
     * The feature id for the '<em><b>Comment</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__COMMENT = 3;

    /**
     * The feature id for the '<em><b>Eclipse Build Id</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__ECLIPSE_BUILD_ID = 4;

    /**
     * The feature id for the '<em><b>Eclipse Product</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__ECLIPSE_PRODUCT = 5;

    /**
     * The feature id for the '<em><b>Java Runtime Version</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__JAVA_RUNTIME_VERSION = 6;

    /**
     * The feature id for the '<em><b>Osgi Ws</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__OSGI_WS = 7;

    /**
     * The feature id for the '<em><b>Osgi Os</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__OSGI_OS = 8;

    /**
     * The feature id for the '<em><b>Osgi Os Version</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__OSGI_OS_VERSION = 9;

    /**
     * The feature id for the '<em><b>Osgi Arch</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__OSGI_ARCH = 10;

    /**
     * The feature id for the '<em><b>Log Message</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__LOG_MESSAGE = 11;

    /**
     * The feature id for the '<em><b>Ignore Similar</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__IGNORE_SIMILAR = 12;

    /**
     * The feature id for the '<em><b>Present Bundles</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__PRESENT_BUNDLES = 13;

    /**
     * The feature id for the '<em><b>Status</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT__STATUS = 14;

    /**
     * The number of structural features of the '<em>Error Report</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT_FEATURE_COUNT = 15;

    /**
     * The number of operations of the '<em>Error Report</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ERROR_REPORT_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.BundleImpl <em>Bundle</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.BundleImpl
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getBundle()
     * @generated
     */
    int BUNDLE = 1;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUNDLE__NAME = 0;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUNDLE__VERSION = 1;

    /**
     * The number of structural features of the '<em>Bundle</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUNDLE_FEATURE_COUNT = 2;

    /**
     * The number of operations of the '<em>Bundle</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BUNDLE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.StatusImpl <em>Status</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.StatusImpl
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getStatus()
     * @generated
     */
    int STATUS = 2;

    /**
     * The feature id for the '<em><b>Plugin Id</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STATUS__PLUGIN_ID = 0;

    /**
     * The feature id for the '<em><b>Plugin Version</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STATUS__PLUGIN_VERSION = 1;

    /**
     * The feature id for the '<em><b>Code</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STATUS__CODE = 2;

    /**
     * The feature id for the '<em><b>Severity</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STATUS__SEVERITY = 3;

    /**
     * The feature id for the '<em><b>Message</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STATUS__MESSAGE = 4;

    /**
     * The feature id for the '<em><b>Fingerprint</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STATUS__FINGERPRINT = 5;

    /**
     * The feature id for the '<em><b>Children</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STATUS__CHILDREN = 6;

    /**
     * The feature id for the '<em><b>Exception</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STATUS__EXCEPTION = 7;

    /**
     * The number of structural features of the '<em>Status</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STATUS_FEATURE_COUNT = 8;

    /**
     * The number of operations of the '<em>Status</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STATUS_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.ThrowableImpl <em>Throwable</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ThrowableImpl
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getThrowable()
     * @generated
     */
    int THROWABLE = 3;

    /**
     * The feature id for the '<em><b>Class Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int THROWABLE__CLASS_NAME = 0;

    /**
     * The feature id for the '<em><b>Message</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int THROWABLE__MESSAGE = 1;

    /**
     * The feature id for the '<em><b>Cause</b></em>' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int THROWABLE__CAUSE = 2;

    /**
     * The feature id for the '<em><b>Stack Trace</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int THROWABLE__STACK_TRACE = 3;

    /**
     * The number of structural features of the '<em>Throwable</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int THROWABLE_FEATURE_COUNT = 4;

    /**
     * The number of operations of the '<em>Throwable</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int THROWABLE_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.StackTraceElementImpl <em>Stack Trace Element</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.StackTraceElementImpl
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getStackTraceElement()
     * @generated
     */
    int STACK_TRACE_ELEMENT = 4;

    /**
     * The feature id for the '<em><b>File Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STACK_TRACE_ELEMENT__FILE_NAME = 0;

    /**
     * The feature id for the '<em><b>Class Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STACK_TRACE_ELEMENT__CLASS_NAME = 1;

    /**
     * The feature id for the '<em><b>Method Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STACK_TRACE_ELEMENT__METHOD_NAME = 2;

    /**
     * The feature id for the '<em><b>Line Number</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STACK_TRACE_ELEMENT__LINE_NUMBER = 3;

    /**
     * The feature id for the '<em><b>Native</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STACK_TRACE_ELEMENT__NATIVE = 4;

    /**
     * The number of structural features of the '<em>Stack Trace Element</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STACK_TRACE_ELEMENT_FEATURE_COUNT = 5;

    /**
     * The number of operations of the '<em>Stack Trace Element</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int STACK_TRACE_ELEMENT_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl <em>Settings</em>}' class. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getSettings()
     * @generated
     */
    int SETTINGS = 5;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__NAME = 0;

    /**
     * The feature id for the '<em><b>Email</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__EMAIL = 1;

    /**
     * The feature id for the '<em><b>Anonymize Messages</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__ANONYMIZE_MESSAGES = 2;

    /**
     * The feature id for the '<em><b>Anonymize Strack Trace Elements</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__ANONYMIZE_STRACK_TRACE_ELEMENTS = 3;

    /**
     * The feature id for the '<em><b>Configured</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__CONFIGURED = 4;

    /**
     * The feature id for the '<em><b>Skip Similar Errors</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__SKIP_SIMILAR_ERRORS = 5;

    /**
     * The feature id for the '<em><b>Action</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__ACTION = 6;

    /**
     * The feature id for the '<em><b>Remember Send Action</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__REMEMBER_SEND_ACTION = 7;

    /**
     * The feature id for the '<em><b>Remember Send Action Period Start</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__REMEMBER_SEND_ACTION_PERIOD_START = 8;

    /**
     * The feature id for the '<em><b>Server Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__SERVER_URL = 9;

    /**
     * The feature id for the '<em><b>Server Configuration Local File</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS__SERVER_CONFIGURATION_LOCAL_FILE = 10;

    /**
     * The number of structural features of the '<em>Settings</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS_FEATURE_COUNT = 11;

    /**
     * The number of operations of the '<em>Settings</em>' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SETTINGS_OPERATION_COUNT = 0;

    /**
     * The meta object id for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.SendAction <em>Send Action</em>}' enum. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.SendAction
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getSendAction()
     * @generated
     */
    int SEND_ACTION = 6;

    /**
     * The meta object id for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction <em>Remember Send Action</em>}' enum.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getRememberSendAction()
     * @generated
     */
    int REMEMBER_SEND_ACTION = 7;

    /**
     * The meta object id for the '<em>UUID</em>' data type.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see java.util.UUID
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getUUID()
     * @generated
     */
    int UUID = 8;

    /**
     * The meta object id for the '<em>List Of Strings</em>' data type.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see java.util.List
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getListOfStrings()
     * @generated
     */
    int LIST_OF_STRINGS = 9;

    /**
     * Returns the meta object for class '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport <em>Error Report</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Error Report</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport
     * @generated
     */
    EClass getErrorReport();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getAnonymousId <em>Anonymous Id</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Anonymous Id</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getAnonymousId()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_AnonymousId();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getName <em>Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getName()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_Name();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getEmail <em>Email</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Email</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getEmail()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_Email();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getComment <em>Comment</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Comment</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getComment()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_Comment();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getEclipseBuildId <em>Eclipse Build Id</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Eclipse Build Id</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getEclipseBuildId()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_EclipseBuildId();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getEclipseProduct <em>Eclipse Product</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Eclipse Product</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getEclipseProduct()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_EclipseProduct();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getJavaRuntimeVersion <em>Java Runtime Version</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Java Runtime Version</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getJavaRuntimeVersion()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_JavaRuntimeVersion();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getOsgiWs <em>Osgi Ws</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Osgi Ws</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getOsgiWs()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_OsgiWs();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getOsgiOs <em>Osgi Os</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Osgi Os</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getOsgiOs()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_OsgiOs();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getOsgiOsVersion <em>Osgi Os Version</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Osgi Os Version</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getOsgiOsVersion()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_OsgiOsVersion();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getOsgiArch <em>Osgi Arch</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Osgi Arch</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getOsgiArch()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_OsgiArch();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#isLogMessage <em>Log Message</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Log Message</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#isLogMessage()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_LogMessage();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#isIgnoreSimilar <em>Ignore Similar</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ignore Similar</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#isIgnoreSimilar()
     * @see #getErrorReport()
     * @generated
     */
    EAttribute getErrorReport_IgnoreSimilar();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getPresentBundles <em>Present Bundles</em>}'.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Present Bundles</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getPresentBundles()
     * @see #getErrorReport()
     * @generated
     */
    EReference getErrorReport_PresentBundles();

    /**
     * Returns the meta object for the containment reference '{@link org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getStatus <em>Status</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Status</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport#getStatus()
     * @see #getErrorReport()
     * @generated
     */
    EReference getErrorReport_Status();

    /**
     * Returns the meta object for class '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Bundle <em>Bundle</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Bundle</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Bundle
     * @generated
     */
    EClass getBundle();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Bundle#getName <em>Name</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Bundle#getName()
     * @see #getBundle()
     * @generated
     */
    EAttribute getBundle_Name();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Bundle#getVersion <em>Version</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Version</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Bundle#getVersion()
     * @see #getBundle()
     * @generated
     */
    EAttribute getBundle_Version();

    /**
     * Returns the meta object for class '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status <em>Status</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Status</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Status
     * @generated
     */
    EClass getStatus();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getPluginId <em>Plugin Id</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Plugin Id</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Status#getPluginId()
     * @see #getStatus()
     * @generated
     */
    EAttribute getStatus_PluginId();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getPluginVersion <em>Plugin Version</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Plugin Version</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Status#getPluginVersion()
     * @see #getStatus()
     * @generated
     */
    EAttribute getStatus_PluginVersion();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getCode <em>Code</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for the attribute '<em>Code</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Status#getCode()
     * @see #getStatus()
     * @generated
     */
    EAttribute getStatus_Code();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getSeverity <em>Severity</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Severity</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Status#getSeverity()
     * @see #getStatus()
     * @generated
     */
    EAttribute getStatus_Severity();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getMessage <em>Message</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Message</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Status#getMessage()
     * @see #getStatus()
     * @generated
     */
    EAttribute getStatus_Message();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getFingerprint <em>Fingerprint</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Fingerprint</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Status#getFingerprint()
     * @see #getStatus()
     * @generated
     */
    EAttribute getStatus_Fingerprint();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getChildren <em>Children</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Children</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Status#getChildren()
     * @see #getStatus()
     * @generated
     */
    EReference getStatus_Children();

    /**
     * Returns the meta object for the containment reference '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getException <em>Exception</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Exception</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Status#getException()
     * @see #getStatus()
     * @generated
     */
    EReference getStatus_Exception();

    /**
     * Returns the meta object for class '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable <em>Throwable</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Throwable</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Throwable
     * @generated
     */
    EClass getThrowable();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getClassName <em>Class Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Class Name</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getClassName()
     * @see #getThrowable()
     * @generated
     */
    EAttribute getThrowable_ClassName();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getMessage <em>Message</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Message</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getMessage()
     * @see #getThrowable()
     * @generated
     */
    EAttribute getThrowable_Message();

    /**
     * Returns the meta object for the containment reference '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getCause <em>Cause</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference '<em>Cause</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getCause()
     * @see #getThrowable()
     * @generated
     */
    EReference getThrowable_Cause();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getStackTrace <em>Stack Trace</em>}'.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @return the meta object for the containment reference list '<em>Stack Trace</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getStackTrace()
     * @see #getThrowable()
     * @generated
     */
    EReference getThrowable_StackTrace();

    /**
     * Returns the meta object for class '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement <em>Stack Trace Element</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Stack Trace Element</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement
     * @generated
     */
    EClass getStackTraceElement();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getFileName <em>File Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>File Name</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getFileName()
     * @see #getStackTraceElement()
     * @generated
     */
    EAttribute getStackTraceElement_FileName();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getClassName <em>Class Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Class Name</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getClassName()
     * @see #getStackTraceElement()
     * @generated
     */
    EAttribute getStackTraceElement_ClassName();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getMethodName <em>Method Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Method Name</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getMethodName()
     * @see #getStackTraceElement()
     * @generated
     */
    EAttribute getStackTraceElement_MethodName();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getLineNumber <em>Line Number</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Line Number</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getLineNumber()
     * @see #getStackTraceElement()
     * @generated
     */
    EAttribute getStackTraceElement_LineNumber();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#isNative <em>Native</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Native</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#isNative()
     * @see #getStackTraceElement()
     * @generated
     */
    EAttribute getStackTraceElement_Native();

    /**
     * Returns the meta object for class '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings <em>Settings</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for class '<em>Settings</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings
     * @generated
     */
    EClass getSettings();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getName <em>Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getName()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_Name();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getEmail <em>Email</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Email</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getEmail()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_Email();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeMessages <em>Anonymize Messages</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Anonymize Messages</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeMessages()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_AnonymizeMessages();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeStrackTraceElements <em>Anonymize Strack Trace Elements</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Anonymize Strack Trace Elements</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isAnonymizeStrackTraceElements()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_AnonymizeStrackTraceElements();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isConfigured <em>Configured</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Configured</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isConfigured()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_Configured();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isSkipSimilarErrors <em>Skip Similar Errors</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Skip Similar Errors</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#isSkipSimilarErrors()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_SkipSimilarErrors();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getAction <em>Action</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Action</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getAction()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_Action();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendAction <em>Remember Send Action</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Remember Send Action</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendAction()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_RememberSendAction();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendActionPeriodStart <em>Remember Send Action Period Start</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Remember Send Action Period Start</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getRememberSendActionPeriodStart()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_RememberSendActionPeriodStart();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getServerUrl <em>Server Url</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Server Url</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getServerUrl()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_ServerUrl();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getServerConfigurationLocalFile <em>Server Configuration Local File</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Server Configuration Local File</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.Settings#getServerConfigurationLocalFile()
     * @see #getSettings()
     * @generated
     */
    EAttribute getSettings_ServerConfigurationLocalFile();

    /**
     * Returns the meta object for enum '{@link org.eclipse.epp.internal.logging.aeri.ui.model.SendAction <em>Send Action</em>}'. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the meta object for enum '<em>Send Action</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.SendAction
     * @generated
     */
    EEnum getSendAction();

    /**
     * Returns the meta object for enum '{@link org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction <em>Remember Send Action</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for enum '<em>Remember Send Action</em>'.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction
     * @generated
     */
    EEnum getRememberSendAction();

    /**
     * Returns the meta object for data type '{@link java.util.UUID <em>UUID</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for data type '<em>UUID</em>'.
     * @see java.util.UUID
     * @model instanceClass="java.util.UUID"
     * @generated
     */
    EDataType getUUID();

    /**
     * Returns the meta object for data type '{@link java.util.List <em>List Of Strings</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @return the meta object for data type '<em>List Of Strings</em>'.
     * @see java.util.List
     * @model instanceClass="java.util.List<java.lang.String>"
     * @generated
     */
    EDataType getListOfStrings();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    ModelFactory getModelFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each operation of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.ErrorReportImpl <em>Error Report</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ErrorReportImpl
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getErrorReport()
         * @generated
         */
        EClass ERROR_REPORT = eINSTANCE.getErrorReport();

        /**
         * The meta object literal for the '<em><b>Anonymous Id</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_REPORT__ANONYMOUS_ID = eINSTANCE.getErrorReport_AnonymousId();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_REPORT__NAME = eINSTANCE.getErrorReport_Name();

        /**
         * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_REPORT__EMAIL = eINSTANCE.getErrorReport_Email();

        /**
         * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_REPORT__COMMENT = eINSTANCE.getErrorReport_Comment();

        /**
         * The meta object literal for the '<em><b>Eclipse Build Id</b></em>' attribute feature. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         * 
         * @generated
         */
        EAttribute ERROR_REPORT__ECLIPSE_BUILD_ID = eINSTANCE.getErrorReport_EclipseBuildId();

        /**
         * The meta object literal for the '<em><b>Eclipse Product</b></em>' attribute feature. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         * 
         * @generated
         */
        EAttribute ERROR_REPORT__ECLIPSE_PRODUCT = eINSTANCE.getErrorReport_EclipseProduct();

        /**
         * The meta object literal for the '<em><b>Java Runtime Version</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EAttribute ERROR_REPORT__JAVA_RUNTIME_VERSION = eINSTANCE.getErrorReport_JavaRuntimeVersion();

        /**
         * The meta object literal for the '<em><b>Osgi Ws</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_REPORT__OSGI_WS = eINSTANCE.getErrorReport_OsgiWs();

        /**
         * The meta object literal for the '<em><b>Osgi Os</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_REPORT__OSGI_OS = eINSTANCE.getErrorReport_OsgiOs();

        /**
         * The meta object literal for the '<em><b>Osgi Os Version</b></em>' attribute feature. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         * 
         * @generated
         */
        EAttribute ERROR_REPORT__OSGI_OS_VERSION = eINSTANCE.getErrorReport_OsgiOsVersion();

        /**
         * The meta object literal for the '<em><b>Osgi Arch</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_REPORT__OSGI_ARCH = eINSTANCE.getErrorReport_OsgiArch();

        /**
         * The meta object literal for the '<em><b>Log Message</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_REPORT__LOG_MESSAGE = eINSTANCE.getErrorReport_LogMessage();

        /**
         * The meta object literal for the '<em><b>Ignore Similar</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute ERROR_REPORT__IGNORE_SIMILAR = eINSTANCE.getErrorReport_IgnoreSimilar();

        /**
         * The meta object literal for the '<em><b>Present Bundles</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ERROR_REPORT__PRESENT_BUNDLES = eINSTANCE.getErrorReport_PresentBundles();

        /**
         * The meta object literal for the '<em><b>Status</b></em>' containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         * 
         * @generated
         */
        EReference ERROR_REPORT__STATUS = eINSTANCE.getErrorReport_Status();

        /**
         * The meta object literal for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.BundleImpl <em>Bundle</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.BundleImpl
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getBundle()
         * @generated
         */
        EClass BUNDLE = eINSTANCE.getBundle();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute BUNDLE__NAME = eINSTANCE.getBundle_Name();

        /**
         * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute BUNDLE__VERSION = eINSTANCE.getBundle_Version();

        /**
         * The meta object literal for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.StatusImpl <em>Status</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.StatusImpl
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getStatus()
         * @generated
         */
        EClass STATUS = eINSTANCE.getStatus();

        /**
         * The meta object literal for the '<em><b>Plugin Id</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STATUS__PLUGIN_ID = eINSTANCE.getStatus_PluginId();

        /**
         * The meta object literal for the '<em><b>Plugin Version</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STATUS__PLUGIN_VERSION = eINSTANCE.getStatus_PluginVersion();

        /**
         * The meta object literal for the '<em><b>Code</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STATUS__CODE = eINSTANCE.getStatus_Code();

        /**
         * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STATUS__SEVERITY = eINSTANCE.getStatus_Severity();

        /**
         * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STATUS__MESSAGE = eINSTANCE.getStatus_Message();

        /**
         * The meta object literal for the '<em><b>Fingerprint</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STATUS__FINGERPRINT = eINSTANCE.getStatus_Fingerprint();

        /**
         * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference STATUS__CHILDREN = eINSTANCE.getStatus_Children();

        /**
         * The meta object literal for the '<em><b>Exception</b></em>' containment reference feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference STATUS__EXCEPTION = eINSTANCE.getStatus_Exception();

        /**
         * The meta object literal for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.ThrowableImpl <em>Throwable</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ThrowableImpl
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getThrowable()
         * @generated
         */
        EClass THROWABLE = eINSTANCE.getThrowable();

        /**
         * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute THROWABLE__CLASS_NAME = eINSTANCE.getThrowable_ClassName();

        /**
         * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute THROWABLE__MESSAGE = eINSTANCE.getThrowable_Message();

        /**
         * The meta object literal for the '<em><b>Cause</b></em>' containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         * 
         * @generated
         */
        EReference THROWABLE__CAUSE = eINSTANCE.getThrowable_Cause();

        /**
         * The meta object literal for the '<em><b>Stack Trace</b></em>' containment reference list feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EReference THROWABLE__STACK_TRACE = eINSTANCE.getThrowable_StackTrace();

        /**
         * The meta object literal for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.StackTraceElementImpl <em>Stack Trace Element</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.StackTraceElementImpl
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getStackTraceElement()
         * @generated
         */
        EClass STACK_TRACE_ELEMENT = eINSTANCE.getStackTraceElement();

        /**
         * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STACK_TRACE_ELEMENT__FILE_NAME = eINSTANCE.getStackTraceElement_FileName();

        /**
         * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STACK_TRACE_ELEMENT__CLASS_NAME = eINSTANCE.getStackTraceElement_ClassName();

        /**
         * The meta object literal for the '<em><b>Method Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STACK_TRACE_ELEMENT__METHOD_NAME = eINSTANCE.getStackTraceElement_MethodName();

        /**
         * The meta object literal for the '<em><b>Line Number</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STACK_TRACE_ELEMENT__LINE_NUMBER = eINSTANCE.getStackTraceElement_LineNumber();

        /**
         * The meta object literal for the '<em><b>Native</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute STACK_TRACE_ELEMENT__NATIVE = eINSTANCE.getStackTraceElement_Native();

        /**
         * The meta object literal for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl <em>Settings</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.SettingsImpl
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getSettings()
         * @generated
         */
        EClass SETTINGS = eINSTANCE.getSettings();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute SETTINGS__NAME = eINSTANCE.getSettings_Name();

        /**
         * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute SETTINGS__EMAIL = eINSTANCE.getSettings_Email();

        /**
         * The meta object literal for the '<em><b>Anonymize Messages</b></em>' attribute feature. <!-- begin-user-doc --> <!-- end-user-doc
         * -->
         * 
         * @generated
         */
        EAttribute SETTINGS__ANONYMIZE_MESSAGES = eINSTANCE.getSettings_AnonymizeMessages();

        /**
         * The meta object literal for the '<em><b>Anonymize Strack Trace Elements</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EAttribute SETTINGS__ANONYMIZE_STRACK_TRACE_ELEMENTS = eINSTANCE.getSettings_AnonymizeStrackTraceElements();

        /**
         * The meta object literal for the '<em><b>Configured</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute SETTINGS__CONFIGURED = eINSTANCE.getSettings_Configured();

        /**
         * The meta object literal for the '<em><b>Skip Similar Errors</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EAttribute SETTINGS__SKIP_SIMILAR_ERRORS = eINSTANCE.getSettings_SkipSimilarErrors();

        /**
         * The meta object literal for the '<em><b>Action</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @generated
         */
        EAttribute SETTINGS__ACTION = eINSTANCE.getSettings_Action();

        /**
         * The meta object literal for the '<em><b>Remember Send Action</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EAttribute SETTINGS__REMEMBER_SEND_ACTION = eINSTANCE.getSettings_RememberSendAction();

        /**
         * The meta object literal for the '<em><b>Remember Send Action Period Start</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SETTINGS__REMEMBER_SEND_ACTION_PERIOD_START = eINSTANCE.getSettings_RememberSendActionPeriodStart();

        /**
         * The meta object literal for the '<em><b>Server Url</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SETTINGS__SERVER_URL = eINSTANCE.getSettings_ServerUrl();

        /**
         * The meta object literal for the '<em><b>Server Configuration Local File</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SETTINGS__SERVER_CONFIGURATION_LOCAL_FILE = eINSTANCE.getSettings_ServerConfigurationLocalFile();

        /**
         * The meta object literal for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.SendAction <em>Send Action</em>}' enum.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.SendAction
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getSendAction()
         * @generated
         */
        EEnum SEND_ACTION = eINSTANCE.getSendAction();

        /**
         * The meta object literal for the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction <em>Remember Send Action</em>}' enum.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getRememberSendAction()
         * @generated
         */
        EEnum REMEMBER_SEND_ACTION = eINSTANCE.getRememberSendAction();

        /**
         * The meta object literal for the '<em>UUID</em>' data type.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see java.util.UUID
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getUUID()
         * @generated
         */
        EDataType UUID = eINSTANCE.getUUID();

        /**
         * The meta object literal for the '<em>List Of Strings</em>' data type.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see java.util.List
         * @see org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelPackageImpl#getListOfStrings()
         * @generated
         */
        EDataType LIST_OF_STRINGS = eINSTANCE.getListOfStrings();

    }

} // ModelPackage
