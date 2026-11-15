# Releasing

Obtain the unsigned APK from CI or local Gradle output. Keep signing credentials outside the repository. Sign with Android build-tools `apksigner` and verify it:

```sh
apksigner sign --key /private/path/signing-key.pk8 \
  --cert /private/path/signing-cert.pem \
  --out dist/flutter-render-compat-1.1.0.apk \
  build/outputs/apk/release/flutter-render-compat-release-unsigned.apk
apksigner verify --verbose dist/flutter-render-compat-1.1.0.apk
```

Use the same signing key for future updates to the public APK. Do not check it in, paste it in an issue, or sign official releases with an ephemeral CI debug key. CI does not receive the maintainer's signing key.


Update versionName/versionCode in `build.gradle` and CHANGELOG.md. Build with `./gradlew assembleRelease lintRelease testReleaseUnitTest`. Verify the signed candidate on the device, then run `python3 tools/checksums.py` and upload only the signed APK and SHA256SUMS. Keep the same signing key for updates. Component tags may use `flutter-vX.Y.Z`.

