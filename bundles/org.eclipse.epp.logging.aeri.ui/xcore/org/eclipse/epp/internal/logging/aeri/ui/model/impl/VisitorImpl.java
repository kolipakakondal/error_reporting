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
package org.eclipse.epp.internal.logging.aeri.ui.model.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.epp.internal.logging.aeri.ui.model.Bundle;
import org.eclipse.epp.internal.logging.aeri.ui.model.ErrorReport;
import org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage;
import org.eclipse.epp.internal.logging.aeri.ui.model.StackTraceElement;
import org.eclipse.epp.internal.logging.aeri.ui.model.Status;
import org.eclipse.epp.internal.logging.aeri.ui.model.Visitor;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Visitor</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class VisitorImpl extends MinimalEObjectImpl.Container implements Visitor {
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected VisitorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ModelPackage.Literals.VISITOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void visit(final ErrorReport report) {

    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void visit(final Status status) {

    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void visit(final Bundle bundle) {

    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void visit(final org.eclipse.epp.internal.logging.aeri.ui.model.Throwable throwable) {

    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void visit(final StackTraceElement element) {

    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
        case ModelPackage.VISITOR___VISIT__ERRORREPORT:
            visit((ErrorReport) arguments.get(0));
            return null;
        case ModelPackage.VISITOR___VISIT__STATUS:
            visit((Status) arguments.get(0));
            return null;
        case ModelPackage.VISITOR___VISIT__BUNDLE:
            visit((Bundle) arguments.get(0));
            return null;
        case ModelPackage.VISITOR___VISIT__THROWABLE:
            visit((org.eclipse.epp.internal.logging.aeri.ui.model.Throwable) arguments.get(0));
            return null;
        case ModelPackage.VISITOR___VISIT__STACKTRACEELEMENT:
            visit((StackTraceElement) arguments.get(0));
            return null;
        }
        return super.eInvoke(operationID, arguments);
    }

} // VisitorImpl
