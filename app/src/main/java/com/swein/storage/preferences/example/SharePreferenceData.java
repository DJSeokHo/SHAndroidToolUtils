package com.swein.storage.preferences.example;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceData
{

	private final String KEY = "com.swein.storage.preferences";

	static Context mContext;

	public SharePreferenceData(Context c ) {
		mContext = c;
	}

	public void putValue(String key, String value )
	{
		SharedPreferences pref = mContext.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}


	public String getValue(String key, String dftValue )
	{
		SharedPreferences pref = mContext.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
		try {
			return pref.getString(key, dftValue);
		}
		catch (Exception e) {
			return dftValue;
		}
	}
}
