//package com.swein.framework.tools.manager.volley;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.widget.ImageView;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.ImageLoader;
//import com.android.volley.toolbox.ImageRequest;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.swein.data.cache.volley.BitmapCache;
//import com.swein.framework.tools.interfaces.volley.VolleyInterface;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.Map;
//
///**
// * Created by seokho on 25/01/2017.
// * //step 1
// * requestQueue = VolleyRequestManager.initRequestQueue();
// *
// * //step 2
// * VolleyRequestManager.volleyStringRequest();
// *
// * //step 3
// * volleyInterface = new VolleyInterface(this, VolleyInterface.listener, VolleyInterface.errorListener) {
// *    @Override
// *    public void onComplete(String result) {
// *        //TO DO
// *    }
// *
// *    @Override
// *    public void onException(VolleyError error) {
// *        //TO DO
// *    }
// * };
// *
// * //step 4
// * VolleyRequestManager.cancelRequestQueueWithTag(requestQueue, "test");
// */
//
//public class VolleyManager {
//
//    //Which protocol
//    public final static String PROTOCOL_SCHEME = "http://";
//    public final static String PROTOCOL_SCHEME_SSL = "https://";
//
//
//    //Retry timeout
//    public static final int TIMEOUT_MS = 30000;
//    public static final DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(TIMEOUT_MS, 1,
//            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//
//
//    //Request string content from url [step 3]
//    public static void volleyStringRequest(RequestQueue requestQueue, String url, final int method, final String tag, final Map hashMap, final boolean shouldCache, VolleyInterface volleyInterface) {
//
//        if(null == requestQueue || null == tag) {
//            return;
//        }
//
//        StringRequest stringRequest;
//
//        if(null == hashMap) {
//            stringRequest = new StringRequest(method, url, volleyInterface.completeListenerString(), volleyInterface.exceptionListener());
//        }
//        else {
//            stringRequest = new StringRequest(method, url, volleyInterface.completeListenerString(), volleyInterface.exceptionListener()){
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    return hashMap;
//                }
//            };
//        }
//
//        stringRequest.setShouldCache(shouldCache);
//        stringRequest.setTag(tag);
//
//        try {
//            requestQueue.add(stringRequest);
//            requestQueue.start();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Request JSONObject content from url [step 3]
//    public static void volleyJsonObjectRequest(RequestQueue requestQueue, String url, final int method, final String tag, final JSONObject jsonObject, final boolean shouldCache, VolleyInterface volleyInterface) {
//
//        if(null == requestQueue || null == tag) {
//            return;
//        }
//
//        JsonObjectRequest jsonObjectRequest;
//
//        if(null == jsonObject) {
//            jsonObjectRequest = new JsonObjectRequest(method, url, null, volleyInterface.completeListenerJSONObject(), volleyInterface.exceptionListener());
//        }
//        else {
//            jsonObjectRequest = new JsonObjectRequest(method, url, jsonObject, volleyInterface.completeListenerJSONObject(), volleyInterface.exceptionListener());
//        }
//
//        jsonObjectRequest.setShouldCache(shouldCache);
//        jsonObjectRequest.setTag(tag);
//
//        try {
//            requestQueue.add(jsonObjectRequest);
//            requestQueue.start();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Request JSONArray content from url [step 3]
//    public static void volleyJsonArrayRequest(RequestQueue requestQueue, String url, final int method, final String tag, final JSONArray jsonArray, final boolean shouldCache, VolleyInterface volleyInterface) {
//
//        if(null == requestQueue || null == tag) {
//            return;
//        }
//
//        JsonArrayRequest jsonArrayRequest;
//
//        if(null == jsonArray) {
//            jsonArrayRequest = new JsonArrayRequest(method, url, null, volleyInterface.completeListenerJSONArray(), volleyInterface.exceptionListener());
//        }
//        else {
//            jsonArrayRequest = new JsonArrayRequest(method, url, jsonArray, volleyInterface.completeListenerJSONArray(), volleyInterface.exceptionListener());
//        }
//
//        jsonArrayRequest.setShouldCache(shouldCache);
//        jsonArrayRequest.setTag(tag);
//
//        try {
//            requestQueue.add(jsonArrayRequest);
//            requestQueue.start();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Request Image content from url [step 3] weight & height can set by 0
//    public static void volleyImageRequest(RequestQueue requestQueue, String url, final int weight, final int height, final String tag, ImageView.ScaleType scaleType, Bitmap.Config decodeConfig, final boolean shouldCache, VolleyInterface volleyInterface) {
//
//        if(null == requestQueue || null == tag) {
//            return;
//        }
//
//        ImageRequest imageRequest;
//
//        imageRequest = new ImageRequest(url, volleyInterface.completeListenerBitmap(), weight, height, scaleType, decodeConfig, volleyInterface.exceptionListener());
//
//        imageRequest.setShouldCache(shouldCache);
//        imageRequest.setTag(tag);
//
//        try {
//            requestQueue.add(imageRequest);
//            requestQueue.start();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Request Image content from url by image loader with bitmap cache [step 3]
//    public static void volleyImageLoaderWithBitmapCache(RequestQueue requestQueue, String url, ImageView imageView, int imageViewResourceID, int imageViewErrorResourceID) {
//
//
//        ImageLoader loader = new ImageLoader(requestQueue, new BitmapCache());
//        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, imageViewResourceID, imageViewErrorResourceID);
//
//        loader.get(url, listener);
//
//    }
//
//    //init RequestQueue [step 1]
//    public static RequestQueue initRequestQueue(Context context) {
//        return Volley.newRequestQueue(context);
//    }
//
//    //Cancel RequestQueue by tag [last step]
//    public static void cancelRequestQueueWithTag(RequestQueue requestQueue, String tag) {
//        if(null == requestQueue) {
//            return;
//        }
//        requestQueue.stop();
//        requestQueue.cancelAll(tag);
//    }
//
//    //Method : GET, POST, PUT, DELETE [step 2]
//    public static String getUrlByRequestMethodSSL(int method, String host, String path, String resourceName) {
//
//        String url = null;
//
//        if (Request.Method.GET == method) {
//            url = PROTOCOL_SCHEME_SSL + host + path + resourceName;
//        } else if (Request.Method.POST == method) {
//            url = PROTOCOL_SCHEME_SSL + host + path + resourceName;
//        }
//        return url;
//    }
//
//    //Method : GET, POST, PUT, DELETE [step 2]
//    public static String getUrlByRequestMethod(int method, String host, String path, String resourceName) {
//
//        String url = null;
//
//        if (Request.Method.GET == method) {
//            url = PROTOCOL_SCHEME_SSL + host + path + resourceName;
//        } else if (Request.Method.POST == method) {
//            url = PROTOCOL_SCHEME_SSL + host + path + resourceName;
//        }
//        return url;
//    }
//
//}
//
