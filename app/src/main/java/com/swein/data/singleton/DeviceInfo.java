package com.swein.data.singleton;

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

    private static DeviceInfo instance = null;

    private static Object obj= new Object();

    public static DeviceInfo getInstance() {

        if(null == instance){

            synchronized(DeviceInfo.class){

                if(null == instance){

                    instance = new DeviceInfo();

                }
            }
        }

        return instance;
    }


}
