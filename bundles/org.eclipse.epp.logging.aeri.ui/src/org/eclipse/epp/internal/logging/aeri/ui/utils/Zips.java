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

import static com.google.common.base.Optional.*;
import static com.google.common.io.ByteStreams.toByteArray;
import static com.google.common.io.Files.newInputStreamSupplier;
import static org.apache.commons.io.filefilter.DirectoryFileFilter.DIRECTORY;
import static org.apache.commons.io.filefilter.FileFileFilter.FILE;
import static org.apache.commons.lang3.StringUtils.removeStart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.io.Closeables;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.OutputSupplier;

public class Zips {

    public static ZipFile NULL() {
        try {
            File tmp = File.createTempFile("recommenders_null_zip", ".zip");
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tmp));
            zos.putNextEntry(new ZipEntry("/"));
            zos.closeEntry();
            zos.close();
            return new ZipFile(tmp);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This abstraction is used for testing.
     */
    @VisibleForTesting
    public interface IFileToJarFileConverter {
        Optional<JarFile> createJarFile(File file);
    }

    public static class DefaultJarFileConverter implements IFileToJarFileConverter {

        @Override
        public Optional<JarFile> createJarFile(File file) {
            try {
                return of(new JarFile(file));
            } catch (IOException e) {
                return absent();
            }
        }
    }

    public static void unzip(File zipFile, File destFolder) throws IOException {
        ZipInputStream zis = null;
        try {
            zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    final File file = new File(destFolder, entry.getName());
                    Files.createParentDirs(file);
                    Files.asByteSink(file, FileWriteMode.APPEND).writeFrom(zis);
                }
            }
        } finally {
            Closeables.close(zis, true);
        }
    }

    public static void zip(File directory, File out) throws IOException {
        ZipOutputStream zos = null;
        try {
            OutputSupplier<FileOutputStream> s = Files.newOutputStreamSupplier(out);
            zos = new ZipOutputStream(s.getOutput());
            for (File f : FileUtils.listFiles(directory, FILE, DIRECTORY)) {
                String path = removeStart(f.getPath(), directory.getAbsolutePath() + File.separator);
                path = path.replace(File.separatorChar, '/');
                ZipEntry e = new ZipEntry(path);
                zos.putNextEntry(e);
                Files.asByteSource(f).copyTo(zos);
                zos.closeEntry();
            }
        } finally {
            Closeables.close(zos, false);
        }
    }

    /**
     * Reads the give file into memory. This method may be used by zip based recommenders to speed up data access.
     */
    public static byte[] readFully(File file) throws IOException {
        return toByteArray(newInputStreamSupplier(file));
    }

    /**
     * Closes the give zip. Exceptions are printed to System.err.
     */
    public static boolean closeQuietly(ZipFile z) {
        if (z == null) {
            return true;
        }
        try {
            z.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
