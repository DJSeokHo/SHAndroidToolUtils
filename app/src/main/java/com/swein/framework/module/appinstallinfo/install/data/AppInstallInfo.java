package com.swein.framework.module.appinstallinfo.install.data;

import org.json.JSONException;
import org.json.JSONObject;

import static com.swein.framework.module.appinstallinfo.install.IO.AppInstallInfoJsonFileIO.DEVICE_NAME;
import static com.swein.framework.module.appinstallinfo.install.IO.AppInstallInfoJsonFileIO.DEVICE_SERIALNUM;
import static com.swein.framework.module.appinstallinfo.install.IO.AppInstallInfoJsonFileIO.OS_TYPE;
import static com.swein.framework.module.appinstallinfo.install.IO.AppInstallInfoJsonFileIO.PACKAGE_NAME;
import static com.swein.framework.module.appinstallinfo.install.IO.AppInstallInfoJsonFileIO.STATUS;
import static com.swein.framework.module.appinstallinfo.install.IO.AppInstallInfoJsonFileIO.VERSION_CODE;

/**
 * User install and update info
 *
 * Created by seokho on 29/03/2017.
 */

public class AppInstallInfo {

    public JSONObject createAppInstallJsonObject(String deviceSerialNum, String status, String osType, String versionCode, String packageName, String deviceName) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put( DEVICE_SERIALNUM, deviceSerialNum );
            jsonObject.put( STATUS, status );
            jsonObject.put( OS_TYPE, osType );
            jsonObject.put( VERSION_CODE, versionCode );
            jsonObject.put( PACKAGE_NAME, packageName );
            jsonObject.put( DEVICE_NAME, deviceName );
        }
        catch ( JSONException e ) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
