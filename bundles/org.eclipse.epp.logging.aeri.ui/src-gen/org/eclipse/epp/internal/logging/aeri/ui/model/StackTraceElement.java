/**
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Stack Trace Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getFileName <em>File Name</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getMethodName <em>Method Name</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#isNative <em>Native</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStackTraceElement()
 * @model
 * @generated
 */
public interface StackTraceElement extends EObject {
    /**
     * Returns the value of the '<em><b>File Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>File Name</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>File Name</em>' attribute.
     * @see #setFileName(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStackTraceElement_FileName()
     * @model
     * @generated
     */
    String getFileName();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getFileName <em>File Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>File Name</em>' attribute.
     * @see #getFileName()
     * @generated
     */
    void setFileName(String value);

    /**
     * Returns the value of the '<em><b>Class Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Class Name</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Class Name</em>' attribute.
     * @see #setClassName(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStackTraceElement_ClassName()
     * @model
     * @generated
     */
    String getClassName();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getClassName <em>Class Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Class Name</em>' attribute.
     * @see #getClassName()
     * @generated
     */
    void setClassName(String value);

    /**
     * Returns the value of the '<em><b>Method Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Method Name</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Method Name</em>' attribute.
     * @see #setMethodName(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStackTraceElement_MethodName()
     * @model
     * @generated
     */
    String getMethodName();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getMethodName <em>Method Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Method Name</em>' attribute.
     * @see #getMethodName()
     * @generated
     */
    void setMethodName(String value);

    /**
     * Returns the value of the '<em><b>Line Number</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Line Number</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Line Number</em>' attribute.
     * @see #setLineNumber(int)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStackTraceElement_LineNumber()
     * @model
     * @generated
     */
    int getLineNumber();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#getLineNumber <em>Line Number</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Line Number</em>' attribute.
     * @see #getLineNumber()
     * @generated
     */
    void setLineNumber(int value);

    /**
     * Returns the value of the '<em><b>Native</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Native</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Native</em>' attribute.
     * @see #setNative(boolean)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStackTraceElement_Native()
     * @model
     * @generated
     */
    boolean isNative();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement#isNative <em>Native</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Native</em>' attribute.
     * @see #isNative()
     * @generated
     */
    void setNative(boolean value);

} // StackTraceElement
