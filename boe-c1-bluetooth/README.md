# BOE Funbook C1 Bluetooth Compatibility

**Device-specific Magisk module.** BOE Funbook C1 / C1S100 (`Funbook C1`, `mt8788`) using a TrebleDroid-compatible Bluetooth stack.

## What changes

The old controller advertises `READ_DEFAULT_ERRONEOUS_DATA_REPORTING`, but responds to HCI opcode `0x0C5A` with `UNKNOWN_HCI_COMMAND`. The tested GSI Bluetooth stack aborted while parsing the response.

```properties
persist.sys.bt.unsupported.commands=182
```

TrebleDroid interprets `182` as **byte 18, bit 2** in the supported-command mask, not as an HCI opcode. Other Bluetooth stacks may ignore this property. The module does not install firmware or a kernel driver.

`system.prop` provides the early boot override. A late-start script waits at most five minutes for boot completion, then reapplies the runtime property after 15 seconds. It does not patch the persistent-property file or private app preferences.

## Treble settings requirement

On the tested ROM, Phh Treble Settings re-applies its own Bluetooth workaround at boot/unlock and can clear this property. Set **Phh Treble Settings → Misc features → Bluetooth workarounds → MediaTek** to avoid that conflict. The built-in MediaTek option sets the same `182` value, and may be sufficient by itself on some ROMs. This module is not a substitute for fixing a saved conflicting setting.

## Install / rollback

1. Confirm the exact BOE model and a Bluetooth stack with this TrebleDroid property support.
2. Install from Magisk 20.4+ (prototype tested on 31.1), set the MediaTek option as above, and reboot.
3. Check Bluetooth can reach ON. Pair and test your own peripherals; startup success does not prove every profile works.
4. To undo: disable/remove the module, restore the Treble workaround to **None** if it was changed for this fix, and reboot.

Module ID: `boe_c1_bluetooth_compat` (unchanged). The module owns the entire command override property; do not combine it with a different module that writes additional command-mask entries without merging the configuration.

Source references:
- [TrebleDroid controller command mask](https://github.com/TrebleDroid/platform_packages_modules_Bluetooth/blob/android-14.0.0_r50-td/system/gd/hci/controller.cc)
- [TrebleDroid command indices](https://github.com/TrebleDroid/platform_packages_modules_Bluetooth/blob/android-14.0.0_r50-td/system/pdl/hci/hci_packets.pdl)
- [Treble app MediaTek workaround](https://github.com/TrebleDroid/treble_app/blob/master/app/src/main/java/me/phh/treble/app/Misc.kt)

## Build

Run `python3 tools/package.py` inside this module directory. Python 3.11+ is required; the ZIP is written to `dist/`.
