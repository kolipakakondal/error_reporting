/**
 * Copyright (c) 2015 Codetrails GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Daniel Haftstein - initial API and implementation.
 */
package org.eclipse.epp.internal.logging.aeri.ui.model;

import static com.google.common.base.Optional.fromNullable;
import static org.eclipse.epp.internal.logging.aeri.ui.model.ProblemStatus.RequiredAction.NONE;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

public class ProblemStatus {

    public static enum RequiredAction {
        IGNORE, NEEDINFO, FIXED, NONE
    }

    private String incidentFingerprint;
    private String problemUrl;
    private int bugId;
    private String bugUrl;
    private RequiredAction action;
    private String message;

    public ProblemStatus() {
    }

    public Optional<String> getIncidentFingerprint() {
        return fromNullable(incidentFingerprint);
    }

    public void setIncidentFingerprint(String fingerprint) {
        this.incidentFingerprint = fingerprint;
    }

    public Optional<String> getProblemUrl() {
        return fromNullable(problemUrl);
    }

    public void setProblemUrl(String problemUrl) {
        this.problemUrl = problemUrl;
    }

    public int getBugId() {
        return bugId;
    }

    public void setBugId(int bugId) {
        this.bugId = bugId;
    }

    public boolean hasBugId() {
        return bugId > 0;
    }

    public Optional<String> getBugUrl() {
        return fromNullable(bugUrl);
    }

    public void setBugUrl(String bugURL) {
        this.bugUrl = bugURL;
    }

    public RequiredAction getAction() {
        return Objects.firstNonNull(action, NONE);
    }

    public void setAction(RequiredAction action) {
        this.action = action;
    }

    public Optional<String> getMessage() {
        return fromNullable(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((action == null) ? 0 : action.hashCode());
        result = prime * result + bugId;
        result = prime * result + ((bugUrl == null) ? 0 : bugUrl.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((problemUrl == null) ? 0 : problemUrl.hashCode());
        result = prime * result + ((incidentFingerprint == null) ? 0 : incidentFingerprint.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProblemStatus other = (ProblemStatus) obj;
        if (action != other.action)
            return false;
        if (bugId != other.bugId)
            return false;
        if (bugUrl == null) {
            if (other.bugUrl != null)
                return false;
        } else if (!bugUrl.equals(other.bugUrl))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (problemUrl == null) {
            if (other.problemUrl != null)
                return false;
        } else if (!problemUrl.equals(other.problemUrl))
            return false;
        if (incidentFingerprint == null) {
            if (other.incidentFingerprint != null)
                return false;
        } else if (!incidentFingerprint.equals(other.incidentFingerprint))
            return false;
        return true;
    }

}
