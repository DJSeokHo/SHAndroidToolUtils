package com.swein.framework.tools.util.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by seokho on 27/12/2016.
 */

public class JSonUtils {

    public static String jsonObjectListToJSonString(List list) {

        JSONArray jsonArray = new JSONArray(list);
        return jsonArray.toString();

    }

    public static String jsonStringToJSonString(JSONArray jsonArray) {

        return jsonArray.toString();

    }

    public static Map<String, String> jsonStringToMap(String string) {

        JSONArray jsonArray = null;
        Map<String, String> hashMap = null;

        try {

            jsonArray = new JSONArray(string);
            hashMap = new HashMap<String, String>();

            for ( int i = 0; i < jsonArray.length(); i++ ) {

                JSONObject j = jsonArray.optJSONObject(i);
                Iterator it = j.keys();

                while (it.hasNext()) {
                    String n = String.valueOf(it.next());
                    hashMap.put(n, j.getString(n));
                }

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return hashMap;
    }

    public static Map<String, String> jsonArrayToMap(JSONArray jsonArray) {

        Map<String, String> hashMap = null;

        try {

            hashMap = new HashMap<String, String>();

            for ( int i = 0; i < jsonArray.length(); i++ ) {

                JSONObject j = jsonArray.optJSONObject(i);
                Iterator it = j.keys();

                while (it.hasNext()) {
                    String n = String.valueOf(it.next());
                    hashMap.put(n, j.getString(n));
                }

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return hashMap;
    }
}