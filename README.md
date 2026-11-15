# Android Compat Modules

[简体中文](README.zh-CN.md) · [Releases](https://github.com/lsongdev/android-compat-modules/releases)

Small, reversible Android compatibility fixes developed on a BOE Funbook C1 (C1S100). Two are device-specific Magisk modules; one is a scoped Xposed application module.

| Module | Install as | Scope / purpose |
|---|---|---|
| [BOE C1 Bluetooth](magisk/boe-c1-bluetooth/README.md) | Magisk ZIP | BOE C1 + compatible TrebleDroid stack; ignore an incorrectly advertised HCI command |
| [BOE C1 Camera](magisk/boe-c1-camera/README.md) | Magisk ZIP | BOE C1; restore factory camera orientation metadata |
| [Flutter Render Compat](flutter-render-compat/README.md) | APK + Vector/LSPosed | Selected Flutter apps; disable Impeller while retaining GPU acceleration |

These are compatibility workarounds, not GPU driver replacements. The Flutter module is potentially useful beyond this tablet, but is not a universal fix for every Flutter version or application.

## Install

Download signed APKs / module ZIPs from Releases and compare them with `SHA256SUMS`.

- **Magisk modules:** install the ZIP from Magisk, then reboot. Installation rejects other hardware. Read the module page first, especially the Bluetooth Treble settings requirement.
- **Flutter:** install the APK, enable it in a compatible Vector/LSPosed manager, and select only the affected apps. Force stop and reopen them. Do not select Android System Framework.
- **Rollback:** remove or disable the Magisk module and reboot; for Flutter, disable its scope and restart the app. The Bluetooth Treble setting must also be reset if you enabled it.

No app permissions, network access, original APK modifications or firmware images are required by the Flutter module. Installing an injection framework is a separate system change; use its upstream distribution.

## Build

Magisk ZIPs require Python 3.11+ only:

```sh
python3 scripts/package-magisk.py                 # both
python3 scripts/package-magisk.py boe-c1-camera  # one component
```

Flutter requires JDK 17+, Android SDK platform 35 and build-tools 35.0.0:

```sh
export ANDROID_HOME=/path/to/android-sdk
./gradlew :flutter-render-compat:assembleRelease \
  :flutter-render-compat:lintRelease \
  :flutter-render-compat:testReleaseUnitTest
```

The release APK is **unsigned** until signed with the maintainer's private key. CI artifacts are build outputs, not the signed release distribution. See [release instructions](docs/RELEASING.md).

## Device verification and limits

The prototype fixes were verified on BOE C1 with unofficial LineageOS 21 / Android 14, Magisk 31.1 and Vector 2.2. Panda English 1.0.3 stopped flickering without the slowdown caused by software rendering when Impeller was disabled. BOE camera metadata and Bluetooth startup were verified after reboot. Bluetooth peripheral audio was not covered by those checks.

The public 1.1.0 source standardizes the prototypes, changes the Flutter package to `org.lsong.fluttercompat`, and adds explicit device guards and build checks. See [verification and migration](docs/COMPATIBILITY.md) for release build status and remaining limitations.

## Project boundaries

- BOE orientation values are not generic MediaTek defaults.
- Bluetooth command masking requires a stack that implements `persist.sys.bt.unsupported.commands`.
- Flutter engines that have removed Skia fallback cannot be fixed by this Impeller switch.
- Apps using custom/native engine bootstrap or unusual class loaders may bypass the Java hook.
- Some apps require Impeller-specific behavior; enable this module per app rather than globally.

Copyright © 2026 Lsong. Licensed under **GPL-3.0-only**. See [third-party notices](THIRD_PARTY_NOTICES.md). Contributions and redacted issue reports are welcome.
