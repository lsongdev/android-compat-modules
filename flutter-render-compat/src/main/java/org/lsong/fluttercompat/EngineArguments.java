// SPDX-License-Identifier: GPL-3.0-only
package org.lsong.fluttercompat;

import java.util.ArrayList;
import java.util.List;

/** Changes only renderer selection; preserves unrelated flags and their ordering. */
final class EngineArguments {
    private EngineArguments() {}

    static String[] disableImpeller(String[] original) {
        List<String> result = new ArrayList<>();
        if (original != null) {
            for (String argument : original) {
                if (argument != null && (argument.equals("--enable-impeller")
                        || argument.startsWith("--enable-impeller=")
                        || argument.equals("--no-enable-impeller"))) {
                    continue;
                }
                result.add(argument);
            }
        }
        result.add("--enable-impeller=false");
        return result.toArray(new String[0]);
    }
}
