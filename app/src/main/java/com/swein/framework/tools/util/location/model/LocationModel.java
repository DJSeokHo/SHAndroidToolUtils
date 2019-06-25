package com.swein.framework.tools.util.location.model;

import org.json.JSONException;
import org.json.JSONObject;

public class LocationModel {

    public String provider;

    /* 위도 */
    public double latitude;

    /* 경도 */
    public double longitude;

    /* 수평 정확도 */
    public float accuracy;

    /* 수직 정확도 (API > Android Oreo)*/
//    public float verticalAccuracy;

    public String toJSONString() {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("provider", provider);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            jsonObject.put("accuracy", accuracy);
//            jsonObject.put("verticalAccuracy", verticalAccuracy);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }
}
