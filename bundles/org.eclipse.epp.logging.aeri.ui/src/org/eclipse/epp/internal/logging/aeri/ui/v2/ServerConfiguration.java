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

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.eclipse.epp.internal.logging.aeri.ui.utils.WildcardRegexConverter;

public class ServerConfiguration {

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

    private long problemsTtl; // in minutes
    private String queryUrl;

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
     * Some exception types may not help a lot.
     */
    private String[] ignoredExceptions;
    private String[] ignoredPluginMessages;

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
        return TimeUnit.MILLISECONDS.convert(getTtl(), TimeUnit.MINUTES);
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
        return TimeUnit.MILLISECONDS.convert(getProblemsTtl(), TimeUnit.MINUTES);
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
    }

    public List<String> getAcceptedPlugins() {
        return acceptedPlugins;
    }

    public void setAcceptedPlugins(List<String> acceptedPlugins) {
        this.acceptedPlugins = acceptedPlugins;
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
    }

    public boolean isAcceptOtherPackages() {
        return acceptOtherPackages;
    }

    public void setAcceptOtherPackages(boolean acceptOtherPackages) {
        this.acceptOtherPackages = acceptOtherPackages;
    }

    public String[] getIgnoredExceptions() {
        return ignoredExceptions;
    }

    public void setIgnoredExceptions(String[] ignoredExceptions) {
        this.ignoredExceptions = ignoredExceptions;
    }

    public boolean isAcceptUiFreezes() {
        return acceptUiFreezes;
    }

    public void setAcceptUiFreezes(boolean acceptUiFreezes) {
        this.acceptUiFreezes = acceptUiFreezes;
    }

    public String[] getIgnoredPluginMessages() {
        return ignoredPluginMessages;
    }

    public void setIgnoredPluginMessages(String[] ignoredPluginMessages) {
        this.ignoredPluginMessages = ignoredPluginMessages;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
