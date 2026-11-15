#!/system/bin/sh
# SPDX-License-Identifier: GPL-3.0-only
[ "$BOOTMODE" = true ] || abort "Install from the Magisk app while Android is running."
[ "$MAGISK_VER_CODE" -ge 20400 ] || abort "Magisk 20.4 or newer is required."
case "$(getprop ro.product.vendor.model)" in
    "Funbook C1"|"C1S100") ;;
    *) abort "This module is specific to BOE Funbook C1 (C1S100)." ;;
esac
[ "$(getprop ro.hardware)" = "mt8788" ] || abort "Unsupported hardware; expected mt8788."
ui_print "- BOE Funbook C1 compatibility module"
ui_print "- Properties are applied by Magisk at boot. Reboot after installation."
set_perm_recursive "$MODPATH" 0 0 0755 0644
[ ! -f "$MODPATH/service.sh" ] || set_perm "$MODPATH/service.sh" 0 0 0755
