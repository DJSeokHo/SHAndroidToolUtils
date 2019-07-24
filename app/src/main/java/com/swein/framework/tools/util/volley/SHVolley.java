package com.swein.framework.tools.util.volley;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seokho on 01/02/2018.
 *
 * After Android 9.0
 * volley can not access http
 *
 * so add this in AndroidManifest.xml between the <application></application>
 *
 * <uses-library android:name="org.apache.http.legacy" android:required="false"/>
 *
 * and add android:usesCleartextTraffic="true" in the <application>
 */
public class SHVolley {

    private final static String TAG = "SHVolley";

    public interface SHVolleyDelegate {
        void onResponse(String response);
        void onErrorResponse(VolleyError error);
    }

    private RequestQueue queue;

    private SHVolley() {}

    private static SHVolley instance = new SHVolley();
    public static SHVolley getInstance() {
        return instance;
    }

    public void requestUrlGet(Context context, String url, final SHVolleyDelegate shVolleyDelegate) {

        if(queue == null) {
            queue = Volley.newRequestQueue(context);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shVolleyDelegate.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        shVolleyDelegate.onErrorResponse(error);
                    }
                }
        );

        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }

    public void requestUrlPost(Context context, String url, final SHVolleyDelegate shVolleyDelegate, @Nullable final HashMap<String, String> hashMap) {

        if(queue == null) {
            queue = Volley.newRequestQueue(context);
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        shVolleyDelegate.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        shVolleyDelegate.onErrorResponse(error);
                    }
                }
        ) {
            @Override protected Map<String, String> getParams() throws AuthFailureError {

                // if need, add this can get correct content response
//                Map<String,String> params = new HashMap<>();
//                params.put("Content-Type","application/json");
//                params.put("Accept","application/json");
//
//                return params;

                if(hashMap == null) {
                    return new HashMap<>();
                }
                else {
                    return hashMap;
                }

            }
        };

        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }

    /**
     * add this when MainActivity finish
     */
    public void close() {
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}
