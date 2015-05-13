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

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
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
public class LinkageErrorAnalyser {

    private final PackageAdmin packageAdmin;

    public LinkageErrorAnalyser() {
        packageAdmin = getService(PackageAdmin.class).orNull();
    }

    @VisibleForTesting
    protected LinkageErrorAnalyser(PackageAdmin packageAdmin) {
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

        String problematicPackage = extractProblematicPackage(throwable).orNull();
        if (problematicPackage == null) {
            return absent();
        }

        Set<String> presentBundlesSymbolicNames = Sets.newHashSet();
        for (Bundle presentBundle : presentBundles) {
            presentBundlesSymbolicNames.add(presentBundle.getName());
        }

        ExportedPackage[] exportedPackages = packageAdmin.getExportedPackages(problematicPackage);
        if (ArrayUtils.isEmpty(exportedPackages)) {
            return Optional.absent();
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
            return absent();
        }

        StringBuilder comment = new StringBuilder();
        comment.append("The problematic package '").append(problematicPackage).append("' may originate in the following bundles:\n");
        for (Entry<org.osgi.framework.Bundle, Collection<org.osgi.framework.Bundle>> entry : exportersToImporters.asMap().entrySet()) {
            org.osgi.framework.Bundle exporter = entry.getKey();
            Collection<org.osgi.framework.Bundle> importers = entry.getValue();
            comment.append("  ").append(exporter.getSymbolicName()).append(' ').append(exporter.getVersion())
                    .append(", from which the following bundles present on the stack trace import it:\n");
            for (org.osgi.framework.Bundle importer : importers) {
                comment.append("    ").append(importer.getSymbolicName()).append(' ').append(importer.getVersion()).append('\n');
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
    static Optional<String> extractProblematicPackage(Throwable throwable) {
        String message = throwable.getMessage();
        if (NoClassDefFoundError.class.getName().equals(throwable.getClassName())
                || LinkageError.class.getName().equals(throwable.getClassName())) {
            int lastIndexOfSlash = message.lastIndexOf('/');
            if (lastIndexOfSlash < 0) {
                return absent();
            } else {
                String packageName = message.substring(0, lastIndexOfSlash).replace('/', '.');
                return Optional.of(packageName);
            }
        } else if (ClassNotFoundException.class.getName().equals(throwable.getClassName())) {
            int firstIndexOfSpace = message.indexOf(" ");
            if (firstIndexOfSpace >= 0) {
                message = message.substring(0, firstIndexOfSpace);
            }
            int lastIndexOfDot = message.lastIndexOf('.');
            if (lastIndexOfDot < 0) {
                return absent();
            } else {
                String packageName = message.substring(0, lastIndexOfDot);
                return Optional.of(packageName);
            }
        } else {
            return absent();
        }
    }
}
