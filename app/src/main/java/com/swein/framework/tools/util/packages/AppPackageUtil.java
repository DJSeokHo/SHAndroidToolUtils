package com.swein.framework.tools.util.packages;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by seokho on 17/11/2016.
 */

public class AppPackageUtil {

    /**
     * check APP and start APP
     * @param context
     * @param packagename
     */
    public static void startApplicationWithPackageName(Context context, String packagename) {

        // get Activities, services, versioncode, name... from app package name
        PackageInfo packageinfo = null;

        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        }
        catch ( PackageManager.NameNotFoundException e ) {
            e.printStackTrace();
        }

        if ( packageinfo == null ) {

            //should download or install app

            return;
        }

        // create a Intent of package that type is CATEGORY_LAUNCHER
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // getPackageManager() queryIntentActivities
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();

        if ( resolveinfo != null ) {

            String packageName = resolveinfo.activityInfo.packageName;
            String className = resolveinfo.activityInfo.name;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);

            //System.exit(0);
        }
    }

}
