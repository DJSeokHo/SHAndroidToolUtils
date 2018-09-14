package com.swein.framework.tools.util.shortcut;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT" />
 * <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
 */
public class ShortCutUtil {

    private static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final String DUPLICATE_ENABLE = "duplicate";

    public static void addShortcut(Context context, Class<?> targetClass, @Nullable Bundle bundle, String shortcutName, int iconResource) {

        Intent shortcutIntent = new Intent(context, targetClass);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        if (bundle != null) {
            shortcutIntent.putExtras(bundle);
        }

        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, iconResource));

        addIntent.setAction(ACTION_ADD_SHORTCUT);
        addIntent.putExtra(DUPLICATE_ENABLE, false);
        context.sendBroadcast(addIntent);
    }

    public static void removeShortcut(Context context, Class<?> targetClass, @Nullable Bundle bundle, String shortcutName, int iconResource) {

        Intent shortcutIntent = new Intent(context, targetClass);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        if (bundle != null) {
            shortcutIntent.putExtras(bundle);
        }

        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, iconResource));

        addIntent.setAction(ACTION_ADD_SHORTCUT);
        context.sendBroadcast(addIntent);

    }

    @TargetApi(Build.VERSION_CODES.O)
    public static void addShortcut(Context context, Class<?> targetClass, @Nullable Bundle bundle, String shortcutId, String shortcutName, int iconResource) {

        ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);

        if(shortcutManager == null) {
            return;
        }

        if (shortcutManager.isRequestPinShortcutSupported()) {

            Intent launcherIntent = new Intent(context, targetClass);

            launcherIntent.setAction(Intent.ACTION_VIEW);

            if (bundle != null) {
                launcherIntent.putExtras(bundle);
            }

            launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launcherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(context, shortcutId)
                    .setIcon(Icon.createWithResource(context, iconResource))
                    .setShortLabel(shortcutName)
                    .setIntent(launcherIntent)
                    .build();

            /*
                add to AndroidManifest.xml

                <receiver
                    android:name="com.swein.framework.tools.util.shortcut.ShortcutReceiver"
                    android:exported="false">
                </receiver>

             */
            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, ShortcutReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);
            shortcutManager.requestPinShortcut(shortcutInfo, shortcutCallbackIntent.getIntentSender());
        }

    }
}
