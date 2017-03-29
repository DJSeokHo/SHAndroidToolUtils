package com.swein.framework.tools.util.device;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import com.swein.data.singleton.device.DeviceInfo;

/**
 * Created by seokho on 14/01/2017.
 */

public class DeviceInfoUtils {

    public static boolean initDeviceScreenDisplayMetricsPixels(Context context) {

        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
            DeviceInfo.getInstance().deviceScreenWidth = displayMetrics.widthPixels;
            DeviceInfo.getInstance().deviceScreenHeight = displayMetrics.heightPixels;
        }

        return true;
    }

    public static String getDeviceSerialNum() {

        return android.os.Build.SERIAL;
    }

    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

}
