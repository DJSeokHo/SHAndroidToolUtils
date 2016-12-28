package com.swein.framework.tools.util.debug.log;

import android.content.Context;
import android.util.Log;

/**
 * Created by seokho on 13/12/2016.
 */

public class ILog {

    private static String HEAD = "[- ILog Print -] ";
    private static String TAG = " ||=============>> ";


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

    public static void iLogDebug(String className, String string) {
        Log.d(HEAD + className + TAG, string);
    }

    public static void iLogDebug(String className, int i) {
        Log.d(HEAD + className + TAG, String.valueOf(i));
    }

    public static void iLogDebug(String className, long l) {
        Log.d(HEAD + className + TAG, String.valueOf(l));
    }

    //exception
    public static void iLogException(Context context, String string) {
        Log.i(HEAD + context.getPackageName() + TAG, string);
    }

    public static void iLogException(Context context, int i) {
        Log.i(HEAD + context.getPackageName() + TAG, String.valueOf(i));
    }

    public static void iLogException(Context context, long l) {
        Log.i(HEAD + context.getPackageName() + TAG, String.valueOf(l));
    }

}
