package com.swein.framework.tools.util.services;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;

public class ServiceUtil {

    /**
     *
     * @param context context
     * @param serviceName can find it at top of your service class (package name + class name)
     * @return is running
     */
    public static boolean isServiceRunning(Context context, String serviceName) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if(activityManager != null) {
            ArrayList<ActivityManager.RunningServiceInfo> services = (ArrayList<ActivityManager.RunningServiceInfo>) activityManager.getRunningServices(Integer.MAX_VALUE);
            for (int i = 0; i < services.size(); i++) {
                if (services.get(i).service.getClassName().equals(serviceName)) {
                    return true;
                }
            }
        }

        return false;
    }



}
