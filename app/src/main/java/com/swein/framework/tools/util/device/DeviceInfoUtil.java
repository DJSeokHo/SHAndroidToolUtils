package com.swein.framework.tools.util.device;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import androidx.core.app.ActivityCompat;

import com.swein.framework.tools.util.network.NetWorkUtil;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
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
            ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
            /*
            displayMetrics.widthPixels;
            displayMetrics.heightPixels;
             */
        }

        return true;
    }

    public static Point getScreenSize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Point size = new Point();
        size.x = metrics.widthPixels;
        size.y = metrics.heightPixels;

        return size;
    }

    public static int getDeviceScreenWidth(Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return 0;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getDeviceScreenHeight(Context context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return 0;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static String getDeviceSerialNum() {

        return android.os.Build.SERIAL;
    }

    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    public static String getDeviceOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getDeviceID(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String tmDevice = "";
        String tmSerial = "";
        String androidId = "";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            tmDevice = "" + telephonyManager.getDeviceId();
            tmSerial = "" + telephonyManager.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        }

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());

        return deviceUuid.toString();
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getIccid(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String iccid = "N/A";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            iccid = telephonyManager.getSimSerialNumber();
        }

        return iccid;
    }

    public static String getNativePhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String nativePhoneNumber = "N/A";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            nativePhoneNumber = telephonyManager.getLine1Number();
        }

        return nativePhoneNumber;
    }


    public static String getPhoneInfo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuffer stringBuffer = new StringBuffer();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            stringBuffer.append("\nLine1Number = " + telephonyManager.getLine1Number());
            stringBuffer.append("\nNetworkOperator = " + telephonyManager.getNetworkOperator());
            stringBuffer.append("\nNetworkOperatorName = " + telephonyManager.getNetworkOperatorName());
            stringBuffer.append("\nSimCountryIso = " + telephonyManager.getSimCountryIso());
            stringBuffer.append("\nSimOperator = " + telephonyManager.getSimOperator());
            stringBuffer.append("\nSimOperatorName = " + telephonyManager.getSimOperatorName());
            stringBuffer.append("\nSimSerialNumber = " + telephonyManager.getSimSerialNumber());
            stringBuffer.append("\nSubscriberId(IMSI) = " + telephonyManager.getSubscriberId());
        }

        return  stringBuffer.toString();
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//2G/3G/4G
                try {

                    for (Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces(); enumeration.hasMoreElements(); ) {
                        NetworkInterface networkInterface = enumeration.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else if (info.getType() == ConnectivityManager.TYPE_WIFI) { // WiFi
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                return NetWorkUtil.intIPtOStringIP(wifiInfo.getIpAddress());
            }
        }
        else {
            // no network
        }
        return null;
    }

}
