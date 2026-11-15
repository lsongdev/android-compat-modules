# Third-party notices

Original project code is licensed under GPL-3.0-only. External dependencies retain their own licenses.

| Component | Use | Upstream / license |
|---|---|---|
| Magisk v31.1 `scripts/module_installer.sh` | Installer entry point in Magisk ZIPs | [Magisk](https://github.com/topjohnwu/Magisk/blob/v31.1/scripts/module_installer.sh), GPL-3.0; corresponding installer is in `third-party/magisk/` |
| Gradle 8.9 wrapper | Build launcher and wrapper JAR | [Gradle](https://github.com/gradle/gradle/tree/v8.9.0/gradle/wrapper), Apache-2.0; license in `third-party/gradle-wrapper/` |
| Xposed API 82 | Compile-only API, not bundled in APK | [XposedBridge](https://github.com/rovo89/XposedBridge), API distributed via https://api.xposed.info/ |
| JUnit 4.13.2 | Build-time tests only | [JUnit 4](https://github.com/junit-team/junit4), EPL-1.0 |
| Android SDK / AGP | Build tools, not redistributed | [Android SDK](https://developer.android.com/studio/terms) |

Vector / LSPosed, Magisk APKs, vendor drivers and Flutter engines are not redistributed by this project. Install a compatible framework separately from its upstream publisher. App names in scope recommendations identify interoperability targets; this project is not affiliated with those apps, BOE, Google or Flutter.
