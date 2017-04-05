package com.swein.framework.module.appinstallinfo.install.IO;

import android.content.Context;
import android.os.Environment;

import com.swein.framework.module.appinstallinfo.install.data.AppInstallInfo;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.storage.FileStorageUtils;

import org.json.JSONObject;

/**
 * Created by seokho on 29/03/2017.
 */

public class AppInstallInfoJsonFileIO {

    final private static String FILE_NAME = "AppUsage.json";

    final public static String API = "service api key";
    final public static String DEVICE_SERIALNUM = "deviceSerialNum";
    final public static String STATUS = "status";
    final public static String OS_TYPE = "osType";
    final public static String VERSION_CODE = "versionCode";
    final public static String PACKAGE_NAME = "packageName";
    final public static String DEVICE_NAME = "deviceName";

    public static void createAppInstallInfoJSONObject( Context context, String deviceSerialNum, String status, String osType, String versionCode, String packageName, String deviceName ) {

        AppInstallInfo appInstallInfo = new AppInstallInfo();

        JSONObject jsonObject = appInstallInfo.createAppInstallJsonObject(deviceSerialNum, status, osType, versionCode, packageName, deviceName);

        ILog.iLogDebug( AppInstallInfoJsonFileIO.class.getSimpleName(), String.valueOf( context.getExternalFilesDir( Environment.DIRECTORY_DOCUMENTS ) ) );

        ILog.iLogDebug( AppInstallInfoJsonFileIO.class.getSimpleName(), String.valueOf( context.getExternalFilesDir( Environment.DIRECTORY_DOCUMENTS ) ) + " " + FILE_NAME);

        FileStorageUtils.writeExternalStorageDirectoryFileWithJSONObject(String.valueOf( context.getExternalFilesDir( Environment.DIRECTORY_DOCUMENTS ) ), FILE_NAME, jsonObject);

    }

    public static JSONObject getAppInstallInfoJSONObject(Context context) {
        ILog.iLogDebug( AppInstallInfoJsonFileIO.class.getSimpleName(), String.valueOf( context.getExternalFilesDir( Environment.DIRECTORY_DOCUMENTS ) ) );
        return FileStorageUtils.readExternalStorageDirectoryFileJSONObject(String.valueOf( context.getExternalFilesDir( Environment.DIRECTORY_DOCUMENTS ) ), FILE_NAME);

    }


}
