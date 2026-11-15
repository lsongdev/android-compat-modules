// SPDX-License-Identifier: GPL-3.0-only
package org.lsong.fluttercompat;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/** Loaded only into the application scope explicitly selected in the framework. */
public final class RenderHook implements IXposedHookLoadPackage {
    private static final String TAG = "FlutterRenderCompat";
    private static final String MODULE_PACKAGE = "org.lsong.fluttercompat";
    private static final Set<Class<?>> INSTALLED =
            Collections.newSetFromMap(new IdentityHashMap<Class<?>, Boolean>());

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam app) {
        if ("android".equals(app.packageName) || MODULE_PACKAGE.equals(app.packageName)) {
            return;
        }
        install(app.classLoader, app.packageName);
        try {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class,
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam parameter) {
                            Context context = (Context) parameter.args[0];
                            install(context.getClassLoader(), app.packageName);
                        }
                    });
        } catch (Throwable error) {
            XposedBridge.log(TAG + ": Application.attach fallback unavailable: " + error);
        }
    }

    private static synchronized void install(ClassLoader loader, final String packageName) {
        try {
            Class<?> flutter = XposedHelpers.findClassIfExists(
                    "io.flutter.embedding.engine.FlutterJNI", loader);
            if (flutter == null || INSTALLED.contains(flutter)) {
                return;
            }
            Set<XC_MethodHook.Unhook> hooks = XposedBridge.hookAllMethods(flutter, "init",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam parameter) {
                            try {
                                for (int i = 0; i < parameter.args.length; i++) {
                                    if (parameter.args[i] instanceof String[]) {
                                        parameter.args[i] = EngineArguments.disableImpeller(
                                                (String[]) parameter.args[i]);
                                        Log.i(TAG, "Applied Impeller=false before FlutterJNI.init: "
                                                + packageName);
                                        return;
                                    }
                                }
                                Log.w(TAG, "Unknown init signature; left unchanged: " + packageName);
                            } catch (Throwable error) {
                                XposedBridge.log(TAG + ": argument hook failed: " + error);
                            }
                        }
                    });
            if (!hooks.isEmpty()) {
                INSTALLED.add(flutter);
            }
            Log.i(TAG, "Registered " + hooks.size() + " init hook(s): " + packageName);
        } catch (Throwable error) {
            XposedBridge.log(TAG + ": hook unavailable for " + packageName + ": " + error);
        }
    }
}
