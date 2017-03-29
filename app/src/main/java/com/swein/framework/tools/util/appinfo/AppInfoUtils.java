package com.swein.framework.tools.util.appinfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by seokho on 29/03/2017.
 */

public class AppInfoUtils {

    public static String getVersionName(Context context)
    {

        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo( context.getPackageName(), 0);
        }
        catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }
        return packageInfo.versionName;

    }

    public static int getVersionCode(Context context)
    {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo    packageInfo    = null;
        int versionCode               = 0;
        try {
            packageInfo = packageManager.getPackageInfo( context.getPackageName(), PackageManager.GET_ACTIVITIES );
        }
        catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }

        if ( packageInfo != null ) {

            versionCode = packageInfo.versionCode;

        }

        return versionCode;

    }

    public static String getPackageName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo    packageInfo    = null;
        String packageName = null;
        try {
            packageInfo = packageManager.getPackageInfo( context.getPackageName(), PackageManager.GET_ACTIVITIES );
        }
        catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }

        if ( packageInfo != null ) {

            packageName = packageInfo.packageName;

        }

        return packageName;
    }

}
