/**
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Remember Send Action</b></em>', and utility methods
 * for working with them. <!-- end-user-doc -->
 * @see org.eclipse.epp.internal.logging.aeri.ui.model.ModelPackage#getRememberSendAction()
 * @model
 * @generated
 */
public enum RememberSendAction implements Enumerator {
    /**
     * The '<em><b>HOURS 24</b></em>' literal object.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #HOURS_24_VALUE
     * @generated
     * @ordered
     */
    HOURS_24(0, "HOURS_24", "HOURS_24"),

    /**
     * The '<em><b>RESTART</b></em>' literal object.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #RESTART_VALUE
     * @generated
     * @ordered
     */
    RESTART(1, "RESTART", "RESTART"),

    /**
     * The '<em><b>PERMANENT</b></em>' literal object.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #PERMANENT_VALUE
     * @generated
     * @ordered
     */
    PERMANENT(2, "PERMANENT", "PERMANENT"),

    /**
     * The '<em><b>NONE</b></em>' literal object.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #NONE_VALUE
     * @generated
     * @ordered
     */
    NONE(3, "NONE", "NONE");

    /**
     * The '<em><b>HOURS 24</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>HOURS 24</b></em>' literal object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #HOURS_24
     * @model
     * @generated
     * @ordered
     */
    public static final int HOURS_24_VALUE = 0;

    /**
     * The '<em><b>RESTART</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>RESTART</b></em>' literal object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #RESTART
     * @model
     * @generated
     * @ordered
     */
    public static final int RESTART_VALUE = 1;

    /**
     * The '<em><b>PERMANENT</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>PERMANENT</b></em>' literal object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #PERMANENT
     * @model
     * @generated
     * @ordered
     */
    public static final int PERMANENT_VALUE = 2;

    /**
     * The '<em><b>NONE</b></em>' literal value.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of '<em><b>NONE</b></em>' literal object isn't clear, there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @see #NONE
     * @model
     * @generated
     * @ordered
     */
    public static final int NONE_VALUE = 3;

    /**
     * An array of all the '<em><b>Remember Send Action</b></em>' enumerators.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private static final RememberSendAction[] VALUES_ARRAY = new RememberSendAction[] {
            HOURS_24,
            RESTART,
            PERMANENT,
            NONE,
        };

    /**
     * A public read-only list of all the '<em><b>Remember Send Action</b></em>' enumerators.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final List<RememberSendAction> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

    /**
     * Returns the '<em><b>Remember Send Action</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @param literal the literal.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static RememberSendAction get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            RememberSendAction result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Remember Send Action</b></em>' literal with the specified name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param name the name.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static RememberSendAction getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            RememberSendAction result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

    /**
     * Returns the '<em><b>Remember Send Action</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @param value the integer value.
     * @return the matching enumerator or <code>null</code>.
     * @generated
     */
    public static RememberSendAction get(int value) {
        switch (value) {
            case HOURS_24_VALUE: return HOURS_24;
            case RESTART_VALUE: return RESTART;
            case PERMANENT_VALUE: return PERMANENT;
            case NONE_VALUE: return NONE;
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
    private RememberSendAction(int value, String name, String literal) {
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

} // RememberSendAction
