package com.swein.data.preferences.example;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by seokho on 22/11/2016.
 */

public class SharedPreferencesData {

    private final String KEY = "com.swein.storage.preferences";

    static Context context;

    public SharedPreferencesData(Context context ) {
        this.context = context;
    }

    public void putValue(String key, String value )
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public String getValue(String key, String dftValue )
    {
        SharedPreferences pref = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        try {
            return pref.getString(key, dftValue);
        }
        catch (Exception e) {
            return dftValue;
        }
    }

}
