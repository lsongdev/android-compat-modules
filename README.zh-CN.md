# Android Compat Modules

[English](README.md) · [下载发布版本](https://github.com/lsongdev/android-compat-modules/releases)

源于 BOE Funbook C1（C1S100）的三个 Android 兼容修复模块，统一维护、各自构建。

| 模块 | 安装方式 | 用途 |
|---|---|---|
| [蓝牙兼容](magisk/boe-c1-bluetooth/README.md) | 面具 ZIP | 特定 TrebleDroid 蓝牙协议栈，屏蔽芯片错误宣称支持的命令 |
| [摄像头方向](magisk/boe-c1-camera/README.md) | 面具 ZIP | 恢复 BOE C1 原厂前后摄像头方向 |
| [Flutter 渲染兼容](flutter-render-compat/README.md) | APK + Vector/LSPosed | 按应用关闭 Impeller，保留 GPU 加速 |

前两个模块只适用于已验证的 BOE C1，不是通用联发科补丁。Flutter 模块不修改原应用签名，不启用软件渲染，不更新显卡驱动。

## 安装与撤销

仓库为私有，查看源码和下载 Release 需要 GitHub 登录及仓库权限。面具模块不配置自动更新地址，后续从 Release 手动下载安装；不要将 GitHub token 放入模块。

从 Releases 下载 ZIP 或已签名 APK，可对照 SHA256SUMS 校验。

- 面具模块：在面具中从存储安装 ZIP，重启生效。卸载或停用后重启撤销；蓝牙另需还原手动设置的 Treble 兼容选项。
- Flutter 模块：安装 APK → 在 Vector/LSPosed 中启用 → 勾选需要修复的应用 → 强制停止并重新打开应用。不要勾选系统框架。取消作用范围或停用后重启应用即可撤销。
- 卸载 Flutter APK 会删除模块。只是临时关闭时，请在管理器停用。

管理器、注入框架、面具本体均需从各自上游安装，本仓库不附带。

## 原型迁移

面具模块保留原 ID，可覆盖升级。Flutter 正式包名为 `org.lsong.fluttercompat`，与原型 `local.funbook.fluttercompat` 是两个应用：安装正式版，取消旧模块作用范围，启用新版并恢复目标应用范围；验证后卸载旧版。不要让两个模块同时注入同一应用。

## 构建与验证

面具 ZIP：`python3 scripts/package-magisk.py`。

Flutter：JDK 17+、Android SDK 35、build-tools 35.0.0，然后执行：

```sh
./gradlew :flutter-render-compat:assembleRelease :flutter-render-compat:lintRelease :flutter-render-compat:testReleaseUnitTest
```

生成的 release APK 未签名，正式发布需用仓库外保存的私钥签名；CI 构建产物不等于正式签名版本。

[验证与兼容范围](docs/COMPATIBILITY.md) · [发版说明](docs/RELEASING.md) · [第三方来源与许可证](THIRD_PARTY_NOTICES.md)

GPL-3.0-only，Copyright © 2026 Lsong。
