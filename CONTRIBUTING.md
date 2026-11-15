# Contributing

Use a separate branch and describe the device, Android/ROM, framework and app versions you verified. Do not include private keys, firmware dumps, device serials, account information or unredacted logs.

Before submitting:

```sh
python3 boe-c1-camera/tools/package.py
python3 boe-c1-bluetooth/tools/package.py
./flutter-render-compat/gradlew -p flutter-render-compat :lintRelease :testReleaseUnitTest :assembleRelease
```

Keep Magisk IDs stable. Do not broaden BOE C1 hardware guards without testing the exact new model and documenting its factory camera values. Flutter rendering selection is process-wide; compatibility claims need real device/app checks. Keep unknown engine versions unchanged rather than patching native code speculatively.

Versions are managed independently in each module's metadata. Document public behavior changes in `CHANGELOG.md`. Generated APKs, ZIPs, signing credentials and machine paths do not belong in Git.
