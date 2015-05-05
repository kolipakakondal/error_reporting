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
package org.eclipse.epp.internal.logging.aeri.ui.model;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Optional.fromNullable;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.*;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epp.internal.logging.aeri.ui.Constants;
import org.eclipse.epp.internal.logging.aeri.ui.model.util.ModelSwitch;
import org.eclipse.epp.internal.logging.aeri.ui.utils.AnonymousId;
import org.eclipse.epp.internal.logging.aeri.ui.utils.EmfFieldExclusionStrategy;
import org.eclipse.epp.internal.logging.aeri.ui.utils.UuidTypeAdapter;
import org.osgi.framework.Bundle;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Reports {

    public static final class ClearMessagesVisitor extends ModelSwitch<Object> {
        @Override
        public Object caseStatus(Status status) {
            status.setMessage(Constants.HIDDEN);
            return Boolean.TRUE;
        }

        @Override
        public Object caseThrowable(Throwable throwable) {
            throwable.setMessage(Constants.HIDDEN);
            return Boolean.TRUE;
        }
    }

    public static final class AnonymizeStacktraceVisitor extends ModelSwitch<Object> {
        private List<String> whitelist;

        public AnonymizeStacktraceVisitor(List<String> whitelist) {
            this.whitelist = whitelist;
        }

        @Override
        public Object caseThrowable(Throwable throwable) {
            if (!isWhitelisted(throwable.getClassName(), whitelist)) {
                throwable.setClassName(Constants.HIDDEN);
            }
            return Boolean.TRUE;
        }

        @Override
        public Object caseStackTraceElement(StackTraceElement element) {
            if (!isWhitelisted(element.getClassName(), whitelist)) {
                element.setClassName(Constants.HIDDEN);
                element.setMethodName(Constants.HIDDEN);
                element.setFileName(Constants.HIDDEN);
                element.setLineNumber(-1);
            }
            return Boolean.TRUE;
        }
    }

    public static final class ThrowableFingerprintComputer extends ModelSwitch<Object> {

        private StringBuilder content = new StringBuilder();
        private List<String> whitelist;
        private int maxframes;

        public ThrowableFingerprintComputer(List<String> whitelist, int maxframes) {
            this.whitelist = whitelist;
            this.maxframes = maxframes;
        }

        @Override
        public Object caseStackTraceElement(StackTraceElement element) {
            if (maxframes < 0) {
                return Boolean.TRUE;
            }
            maxframes--;
            if (isWhitelisted(element.getClassName(), whitelist)) {
                content.append(element.getClassName()).append(element.getMethodName());
            }
            return Boolean.TRUE;
        }

        @Override
        public Object caseThrowable(Throwable throwable) {
            if (isWhitelisted(throwable.getClassName(), whitelist)) {
                content.append(throwable.getClassName());
            }
            return Boolean.TRUE;
        }

        public String hash() {
            return Hashing.murmur3_32().hashUnencodedChars(content.toString()).toString();
        }
    }

    public static final class CollectStackTraceElementPackagesVisitor extends ModelSwitch<Object> {
        public TreeSet<String> packages = Sets.newTreeSet();

        @Override
        public Object caseStackTraceElement(StackTraceElement element) {
            String pkg = replace(substringBeforeLast(element.getClassName(), "."), ".internal.", ".");
            packages.add(pkg);

            return Boolean.TRUE;
        }
    }

    public static class PrettyPrintVisitor extends ModelSwitch<Object> {
        private static final int RIGHT_PADDING = 20;
        private StringBuilder reportStringBuilder = new StringBuilder();
        private StringBuilder statusStringBuilder = new StringBuilder();
        private StringBuilder bundlesStringBuilder = new StringBuilder();

        public PrettyPrintVisitor() {
            bundlesStringBuilder = new StringBuilder();
            appendHeadline("BUNDLES", bundlesStringBuilder);
        }

        private void appendAttributes(EObject object, StringBuilder builder) {
            for (EAttribute attribute : object.eClass().getEAllAttributes()) {
                String format = "%-" + RIGHT_PADDING + "s%s\n";
                Object value = firstNonNull(object.eGet(attribute), "");
                String line = String.format(format, attribute.getName(), value);
                builder.append(line);
            }
            builder.append("\n");
        }

        private void appendHeadline(String headline, StringBuilder builder) {
            if (builder.length() != 0) {
                builder.append("\n");
            }
            String line = headline.replaceAll(".", "-") + "\n";
            builder.append(line);
            builder.append(headline + "\n");
            builder.append(line);
        }

        @Override
        public Object caseErrorReport(ErrorReport report) {
            appendHeadline("REPORT", reportStringBuilder);
            appendAttributes(report, reportStringBuilder);
            return Boolean.TRUE;
        }

        @Override
        public Object caseStatus(Status status) {
            appendHeadline("STATUS", statusStringBuilder);
            appendAttributes(status, statusStringBuilder);
            Throwable exception = status.getException();
            if (exception != null) {
                statusStringBuilder.append("Exception:");
                append(exception, statusStringBuilder);
            }
            return Boolean.TRUE;
        }

        private void append(Throwable throwable, StringBuilder builder) {
            builder.append(String.format("%s: %s\n", throwable.getClassName(), throwable.getMessage()));
            for (StackTraceElement element : throwable.getStackTrace()) {
                builder.append(String.format("\t at %s.%s(%s:%s)\n", element.getClassName(), element.getMethodName(),
                        element.getFileName(), element.getLineNumber()));
            }
            Throwable cause = throwable.getCause();
            if (cause != null) {
                statusStringBuilder.append("Caused by: ");
                append(cause, builder);
            }
        }

        @Override
        public Object caseBundle(org.eclipse.epp.internal.logging.aeri.ui.model.Bundle bundle) {
            appendAttributes(bundle, bundlesStringBuilder);
            return Boolean.TRUE;
        }

        public String print() {
            return new StringBuilder().append(statusStringBuilder).append("\n").append(reportStringBuilder)
                    .append(bundlesStringBuilder).toString();
        }

    }

    private static class MultiStatusFilter {

        private static void filter(Status status) {
            HashSet<Throwable> throwables = new HashSet<Throwable>();
            filter(status, throwables);
        }

        private static void filter(Status status, Set<Throwable> throwables) {
            EList<Status> children = status.getChildren();
            int removedCount = 0;
            for (int i = children.size() - 1; i >= 0; i--) {
                Status childStatus = children.get(i);
                if (filterChild(childStatus, throwables)) {
                    children.remove(i);
                    removedCount++;
                } else {
                    filter(childStatus, throwables);
                }
            }
            if (removedCount > 0) {
                status.setMessage(String.format("%s [%d child-status duplicates removed by Error Reporting]",
                        status.getMessage(), removedCount));
            }
        }

        private static boolean filterChild(Status status, Set<Throwable> throwables) {
            Throwable throwable = status.getException();
            if (throwable.getStackTrace().isEmpty()) {
                return true;
            }
            for (Throwable t : throwables) {
                if (stackTraceMatches(throwable, t)) {
                    return true;
                }
            }
            throwables.add(throwable);
            return false;
        }

        private static boolean stackTraceMatches(Throwable throwable, Throwable t) {
            EList<StackTraceElement> stackTrace = throwable.getStackTrace();
            EList<StackTraceElement> stackTrace2 = t.getStackTrace();
            if (stackTrace.size() != stackTrace2.size()) {
                return false;
            }
            for (int i = 0; i < stackTrace.size(); i++) {
                StackTraceElement ste = stackTrace.get(i);
                StackTraceElement ste2 = stackTrace2.get(i);
                if (!classNameAndMethodNameEqual(ste, ste2)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean classNameAndMethodNameEqual(StackTraceElement ste, StackTraceElement ste2) {
            return ste.getClassName().equals(ste2.getClassName()) && ste.getMethodName().equals(ste2.getMethodName());
        }

    }

    private static ModelFactory factory = ModelFactory.eINSTANCE;

    static boolean isWhitelisted(String className, List<String> whitelist) {
        for (String whiteListedPrefix : whitelist) {
            if (className.startsWith(whiteListedPrefix)) {
                return true;
            }
        }
        return false;
    }

    public static ErrorReport copy(ErrorReport org) {
        return EcoreUtil.copy(org);
    }

    public static String toJson(ErrorReport report, Settings settings, boolean pretty) {
        // work on a copy:
        report = copy(report);

        report.setName(settings.getName());
        report.setEmail(settings.getEmail());
        if (settings.isAnonymizeStrackTraceElements()) {
            anonymizeStackTrace(report, settings);
        }
        if (settings.isAnonymizeMessages()) {
            clearMessages(report);
        }

        Gson gson = createGson(pretty);
        String json = gson.toJson(report);
        return json;
    }

    private static Gson createGson(boolean pretty) {
        GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        builder.registerTypeAdapter(UUID.class, new UuidTypeAdapter());
        builder.addSerializationExclusionStrategy(new EmfFieldExclusionStrategy());
        if (pretty) {
            builder.setPrettyPrinting();
        }
        Gson gson = builder.create();
        return gson;
    }

    public static ErrorReport newErrorReport(IStatus event, Settings settings) {
        ErrorReport mReport = factory.createErrorReport();
        mReport.setAnonymousId(AnonymousId.getId());
        mReport.setName(settings.getName());
        mReport.setEmail(settings.getEmail());

        mReport.setJavaRuntimeVersion(SystemUtils.JAVA_RUNTIME_VERSION);
        mReport.setEclipseBuildId(getEclipseBuildId().or("-"));
        mReport.setEclipseProduct(System.getProperty("eclipse.product", "-"));
        mReport.setOsgiArch(System.getProperty("osgi.arch", "-"));
        mReport.setOsgiWs(System.getProperty("osgi.ws", "-"));
        mReport.setOsgiOs(System.getProperty(org.osgi.framework.Constants.FRAMEWORK_OS_NAME, "-"));
        mReport.setOsgiOsVersion(System.getProperty(org.osgi.framework.Constants.FRAMEWORK_OS_VERSION, "-"));
        mReport.setStatus(newStatus(event, settings));

        return mReport;
    }

    public static Optional<String> getEclipseBuildId() {
        String res = System.getProperty("eclipse.buildId");
        return fromNullable(res);
    }

    public static void guessInvolvedPlugins(ErrorReport report) {
        CollectStackTraceElementPackagesVisitor v = new CollectStackTraceElementPackagesVisitor();
        visit(report, v);
        Set<String> uniqueBundleNames = Sets.newHashSet();
        for (String packageName : v.packages) {
            while (packageName.contains(".")) {
                Bundle guessedBundleForPackageName = Platform.getBundle(packageName);
                packageName = StringUtils.substringBeforeLast(packageName, ".");
                if (guessedBundleForPackageName != null) {
                    if (uniqueBundleNames.add(guessedBundleForPackageName.getSymbolicName())) {
                        org.eclipse.epp.internal.logging.aeri.ui.model.Bundle bundle = factory.createBundle();
                        bundle.setName(guessedBundleForPackageName.getSymbolicName());
                        bundle.setVersion(guessedBundleForPackageName.getVersion().toString());
                        report.getPresentBundles().add(bundle);
                    }
                    continue;
                }
            }
        }
    }

    @VisibleForTesting
    public static Status newStatus(IStatus status, Settings settings) {
        Status mStatus = factory.createStatus();
        mStatus.setMessage(removeSourceFileContents(status.getMessage()));
        mStatus.setSeverity(status.getSeverity());
        mStatus.setCode(status.getCode());
        mStatus.setPluginId(status.getPlugin());

        Bundle bundle = Platform.getBundle(status.getPlugin());
        if (bundle != null) {
            mStatus.setPluginVersion(bundle.getVersion().toString());
        }

        List<Status> mChildren = mStatus.getChildren();
        java.lang.Throwable exception = status.getException();
        // CoreException handling
        for (java.lang.Throwable cur = exception; cur != null; cur = cur.getCause()) {
            if (cur instanceof CoreException) {
                CoreException coreException = (CoreException) cur;
                IStatus coreExceptionStatus = coreException.getStatus();
                Status mCoreExceptionStatus = newStatus(coreExceptionStatus, settings);
                String detachedMessage = format(
                        "{0} [detached from CoreException of Status ''{1}'' by Error Reporting]",
                        mCoreExceptionStatus.getMessage(), mStatus.getMessage());
                mCoreExceptionStatus.setMessage(detachedMessage);
                mChildren.add(mCoreExceptionStatus);
                // further CoreExceptions are handled in the detached Status
                break;
            }
        }
        // Multistatus handling
        for (IStatus child : status.getChildren()) {
            mChildren.add(newStatus(child, settings));
        }
        // some stacktraces from ui.monitoring should be filtered
        boolean needFiltering = "org.eclipse.ui.monitoring".equals(status.getPlugin())
                && (status.getCode() == 0 || status.getCode() == 1);
        if (needFiltering) {
            MultiStatusFilter.filter(mStatus);
        }

        if (exception != null) {
            Throwable mException = newThrowable(exception);
            mStatus.setException(mException);
        }

        mStatus.setFingerprint(computeFingerprintFor(mStatus, settings));

        return mStatus;
    }

    public static String computeFingerprintFor(Status status, Settings settings) {
        ThrowableFingerprintComputer fingerprintComputer = new ThrowableFingerprintComputer(
                settings.getWhitelistedPackages(), 1024);
        visit(status, fingerprintComputer);
        return fingerprintComputer.hash();
    }

    private static String removeSourceFileContents(String message) {
        if (message.contains(Constants.SOURCE_BEGIN_MESSAGE)) {
            return Constants.SOURCE_FILE_REMOVED;
        } else {
            return message;
        }
    }

    public static Throwable newThrowable(java.lang.Throwable throwable) {
        Throwable mThrowable = factory.createThrowable();
        mThrowable.setMessage(throwable.getMessage());
        mThrowable.setClassName(throwable.getClass().getName());
        List<StackTraceElement> mStackTrace = mThrowable.getStackTrace();
        for (java.lang.StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            StackTraceElement mStackTraceElement = factory.createStackTraceElement();
            mStackTraceElement.setFileName(stackTraceElement.getFileName());
            mStackTraceElement.setClassName(ensureNotBlank(stackTraceElement.getClassName(), throwable));
            mStackTraceElement.setMethodName(ensureNotBlank(stackTraceElement.getMethodName(), throwable));
            mStackTraceElement.setLineNumber(stackTraceElement.getLineNumber());
            mStackTrace.add(mStackTraceElement);
        }
        java.lang.Throwable cause = throwable.getCause();
        if (cause != null) {
            if (cause == throwable) {
                log(WARN_CYCLIC_EXCEPTION, cause.toString());
                return mThrowable;
            }
            mThrowable.setCause(newThrowable(cause));
        }
        return mThrowable;
    }

    private static String ensureNotBlank(String str, java.lang.Throwable throwable) {
        if (isBlank(str)) {
            // Note: we cannot call throwable.toString()) nor
            // reflectionToString() --> NPE:
            log(WARN_STACKTRACE_WITH_NULL);
            return Constants.MISSING;
        }
        return str;
    }

    public static void clearMessages(ErrorReport report) {
        visit(report, new ClearMessagesVisitor());
    }

    public static void anonymizeStackTrace(ErrorReport report, final Settings settings) {
        visit(report, new AnonymizeStacktraceVisitor(settings.getWhitelistedPackages()));
    }

    public static String prettyPrint(ErrorReport report, Settings settings) {
        if (settings.isAnonymizeStrackTraceElements()) {
            anonymizeStackTrace(report, settings);
        }
        if (settings.isAnonymizeMessages()) {
            clearMessages(report);
        }
        PrettyPrintVisitor prettyPrintVisitor = new PrettyPrintVisitor();
        visit(report, prettyPrintVisitor);
        return prettyPrintVisitor.print();
    }

    public static String getFingerprint(ErrorReport report) {
        return report.getStatus().getFingerprint();
    }

    public static <T, K extends EObject> void visit(K object, ModelSwitch<T> s) {
        s.doSwitch(object);
        TreeIterator<EObject> allContents = EcoreUtil.getAllContents(object, true);
        for (TreeIterator<EObject> iterator = allContents; iterator.hasNext();) {
            EObject modelElement = iterator.next();
            s.doSwitch(modelElement);
        }
    }

    public static String exactIdentityHash(ErrorReport report) {
        final Hasher hasher = Hashing.murmur3_128().newHasher();
        ModelSwitch<Hasher> s = new ModelSwitch<Hasher>() {
            @Override
            public Hasher caseErrorReport(ErrorReport object) {
                hasher.putString(stripToEmpty(object.getEclipseProduct()), UTF_8);
                hasher.putString(stripToEmpty(object.getEclipseBuildId()), UTF_8);
                hasher.putString(stripToEmpty(object.getJavaRuntimeVersion()), UTF_8);
                hasher.putString(stripToEmpty(object.getOsgiOs()), UTF_8);
                hasher.putString(stripToEmpty(object.getOsgiOsVersion()), UTF_8);
                hasher.putString(stripToEmpty(object.getOsgiArch()), UTF_8);
                hasher.putString(stripToEmpty(object.getOsgiWs()), UTF_8);
                return hasher;
            };

            @Override
            public Hasher caseStatus(Status object) {
                hasher.putString(stripToEmpty(object.getPluginId()), UTF_8);
                hasher.putString(stripToEmpty(object.getPluginVersion()), UTF_8);
                hasher.putString(stripToEmpty(object.getMessage()), UTF_8);
                hasher.putInt(object.getSeverity());
                hasher.putInt(object.getCode());
                return hasher;
            }

            @Override
            public Hasher caseBundle(org.eclipse.epp.internal.logging.aeri.ui.model.Bundle object) {
                hasher.putString(stripToEmpty(object.getName()), UTF_8);
                hasher.putString(stripToEmpty(object.getVersion()), UTF_8);
                return hasher;
            }

            @Override
            public Hasher caseStackTraceElement(StackTraceElement object) {
                hasher.putString(stripToEmpty(object.getClassName()), UTF_8);
                hasher.putString(stripToEmpty(object.getMethodName()), UTF_8);
                hasher.putInt(object.getLineNumber());
                return hasher;
            }

            @Override
            public Hasher caseThrowable(Throwable object) {
                hasher.putString(stripToEmpty(object.getClassName()), UTF_8);
                hasher.putString(stripToEmpty(object.getMessage()), UTF_8);
                return hasher;
            }
        };

        visit(report, s);
        String hash = hasher.hash().toString();
        return hash;
    }

    public static String traceIdentityHash(ErrorReport report) {
        final Hasher hasher = Hashing.murmur3_128().newHasher();
        visit(report, new ModelSwitch<Hasher>() {

            @Override
            public Hasher caseStackTraceElement(StackTraceElement element) {
                hasher.putString(element.getClassName(), UTF_8);
                hasher.putString(element.getMethodName(), UTF_8);
                hasher.putInt(element.getLineNumber());
                return hasher;
            }
        });
        String hash = hasher.hash().toString();
        return hash;
    }
}
