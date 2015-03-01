/**
 * Copyright (c) 2014 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Daniel Haftstein - initial tests.
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.epp.internal.logging.aeri.ui.model.impl.ModelFactoryImpl;
import org.junit.Test;

public class ModelFactoryTest {

    @Test
    public void testStringToListConversionImplemented() {

        // This test checks if the list-string conversion is implemented in the generated code.
        // If it fails, replace the tested methods with the following implementation:

        // public List<String> createListOfStringsFromString(EDataType eDataType, String initialValue) {
        // return Splitter.on(';').omitEmptyStrings().trimResults().splitToList(initialValue);
        //
        // }
        //
        // public String convertListOfStringsToString(EDataType eDataType, Object instanceValue) {
        // return Joiner.on(";").skipNulls().join((Iterable<?>) instanceValue);
        // }

        ModelFactoryImpl mf = (ModelFactoryImpl) ModelFactory.eINSTANCE;
        String stringList = "a.b;c.d";
        List<String> theList = mf.createListOfStringsFromString(mock(EDataType.class), stringList);
        String convertedStringList = mf.convertListOfStringsToString(mock(EDataType.class), theList);
        assertThat(stringList, is(convertedStringList));
    }
}
