# Compatibility and verification

## Verified prototype behavior

Device: BOE Funbook C1 (C1S100), MT8788 / Mali-G72 MP3, original Android 10 vendor stack and Linux 4.14 kernel. ROM: unofficial LineageOS 21 GSI / Android 14. Magisk: 31.1. Hook framework: Vector 2.2 (3080).

| Component | Observations | Limits |
|---|---|---|
| Camera prototype | Rear 90° and front 270° metadata persisted after reboot with the original vendor property file restored; user confirmed proportions before module migration | Recheck actual preview in each app; device values are not portable |
| Bluetooth prototype | Reboot and off/on cycle reached ON without the previous startup abort | Peripheral pairing/audio not covered |
| Flutter prototype | Panda English 1.0.3 default Impeller/Vulkan flickered; software rendering stopped flicker but was slow; disabling Impeller alone resolved both. Scoped hook worked from the original app entry | Other apps and engines not yet verified |

Changing WebView provider from AOSP 137 to Google 157 and disabling Android hardware overlays did not resolve the Panda issue. That investigation supports an Impeller/driver interoperability problem, but does not identify a specific driver defect.

## Public 1.1.0

The first public build standardizes source/layout, changes the APK namespace, adds device guards, and refactors renderer argument handling into a checked pure-Java helper. Build/lint results are published in GitHub Actions. Device verification for this build is recorded below after migration; until recorded, prototype success is not a claim that a rebuilt binary was device tested.

## Migration

Magisk IDs remain stable, so install new ZIPs over the prototypes and reboot. The new Flutter APK uses `org.lsong.fluttercompat`; do not enable it and the old `local.funbook.fluttercompat` module simultaneously. Copy only the intended application scope, restart those apps and verify logs and visible behavior before removing the prototype.

Private device captures, serial numbers, addresses, account data and signing keys are not part of this public repository.

The module intentionally targets API 35 with Android 8.0+ support. A newer lint database may recommend a newer target (`OldTargetApi`); that recommendation is retained rather than suppressed. It is not a build error. Backup is explicitly disabled for both the pre-Android-12 and newer extraction policies.


## Signed 1.1.0 device migration — 2026-11-15

- Both BOE Magisk modules upgraded in place and report 1.1.0 / versionCode 2 after reboot.
- Bluetooth reached ON with zero startup crashes; command override remained 182.
- Camera service reports rear 90° / front 270°; original vendor property file remains unchanged.
- Signed `org.lsong.fluttercompat` 1.1.0 / versionCode 2 / target API 35 installed. The prototype APK was removed. Only Panda English/user 0 is enabled in the new scope.
- Direct cold launch of Panda, without helper extras, reports the new module applying Impeller=false before FlutterJNI.init. The user confirmed no flicker and no slowdown.
- The final CI build is [44806b8](https://github.com/lsongdev/android-compat-modules/commit/44806b8405e940e0c6d7bfa3e347e363e5936c6f), [run 43669047198](https://github.com/lsongdev/android-compat-modules/actions/runs/43669047198). Three argument tests passed; Android lint has no errors and retains the documented OldTargetApi recommendation.
- The repository is now public. No private signing material was committed or sent to CI. This version retains manual updates without Magisk updateJson URLs.

The two final APK builds differ only in their version-control provenance entry; all code and resource payloads match. The installed release APK was signed from the final CI artifact. Bluetooth peripheral audio and other Flutter apps remain outside these checks.
