/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Marcel Bruch - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui.utils;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.Test;

import com.google.gson.reflect.TypeToken;

public class JsonTest {

    @Test
    public void testEmptyFileDeserialization() throws IOException {
        // setup
        File f = File.createTempFile("tmp", ".json");
        f.deleteOnExit();

        // exercise
        Object res = Json.deserialize(f, new TypeToken<Map<File, String>>() {
        }.getType());

        // it's actually null :)
        assertNull(res);
    }

    @Test
    public void testPrettyPrint() {
        final JsonTestStruct struct = JsonTestStruct.create("string", 0.43d, "s1", "s2");
        Json.serialize(struct);
    }

    @Test
    public void testSerializationRoundTrip() {
        // setup
        final JsonTestStruct input = JsonTestStruct.create("string", 0.43d, "s1", "s2");
        // exercise
        final String json = Json.serialize(input);
        final JsonTestStruct output = Json.deserialize(json, JsonTestStruct.class);
        // verify
        assertEquals(input, output);
    }

    @Test
    public void testSerializationRoundTrip_ViaInputStream() {
        // setup
        final JsonTestStruct input = JsonTestStruct.create("string", 0.43d, "s1", "s2");
        // exercise
        final String json = Json.serialize(input);
        final JsonTestStruct output = Json.deserialize(new ByteArrayInputStream(json.getBytes()), JsonTestStruct.class);
        // verify
        assertEquals(input, output);
    }

    @Test
    public void testSerializationPrettyRoundTrip_ViaInputStream() {
        // setup
        final JsonTestStruct input = JsonTestStruct.create("string", 0.43d, "s1", "s2");
        // exercise
        final String prettyJson = Json.serialize(input);
        final JsonTestStruct output = Json.deserialize(prettyJson, JsonTestStruct.class);
        // verify
        assertEquals(input, output);
    }

    public static class JsonTestStruct {

        String string;

        double d;

        List<String> list;

        public static JsonTestStruct create(String s, double d, String... list) {
            JsonTestStruct res = new JsonTestStruct();
            res.string = s;
            res.d = d;
            res.list = Arrays.asList(list);
            return res;
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    }
}
