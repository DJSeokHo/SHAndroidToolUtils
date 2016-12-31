package com.swein.data.singleton.request;

/**
 * Created by seokho on 31/12/2016.
 */

public class RequestData {

    private RequestData() {}

    private static RequestData instance = new RequestData();

    public static RequestData getInstance() {
        return instance;
    }

    public final static int ACTIVITY_REQUEST_CODE = 10;
    public final static int ACTIVITY_RESULT_CODE = 0;
}
