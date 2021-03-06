/**
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Send Action</b></em>', and utility methods for
 * working with them. <!-- end-user-doc -->
 * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getSendAction()
 * @model
 * @generated
 */
public enum SendAction implements Enumerator {
    /**
     * The '<em><b>ASK</b></em>' literal object.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #ASK_VALUE
     * @generated
     * @ordered
     */
    ASK(0, "ASK", "ASK"),

    /**
     * The '<em><b>IGNORE</b></em>' literal object.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #IGNORE_VALUE
     * @generated
     * @ordered
     */
    IGNORE(1, "IGNORE", "IGNORE"),

    /**
     * The '<em><b>SILENT</b></em>' literal object.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #SILENT_VALUE
     * @generated
     * @ordered
     */
    SILENT(2, "SILENT", "SILENT");

    /**
     * The '<em><b>ASK</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>ASK</b></em>' literal object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #ASK
     * @model
     * @generated
     * @ordered
     */
    public static final int ASK_VALUE = 0;

    /**
     * The '<em><b>IGNORE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>IGNORE</b></em>' literal object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #IGNORE
     * @model
     * @generated
     * @ordered
     */
    public static final int IGNORE_VALUE = 1;

    /**
     * The '<em><b>SILENT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>SILENT</b></em>' literal object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #SILENT
     * @model
     * @generated
     * @ordered
     */
    public static final int SILENT_VALUE = 2;

    /**
     * An array of all the '<em><b>Send Action</b></em>' enumerators.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private static final SendAction[] VALUES_ARRAY = new SendAction[] {
            ASK,
            IGNORE,
            SILENT,
        };

    /**
     * A public read-only list of all the '<em><b>Send Action</b></em>' enumerators.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final List<SendAction> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Send Action</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static SendAction get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SendAction result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Send Action</b></em>' literal with the specified name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static SendAction getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SendAction result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Send Action</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static SendAction get(int value) {
        switch (value) {
            case ASK_VALUE: return ASK;
            case IGNORE_VALUE: return IGNORE;
            case SILENT_VALUE: return SILENT;
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final int value;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final String name;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private final String literal;

    /**
     * Only this class can construct instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private SendAction(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public int getValue() {
      return value;
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
    public String getLiteral() {
      return literal;
    }

    /**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        return literal;
    }

} // SendAction
