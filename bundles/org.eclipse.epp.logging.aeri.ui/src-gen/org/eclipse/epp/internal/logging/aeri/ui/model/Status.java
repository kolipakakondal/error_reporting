/**
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Status</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getPluginId <em>Plugin Id</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getPluginVersion <em>Plugin Version</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getCode <em>Code</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getSeverity <em>Severity</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getMessage <em>Message</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getFingerprint <em>Fingerprint</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getChildren <em>Children</em>}</li>
 *   <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getException <em>Exception</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStatus()
 * @model
 * @generated
 */
public interface Status extends EObject {
    /**
     * Returns the value of the '<em><b>Plugin Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Plugin Id</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Plugin Id</em>' attribute.
     * @see #setPluginId(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStatus_PluginId()
     * @model
     * @generated
     */
    String getPluginId();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getPluginId <em>Plugin Id</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Plugin Id</em>' attribute.
     * @see #getPluginId()
     * @generated
     */
    void setPluginId(String value);

    /**
     * Returns the value of the '<em><b>Plugin Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Plugin Version</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Plugin Version</em>' attribute.
     * @see #setPluginVersion(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStatus_PluginVersion()
     * @model
     * @generated
     */
    String getPluginVersion();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getPluginVersion <em>Plugin Version</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Plugin Version</em>' attribute.
     * @see #getPluginVersion()
     * @generated
     */
    void setPluginVersion(String value);

    /**
     * Returns the value of the '<em><b>Code</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Code</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Code</em>' attribute.
     * @see #setCode(int)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStatus_Code()
     * @model
     * @generated
     */
    int getCode();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getCode <em>Code</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Code</em>' attribute.
     * @see #getCode()
     * @generated
     */
    void setCode(int value);

    /**
     * Returns the value of the '<em><b>Severity</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Severity</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Severity</em>' attribute.
     * @see #setSeverity(int)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStatus_Severity()
     * @model
     * @generated
     */
    int getSeverity();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getSeverity <em>Severity</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Severity</em>' attribute.
     * @see #getSeverity()
     * @generated
     */
    void setSeverity(int value);

    /**
     * Returns the value of the '<em><b>Message</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Message</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Message</em>' attribute.
     * @see #setMessage(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStatus_Message()
     * @model
     * @generated
     */
    String getMessage();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getMessage <em>Message</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Message</em>' attribute.
     * @see #getMessage()
     * @generated
     */
    void setMessage(String value);

    /**
     * Returns the value of the '<em><b>Fingerprint</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Fingerprint</em>' attribute isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Fingerprint</em>' attribute.
     * @see #setFingerprint(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStatus_Fingerprint()
     * @model
     * @generated
     */
    String getFingerprint();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getFingerprint <em>Fingerprint</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Fingerprint</em>' attribute.
     * @see #getFingerprint()
     * @generated
     */
    void setFingerprint(String value);

    /**
     * Returns the value of the '<em><b>Children</b></em>' containment reference list.
     * The list contents are of type {@link org.eclipse.epp.internal.logging.aeri.ui.model.Status}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Children</em>' reference list isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Children</em>' containment reference list.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStatus_Children()
     * @model containment="true"
     * @generated
     */
    EList<Status> getChildren();

    /**
     * Returns the value of the '<em><b>Exception</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Exception</em>' containment reference isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Exception</em>' containment reference.
     * @see #setException(org.eclipse.epp.internal.logging.aeri.ui.model.Throwable)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getStatus_Exception()
     * @model containment="true"
     * @generated
     */
    org.eclipse.epp.internal.logging.aeri.ui.model.Throwable getException();

    /**
     * Sets the value of the '{@link org.eclipse.epp.internal.logging.aeri.ui.model.Status#getException <em>Exception</em>}' containment reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Exception</em>' containment reference.
     * @see #getException()
     * @generated
     */
    void setException(org.eclipse.epp.internal.logging.aeri.ui.model.Throwable value);

} // Status
