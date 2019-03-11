package com.swein.framework.tools.util.appinfo;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;

import com.swein.shandroidtoolutils.BuildConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by seokho on 29/03/2017.
 */

public class AppInfoUtil {

    public static void moveToAppPushSetting(Context context) {

        Intent intent = new Intent();

        if(android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1){
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        }
        else if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        }
        else {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        }

        context.startActivity(intent);

    }

    public static boolean isPushNotificationEnable(Context context) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            return notificationManagerCompat.areNotificationsEnabled();
        }
        else {

            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();

            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;

            Class appOpsClass = null; /* Context.APP_OPS_MANAGER */

            try{
                appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod  = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);

                Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
                int value = (int)opPostNotificationValue.get(Integer.class);
                return ((int)checkOpNoThrowMethod.invoke(mAppOps,value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
            }
            catch(Exception e){
                e.printStackTrace();
            }

            return true;
        }
    }

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

    /**
     * must run on thread
     * need
     * implementation 'org.jsoup:jsoup:1.10.2'
     */
    public static String getLastVersion() {

        String newVersion = "";

        try {
            Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();

            if (document != null) {
                Elements element = document.getElementsContainingOwnText("Current Version");
                for (Element ele : element) {
                    if (ele.siblingElements() != null) {
                        Elements siblingElements = ele.siblingElements();
                        for (Element siblingElement : siblingElements) {
                            newVersion = siblingElement.text();
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return newVersion;
    }

    public static void openDetailPage(Activity activity) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + activity.getPackageName()));

            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            }
            else {
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName()));
                activity.startActivity(intent);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isThisApkInDebugMode(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isOtherApkInDebugMode(Context context,String packageName) {
        try {
            PackageInfo pkginfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);

            if (pkginfo != null ) {
                ApplicationInfo info= pkginfo.applicationInfo;
                return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
