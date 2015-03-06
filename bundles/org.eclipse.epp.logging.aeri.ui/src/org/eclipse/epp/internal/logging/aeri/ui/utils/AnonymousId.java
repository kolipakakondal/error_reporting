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

import static org.apache.commons.lang3.SystemUtils.getUserHome;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.Files;

public class AnonymousId {

    private static UUID anonymousId;

    public static synchronized UUID getId() {
        if (anonymousId == null) {
            try {
                anonymousId = readOrCreateUUID();
            } catch (IOException e) {
                Throwables.propagate(e);
            }
        }
        return anonymousId;
    }

    private static UUID readOrCreateUUID() throws IOException {
        File f = new File(getUserHome(), ".eclipse/org.eclipse.recommenders/anonymousId");
        if (f.exists()) {
            String uuid = Files.readFirstLine(f, Charsets.UTF_8);
            return UUID.fromString(uuid);
        } else {
            f.getParentFile().mkdirs();
            UUID uuid = UUID.randomUUID();
            Files.write(uuid.toString(), f, Charsets.UTF_8);
            return uuid;
        }
    }
}
