// SPDX-License-Identifier: GPL-3.0-only
package org.lsong.fluttercompat;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public final class EngineArgumentsTest {
    @Test public void replacesConflictingFlagsAndPreservesOtherSettings() {
        String[] input = {"--cache-dir-path=/data/example", "--enable-impeller=true",
                "--enable-software-rendering", "--enable-impeller", "--no-enable-impeller",
                "--enable-impeller=false", "--trace-skia"};
        assertArrayEquals(new String[] {"--cache-dir-path=/data/example",
                "--enable-software-rendering", "--trace-skia", "--enable-impeller=false"},
                EngineArguments.disableImpeller(input));
    }
    @Test public void handlesEmptyArguments() {
        assertArrayEquals(new String[] {"--enable-impeller=false"},
                EngineArguments.disableImpeller(new String[0]));
    }
    @Test public void isIdempotentAndDoesNotMutateInput() {
        String[] input = {"--enable-impeller=true"};
        String[] once = EngineArguments.disableImpeller(input);
        assertArrayEquals(once, EngineArguments.disableImpeller(once));
        assertArrayEquals(new String[] {"--enable-impeller=true"}, input);
    }
}
