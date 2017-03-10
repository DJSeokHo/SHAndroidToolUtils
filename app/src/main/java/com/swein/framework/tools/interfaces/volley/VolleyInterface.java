//package com.swein.framework.tools.interfaces.volley;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
///**
// * Created by seokho on 23/01/2017.
// */
//
//public abstract class VolleyInterface {
//
//    private Context context;
//    public static Response.Listener listener;
//    public static Response.ErrorListener errorListener;
//
//    public VolleyInterface(Context context) {
//        this.context = context;
//    }
//
//    public VolleyInterface(Context context, Response.Listener<String> listener, Response.ErrorListener errorListener) {
//        this.context = context;
//        this.listener = listener;
//        this.errorListener = errorListener;
//    }
//
//    public Response.Listener<String> completeListenerString() {
//        listener = new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                onComplete(response);
//            }
//        };
//        return listener;
//    }
//
//    public Response.Listener<JSONObject> completeListenerJSONObject() {
//        listener = new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                onComplete(response);
//            }
//        };
//        return listener;
//    }
//
//    public Response.Listener<JSONArray> completeListenerJSONArray() {
//        listener = new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                onComplete(response);
//            }
//        };
//        return listener;
//    }
//
//    public Response.Listener<Bitmap> completeListenerBitmap() {
//        listener = new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                onComplete(response);
//            }
//        };
//        return listener;
//    }
//
//
//
//    public Response.ErrorListener exceptionListener() {
//        errorListener = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                onException(error);
//            }
//        };
//        return errorListener;
//    }
//
//    public abstract void onComplete(String result);
//    public abstract void onComplete(JSONObject result);
//    public abstract void onComplete(JSONArray result);
//    public abstract void onComplete(Bitmap result);
//
//    public abstract void onException(VolleyError error);
//}
