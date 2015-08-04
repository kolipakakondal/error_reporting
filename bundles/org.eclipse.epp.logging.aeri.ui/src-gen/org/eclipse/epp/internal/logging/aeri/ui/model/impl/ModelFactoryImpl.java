/**
 */
package org.eclipse.epp.internal.logging.aeri.ui.model.impl;

import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.epp.internal.logging.aeri.ui.model.Bundle;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelFactory;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage;
import org.eclipse.epp.internal.logging.aeri.ui.model.RememberSendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.SendAction;
import org.eclipse.epp.internal.logging.aeri.ui.model.Settings;
import org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement;
import org.eclipse.epp.internal.logging.aeri.ui.model.Status;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 *
 * @generated
 */
public class ModelFactoryImpl extends EFactoryImpl implements ModelFactory {
    /**
     * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static ModelFactory init() {
        try {
            ModelFactory theModelFactory = (ModelFactory) EPackage.Registry.INSTANCE.getEFactory(ModelPackage.eNS_URI);
            if (theModelFactory != null) {
                return theModelFactory;
            }
        } catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new ModelFactoryImpl();
    }

    /**
     * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ModelFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
        case ModelPackage.ERROR_REPORT:
            return createErrorReport();
        case ModelPackage.BUNDLE:
            return createBundle();
        case ModelPackage.STATUS:
            return createStatus();
        case ModelPackage.THROWABLE:
            return createThrowable();
        case ModelPackage.STACK_TRACE_ELEMENT:
            return createStackTraceElement();
        case ModelPackage.SETTINGS:
            return createSettings();
        default:
            throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
        case ModelPackage.SEND_ACTION:
            return createSendActionFromString(eDataType, initialValue);
        case ModelPackage.REMEMBER_SEND_ACTION:
            return createRememberSendActionFromString(eDataType, initialValue);
        case ModelPackage.UUID:
            return createUUIDFromString(eDataType, initialValue);
        case ModelPackage.LIST_OF_STRINGS:
            return createListOfStringsFromString(eDataType, initialValue);
        default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
        case ModelPackage.SEND_ACTION:
            return convertSendActionToString(eDataType, instanceValue);
        case ModelPackage.REMEMBER_SEND_ACTION:
            return convertRememberSendActionToString(eDataType, instanceValue);
        case ModelPackage.UUID:
            return convertUUIDToString(eDataType, instanceValue);
        case ModelPackage.LIST_OF_STRINGS:
            return convertListOfStringsToString(eDataType, instanceValue);
        default:
            throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ErrorReport createErrorReport() {
        ErrorReportImpl errorReport = new ErrorReportImpl();
        return errorReport;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Bundle createBundle() {
        BundleImpl bundle = new BundleImpl();
        return bundle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Status createStatus() {
        StatusImpl status = new StatusImpl();
        return status;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public org.eclipse.epp.internal.logging.aeri.ui.model.Throwable createThrowable() {
        ThrowableImpl throwable = new ThrowableImpl();
        return throwable;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public StackTraceElement createStackTraceElement() {
        StackTraceElementImpl stackTraceElement = new StackTraceElementImpl();
        return stackTraceElement;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Settings createSettings() {
        SettingsImpl settings = new SettingsImpl();
        return settings;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public SendAction createSendActionFromString(EDataType eDataType, String initialValue) {
        SendAction result = SendAction.get(initialValue);
        if (result == null) {
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertSendActionToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public RememberSendAction createRememberSendActionFromString(EDataType eDataType, String initialValue) {
        RememberSendAction result = RememberSendAction.get(initialValue);
        if (result == null) {
            throw new IllegalArgumentException(
                    "The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        }
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertRememberSendActionToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public UUID createUUIDFromString(EDataType eDataType, String initialValue) {
        return (UUID) super.createFromString(eDataType, initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public String convertUUIDToString(EDataType eDataType, Object instanceValue) {
        return super.convertToString(eDataType, instanceValue);
    }

    public List<String> createListOfStringsFromString(EDataType eDataType, String initialValue) {
        return Splitter.on(';').omitEmptyStrings().trimResults().splitToList(initialValue);

    }

    public String convertListOfStringsToString(EDataType eDataType, Object instanceValue) {
        return Joiner.on(";").skipNulls().join((Iterable<?>) instanceValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ModelPackage getModelPackage() {
        return (ModelPackage) getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @deprecated
     * @generated
     */
    @Deprecated
    public static ModelPackage getPackage() {
        return ModelPackage.eINSTANCE;
    }

} // ModelFactoryImpl
