package com.swein.framework.module.screenshot.services;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.UriMatcher;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import com.swein.framework.module.screenshot.datas.UsageData;
import com.swein.framework.module.screenshot.javainterface.IGetCurrentAppName;
import com.swein.framework.module.screenshot.javainterface.IGetScreenShotsInfo;
import com.swein.framework.tools.util.debug.log.ILog;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("Registered")
public class ScreenShotsCaptureInfoService extends Service implements IGetScreenShotsInfo, IGetCurrentAppName {

    private MyBinder binder = new MyBinder();

    String[] screen_shot_dir = {"Screenshots"};

    private static final int IMAGE_UPDATED = 1;
    private static final int IMAGE_INSERTED = 0;
    private static final int DELETED = 2;
    private static final int DOWNLOAD_UPDATED = 3;
    final static String TAG = "ScreenShotsCaptureInfoService";
    private static final String EXTERNAL_CONTENT_URI_MATCHER = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString();
    private static final String[] PROJECTION = new String[]{
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA
    };

    private ContentObserver contentObserver;
    private String filePath, fileDir, appName;
    private UriMatcher uriMatcher;
    private int firstMode, secondMode;


    private static final String LAUNCHER = "com.android.launcher";
    private static final String SYSTEMUI = "com.android.systemui";


    private UsageData launcherUsageData, systemuiUsageData, otherUsageData;
    private ArrayList<UsageData> usageDataList = new ArrayList<UsageData>();
    private int count;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service is Created");

