# Releasing

Each module has its own version fields and can be built and released independently. The initial `v1.1.0` release contains all three. Future component-only tags may use `camera-vX.Y.Z`, `bluetooth-vX.Y.Z` or `flutter-vX.Y.Z`; update the corresponding Magisk update JSON to the actual asset URL.

1. Update the changed component's version and integer versionCode, changelog and update metadata. Keep Magisk IDs stable.
2. Run the Build workflow (unit checks, Android lint, unsigned release APK and deterministic Magisk ZIPs). Do not call an untested source refactor device-verified.
3. Obtain the unsigned APK from CI or local Gradle output. Keep signing credentials outside the repository. Sign with Android build-tools `apksigner` and verify it:

```sh
apksigner sign --key /private/path/signing-key.pk8 \
  --cert /private/path/signing-cert.pem \
  --out dist/flutter-render-compat-1.1.0.apk \
  flutter-render-compat/build/outputs/apk/release/flutter-render-compat-release-unsigned.apk
apksigner verify --verbose dist/flutter-render-compat-1.1.0.apk
```

Use the same signing key for future updates to the public APK. Do not check it in, paste it in an issue, or sign official releases with an ephemeral CI debug key. CI does not receive the maintainer's signing key.

4. Build desired Magisk modules with `python3 scripts/package-magisk.py [component]`.
5. Install signed candidates on the target device, verify version, scope/hook behavior and affected hardware. Check rollback. Keep private observations locally and publish only a redacted compatibility summary.
6. Run `python3 scripts/checksums.py`. Upload only signed APKs, module ZIPs and SHA256SUMS as release assets. CI artifacts with `unsigned` in their name cannot be installed as-is.
7. Publish the tag and release notes. The repository root LICENSE and third-party notices must remain available with the corresponding source.

The prototype APK had a different package name and does not impose a signing identity on the public package. The first public package can use an existing private maintainer key kept outside this repository.
