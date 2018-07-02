package com.swein.framework.module.knoxmdm.share;

import android.content.Context;

public class KnoxMDMPreference {

    private static final String STATE = "STATE";
    private static final String KEY_KNOX_LICENSE_ACTIVATED = "KEY_KNOX_LICENSE_ACTIVATED";
    private static final String KEY_BACKWARD_LICENSE_ACTIVATED = "KEY_BACKWARD_LICENSE_ACTIVATED";

    public static void setKLMLicenseActivated(Context context, boolean activated) {
        context.getSharedPreferences(STATE, Context.MODE_PRIVATE)
                .edit().putBoolean(KEY_KNOX_LICENSE_ACTIVATED, activated)
                .apply();
    }

    public static boolean isKLMLicenseActivated(Context context) {
        return context.getSharedPreferences(STATE, Context.MODE_PRIVATE)
                .getBoolean(KEY_KNOX_LICENSE_ACTIVATED, false);
    }

    public static void setELMLicenseActivated(Context context, boolean activated) {
        context.getSharedPreferences(STATE, Context.MODE_PRIVATE)
                .edit().putBoolean(KEY_BACKWARD_LICENSE_ACTIVATED, activated)
                .apply();
    }

    public static boolean isELMLicenseActivated(Context context) {
        return context.getSharedPreferences(STATE, Context.MODE_PRIVATE)
                .getBoolean(KEY_BACKWARD_LICENSE_ACTIVATED, false);
    }
}
