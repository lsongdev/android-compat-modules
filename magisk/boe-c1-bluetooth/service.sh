#!/system/bin/sh
# SPDX-License-Identifier: GPL-3.0-only
# Run in Magisk late_start. Do not wait indefinitely for boot completion.
remaining=150
while [ "$(getprop sys.boot_completed)" != "1" ]; do
    [ "$remaining" -gt 0 ] || exit 0
    sleep 2
    remaining=$((remaining - 1))
done
sleep 15
# Runtime override only. The Treble app may reapply its own saved setting later.
resetprop -n persist.sys.bt.unsupported.commands 182
