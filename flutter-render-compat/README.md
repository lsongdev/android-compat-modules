# Flutter Render Compat

A scoped Legacy Xposed API 82 module. Package: **`org.lsong.fluttercompat`**.

It hooks `io.flutter.embedding.engine.FlutterJNI.init` and replaces Impeller selection arguments with `--enable-impeller=false`. Other flags, including any app-owned software rendering setting, are preserved. This module never enables software rendering itself.

## Install

1. Install Magisk/Zygisk plus a compatible Vector or LSPosed framework from upstream.
2. Install the **signed** module APK from Releases.
3. In the framework manager enable **Flutter Render Compat / Flutter 渲染兼容** and select affected apps. Panda English is a recommended scope, not an automatically enabled global policy.
4. Save/apply the scope, force stop the app, and launch it normally.
5. In logcat, look for `FlutterRenderCompat: Applied Impeller=false before FlutterJNI.init`. Flutter may also log an Impeller opt-out deprecation notice.

No special desktop shortcut is required. A successful hook log proves argument injection, not that every possible renderer bug is fixed.

## How it works

Hook registration first uses the package class loader, then retries after `Application.attach` for cases where the application context exposes a different loader. Hooks are deduplicated by class identity. Initialization signatures without a recognized `String[]` are left unchanged. System framework (`android`) and the module itself are excluded.

No original APK changes, background service, networking, app permissions or native-library patches are involved. Rendering choice applies to the Flutter engine/process, not only one WebView page.

## Limitations

- Flutter must still include a working Skia fallback. Engines that removed it are unsupported.
- Custom/native bootstrap, custom class loaders and future embedding changes can bypass the hook.
- Impeller-dependent shaders or features may behave differently. Use per-app scope rather than enabling it for everything.
- This does not force Vulkan off system-wide or repair vendor drivers.
- Existing Flutter engines must be restarted after changing scope.

## Migration and removal

The prototype `local.funbook.fluttercompat` is a different app. Install the public APK, disable the old module/scope, enable the new module and restore the app scope, restart and verify, then uninstall the prototype. Existing helper desktop shortcuts can remain as a fallback.

To undo, deselect the target app or disable this module, save/apply, and force stop/reopen the app. Uninstalling this APK deletes the module. Removing the framework is a separate Magisk change.

The signing key is intentionally absent from source and CI. See [release instructions](docs/RELEASING.md).

Upstream initialization reference: [FlutterJNI](https://github.com/flutter/engine/blob/main/shell/platform/android/io/flutter/embedding/engine/FlutterJNI.java). The installed app's bundled engine version, not the current upstream branch, determines actual compatibility.

## Build

From this directory, run `./gradlew assembleRelease lintRelease testReleaseUnitTest` with JDK 17+, Android SDK 35 and build-tools 35.0.0. The wrapper and all Gradle configuration belong to this module. Signed release files go in `dist/`; run `python3 tools/checksums.py` to generate their checksums.

## Project documentation

[Compatibility](docs/COMPATIBILITY.md) · [Release process](docs/RELEASING.md) · [Changelog](CHANGELOG.md) · [Third-party notices](THIRD_PARTY_NOTICES.md)
