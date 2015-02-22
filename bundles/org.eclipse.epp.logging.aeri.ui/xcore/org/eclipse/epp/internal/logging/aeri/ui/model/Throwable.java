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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Throwable</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getClassName
 * <em>Class Name</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getMessage
 * <em>Message</em>}</li>
 * <li>{@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getCause
 * <em>Cause</em>}</li>
 * <li>
 * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getStackTrace
 * <em>Stack Trace</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getThrowable()
 * @model
 * @generated
 */
public interface Throwable extends EObject {
    /**
     * Returns the value of the '<em><b>Class Name</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Class Name</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Class Name</em>' attribute.
     * @see #setClassName(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getThrowable_ClassName()
     * @model unique="false"
     * @generated
     */
    String getClassName();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getClassName
     * <em>Class Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Class Name</em>' attribute.
     * @see #getClassName()
     * @generated
     */
    void setClassName(String value);

    /**
     * Returns the value of the '<em><b>Message</b></em>' attribute. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Message</em>' attribute isn't clear, there
     * really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Message</em>' attribute.
     * @see #setMessage(String)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getThrowable_Message()
     * @model unique="false"
     * @generated
     */
    String getMessage();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getMessage
     * <em>Message</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @param value
     *            the new value of the '<em>Message</em>' attribute.
     * @see #getMessage()
     * @generated
     */
    void setMessage(String value);

    /**
     * Returns the value of the '<em><b>Cause</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Cause</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Cause</em>' containment reference.
     * @see #setCause(Throwable)
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getThrowable_Cause()
     * @model containment="true"
     * @generated
     */
    Throwable getCause();

    /**
     * Sets the value of the '
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.Throwable#getCause
     * <em>Cause</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @param value
     *            the new value of the '<em>Cause</em>' containment reference.
     * @see #getCause()
     * @generated
     */
    void setCause(Throwable value);

    /**
     * Returns the value of the '<em><b>Stack Trace</b></em>' containment
     * reference list. The list contents are of type
     * {@link org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Stack Trace</em>' containment reference list
     * isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Stack Trace</em>' containment reference
     *         list.
     * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getThrowable_StackTrace()
     * @model containment="true"
     * @generated
     */
    EList<StackTraceElement> getStackTrace();

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @model vUnique="false" annotation=
     *        "http://www.eclipse.org/emf/2002/GenModel body='v.visit(this);\n<%org.eclipse.emf.common.util.EList%><<%org.eclipse.recommenders.internal.stacktraces.rcp.model.StackTraceElement%>> _stackTrace = this.getStackTrace();\nfor (final <%org.eclipse.recommenders.internal.stacktraces.rcp.model.StackTraceElement%> element : _stackTrace)\n{\n\telement.accept(v);\n}\n<%org.eclipse.recommenders.internal.stacktraces.rcp.model.Throwable%> _cause = this.getCause();\nboolean _notEquals = (!<%com.google.common.base.Objects%>.equal(_cause, null));\nif (_notEquals)\n{\n\t<%org.eclipse.recommenders.internal.stacktraces.rcp.model.Throwable%> _cause_1 = this.getCause();\n\t_cause_1.accept(v);\n}'"
     * @generated
     */
    void accept(Visitor v);

} // Throwable
