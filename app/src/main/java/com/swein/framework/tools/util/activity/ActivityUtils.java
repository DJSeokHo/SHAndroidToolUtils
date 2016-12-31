package com.swein.framework.tools.util.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by seokho on 29/12/2016.
 */

public class ActivityUtils {



    public static void startNewActivityWithoutFinish(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        packageContext.startActivity(intent);

    }

    public static void startNewActivityWithFinish(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        packageContext.startActivity(intent);
        ((Activity) packageContext).finish();
    }



}
