package com.swein.framework.template.webview.javascript.nativemethods;

import android.content.Context;
import android.net.Uri;

import com.swein.framework.tools.util.device.DeviceUtil;
import com.swein.framework.tools.util.email.EmailUtil;

/**
 * Created by seokho on 19/01/2018.
 */

public class NativeMethods {

    public interface NativeMethodsDelegate {
        void onCalendarSelectFinished(String date, String btnType);
    }

    public interface NativeMethodsRequestDelegate {
        void onPushStateInitRequested();
    }

    public interface NativeMethodsRequestGPSDelegate {
        void onCheckGPS(String callBack);
    }

    public interface NativeMethodsRequestCameraDelegate {
        void onImageUpload();
    }

    public interface NativeMethodQRCodeScannerDelegate {
        void onResult(String callBack, String param);
    }

    private final static String QUERY_PARAM_JSON = "jsonParam";

    // calendar param key start
    private final static String QUERY_PARAM_DATE_TYPE = "dateType";
    private final static String QUERY_PARAM_BTN_TYPE = "btnType";
    private final static String QUERY_PARAM_DATE = "date";
    private final static String QUERY_PARAM_OTHER_DATE = "otherDate";

    private final static String QUERY_PARAM_DATE_TYPE_FULL = "YYYY-MM-DD";
    private final static String QUERY_PARAM_DATE_TYPE_YEAR_MONTH = "YYYY-MM";
    // calendar param key end

    // push setting
    private final static String CL_ACTION_KEY = "action";
    private final static String CL_BIG_USE_YN_KEY = "useYn";

    // gps callback
    private final static String GPS_CALLBACK = "callback";

    // qr code
    private final static String QR_PARAM = "value";


    public void runCamera(NativeMethodsRequestCameraDelegate nativeMethodsRequestCameraDelegate) {
        nativeMethodsRequestCameraDelegate.onImageUpload();
    }

    public void getCurrentPosition(String url, NativeMethodsRequestGPSDelegate nativeMethodsRequestGPSDelegate) {

        Uri uri = Uri.parse(url);
        String callBackMethod = uri.getQueryParameter(GPS_CALLBACK);

        nativeMethodsRequestGPSDelegate.onCheckGPS(callBackMethod);

    }

    public void tel(Context context, String phone) {
        DeviceUtil.callWithDialog(context, phone);
    }

    public void mailto(Context context, String email) {
        EmailUtil.mailTo(context, email, "", "");
    }

}
