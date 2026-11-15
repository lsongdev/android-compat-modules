#!/usr/bin/env python3
# SPDX-License-Identifier: GPL-3.0-only
"""Build deterministic Magisk ZIPs from an explicit source allowlist."""
from pathlib import Path
import re
import zipfile

ROOT = Path(__file__).resolve().parent.parent
COMPONENT = "boe-c1-camera"


def package(component):
    source = ROOT
    properties = dict(line.split("=", 1) for line in
                      (source / "module.prop").read_text().splitlines()
                      if "=" in line and not line.startswith("#"))
    if not re.fullmatch(r"[A-Za-z][A-Za-z0-9._-]+", properties["id"]):
        raise ValueError("Invalid Magisk module id")
    if not re.fullmatch(r"[0-9]+(?:\.[0-9]+){2}", properties["version"]):
        raise ValueError("Version must be MAJOR.MINOR.PATCH")
    output = ROOT / "dist" / f"{component}-{properties['version']}.zip"
    output.parent.mkdir(exist_ok=True)
    entries = {
        "META-INF/com/google/android/update-binary":
            (ROOT / "third-party/magisk/update-binary").read_bytes(),
        "META-INF/com/google/android/updater-script": b"#MAGISK\n",
        "LICENSE": (ROOT / "LICENSE").read_bytes(),
    }
    for name in ("module.prop", "system.prop", "customize.sh", "service.sh", "README.md"):
        file = source / name
        if file.is_file():
            data = file.read_bytes()
            if b"\r" in data:
                raise ValueError(f"Use LF line endings: {file}")
            entries[name] = data
    with zipfile.ZipFile(output, "w", compression=zipfile.ZIP_DEFLATED, compresslevel=9) as archive:
        for name, data in sorted(entries.items()):
            item = zipfile.ZipInfo(name, date_time=(1980, 1, 1, 0, 0, 0))
            item.create_system = 3
            executable = name.endswith(".sh") or name.endswith("update-binary")
            item.external_attr = (0o100755 if executable else 0o100644) << 16
            item.compress_type = zipfile.ZIP_DEFLATED
            archive.writestr(item, data)
    print(output.relative_to(ROOT))


if __name__ == "__main__":
    package(COMPONENT)
