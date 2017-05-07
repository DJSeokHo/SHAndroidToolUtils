package com.swein.framework.tools.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by seokho on 07/05/2017.
 */

public class GsonUtils {

    public static String createGsonStringWithObject(Object object) {
        return new Gson().toJson(object);
    }

    public static String createPrettyGsonStringWithObject(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    /**
     * if value has Date Type
     * @param object
     * @return
     */
    public static String createDateAnalysisGsonStringWithObject(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

}
