/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Andreas Sewe - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import static com.google.common.base.Optional.*;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.ExportedPackage;
import org.osgi.service.packageadmin.PackageAdmin;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

@SuppressWarnings("deprecation")
public class ErrorAnalyser {

    private final PackageAdmin packageAdmin;

    public ErrorAnalyser() {
        packageAdmin = getService(PackageAdmin.class).orNull();
    }

    @VisibleForTesting
    protected ErrorAnalyser(PackageAdmin packageAdmin) {
        this.packageAdmin = packageAdmin;
    }

    protected <T> Optional<T> getService(Class<T> serviceClass) {
        if (LogMessages.BUNDLE == null) {
            return absent();
        }
        BundleContext context = LogMessages.BUNDLE.getBundleContext();
        if (context == null) {
            return absent();
        }

        ServiceReference<T> reference = context.getServiceReference(serviceClass);
        if (reference == null) {
            return absent();
        }
        return fromNullable(context.getService(reference));
    }

    public Optional<String> computeComment(final List<Bundle> presentBundles, Throwable throwable) {
        if (packageAdmin == null) {
            return absent();
        }

        List<String> problematicPackages = extractProblematicPackage(throwable);
        if (problematicPackages.isEmpty()) {
            return absent();
        }

        Set<String> presentBundlesSymbolicNames = Sets.newHashSet();
        for (Bundle presentBundle : presentBundles) {
            presentBundlesSymbolicNames.add(presentBundle.getName());
        }

        StringBuilder comment = new StringBuilder();
        for (String problematicPackage : problematicPackages) {
            comment.append("The problematic package '").append(problematicPackage).append("' may originate in the following bundles:\n");
            ExportedPackage[] exportedPackages = packageAdmin.getExportedPackages(problematicPackage);
            if (ArrayUtils.isEmpty(exportedPackages)) {
                continue;
            }
            Multimap<org.osgi.framework.Bundle, org.osgi.framework.Bundle> exportersToImporters = HashMultimap.create();
            for (ExportedPackage exportedPackage : exportedPackages) {
                org.osgi.framework.Bundle exportingBundle = exportedPackage.getExportingBundle();
                if (!isPresent(exportingBundle)) {
                    continue;
                }
                for (org.osgi.framework.Bundle importingBundle : exportedPackage.getImportingBundles()) {
                    if (!isPresent(importingBundle)) {
                        continue;
                    }
                    if (presentBundlesSymbolicNames.contains(importingBundle.getSymbolicName())) {
                        exportersToImporters.put(exportingBundle, importingBundle);
                    }
                }
            }
            if (exportersToImporters.isEmpty()) {
                continue;
            }

            for (Entry<org.osgi.framework.Bundle, Collection<org.osgi.framework.Bundle>> entry : exportersToImporters.asMap().entrySet()) {
                org.osgi.framework.Bundle exporter = entry.getKey();
                Collection<org.osgi.framework.Bundle> importers = entry.getValue();
                comment.append("  ").append(exporter.getSymbolicName()).append(' ').append(exporter.getVersion())
                        .append(", from which the following bundles present on the stack trace import it:\n");
                for (org.osgi.framework.Bundle importer : importers) {
                    comment.append("    ").append(importer.getSymbolicName()).append(' ').append(importer.getVersion()).append('\n');
                }
            }
        }
        return Optional.of(comment.toString());
    }

    private boolean isPresent(org.osgi.framework.Bundle bundle) {
        switch (bundle.getState()) {
        case org.osgi.framework.Bundle.RESOLVED:
        case org.osgi.framework.Bundle.STARTING:
        case org.osgi.framework.Bundle.ACTIVE:
            return true;
        default:
            return false;
        }
    }

    @VisibleForTesting
    static List<String> extractProblematicPackage(Throwable throwable) {
        String message = throwable.getMessage();
        if (StringUtils.equals(Constants.HIDDEN, message)) {
            return newArrayList();
        }
        if (NoClassDefFoundError.class.getName().equals(throwable.getClassName())
                || LinkageError.class.getName().equals(throwable.getClassName())) {
            return handleNoClassDefFoundErrorAndLinkageError(message);
        } else if (ClassNotFoundException.class.getName().equals(throwable.getClassName())) {
            return handleClassNotFoundException(message);
        } else if (NoSuchMethodError.class.getName().equals(throwable.getClassName())) {
            return handleMethodNotFoundException(message);
        } else if (VerifyError.class.getName().equals(throwable.getClassName())) {
            return handleVerifyError(message);
        } else {
            return newArrayList();
        }
    }

    private static List<String> handleVerifyError(String message) {
        List<String> packages = newArrayList();
        // extract foo/bar/baz from all patterns 'foo/bar/baz'
        Pattern pattern = Pattern.compile("'([\\S]*)'");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            // the extracted pattern package/package/Class
            String clazz = matcher.group(1);
            int lastIndexOfSlash = clazz.lastIndexOf('/');
            if (lastIndexOfSlash == -1) {
                continue;
            }
            String packageName = clazz.substring(0, lastIndexOfSlash);
            packageName = packageName.replaceAll("/", ".");
            if (!packages.contains(packageName)) {
                packages.add(packageName);
            }
        }
        return packages;
    }

    private static List<String> handleNoClassDefFoundErrorAndLinkageError(String message) {
        int lastIndexOfSlash = message.lastIndexOf('/');
        if (lastIndexOfSlash < 0) {
            return newArrayList();
        } else {
            String packageName = message.substring(0, lastIndexOfSlash).replace('/', '.');
            return newArrayList(packageName);
        }
    }

    private static List<String> handleClassNotFoundException(String message) {
        int firstIndexOfSpace = message.indexOf(" ");
        if (firstIndexOfSpace >= 0) {
            message = message.substring(0, firstIndexOfSpace);
        }
        int lastIndexOfDot = message.lastIndexOf('.');
        if (lastIndexOfDot < 0) {
            return newArrayList();
        } else {
            String packageName = message.substring(0, lastIndexOfDot);
            return newArrayList(packageName);
        }
    }

    private static List<String> handleMethodNotFoundException(String message) {
        // java.lang.NoSuchMethodError: org.eclipse.recommenders.utils.Checks.anyIsNull([Ljava/lang/Object;)Z
        // java.lang.NoSuchMethodError: HIDDEN
        String className = substringBeforeLast(message, ".");
        // we assume that there is a package...
        String packageName = substringBeforeLast(className, ".");
        return newArrayList(packageName);
    }
}
