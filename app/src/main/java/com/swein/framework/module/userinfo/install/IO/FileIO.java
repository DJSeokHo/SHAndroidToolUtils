package com.swein.framework.module.userinfo.install.IO;

import android.os.Environment;

import com.swein.framework.module.userinfo.install.data.UsageInstallInfo;
import com.swein.framework.tools.util.storage.FileStorageUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by seokho on 29/03/2017.
 */

public class FileIO {

    final private static String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    final private static String FILE_NAME = "AppUsage.json";

    private UsageInstallInfo usageInstallInfo;
    private JSONObject jsonObject;

    final public static String API = "service api key";
    final public static String DEVICE_SERIALNUM = "deviceSerialNum";
    final public static String STATUS = "status";
    final public static String OS_TYPE = "osType";
    final public static String VERSION_CODE = "versionCode";
    final public static String PACKAGE_NAME = "packageName";
    final public static String DEVICE_NAME = "deviceName";

    public void createUsageInstallInfoJSONObject( String deviceSerialNum, String status, String osType, String versionCode, String packageName, String deviceName ) {

        usageInstallInfo = new UsageInstallInfo(deviceSerialNum, status, osType, versionCode, packageName, deviceName);

        jsonObject = new JSONObject();

        try {
            jsonObject.put( DEVICE_SERIALNUM, usageInstallInfo.deviceSerialNum );
            jsonObject.put( STATUS, usageInstallInfo.status );
            jsonObject.put( OS_TYPE, usageInstallInfo.osType );
            jsonObject.put( VERSION_CODE, usageInstallInfo.versionCode );
            jsonObject.put( PACKAGE_NAME, usageInstallInfo.packageName );
            jsonObject.put( DEVICE_NAME, usageInstallInfo.deviceName );
        }
        catch ( JSONException e ) {
            e.printStackTrace();
        }
    }

    public void writeUsageInfoJSONObject() {

        if(jsonObject == null) {
            return;
        }

        FileStorageUtils.writeExternalStorageDirectoryFileWithJSONObject(FILE_PATH, FILE_NAME, jsonObject);

    }

    public JSONObject getUsageInfoJSONObject() {

        return FileStorageUtils.readExternalStorageDirectoryFileJSONObject(FILE_PATH, FILE_NAME);

    }


}
