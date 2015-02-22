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

import static com.google.common.base.Throwables.propagate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.google.common.collect.Lists;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
    public static final Type T_LIST_STRING = new TypeToken<List<String>>() {
    }.getType();

    private static Gson gson;

    public static synchronized Gson getInstance() {
        if (gson == null) {
            final GsonBuilder builder = new GsonBuilder();

            builder.registerTypeAdapter(UUID.class, new UuidTypeAdapter());

            builder.enableComplexMapKeySerialization();
            builder.setPrettyPrinting();
            gson = builder.create();
        }
        return gson;
    }

    public static <T> T deserialize(final CharSequence json, final Type classOfT) {
        return deserialize(json.toString(), classOfT);
    }

    public static <T> T deserialize(final String json, final Type classOfT) {
        try {
            return getInstance().fromJson(json, classOfT);
        } catch (final Exception e) {
            throw propagate(e);
        }
    }

    public static <T> T deserialize(final InputStream jsonStream, final Type classOfT) {
        try (Reader reader = new InputStreamReader(jsonStream, "UTF-8")) {
            return getInstance().fromJson(reader, classOfT);
        } catch (final Exception e) {
            throw propagate(e);
        }
    }

    public static <T> T deserialize(final File jsonFile, final Type classOfT) {
        try (InputStream in = new BufferedInputStream(new FileInputStream(jsonFile))) {
            return deserialize(in, classOfT);
        } catch (final Exception e) {
            throw propagate(e);
        }
    }

    public static String serialize(final Object obj) {
        final StringBuilder sb = new StringBuilder();
        serialize(obj, sb);
        return sb.toString();
    }

    public static void serialize(final Object obj, final Appendable writer) {
        try {
            getInstance().toJson(obj, writer);
        } catch (final Exception e) {
            throw propagate(e);
        }
    }

    public static void serialize(final Object obj, final File jsonFile) {
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(jsonFile))) {
            serialize(obj, out);
        } catch (final Exception e) {
            throw propagate(e);
        }
    }

    public static void serialize(final Object obj, final OutputStream out) {
        try (Writer writer = new OutputStreamWriter(out, "UTF-8")) {
            getInstance().toJson(obj, writer);
        } catch (final Exception e) {
            throw propagate(e);
        }
    }

    public static <T> List<T> deserializeZip(File zip, Class<T> classOfT) throws IOException {
        List<T> res = Lists.newLinkedList();
        ZipInputStream zis = null;
        try {
            InputSupplier<FileInputStream> fis = Files.newInputStreamSupplier(zip);
            zis = new ZipInputStream(fis.getInput());
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    res.add(GsonUtil.<T>deserialize(zis, classOfT));
                }
            }
        } finally {
            Closeables.close(zis, true);
        }
        return res;
    }
}
