package com.swein.framework.tools.util.debug.log;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

/**
 * Created by seokho on 29/12/2016.
 */

public class ILog {

    //debug
    public static void iLogDebug(Context context, String string) {
        Log.d("[- ILog Print -] " + context.getPackageName() + " ||===> ", string + "\n");
    }

    public static void iLogDebug(Context context, int i) {
        Log.d("[- ILog Print -] " + context.getPackageName() + " ||===> ", String.valueOf(i) + "\n");
    }

    public static void iLogDebug(Context context, long l) {
        Log.d("[- ILog Print -] " + context.getPackageName() + " ||===> ", String.valueOf(l) + "\n");
    }

    public static void iLogDebug(Context context, boolean b) {
        Log.d("[- ILog Print -] " + context.getPackageName() + " ||===> ", String.valueOf(b) + "\n");
    }

    public static void iLogDebug(Context context, Uri uri) {
        Log.d("[- ILog Print -] " + context.getPackageName() + " ||===> ", String.valueOf(uri) + "\n");
    }

    public static void iLogDebug(String className, String string) {
        Log.d("[- ILog Print -] " + className + " ||===> ", string + "\n");
    }

    public static void iLogDebug(String className, int i) {
        Log.d("[- ILog Print -] " + className + " ||===> ", String.valueOf(i) + "\n");
    }

    public static void iLogDebug(String className, long l) {
        Log.d("[- ILog Print -] " + className + " ||===> ", String.valueOf(l) + "\n");
    }

    public static void iLogDebug(String className, boolean b) {
        Log.d("[- ILog Print -] " + className + " ||===> ", String.valueOf(b) + "\n");
    }

    public static void iLogDebug(String className, Uri uri) {
        Log.d("[- ILog Print -] " + className + " ||===> ", String.valueOf(uri) + "\n");
    }

    //exception
    public static void iLogException(Context context, String string) {
        Log.e("[- ILog Print -] " + context.getPackageName() + " ||===> ", string + "\n");
    }

    public static void iLogException(Context context, int i) {
        Log.e("[- ILog Print -] " + context.getPackageName() + " ||===> ", String.valueOf(i) + "\n");
    }

    public static void iLogException(Context context, long l) {
        Log.e("[- ILog Print -] " + context.getPackageName() + " ||===> ", String.valueOf(l) + "\n");
    }

    public static void iLogException(Context context, boolean b) {
        Log.e("[- ILog Print -] " + context.getPackageName() + " ||===> ", String.valueOf(b) + "\n");
    }

    public static void iLogException(Context context, Uri uri) {
        Log.e("[- ILog Print -] " + context.getPackageName() + " ||===> ", String.valueOf(uri) + "\n");
    }

    public static void iLogException(String className, String string) {
        Log.e("[- ILog Print -] " + className + " ||===> ", string + "\n");
    }

    public static void iLogException(String className, Uri uri) {
        Log.e("[- ILog Print -] " + className + " ||===> ", String.valueOf(uri) + "\n");
    }

}
