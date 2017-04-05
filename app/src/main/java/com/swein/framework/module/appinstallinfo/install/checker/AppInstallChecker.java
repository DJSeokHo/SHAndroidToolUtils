package com.swein.framework.module.appinstallinfo.install.checker;

import android.content.Context;

import com.swein.framework.tools.util.appinfo.AppInfoUtils;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.device.DeviceInfoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.swein.framework.module.appinstallinfo.install.IO.AppInstallInfoJsonFileIO.VERSION_CODE;
import static com.swein.framework.module.appinstallinfo.install.IO.AppInstallInfoJsonFileIO.createAppInstallInfoJSONObject;
import static com.swein.framework.module.appinstallinfo.install.IO.AppInstallInfoJsonFileIO.getAppInstallInfoJSONObject;

/**
 * Created by seokho on 29/03/2017.
 */

public class AppInstallChecker {


    public static void checkAppInstallInfoJSONObject( Context context ) {

        JSONObject jsonObject = getAppInstallInfoJSONObject( context );

        if ( jsonObject == null ) {

            //first install app

            installApp( context );

            return;
        }

        try {

            int    preVersionCode = Integer.parseInt( jsonObject.getString( VERSION_CODE ) );

            if ( preVersionCode < AppInfoUtils.getVersionCode( context ) ) {

                updateApp( context );

            }

        }
        catch ( JSONException e ) {
            e.printStackTrace();
        }

    }

    private static void installApp( Context context ) {
        ILog.iLogDebug( AppInstallChecker.class.getSimpleName(), "installApp" );
        String deviceSerialNum = DeviceInfoUtils.getDeviceID( context );
        String status          = "I";
        String osType          = "A";
        String versionCode     = String.valueOf( AppInfoUtils.getVersionCode( context ) );
        String packageName     = AppInfoUtils.getPackageName( context );
        String deviceName      = DeviceInfoUtils.getDeviceName();

        //save file
        createAppInstallInfoJSONObject( context, deviceSerialNum, status, osType, versionCode, packageName, deviceName );


        //send to service

    }

    private static void updateApp( Context context ) {
        ILog.iLogDebug( AppInstallChecker.class.getSimpleName(), "updateApp" );
        //update app
        String deviceSerialNum = DeviceInfoUtils.getDeviceID( context );
        String status          = "U";
        String osType          = "A";
        String versionCode     = String.valueOf( AppInfoUtils.getVersionCode( context ) );
        String packageName     = AppInfoUtils.getPackageName( context );
        String deviceName      = DeviceInfoUtils.getDeviceName();

        //save file
        //update file
        createAppInstallInfoJSONObject( context, deviceSerialNum, status, osType, versionCode, packageName, deviceName );

        //send to service

    }

}
