package com.swein.framework.module.javascript.nativemethods;

import android.content.Context;
import android.net.Uri;

import com.swein.framework.tools.util.debug.log.ILog;

/**
 * Created by seokho on 19/01/2018.
 */

public class NativeMethods {

    private final static String TAG = "NativeMethods";

    public interface NativeMethodsDelegate{
        void onCalendarSelectFinished(String date, String btnType);
    }

    /*
        calendar param key
     */
    private final static String QUERY_PARAM_ONE = "one";
    private final static String QUERY_PARAM_TWO = "two";

    /*
        push function param key
     */
    //TODO

    public void nativeMethod(final Context context, String url, final NativeMethodsDelegate nativeMethodsDelegate) {

        Uri uri = Uri.parse(url);

        String one = uri.getQueryParameter(QUERY_PARAM_ONE);
        String two = uri.getQueryParameter(QUERY_PARAM_TWO);

        ILog.iLogDebug(TAG, one);
        ILog.iLogDebug(TAG, two);
    }

}
