package com.swein.framework.module.appanalysisreport.data.model.impl;

import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;

public class DeviceUserData implements AppAnalysisData {

    /* 기기 UUID */
    private String deviceUUID = "";

    /* 기기 모델 */
    private String deviceModel = "";

    /* OS 버전 */
    private String osVersion = "";

    /* 앱 이름 */
    private String appName = "";

    /* 앱 버전 */
    private String appVersion = "";

    /* 기타 */
    private String other = "";


    public static class Builder {

        /* 기기 UUID */
        private String deviceUUID = "";

        /* 기기 모델 */
        private String deviceModel = "";

        /* OS 버전 */
        private String osVersion = "";

        /* 앱 이름 */
        private String appName = "";

        /* 앱 버전 */
        private String appVersion = "";

        /* 기타 */
        private String other = "";

        public Builder setDeviceUUID(String deviceUUID) {
            this.deviceUUID = deviceUUID;
            return this;
        }

        public Builder setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
            return this;
        }

        public Builder setOsVersion(String osVersion) {
            this.osVersion = osVersion;
            return this;
        }

        public Builder setAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder setAppVersion(String appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        public Builder setOther(String other) {
            this.other = other;
            return this;
        }

        public DeviceUserData build() {
            return new DeviceUserData(this);
        }
    }

    private DeviceUserData(Builder builder) {
        this.deviceUUID = builder.deviceUUID;
        this.deviceModel = builder.deviceModel;
        this.osVersion = builder.osVersion;
        this.appName = builder.appName;
        this.appVersion = builder.appVersion;
        this.other = builder.other;
    }

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getOther() {
        return other;
    }

    @Override
    public String toString() {
        return deviceUUID + " " + deviceModel + " " + osVersion + " " + appName + " " + appVersion + " " + other;
    }

    @Override
    public String toReport() {

        return DEVICE_UUID_KEY + deviceUUID + "\n" +
                DEVICE_MODEL_KEY + deviceModel + "\n" +
                OS_VERSION_KEY + osVersion + "\n" +
                APP_NAME_KEY + appName + "\n" +
                APP_VERSION_KEY + appVersion + "\n" +
                OTHER_KEY + other;
    }

    private final static String DEVICE_UUID_KEY = "기기 UUID: ";
    private final static String DEVICE_MODEL_KEY = "기기 모델: ";
    private final static String OS_VERSION_KEY = "OS 버잔: ";
    private final static String APP_NAME_KEY = "앱 이름: ";
    private final static String APP_VERSION_KEY = "앱 버전: ";
    private final static String OTHER_KEY = "기타: ";
}
