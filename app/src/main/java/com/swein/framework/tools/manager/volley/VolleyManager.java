package com.swein.framework.tools.manager.volley;

/**
 * Created by seokho on 25/01/2017.
 * //step 1
 * requestQueue = VolleyRequestManager.initRequestQueue();
 *
 * //step 2
 * VolleyRequestManager.volleyStringRequest();
 *
 * //step 3
 * volleyInterface = new VolleyInterface(this, VolleyInterface.listener, VolleyInterface.errorListener) {
 *    @Override
 *    public void onComplete(String result) {
 *        //TO DO
 *    }
 *
 *    @Override
 *    public void onException(VolleyError error) {
 *        //TO DO
 *    }
 * };
 *
 * //step 4
 * VolleyRequestManager.cancelRequestQueueWithTag(requestQueue, "test");
 */

public class VolleyManager {

}

