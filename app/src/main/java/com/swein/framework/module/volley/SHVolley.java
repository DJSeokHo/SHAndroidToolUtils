package com.swein.framework.module.volley;

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
 */

public class SHVolley {

    public interface SHVolleyDelegate {
        void onResponse(String response);
        void onErrorResponse(VolleyError error);
    }

    private RequestQueue queue;

    public SHVolley(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void requestUrlGet(String url, final SHVolleyDelegate shVolleyDelegate) {

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

        queue.add(stringRequest);
    }

    public void requestUrlPost(String url, final SHVolleyDelegate shVolleyDelegate, @Nullable final HashMap<String, String> hashMap) {


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

                if(hashMap == null) {
                    return new HashMap<>();
                }
                else {
                    return hashMap;
                }

            }
        };

        queue.add(stringRequest);
    }

}
