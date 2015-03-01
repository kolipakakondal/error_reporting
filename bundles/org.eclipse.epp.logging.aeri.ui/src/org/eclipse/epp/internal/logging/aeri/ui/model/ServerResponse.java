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

import com.google.common.base.Optional;

public class ServerResponse {

    public static enum ProblemResolution {
        NEW("new"), UNCONFIRMED("unconfirmed"), FIXED("fixed"), NEEDINFO("need info"), INVALID("invalid"), WONTFIX("won't fix");

        private String text;

        ProblemResolution(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private String incidentUrl;
    private String incidentId;
    private Optional<String> bugUrl = Optional.absent();
    private Optional<Integer> bugId = Optional.absent();
    private ServerResponse.ProblemResolution resolution;
    private Optional<String> committerMessage = Optional.absent();
    private String reportTitle;

    public ServerResponse.ProblemResolution getResolution() {
        return resolution;
    }

    public void setResolution(ServerResponse.ProblemResolution resolution) {
        this.resolution = resolution;
    }

    public Optional<String> getCommitterMessage() {
        return committerMessage;
    }

    public void setCommitterMessage(String committerMessage) {
        this.committerMessage = Optional.fromNullable(committerMessage);
    }

    public void setIncidentUrl(String incidentUrl) {
        this.incidentUrl = incidentUrl;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public void setBugUrl(String bugUrl) {
        this.bugUrl = Optional.fromNullable(bugUrl);
    }

    public void setBugId(Integer bugId) {
        this.bugId = Optional.fromNullable(bugId);
    }

    public String getIncidentUrl() {
        return incidentUrl;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public Optional<String> getBugUrl() {
        return bugUrl;
    }

    public Optional<Integer> getBugId() {
        return bugId;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }
}
