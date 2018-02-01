package com.swein.framework.tools.util.device;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.UUID;

/**
 * Created by seokho on 14/01/2017.
 */

public class DeviceInfoUtil {

    /**
     * must use this method in application first
     *
     * @param context
     * @return
     */
    public static boolean initDeviceScreenDisplayMetricsPixels(Context context) {

        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
//            displayMetrics.widthPixels;
//            displayMetrics.heightPixels;
        }

        return true;
    }

    public static Point getScreenSize(Context context ) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Point          size    = new Point();
        size.x = metrics.widthPixels;
        size.y = metrics.heightPixels;

        return size;
    }

    public static int getDeviceScreenWidth(Context context) {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return 0;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getDeviceScreenHeight(Context context) {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return 0;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity)context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static String getDeviceSerialNum() {

        return android.os.Build.SERIAL;
    }

    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    public static String getDeviceID( Context context ) {
        TelephonyManager tm = (TelephonyManager)context.getSystemService( Context.TELEPHONY_SERVICE );

        String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString( context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID );

        UUID deviceUuid = new UUID( androidId.hashCode(), ( (long)tmDevice.hashCode() << 32 ) | tmSerial.hashCode() );

        return deviceUuid.toString();
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model        = Build.MODEL;
        if ( model.startsWith( manufacturer ) ) {
            return capitalize( model );
        }
        else {
            return capitalize( manufacturer ) + " " + model;
        }
    }

    private static String capitalize( String s ) {
        if ( s == null || s.length() == 0 ) {
            return "";
        }
        char first = s.charAt( 0 );
        if ( Character.isUpperCase( first ) ) {
            return s;
        }
        else {
            return Character.toUpperCase( first ) + s.substring( 1 );
        }
    }

}
