package com.swein.framework.tools.util.debug;

import android.util.Log;

/**
 * Created by seokho on 13/12/2016.
 */

public class SeokHoTest {

    private static String HEAD = "[- seokho test -] ";
    private static String TAG = " ||=============>> ";


    public static void testSeokHo(String tag, String string) {
        Log.d(HEAD + tag + TAG, string);
    }

    public static void testSeokHo(String tag, int i) {
        Log.d(HEAD + tag + TAG, String.valueOf(i));
    }

    public static void testSeokHo(String tag, long l) {
        Log.d(HEAD + tag + TAG, String.valueOf(l));
    }

}
