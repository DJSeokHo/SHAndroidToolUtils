package com.swein.framework.module.userinfo.install.checker;

import android.content.Context;

import com.swein.framework.module.userinfo.install.IO.FileIO;
import com.swein.framework.tools.util.appinfo.AppInfoUtils;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.device.DeviceInfoUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.swein.framework.module.userinfo.install.IO.FileIO.STATUS;
import static com.swein.framework.module.userinfo.install.IO.FileIO.VERSION_CODE;

/**
 * Created by seokho on 29/03/2017.
 */

public class UsageInstallChecker {

    private FileIO fileIO;
    private Context context;

    public UsageInstallChecker(Context context) {
        fileIO = new FileIO(context);
        this.context = context;
    }

    public void checkUsageInfoJSONObject() {

        JSONObject jsonObject = fileIO.getUsageInfoJSONObject();

        if(jsonObject == null) {

            //first install app

            installApp();

            return;
        }

        try {
            String preStatus = jsonObject.getString( STATUS );
            int preVersionCode = Integer.parseInt( jsonObject.getString( VERSION_CODE ) );

            if(preStatus.equals( "I" ) || preStatus.equals( "U" )) {
                if(preVersionCode < AppInfoUtils.getVersionCode(context)) {

                    updateApp();

                }
                else if(preVersionCode > AppInfoUtils.getVersionCode(context)) {

                    updateApp();

                }
                else {

                    ILog.iLogDebug( UsageInstallChecker.class.getSimpleName(), "normal" );

                }
//                //test
//                installApp();
            }

        }
        catch ( JSONException e ) {
            e.printStackTrace();
        }

    }

    private void installApp() {
        ILog.iLogDebug( UsageInstallChecker.class.getSimpleName(), "installApp" );
        String deviceSerialNum = DeviceInfoUtils.getDeviceID(context);
        String status = "I";
        String osType = "A";
        String versionCode = String.valueOf( AppInfoUtils.getVersionCode( context ) );
        String packageName = AppInfoUtils.getPackageName( context );
        String deviceName = DeviceInfoUtils.getDeviceName();

        fileIO.createUsageInstallInfoJSONObject( deviceSerialNum, status, osType, versionCode, packageName, deviceName );
        //save file
        fileIO.writeUsageInfoJSONObject();

        //send to service

    }

    private void updateApp() {
        ILog.iLogDebug( UsageInstallChecker.class.getSimpleName(), "updateApp" );
        //update app
        String deviceSerialNum = DeviceInfoUtils.getDeviceID(context);
        String status = "U";
        String osType = "A";
        String versionCode = String.valueOf( AppInfoUtils.getVersionCode( context ) );
        String packageName = AppInfoUtils.getPackageName( context );
        String deviceName = DeviceInfoUtils.getDeviceName();

        //update file
        fileIO.createUsageInstallInfoJSONObject( deviceSerialNum, status, osType, versionCode, packageName, deviceName );
        //save file
        fileIO.writeUsageInfoJSONObject();

        //send to service

    }

}
