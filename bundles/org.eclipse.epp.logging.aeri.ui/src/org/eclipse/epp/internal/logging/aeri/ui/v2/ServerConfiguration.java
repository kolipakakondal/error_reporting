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
package org.eclipse.epp.internal.logging.aeri.ui.v2;

import static java.util.concurrent.TimeUnit.*;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.LogMessages.WARN_INVALID_PATTERN;
import static org.eclipse.epp.internal.logging.aeri.ui.l10n.Logs.log;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.epp.internal.logging.aeri.ui.utils.WildcardRegexConverter;

import com.google.common.collect.Lists;

public class ServerConfiguration {

    public static class IgnorePattern {

        public static enum ExceptionStacktracePosition {
            TOP, BOTTOM, ANY
        }

        public static enum StatusChainPosition {
            FIRST, ANY
        }

        public static IgnorePattern fromString(String s) {
            int firstSep = s.indexOf(':');
            if (firstSep == -1) {
                return null;
            }
            String pluginPattern = s.substring(0, firstSep);
            StatusChainPosition statusChainPosition = StatusChainPosition.ANY;
            if (pluginPattern.startsWith("^")) {
                pluginPattern = pluginPattern.substring(1);
                statusChainPosition = StatusChainPosition.FIRST;
            }
            String tail = s.substring(firstSep + 1);
            int secondSep = tail.indexOf(':');
            if (secondSep == -1) {
                return null;
            }
            String exceptionPattern = tail.substring(0, secondSep);
            ExceptionStacktracePosition exceptionStacktracePosition = ExceptionStacktracePosition.ANY;
            if (exceptionPattern.startsWith("^")) {
                exceptionPattern = exceptionPattern.substring(1);
                exceptionStacktracePosition = ExceptionStacktracePosition.TOP;
            } else if (exceptionPattern.startsWith("$")) {
                exceptionPattern = exceptionPattern.substring(1);
                exceptionStacktracePosition = ExceptionStacktracePosition.BOTTOM;
            }
            String messagePattern = tail.substring(secondSep + 1);
            IgnorePattern ip = new IgnorePattern();
            ip.pluginPattern = WildcardRegexConverter.convert(defaultIfEmpty(pluginPattern, "*"));
            ip.statusChainPosition = statusChainPosition;
            ip.exceptionPattern = WildcardRegexConverter.convert(defaultIfEmpty(exceptionPattern, "*"));
            ip.exceptionPosition = exceptionStacktracePosition;
            ip.messagePattern = WildcardRegexConverter.convert(defaultIfEmpty(messagePattern, "*"));
            return ip;
        }

        private Pattern pluginPattern;
        private Pattern exceptionPattern;
        private Pattern messagePattern;
        private ExceptionStacktracePosition exceptionPosition;
        private StatusChainPosition statusChainPosition;

        public Pattern getPluginPattern() {
            return pluginPattern;
        }

        public Pattern getExceptionPattern() {
            return exceptionPattern;
        }

        public Pattern getMessagePattern() {
            return messagePattern;
        }

        public StatusChainPosition getStatusChainPosition() {
            return statusChainPosition;
        }

        public ExceptionStacktracePosition getExceptionPosition() {
            return exceptionPosition;
        }

