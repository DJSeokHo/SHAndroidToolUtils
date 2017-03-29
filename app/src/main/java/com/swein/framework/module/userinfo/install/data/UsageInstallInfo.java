package com.swein.framework.module.userinfo.install.data;

/**
 * User install and update info
 *
 * Created by seokho on 29/03/2017.
 */

public class UsageInstallInfo {

    public String deviceSerialNum;
    public String status;
    public String osType;
    public String versionCode;
    public String packageName;
    public String deviceName;

    public UsageInstallInfo(String deviceSerialNum, String status, String osType, String versionCode, String packageName, String deviceName) {
        this.deviceSerialNum = deviceSerialNum;
        this.status = status;
        this.osType = osType;
        this.versionCode = versionCode;
        this.packageName = packageName;
        this.deviceName = deviceName;
    }

}
