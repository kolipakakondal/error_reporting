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
package org.eclipse.epp.internal.logging.aeri.ui.utils;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

public class WildcardRegexConverter {

    private static final String QUOTE_START = "\\Q";
    private static final String QUOTE_END = "\\E";
    private static final String WILDCARD_PATTERN = ".*";

    public static Pattern convert(String string) {
        StringBuilder b = new StringBuilder();
        b.append(QUOTE_START);
        for (char c : string.toCharArray()) {
            if (c == '*') {
                b.append(QUOTE_END);
                b.append(WILDCARD_PATTERN);
                b.append(QUOTE_START);
            } else {
                b.append(c);
            }
        }
        b.append(QUOTE_END);
        return Pattern.compile(b.toString());
    }

    public static List<Pattern> convert(Collection<String> wildcardStrings) {
        List<Pattern> patterns = Lists.newArrayList();
        for (String s : wildcardStrings) {
            patterns.add(WildcardRegexConverter.convert(s));
        }
        return patterns;
    }
}