        public boolean matches(IStatus status) {
            if (matches(status.getPlugin(), status.getException(), status.getMessage())) {
                return true;
            } else if (statusChainPosition == StatusChainPosition.ANY) {
                for (IStatus child : status.getChildren()) {
                    if (matches(child)) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean matches(String pluginId, Throwable exception, String message) {
            boolean pluginMatches = pluginPattern.matcher(pluginId).matches();
            boolean messageMatches = messagePattern.matcher(message).matches();
            if (pluginMatches && messageMatches) {
                switch (exceptionPosition) {
                case ANY: {
                    Throwable current = exception;
                    while (current != null) {
                        if (exceptionPattern.matcher(current.getClass().getName()).matches()) {
                            return true;
                        }
                        current = current.getCause();
                    }
                    break;
                }
                case BOTTOM: {
                    Throwable current = exception;
                    while (current.getCause() != null) {
                        current = current.getCause();
                    }
                    if (exceptionPattern.matcher(current.getClass().getName()).matches()) {
                        return true;
                    }
                    break;
                }
                case TOP: {
                    if (exceptionPattern.matcher(exception.getClass().getName()).matches()) {
                        return true;
                    }
                    break;
                }
                default:
                    break;
                }
            }
            return false;
        }

    }

    private String version;
    private String title;
    private String description;
    private long timestamp; // when did we discover the service the last time?
    private long ttl; // (in minutes) how long is this service configuration valid?

    private String helpUrl;
    private String feedbackUrl;
    private String aboutUrl;
    private String submitUrl;
    private int submitSizeLimit;
    private String problemsUrl;

    // in minutes
    private long problemsTtl;
    private String queryUrl;

    // max time in seconds until a connection to the server has to be established.
    private int connectTimeout = (int) TimeUnit.SECONDS.toMillis(3);;
    // max time in seconds between two packets sent back to the client.
    private int socketTimeout = (int) TimeUnit.SECONDS.toMillis(10);;

    private List<String> acceptedProducts;
    private transient List<Pattern> acceptedProductsPatterns;
    private List<String> acceptedPlugins;
    private transient List<Pattern> acceptedPluginsPatterns;
    private List<String> acceptedPackages;
    private transient List<Pattern> acceptedPackagesPatterns;

    /**
     * Whether or not stacktraces with other than the accepted packages will be send (=true) or discarded (=false)
     */
    private boolean acceptOtherPackages;
    private boolean acceptUiFreezes;

    /**
     * Patterns to ignore errors</br>
     * format: <code>pluginId:exception:message</code>. use '*' for wildcard or leave empty (e.g. '
     * <code>::<code>' is the minimal pattern to ignore everything )
     * <p>
     * prefix for pluginId: </br>
     * '^' : check only the first status </br>
     * none : check every status and it's children
     * <p>
     * prefix for exception: </br>
     * '^' : only top of stacktrace </br>
     * '$' : only last of stacktrace </br>
     * none : anywhere in stacktrace
     * <p>
     * example: <code>org.eclipse.epp.logging.aeri.*:^*StandInException:*</code> </br>
     * to ignore all exceptions from plugins starting with <code>org.eclipse.epp.logging.aeri.</code> on top of a stacktrace, ending with
     * <code>StandInException</code> with any message. The status may be included in other status-objects.
     */
    private List<String> ignoredPluginMessages;
    private transient List<IgnorePattern> ignoredPatterns;
    private long problemsZipLastDownloadTimestamp;

    public int getConnectTimeoutMs() {
        return (int) MILLISECONDS.convert(connectTimeout, SECONDS);
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeoutMs() {
        return (int) MILLISECONDS.convert(socketTimeout, SECONDS);
    }

    public long getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the time-to-live of these settings in minutes, i.e., informs the client when he should refresh the settings.
     */
    public long getTtl() {
        return ttl;
    }

    public long getTtlMs() {
        return MILLISECONDS.convert(getTtl(), MINUTES);
    }

    public void setTtl(long ttlInMinutes) {
        this.ttl = ttlInMinutes;
    }

    public String getHelpUrl() {
        return helpUrl;
    }

    public void setHelpUrl(String helpUrl) {
        this.helpUrl = helpUrl;
    }

    public String getFeedbackUrl() {
        return feedbackUrl;
    }

    public void setFeedbackUrl(String feedbackUrl) {
        this.feedbackUrl = feedbackUrl;
    }

    public String getAboutUrl() {
        return aboutUrl;
    }

    public void setAboutUrl(String aboutUrl) {
        this.aboutUrl = aboutUrl;
    }

    public String getSubmitUrl() {
        return submitUrl;
    }

    public void setSubmitUrl(String submitUrl) {
        this.submitUrl = submitUrl;
    }

    public int getSubmitSizeLimit() {
        return submitSizeLimit;
    }

    public void setSubmitSizeLimit(int submitSizeLimit) {
        this.submitSizeLimit = submitSizeLimit;
    }

    public String getProblemsUrl() {
        return problemsUrl;
    }

    public void setProblemsUrl(String problemsUrl) {
        this.problemsUrl = problemsUrl;
    }

    /**
     * Returns the time-to-live of the error reports database in minutes, i.e., informs the client when he should refresh the database.
     */
    public long getProblemsTtl() {
        return problemsTtl;
    }

    public long getProblemsTtlMs() {
        return MILLISECONDS.convert(getProblemsTtl(), MINUTES);
    }

    public void setProblemsTtl(long problemsTtlInMinutes) {
        this.problemsTtl = problemsTtlInMinutes;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public List<String> getAcceptedProducts() {
        return acceptedProducts;
    }

    public void setAcceptedProducts(List<String> acceptedProducts) {
        this.acceptedProducts = acceptedProducts;
        this.acceptedProductsPatterns = null;
    }

    public List<String> getAcceptedPlugins() {
        return acceptedPlugins;
    }

    public void setAcceptedPlugins(List<String> acceptedPlugins) {
        this.acceptedPlugins = acceptedPlugins;
        this.acceptedPluginsPatterns = null;
    }

    public List<Pattern> getAcceptedProductsPatterns() {
        if (acceptedProductsPatterns == null) {
            acceptedProductsPatterns = WildcardRegexConverter.convert(acceptedProducts);
        }
        return acceptedProductsPatterns;
    }

    public List<Pattern> getAcceptedPluginsPatterns() {
        if (acceptedPluginsPatterns == null) {
            acceptedPluginsPatterns = WildcardRegexConverter.convert(acceptedPlugins);
        }
        return acceptedPluginsPatterns;
    }

    public List<Pattern> getAcceptedPackagesPatterns() {
        if (acceptedPackagesPatterns == null) {
            acceptedPackagesPatterns = WildcardRegexConverter.convert(acceptedPackages);
        }
        return acceptedPackagesPatterns;
    }

    public List<String> getAcceptedPackages() {
        return acceptedPackages;
    }

    public void setAcceptedPackages(List<String> acceptedPackages) {
        this.acceptedPackages = acceptedPackages;
        this.acceptedPackagesPatterns = null;
    }

    public boolean isAcceptOtherPackages() {
        return acceptOtherPackages;
    }

    public void setAcceptOtherPackages(boolean acceptOtherPackages) {
        this.acceptOtherPackages = acceptOtherPackages;
    }

    public List<String> getIgnoredPluginMessages() {
        return ignoredPluginMessages;
    }

    public void setIgnoredPluginMessages(List<String> ignoredPluginMessages) {
        this.ignoredPluginMessages = ignoredPluginMessages;
    }

    public void setIgnoredExceptions(List<String> ignoredPatternsStrings) {
        this.ignoredPluginMessages = ignoredPatternsStrings;
    }

    public List<IgnorePattern> getIgnoredPluginMessagesPatterns() {
        if (ignoredPatterns == null) {
            ignoredPatterns = Lists.newArrayList();
            for (String s : getIgnoredPluginMessages()) {
                IgnorePattern pattern = IgnorePattern.fromString(s);
                if (pattern != null) {
                    ignoredPatterns.add(pattern);
                } else {
                    log(WARN_INVALID_PATTERN, s);
                }
            }
        }
        return ignoredPatterns;
    }

    public boolean isAcceptUiFreezes() {
        return acceptUiFreezes;
    }

    public void setAcceptUiFreezes(boolean acceptUiFreezes) {
        this.acceptUiFreezes = acceptUiFreezes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getProblemsZipLastDownloadTimestamp() {
        return problemsZipLastDownloadTimestamp;
    }

    public void setProblemsZipLastDownloadTimestamp(long problemsZipLastDownloadTimestamp) {
        this.problemsZipLastDownloadTimestamp = problemsZipLastDownloadTimestamp;
    }
}
