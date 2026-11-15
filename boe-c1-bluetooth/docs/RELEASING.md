# Releasing

Update `module.prop` version and versionCode, retaining the module ID. Run `python3 tools/package.py` from this module directory. Install the candidate on the supported device, reboot and verify behavior and rollback. Generate SHA256 checksums for the ZIP and publish it with release notes. Component tags may use `camera-vX.Y.Z` or `bluetooth-vX.Y.Z`. Updates use manual downloads; no updateJson URL is configured.
