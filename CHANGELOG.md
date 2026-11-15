# Changelog

## 1.1.0 — 2026-11-15

First maintained release, standardized from locally verified BOE Funbook C1 fixes.

- BOE C1 camera: restore rear 90° / front 270° metadata through Magisk properties.
- BOE C1 Bluetooth: mask incorrectly advertised HCI command using TrebleDroid index 182.
- Both Magisk modules retain prototype module IDs and add hardware guards.
- Bound Bluetooth boot wait to five minutes; document the Treble settings override.
- Flutter: use public package `org.lsong.fluttercompat`; hook `FlutterJNI.init` in selected apps.
- Preserve GPU acceleration and all non-Impeller arguments; skip unknown init signatures.
- Add Gradle builds, argument checks, lint, deterministic ZIP packaging and CI.
- Separate release signing from source; firmware, private keys and device captures are excluded.

Prototype APK package: `local.funbook.fluttercompat`. Public APK is a separate app; see migration instructions.

- Manual release distribution: no GitHub credentials or Magisk update URLs are embedded.