        filePath = "";
        fileDir = "";
        appName = "";
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        firstMode = -1;
        secondMode = -1;
        launcherUsageData = null;
        systemuiUsageData = null;
        otherUsageData = null;
        doScreenCapture();
        doRegister();
    }



    @Override
    public void doScreenCapture() {
        //[scheme:][//host:port][path][?query][#fragment]
        uriMatcher.addURI("media", "external/images/media", IMAGE_UPDATED);
        uriMatcher.addURI("media", "external/images/media/#", IMAGE_INSERTED);
        uriMatcher.addURI("media", "external", DELETED);
        uriMatcher.addURI("media", null, DOWNLOAD_UPDATED);

        contentObserver = new ContentObserver(null) {

            /**
             ILog.iLogDebug(TAG, "getScheme is ======= " + uri.getScheme());
             ILog.iLogDebug(TAG, "getHost is ======= " + uri.getHost());
             ILog.iLogDebug(TAG, "getPort is ======= " + uri.getPort()+"");
             ILog.iLogDebug(TAG, "getAuthority is ======= " + uri.getAuthority());
             ILog.iLogDebug(TAG, "getPath is ======= " + uri.getPath());
             ILog.iLogDebug(TAG, "getFragment is ======= " + uri.getFragment());

             image_update 1 + image_insert 0     = capture screenshots
             download_update 3 + image_update 1  = download image
             delete 2                            = delete image
             image_update 1                      = camera take photo
             */

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                
                ILog.iLogDebug(TAG, "uri is : " + uri.toString());
                checkImageMode(uri);
                getAppUsageList();
            }

            @Override
            public boolean deliverSelfNotifications() {

                ILog.iLogDebug(TAG, "deliverSelfNotifications");
                return deliverSelfNotifications();

            }
        };
    }

    @Override
    public void checkImageMode(Uri uri) {

        switch (uriMatcher.match(uri)){

            case IMAGE_UPDATED:

                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {

                    ILog.iLogDebug(TAG, "<<<<<< image updated >>>>>>>>>> API:" + Build.VERSION.SDK_INT);

                    getAftMarshmallowCaptureImageInfo(uri);

                }
                else {

                    firstMode = IMAGE_UPDATED;

                    ILog.iLogDebug(TAG, "<<<<<< image updated >>>>>>>>>> API:" + Build.VERSION.SDK_INT);
                }

                break;

            case IMAGE_INSERTED:

                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {

                    ILog.iLogDebug(TAG, "<<<<<< image inserted >>>>>>>>>> API:" + Build.VERSION.SDK_INT);

                    secondMode = IMAGE_INSERTED;

                    if( IMAGE_UPDATED == firstMode && IMAGE_INSERTED == secondMode ) {

                        getPerMarshmallowCaptureImageInfo(uri);

                    }

                }
                else {

                    ILog.iLogDebug(TAG, "<<<<<< image inserted >>>>>>>>>> API:" + Build.VERSION.SDK_INT);

                }

                break;

            case DELETED:

                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {

                    ILog.iLogDebug(TAG, "<<<<<< image deleted >>>>>>>>>> API:" + Build.VERSION.SDK_INT);

                }

                break;

            case DOWNLOAD_UPDATED:

                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {

                    ILog.iLogDebug(TAG, "<<<<<< image download updated >>>>>>>>>> API:" + Build.VERSION.SDK_INT);

                }
                else {

                    ILog.iLogDebug(TAG, "<<<<<< image deleted >>>>>>>>>> API:" + Build.VERSION.SDK_INT);

                }

                break;

            default:

                break;
        }
    }

    @Override
    public void getPerMarshmallowCaptureImageInfo(Uri uri) {
        ILog.iLogDebug(TAG, "Capture uri : " + uri + " = " + EXTERNAL_CONTENT_URI_MATCHER + " ?");

        if ( !uri.toString().matches(EXTERNAL_CONTENT_URI_MATCHER)) {

            ILog.iLogDebug(TAG, "uri and EXTERNAL_CONTENT_URI_MATCHER is matched");

            Cursor cursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    PROJECTION,
                    null,
                    null,
                    MediaStore.MediaColumns.DATE_MODIFIED + " desc");

            if ( cursor != null ) {

                if ( cursor.moveToFirst() ) {

                    fileDir = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    for (int i = 0; i < screen_shot_dir.length; i++) {
                        ILog.iLogDebug(TAG, i + " 'th");
                        if (fileDir.toLowerCase().equals(screen_shot_dir[i].toLowerCase())) {
                            appName = getAppNameByPackageName(getTopAppPackageName());
                            ILog.iLogDebug(TAG, "App is : " + appName);
                            ILog.iLogDebug(TAG, "Dir is : " + fileDir);
                            ILog.iLogDebug(TAG, "Path is : " + filePath);

                            break;
                        }
                    }
                }
            }
            cursor.close();
        }
    }

    @Override
    public void getAftMarshmallowCaptureImageInfo(Uri uri) {
        ILog.iLogDebug(TAG, "Capture uri : " + uri + " = " + EXTERNAL_CONTENT_URI_MATCHER + " ?");

        if ( uri.toString().matches(EXTERNAL_CONTENT_URI_MATCHER)) {

            ILog.iLogDebug(TAG, "uri and EXTERNAL_CONTENT_URI_MATCHER is matched");

            Cursor cursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    PROJECTION,
                    null,
                    null,
                    MediaStore.MediaColumns.DATE_MODIFIED + " desc");

            if ( cursor != null ) {

                if ( cursor.moveToFirst() ) {

                    fileDir = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    for (int i = 0; i < screen_shot_dir.length; i++) {
                        ILog.iLogDebug(TAG, i + " 'th");
                        if (fileDir.toLowerCase().equals(screen_shot_dir[i].toLowerCase())) {

                            appName = getAppNameByPackageName(getTopAppPackageName());
                            ILog.iLogDebug(TAG, "App is : " + appName);
                            ILog.iLogDebug(TAG, "Dir is : " + fileDir);
                            ILog.iLogDebug(TAG, "Path is : " + filePath);

                            break;
                        }
                    }
                }
            }
            cursor.close();
        }
    }

    @Override
    public void doRegister() {
        getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver);
    }

    @Override
    public void unRegister() {
        getContentResolver().unregisterContentObserver(contentObserver);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public String getTopAppPackageName() {

        long time = System.currentTimeMillis();

        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);

        List<UsageStats> queryUsageStats = null;
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {

            //(UsageStatsManager.INTERVAL_BEST, time - time, time) ??
            queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, time);

        }

        UsageStats recentStats = null;

        for ( UsageStats usageStats : queryUsageStats ) {

            if ( recentStats == null || recentStats.getLastTimeUsed() < usageStats.getLastTimeUsed() ) {

                recentStats = usageStats;
            }
        }

        return recentStats.getPackageName();
    }

    @Override
    public String getAppNameByPackageName(String packageName) {
        try {

            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo( packageName , 0 );

            return (String) (ai != null ? pm.getApplicationLabel( ai ) : "(unknown)");
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "(unknown)";

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void getAppUsageList(){

        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(USAGE_STATS_SERVICE);

        List<UsageStats> queryUsageStats = null;

        count = 0;
        long startTime = 0;
        long endTime = System.currentTimeMillis();

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            ILog.iLogDebug(TAG, "request time is : " + startTime + " - " + endTime);
            //(UsageStatsManager.INTERVAL_BEST, time - time, time) ??
            queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime);
        }

        UsageStats recentStats = null;

        for ( UsageStats usageStats : queryUsageStats ) {

            recentStats = usageStats;

            if ( recentStats.getLastTimeUsed() >= startTime && recentStats.getLastTimeUsed() <= endTime ) {

                usageDataList.add(new UsageData(recentStats.getPackageName(), recentStats.getLastTimeUsed()));
                count++;

                /*
                switch ( recentStats.getPackageName() ){

                    case LAUNCHER:

                        if( launcherUsageData == null ){
                            launcherUsageData = new UsageData(recentStats.getPackageName(), recentStats.getLastTimeUsed());
                        }
                        else {
                            if (recentStats.getLastTimeUsed() > launcherUsageData.getLastUsedTime()) {
                                launcherUsageData = new UsageData(recentStats.getPackageName(), recentStats.getLastTimeUsed());
                            }
                        }
                        ILog.iLogDebug(TAG, "~~~~~~~~~~~~~~~package is : " + launcherUsageData.getPackageName() + " " + startTime + " < [" + launcherUsageData.getLastUsedTime() + "] < " + endTime);

                        break;

                    case SYSTEMUI:
                        if( systemuiUsageData == null){
                            systemuiUsageData = new UsageData(recentStats.getPackageName(), recentStats.getLastTimeUsed());
                        }
                        else {
                            if( recentStats.getLastTimeUsed() > systemuiUsageData.getLastUsedTime()) {
                                systemuiUsageData = new UsageData(recentStats.getPackageName(), recentStats.getLastTimeUsed());
                            }
                        }

                        ILog.iLogDebug(TAG, "~~~~~~~~~~~~~~~package is : " + systemuiUsageData.getPackageName() + " " + startTime + " < [" + systemuiUsageData.getLastUsedTime() + "] < " + endTime);

                        break;

                    default:
                        if( otherUsageData == null ){
                            otherUsageData = new UsageData(recentStats.getPackageName(), recentStats.getLastTimeUsed());
                        }
                        else {
                            if (recentStats.getLastTimeUsed() > otherUsageData.getLastUsedTime()) {
                                otherUsageData = new UsageData(recentStats.getPackageName(), recentStats.getLastTimeUsed());
                            }
                        }
                        ILog.iLogDebug(TAG, "~~~~~~~~~~~~~~~package   is : " + otherUsageData.getPackageName() + " " + startTime + " < [" + otherUsageData.getLastUsedTime() + "] < " + endTime);

                        break;
                }
                */
            //    ILog.iLogDebug(TAG, "real app is : " + getCurrentAppName(launcherUsageData, systemuiUsageData, otherUsageData));

            }
        }

        selectSort(usageDataList);

    //    for(int i=0; i<=count; i++) {

    //        ILog.iLogDebug(TAG, i + " : " +  usageDataList.get(i).getPackageName() + " " + usageDataList.get(i).getLastUsedTime());

    //    }
    //    ILog.iLogDebug(TAG, "real app is : " + getCurrentAppName(launcherUsageData, systemuiUsageData, otherUsageData));

    }



    public void selectSort(ArrayList<UsageData> usageDataArrayList){

        if(0 == usageDataArrayList.size()) {
            return;
        }

        for ( int i = 0;i < usageDataArrayList.size() - 1;i++ ) {

            for ( int j = usageDataArrayList.size() - 1;j > i;j-- ) {
                if (usageDataArrayList.get(j).getLastUsedTime() == usageDataArrayList.get(i).getLastUsedTime()) {
                    usageDataArrayList.remove(j);
                }
            }
        }

        int position = 0;

        for( int i = 0;i < usageDataArrayList.size();i++ ) {
            int j = i + 1;
            position = i;
            UsageData temp = new UsageData(usageDataArrayList.get(i).getPackageName(), usageDataArrayList.get(i).getLastUsedTime());
            for( ;j < usageDataArrayList.size(); j++ ) {

                if( usageDataArrayList.get(j).getLastUsedTime() < temp.getLastUsedTime() ) {
                    temp.setPackageName(usageDataArrayList.get(j).getPackageName());
                    temp.setLastUsedTime(usageDataArrayList.get(j).getLastUsedTime());
                    position = j;
                }

            }
            usageDataArrayList.get(position).setPackageName(usageDataArrayList.get(i).getPackageName());
            usageDataArrayList.get(position).setLastUsedTime(usageDataArrayList.get(i).getLastUsedTime());
            usageDataArrayList.get(i).setPackageName(temp.getPackageName());
            usageDataArrayList.get(i).setLastUsedTime(temp.getLastUsedTime());
        }


        if( Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1 ) {

            if(firstMode == IMAGE_UPDATED && secondMode == IMAGE_INSERTED){
                for ( int i = 0; i < usageDataArrayList.size(); i++ ) {
                    ILog.iLogDebug(TAG, i + " : " + getAppNameByPackageName(usageDataArrayList.get(i).getPackageName())
                            + " " + usageDataArrayList.get(i).getLastUsedTime());
                }
            }
        }
        else if( Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 ) {

            if(firstMode == IMAGE_UPDATED){
                for ( int i = usageDataArrayList.size() - 3; i < usageDataArrayList.size(); i++ ) {
                    ILog.iLogDebug(TAG, i + " : " + getAppNameByPackageName(usageDataArrayList.get(i).getPackageName())
                            + " " + usageDataArrayList.get(i).getLastUsedTime());
                }
            }
        }


    }


    @Override
    public void getCurrentAppName(UsageData usageDataL, UsageData usageDataS, UsageData usageDataO) {

        if( usageDataS == null && usageDataO == null && usageDataL == null ) {
            return;
        }

        if( usageDataO == null && usageDataS == null && usageDataL != null ){
            ILog.iLogDebug(TAG, "real app is 1 : " + getAppNameByPackageName(usageDataL.getPackageName()));    //1
        }
        else if( usageDataO == null && usageDataL == null && usageDataS != null ){
            ILog.iLogDebug(TAG, "real app is 2 : " + getAppNameByPackageName(usageDataS.getPackageName()));    //2
        }
        else if( usageDataL == null && usageDataS == null && usageDataO != null ){
            ILog.iLogDebug(TAG, "real app is 3 : " + getAppNameByPackageName(usageDataO.getPackageName()));    //3
        }
        else if( usageDataL != null && usageDataS != null && usageDataO == null ){
            if( usageDataL.getLastUsedTime() < usageDataS.getLastUsedTime() ){
                ILog.iLogDebug(TAG, "real app is 4-1 : " + getAppNameByPackageName(usageDataL.getPackageName()));//4-1
            }
            else{
                ILog.iLogDebug(TAG, "real app is 4-2 : " + getAppNameByPackageName(usageDataS.getPackageName()));//4-2
            }
        }
        else if( usageDataL != null && usageDataS == null && usageDataO != null ){
            if( usageDataL.getLastUsedTime() < usageDataO.getLastUsedTime() ){
                ILog.iLogDebug(TAG, "real app is 5-1 : " + getAppNameByPackageName(usageDataL.getPackageName()));//5-1
            }
            else{
                ILog.iLogDebug(TAG, "real app is 5-2 : " + getAppNameByPackageName(usageDataO.getPackageName()));//5-2
            }
        }
        else if( usageDataL == null && usageDataS != null && usageDataO != null ){
            if( usageDataS.getLastUsedTime() < usageDataO.getLastUsedTime() ){
                ILog.iLogDebug(TAG, "real app is 6-1 : " + getAppNameByPackageName(usageDataS.getPackageName()));//6-1
            }
            else{
                ILog.iLogDebug(TAG, "real app is 6-2 : " + getAppNameByPackageName(usageDataO.getPackageName()));//6-2
            }
        }

    }


    public class MyBinder extends Binder {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Service is Binded");
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        doRegister();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Service is Started");
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("Service is Unbinded");
        unRegister();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.print("Service is Destroyed");
    }

}
