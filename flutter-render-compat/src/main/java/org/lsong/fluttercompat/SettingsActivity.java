// SPDX-License-Identifier: GPL-3.0-only
package org.lsong.fluttercompat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

/** Instructions only; scope and enable state are managed by Vector/LSPosed. */
public final class SettingsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setTitle(R.string.app_name);
        TextView instructions = new TextView(this);
        instructions.setText(R.string.settings_text);
        instructions.setTextSize(18);
        int padding = Math.round(24 * getResources().getDisplayMetrics().density);
        instructions.setPadding(padding, padding, padding, padding);
        ScrollView scroll = new ScrollView(this);
        scroll.addView(instructions);
        setContentView(scroll);
    }
}
