package com.swein.framework.tools.util.debug.log;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

/**
 * Created by seokho on 29/12/2016.
 */

public class ILog {

    private static String HEAD = "[- ILog Print -] ";
    private static String TAG = " ||===> ";


    //debug
    public static void iLogDebug(Context context, String string) {
        Log.d(HEAD + context.getPackageName() + TAG, string);
    }

    public static void iLogDebug(Context context, int i) {
        Log.d(HEAD + context.getPackageName() + TAG, String.valueOf(i));
    }

    public static void iLogDebug(Context context, long l) {
        Log.d(HEAD + context.getPackageName() + TAG, String.valueOf(l));
    }

    public static void iLogDebug(Context context, boolean b) {
        Log.d(HEAD + context.getPackageName() + TAG, String.valueOf(b));
    }

    public static void iLogDebug(Context context, Uri uri) {
        Log.d(HEAD + context.getPackageName() + TAG, String.valueOf(uri));
    }

    public static void iLogDebug(String className, String string) {
        Log.d(HEAD + className + TAG, string);
    }

    public static void iLogDebug(String className, int i) {
        Log.d(HEAD + className + TAG, String.valueOf(i));
    }

    public static void iLogDebug(String className, long l) {
        Log.d(HEAD + className + TAG, String.valueOf(l));
    }

    public static void iLogDebug(String className, boolean b) {
        Log.d(HEAD + className + TAG, String.valueOf(b));
    }

    public static void iLogDebug(String className, Uri uri) {
        Log.d(HEAD + className + TAG, String.valueOf(uri));
    }

    //exception
    public static void iLogException(Context context, String string) {
        Log.e(HEAD + context.getPackageName() + TAG, string);
    }

    public static void iLogException(Context context, int i) {
        Log.e(HEAD + context.getPackageName() + TAG, String.valueOf(i));
    }

    public static void iLogException(Context context, long l) {
        Log.e(HEAD + context.getPackageName() + TAG, String.valueOf(l));
    }

    public static void iLogException(Context context, boolean b) {
        Log.e(HEAD + context.getPackageName() + TAG, String.valueOf(b));
    }

    public static void iLogException(Context context, Uri uri) {
        Log.e(HEAD + context.getPackageName() + TAG, String.valueOf(uri));
    }

    public static void iLogException(String className, String string) {
        Log.e(HEAD + className + TAG, string);
    }

    public static void iLogException(String className, Uri uri) {
        Log.e(HEAD + className + TAG, String.valueOf(uri));
    }

}
