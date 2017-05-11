package com.swein.data.singleton.device;

/**
 * Created by seokho on 14/01/2017.
 */

public class DeviceInfo {

    public int deviceScreenWidth;
    public int deviceScreenHeight;

    private DeviceInfo() {
        deviceScreenWidth = 0;
        deviceScreenHeight = 0;
    }

    private static DeviceInfo instance = new DeviceInfo();

    public static DeviceInfo getInstance() {

        return instance;
    }


}
