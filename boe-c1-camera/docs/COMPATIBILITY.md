# Camera compatibility

Verified on BOE Funbook C1 (C1S100), MT8788 / Mali-G72 MP3, Android 10 vendor stack, unofficial LineageOS 21 / Android 14, Magisk 31.1.

## Verified 1.1.0 behavior — 2026-11-15

Camera service reports rear 90° / front 270° after the 1.1.0 upgrade and reboot. The original vendor property file remains unchanged. The user confirmed normal preview proportions before migration. Recheck actual previews in affected apps; these factory values are device-specific.
