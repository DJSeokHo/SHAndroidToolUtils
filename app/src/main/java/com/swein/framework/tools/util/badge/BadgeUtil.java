package com.swein.framework.tools.util.badge;

import android.content.Context;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by seokho on 20/02/2018.
 */

public class BadgeUtil {

    public static void setBadgeNumber(Context context, int number) {
        ShortcutBadger.applyCount(context, number);
    }

    public static void clearBadgeNumber(Context context) {
        ShortcutBadger.removeCount(context);
    }

    public static void clearBadgeNumber(Context context, int number) {
        ShortcutBadger.applyCount(context, number);
    }
}
