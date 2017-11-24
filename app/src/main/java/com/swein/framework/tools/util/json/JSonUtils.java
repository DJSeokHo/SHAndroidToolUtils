package com.swein.framework.tools.util.json;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by seokho on 27/12/2016.
 */

public class JSonUtils {

    public static JSONObject createJSONObjectWithList(List<String> keyList, List list) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        for(int i = 0; i < keyList.size(); i++) {
            jsonObject.put(keyList.get(i), list.get(i));
        }

        return jsonObject;
    }

    public static JSONObject createJSONObjectWithMap(Map<String, Object> map) {

        return new JSONObject(map);
    }

    public static JSONObject readJSONObjectWithFile(File file) throws IOException, JSONException {

        return new JSONObject(FileUtils.readFileToString(file));
    }

    public static String jsonObjectListToJSonString(List list) {
        return new JSONArray(list).toString();
    }

    public static String jsonArrayToJSonString(JSONArray jsonArray) {
        return jsonArray.toString();
    }

    public static Map<String, String> jsonStringToMap(String string) {

        JSONArray jsonArray;
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

    public static Map<String, String> jsonArrayToMapForJSONFileFromXML(JSONArray jsonArray, String key, String value) {

        Map<String, String> hashMap = null;

        try {
            hashMap = new HashMap<String, String>();
            for(int i = 0; i < jsonArray.length(); i++){
                hashMap.put(jsonArray.getJSONObject(i).getString(key), jsonArray.getJSONObject(i).getString(value));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return hashMap;
    }
}