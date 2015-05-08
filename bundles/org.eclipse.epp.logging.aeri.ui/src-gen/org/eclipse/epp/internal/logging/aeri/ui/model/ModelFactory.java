/**
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of the model. <!--
 * end-user-doc -->
 * 
 * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage
 * @generated
 */
public interface ModelFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    ModelFactory eINSTANCE = org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Error Report</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Error Report</em>'.
     * @generated
     */
    ErrorReport createErrorReport();

    /**
     * Returns a new object of class '<em>Bundle</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Bundle</em>'.
     * @generated
     */
    Bundle createBundle();

    /**
     * Returns a new object of class '<em>Status</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Status</em>'.
     * @generated
     */
    Status createStatus();

    /**
     * Returns a new object of class '<em>Throwable</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Throwable</em>'.
     * @generated
     */
    Throwable createThrowable();

    /**
     * Returns a new object of class '<em>Stack Trace Element</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Stack Trace Element</em>'.
     * @generated
     */
    StackTraceElement createStackTraceElement();

    /**
     * Returns a new object of class '<em>Settings</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return a new object of class '<em>Settings</em>'.
     * @generated
     */
    Settings createSettings();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @return the package supported by this factory.
     * @generated
     */
    ModelPackage getModelPackage();

} // ModelFactory
