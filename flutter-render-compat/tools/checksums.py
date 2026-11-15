#!/usr/bin/env python3
# SPDX-License-Identifier: GPL-3.0-only
"""Write SHA256SUMS for signed release APKs and module ZIPs in dist/."""
import hashlib
from pathlib import Path
root = Path(__file__).resolve().parent.parent
files = sorted(path for path in (root / "dist").iterdir()
               if path.suffix in (".apk", ".zip")
               and "unsigned" not in path.name and "debug" not in path.name)
lines = []
for file in files:
    with file.open("rb") as stream:
        digest = hashlib.file_digest(stream, "sha256").hexdigest()
    lines.append(f"{digest}  {file.name}\n")
(root / "dist/SHA256SUMS").write_text("".join(lines))
print("".join(lines), end="")
