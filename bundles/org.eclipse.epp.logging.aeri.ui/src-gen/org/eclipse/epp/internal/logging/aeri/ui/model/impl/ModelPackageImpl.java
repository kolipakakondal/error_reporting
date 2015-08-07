/**
 */
package org.eclipse.epp.internal.logging.aeri.ui.model.impl;

import java.util.List;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.epp.internal.logging.aeri.ui.model.Bundle;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelFactory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement;
import org.eclipse.epp.internal.logging.aeri.ui.model.Status;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass errorReportEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass bundleEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass statusEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass throwableEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass stackTraceElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass settingsEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum sendActionEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum rememberSendActionEEnum = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType uuidEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType listOfStringsEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ModelPackageImpl() {
        super(eNS_URI, ModelFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ModelPackage init() {
        if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

        // Obtain or create and register package
        ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ModelPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theModelPackage.createPackageContents();

        // Initialize created meta-data
        theModelPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theModelPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
        return theModelPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getErrorReport() {
        return errorReportEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_AnonymousId() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_Name() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_Email() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_Comment() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_EclipseBuildId() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_EclipseProduct() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_JavaRuntimeVersion() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_OsgiWs() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_OsgiOs() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_OsgiOsVersion() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_OsgiArch() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_LogMessage() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getErrorReport_IgnoreSimilar() {
        return (EAttribute)errorReportEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getErrorReport_PresentBundles() {
        return (EReference)errorReportEClass.getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getErrorReport_Status() {
        return (EReference)errorReportEClass.getEStructuralFeatures().get(14);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getBundle() {
        return bundleEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBundle_Name() {
        return (EAttribute)bundleEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBundle_Version() {
        return (EAttribute)bundleEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getStatus() {
        return statusEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStatus_PluginId() {
        return (EAttribute)statusEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStatus_PluginVersion() {
        return (EAttribute)statusEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStatus_Code() {
        return (EAttribute)statusEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStatus_Severity() {
        return (EAttribute)statusEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStatus_Message() {
        return (EAttribute)statusEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStatus_Fingerprint() {
        return (EAttribute)statusEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getStatus_Children() {
        return (EReference)statusEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getStatus_Exception() {
        return (EReference)statusEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getThrowable() {
        return throwableEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getThrowable_ClassName() {
        return (EAttribute)throwableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getThrowable_Message() {
        return (EAttribute)throwableEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getThrowable_Cause() {
        return (EReference)throwableEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getThrowable_StackTrace() {
        return (EReference)throwableEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getStackTraceElement() {
        return stackTraceElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStackTraceElement_FileName() {
        return (EAttribute)stackTraceElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStackTraceElement_ClassName() {
        return (EAttribute)stackTraceElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStackTraceElement_MethodName() {
        return (EAttribute)stackTraceElementEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStackTraceElement_LineNumber() {
        return (EAttribute)stackTraceElementEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStackTraceElement_Native() {
        return (EAttribute)stackTraceElementEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getSettings() {
        return settingsEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_Name() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_Email() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_AnonymizeMessages() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_AnonymizeStrackTraceElements() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_Configured() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_SkipSimilarErrors() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_Action() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_RememberSendAction() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_RememberSendActionPeriodStart() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_ServerUrl() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSettings_ServerConfigurationLocalFile() {
        return (EAttribute)settingsEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getSendAction() {
        return sendActionEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getRememberSendAction() {
        return rememberSendActionEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getUUID() {
        return uuidEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getListOfStrings() {
        return listOfStringsEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ModelFactory getModelFactory() {
        return (ModelFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but its first. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        errorReportEClass = createEClass(ERROR_REPORT);
        createEAttribute(errorReportEClass, ERROR_REPORT__ANONYMOUS_ID);
        createEAttribute(errorReportEClass, ERROR_REPORT__NAME);
        createEAttribute(errorReportEClass, ERROR_REPORT__EMAIL);
        createEAttribute(errorReportEClass, ERROR_REPORT__COMMENT);
        createEAttribute(errorReportEClass, ERROR_REPORT__ECLIPSE_BUILD_ID);
        createEAttribute(errorReportEClass, ERROR_REPORT__ECLIPSE_PRODUCT);
        createEAttribute(errorReportEClass, ERROR_REPORT__JAVA_RUNTIME_VERSION);
        createEAttribute(errorReportEClass, ERROR_REPORT__OSGI_WS);
        createEAttribute(errorReportEClass, ERROR_REPORT__OSGI_OS);
        createEAttribute(errorReportEClass, ERROR_REPORT__OSGI_OS_VERSION);
        createEAttribute(errorReportEClass, ERROR_REPORT__OSGI_ARCH);
        createEAttribute(errorReportEClass, ERROR_REPORT__LOG_MESSAGE);
        createEAttribute(errorReportEClass, ERROR_REPORT__IGNORE_SIMILAR);
        createEReference(errorReportEClass, ERROR_REPORT__PRESENT_BUNDLES);
        createEReference(errorReportEClass, ERROR_REPORT__STATUS);

        bundleEClass = createEClass(BUNDLE);
        createEAttribute(bundleEClass, BUNDLE__NAME);
        createEAttribute(bundleEClass, BUNDLE__VERSION);

        statusEClass = createEClass(STATUS);
        createEAttribute(statusEClass, STATUS__PLUGIN_ID);
        createEAttribute(statusEClass, STATUS__PLUGIN_VERSION);
        createEAttribute(statusEClass, STATUS__CODE);
        createEAttribute(statusEClass, STATUS__SEVERITY);
        createEAttribute(statusEClass, STATUS__MESSAGE);
        createEAttribute(statusEClass, STATUS__FINGERPRINT);
        createEReference(statusEClass, STATUS__CHILDREN);
        createEReference(statusEClass, STATUS__EXCEPTION);

        throwableEClass = createEClass(THROWABLE);
        createEAttribute(throwableEClass, THROWABLE__CLASS_NAME);
        createEAttribute(throwableEClass, THROWABLE__MESSAGE);
        createEReference(throwableEClass, THROWABLE__CAUSE);
        createEReference(throwableEClass, THROWABLE__STACK_TRACE);

        stackTraceElementEClass = createEClass(STACK_TRACE_ELEMENT);
        createEAttribute(stackTraceElementEClass, STACK_TRACE_ELEMENT__FILE_NAME);
        createEAttribute(stackTraceElementEClass, STACK_TRACE_ELEMENT__CLASS_NAME);
        createEAttribute(stackTraceElementEClass, STACK_TRACE_ELEMENT__METHOD_NAME);
        createEAttribute(stackTraceElementEClass, STACK_TRACE_ELEMENT__LINE_NUMBER);
        createEAttribute(stackTraceElementEClass, STACK_TRACE_ELEMENT__NATIVE);

        settingsEClass = createEClass(SETTINGS);
        createEAttribute(settingsEClass, SETTINGS__NAME);
        createEAttribute(settingsEClass, SETTINGS__EMAIL);
        createEAttribute(settingsEClass, SETTINGS__ANONYMIZE_MESSAGES);
        createEAttribute(settingsEClass, SETTINGS__ANONYMIZE_STRACK_TRACE_ELEMENTS);
        createEAttribute(settingsEClass, SETTINGS__CONFIGURED);
        createEAttribute(settingsEClass, SETTINGS__SKIP_SIMILAR_ERRORS);
        createEAttribute(settingsEClass, SETTINGS__ACTION);
        createEAttribute(settingsEClass, SETTINGS__REMEMBER_SEND_ACTION);
        createEAttribute(settingsEClass, SETTINGS__REMEMBER_SEND_ACTION_PERIOD_START);
        createEAttribute(settingsEClass, SETTINGS__SERVER_URL);
        createEAttribute(settingsEClass, SETTINGS__SERVER_CONFIGURATION_LOCAL_FILE);

        // Create enums
        sendActionEEnum = createEEnum(SEND_ACTION);
        rememberSendActionEEnum = createEEnum(REMEMBER_SEND_ACTION);

        // Create data types
        uuidEDataType = createEDataType(UUID);
        listOfStringsEDataType = createEDataType(LIST_OF_STRINGS);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes, features, and operations; add parameters
        initEClass(errorReportEClass, ErrorReport.class, "ErrorReport", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getErrorReport_AnonymousId(), this.getUUID(), "anonymousId", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_Name(), ecorePackage.getEString(), "name", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_Email(), ecorePackage.getEString(), "email", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_EclipseBuildId(), ecorePackage.getEString(), "eclipseBuildId", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_EclipseProduct(), ecorePackage.getEString(), "eclipseProduct", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_JavaRuntimeVersion(), ecorePackage.getEString(), "javaRuntimeVersion", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_OsgiWs(), ecorePackage.getEString(), "osgiWs", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_OsgiOs(), ecorePackage.getEString(), "osgiOs", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_OsgiOsVersion(), ecorePackage.getEString(), "osgiOsVersion", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_OsgiArch(), ecorePackage.getEString(), "osgiArch", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_LogMessage(), ecorePackage.getEBoolean(), "logMessage", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getErrorReport_IgnoreSimilar(), ecorePackage.getEBoolean(), "ignoreSimilar", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getErrorReport_PresentBundles(), this.getBundle(), null, "presentBundles", null, 0, -1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getErrorReport_Status(), this.getStatus(), null, "status", null, 0, 1, ErrorReport.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(bundleEClass, Bundle.class, "Bundle", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getBundle_Name(), ecorePackage.getEString(), "name", null, 0, 1, Bundle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBundle_Version(), ecorePackage.getEString(), "version", null, 0, 1, Bundle.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(statusEClass, Status.class, "Status", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getStatus_PluginId(), ecorePackage.getEString(), "pluginId", null, 0, 1, Status.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStatus_PluginVersion(), ecorePackage.getEString(), "pluginVersion", null, 0, 1, Status.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStatus_Code(), ecorePackage.getEInt(), "code", null, 0, 1, Status.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStatus_Severity(), ecorePackage.getEInt(), "severity", null, 0, 1, Status.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStatus_Message(), ecorePackage.getEString(), "message", null, 0, 1, Status.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStatus_Fingerprint(), ecorePackage.getEString(), "fingerprint", null, 0, 1, Status.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getStatus_Children(), this.getStatus(), null, "children", null, 0, -1, Status.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getStatus_Exception(), this.getThrowable(), null, "exception", null, 0, 1, Status.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(throwableEClass, org.eclipse.epp.internal.logging.aeri.ui.model.Throwable.class, "Throwable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getThrowable_ClassName(), ecorePackage.getEString(), "className", null, 0, 1, org.eclipse.epp.internal.logging.aeri.ui.model.Throwable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getThrowable_Message(), ecorePackage.getEString(), "message", null, 0, 1, org.eclipse.epp.internal.logging.aeri.ui.model.Throwable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getThrowable_Cause(), this.getThrowable(), null, "cause", null, 0, 1, org.eclipse.epp.internal.logging.aeri.ui.model.Throwable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getThrowable_StackTrace(), this.getStackTraceElement(), null, "stackTrace", null, 0, -1, org.eclipse.epp.internal.logging.aeri.ui.model.Throwable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(stackTraceElementEClass, StackTraceElement.class, "StackTraceElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getStackTraceElement_FileName(), ecorePackage.getEString(), "fileName", null, 0, 1, StackTraceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStackTraceElement_ClassName(), ecorePackage.getEString(), "className", null, 0, 1, StackTraceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStackTraceElement_MethodName(), ecorePackage.getEString(), "methodName", null, 0, 1, StackTraceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStackTraceElement_LineNumber(), ecorePackage.getEInt(), "lineNumber", null, 0, 1, StackTraceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getStackTraceElement_Native(), ecorePackage.getEBoolean(), "native", null, 0, 1, StackTraceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(settingsEClass, Settings.class, "Settings", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSettings_Name(), ecorePackage.getEString(), "name", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSettings_Email(), ecorePackage.getEString(), "email", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSettings_AnonymizeMessages(), ecorePackage.getEBoolean(), "anonymizeMessages", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSettings_AnonymizeStrackTraceElements(), ecorePackage.getEBoolean(), "anonymizeStrackTraceElements", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSettings_Configured(), ecorePackage.getEBoolean(), "configured", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSettings_SkipSimilarErrors(), ecorePackage.getEBoolean(), "skipSimilarErrors", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSettings_Action(), this.getSendAction(), "action", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSettings_RememberSendAction(), this.getRememberSendAction(), "rememberSendAction", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSettings_RememberSendActionPeriodStart(), ecorePackage.getELong(), "rememberSendActionPeriodStart", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSettings_ServerUrl(), ecorePackage.getEString(), "serverUrl", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSettings_ServerConfigurationLocalFile(), ecorePackage.getEString(), "serverConfigurationLocalFile", null, 0, 1, Settings.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(sendActionEEnum, SendAction.class, "SendAction");
        addEEnumLiteral(sendActionEEnum, SendAction.ASK);
        addEEnumLiteral(sendActionEEnum, SendAction.IGNORE);
        addEEnumLiteral(sendActionEEnum, SendAction.SILENT);

        initEEnum(rememberSendActionEEnum, RememberSendAction.class, "RememberSendAction");
        addEEnumLiteral(rememberSendActionEEnum, RememberSendAction.HOURS_24);
        addEEnumLiteral(rememberSendActionEEnum, RememberSendAction.RESTART);
        addEEnumLiteral(rememberSendActionEEnum, RememberSendAction.PERMANENT);
        addEEnumLiteral(rememberSendActionEEnum, RememberSendAction.NONE);

        // Initialize data types
        initEDataType(uuidEDataType, java.util.UUID.class, "UUID", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
        initEDataType(listOfStringsEDataType, List.class, "ListOfStrings", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS, "java.util.List<java.lang.String>");

        // Create resource
        createResource(eNS_URI);
    }

} // ModelPackageImpl
