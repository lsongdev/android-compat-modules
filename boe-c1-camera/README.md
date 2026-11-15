# BOE Funbook C1 Camera Orientation

**Device-specific Magisk module.** BOE Funbook C1 / C1S100, vendor model `Funbook C1`, hardware `mt8788`. Do not use these values on unrelated tablets.

## What changes

Magisk applies two factory values at boot:

```properties
ro.vendor.camera.main.orientation=90
ro.vendor.camera.sub.orientation=270
```

These restore the rear/front sensor orientation metadata used by the vendor camera HAL. They corrected rotated or stretched camera previews after installing a GSI on the tested device. Applications must recreate their camera sessions; cached metadata can persist until the app is force stopped.

The module does not write `/vendor/build.prop`, patch image buffers, or change app aspect ratios. If you previously edited the physical vendor properties, disabling this module alone will not undo those edits.

## Install / rollback

1. Magisk 20.4+; tested prototype with Magisk 31.1 / Android 14 GSI.
2. Install this ZIP in Magisk and reboot.
3. Force stop and reopen the system camera and affected apps. Check front/back orientation and the shape of a circular object.
4. To undo: disable or remove the module, reboot, then restart affected apps.

The installer checks the vendor model and hardware before applying the device-specific values. There is no recovery ZIP installation support.

Module ID: `boe_c1_camera_orientation` (unchanged from the prototype). Properties and scripts are GPL-3.0-only; installer attribution is in the repository's third-party notices.

## Build

Run `python3 tools/package.py` inside this module directory. Python 3.11+ is required; the ZIP is written to `dist/`.

## Project documentation

[Compatibility](docs/COMPATIBILITY.md) · [Release process](docs/RELEASING.md) · [Changelog](CHANGELOG.md) · [Third-party notices](THIRD_PARTY_NOTICES.md)
