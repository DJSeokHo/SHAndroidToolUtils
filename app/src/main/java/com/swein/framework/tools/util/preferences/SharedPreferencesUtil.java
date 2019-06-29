package com.swein.framework.tools.util.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.swein.shandroidtoolutils.BuildConfig;

import java.util.Map;

/**
 * Created by seokho on 22/11/2016.
 *
 * commit is better than apply
 *
 */
public class SharedPreferencesUtil {

    private final static String KEY = BuildConfig.APPLICATION_ID;

    /**
     *  MODE_PRIVATE: can only edit by this app
     */
    public static void putValue(Context context, String key, String value)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putValue(Context context, String key, int value)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putValue(Context context, String key, boolean value)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getValue(Context context, String key, boolean dftValue)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        try {
            return pref.getBoolean(key, dftValue);
        }
        catch (Exception e) {
            return dftValue;
        }
    }

    public static String getValue(Context context, String key, String dftValue)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        try {
            return pref.getString(key, dftValue);
        }
        catch (Exception e) {
            return dftValue;
        }
    }

    public static int getValue(Context context, String key, int dftValue)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        try {
            return pref.getInt(key, dftValue);
        }
        catch (Exception e) {
            return dftValue;
        }
    }

    public static boolean isContainValue(Context context, String key)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);

        return pref.contains(key);
    }

    public static Map getAllKeyValue(Context context)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        return pref.getAll();
    }

    public static void clearSharedPreferencesData(Context context)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
    }

    public static void removeValue(Context context, String key)
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
    }


}
