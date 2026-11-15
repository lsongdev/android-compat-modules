# Flutter Render Compat compatibility

Verified on BOE Funbook C1 (C1S100), MT8788 / Mali-G72 MP3, Android 10 vendor stack, unofficial LineageOS 21 / Android 14, Magisk 31.1.

## Verified 1.1.0 behavior — 2026-11-15

Panda English 1.0.3 flickered with default Impeller/Vulkan. Software rendering resolved flicker but slowed the app; disabling Impeller alone resolved both. Changing WebView provider and hardware overlays did not resolve it. This supports renderer/driver interoperability trouble without identifying a specific driver defect.

Signed 1.1.0, package `org.lsong.fluttercompat`, was verified with Vector 2.2 (3080), scoped only to Panda English/user 0. Cold launch from the original entry logged Impeller=false before FlutterJNI.init; the user confirmed no flicker or slowdown. The prototype APK was removed. Other apps and engines remain unverified.

Historical release [CI run 43669047198](https://github.com/lsongdev/android-compat-modules/actions/runs/43669047198) passed three argument tests and lint with no errors, retaining OldTargetApi. API 35 is intentional; backup is disabled. These observations describe the original release, not device testing of later build-layout changes.
