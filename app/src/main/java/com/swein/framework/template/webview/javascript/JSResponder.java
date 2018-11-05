package com.swein.framework.template.webview.javascript;

import android.content.Context;

import com.swein.framework.template.webview.constants.WebConstants;
import com.swein.framework.template.webview.javascript.nativemethods.NativeMethods;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by seokho on 19/01/2018.
 */

public class JSResponder {

    public interface JSResponderDelegate {

        void onCheckGPS(String callBack);

        void onImageUpload();

        void nativeFuncNotExistException(String errorCode, String errorMsg, String nativeFuncName);

    }

    private final static String JS_NATIVE_CALL_TEL = "tel";
    private final static String TAG_CALL_TEL = "tel";

    private final static String JS_NATIVE_CALL_MAIL = "mailto";
    private final static String TAG_CALL_MAIL = "mailto";

    private final static String JS_NATIVE_CALL_SCHEME = "nativecall";
    private final static String JS_NATIVE_CALL_SCHEME_CAMEL = "nativeCall";

    private final static String JS_NATIVE_CALL_SEPARATOR = "://";
    private final static String TAG_CALL_CHECK_GPS = "getCurrentPosition";
    private final static String TAG_RUN_CAMERA = "runCamera";


    private Context context;
    private String url;

    public JSResponder(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    /**
     * {@link NativeMethods}
     *
     * 전화 걸기
     * @see NativeMethods#tel(Context, String)
     *
     * 메일 발송
     * @see NativeMethods#mailto(Context, String)
     *
     * 위치 정보 요청
     * @see NativeMethods#getCurrentPosition(String, NativeMethods.NativeMethodsRequestGPSDelegate)
     *
     * 사진 찍기
     * @see NativeMethods#runCamera(NativeMethods.NativeMethodsRequestCameraDelegate)
     */
    public boolean response(final JSResponderDelegate jsResponderDelegate) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        if(!url.contains(JS_NATIVE_CALL_SCHEME) && !url.contains(JS_NATIVE_CALL_TEL) && !url.contains(JS_NATIVE_CALL_MAIL) && !url.contains(JS_NATIVE_CALL_SCHEME_CAMEL)) {
            return false;
        }

        String nativeMethod = "";

        if(url.contains(JS_NATIVE_CALL_SCHEME)) {
            if(url.contains(WebConstants.QUESTION_MARK)) {
                nativeMethod = url.substring(0, url.indexOf(WebConstants.QUESTION_MARK)).replace(JS_NATIVE_CALL_SCHEME + JS_NATIVE_CALL_SEPARATOR, "");
            }
            else {
                nativeMethod = url.replace(JS_NATIVE_CALL_SCHEME + JS_NATIVE_CALL_SEPARATOR, "");
            }
        }
        else if(url.contains(JS_NATIVE_CALL_SCHEME_CAMEL)) {
            if(url.contains(WebConstants.QUESTION_MARK)) {
                nativeMethod = url.substring(0, url.indexOf(WebConstants.QUESTION_MARK)).replace(JS_NATIVE_CALL_SCHEME_CAMEL + JS_NATIVE_CALL_SEPARATOR, "");
            }
            else {
                nativeMethod = url.replace(JS_NATIVE_CALL_SCHEME_CAMEL + JS_NATIVE_CALL_SEPARATOR, "");
            }
        }
        else if(url.contains(JS_NATIVE_CALL_TEL)) {
            nativeMethod = url.substring(0, url.indexOf(WebConstants.COLON_SEPARATOR));
        }
        else if(url.contains(JS_NATIVE_CALL_MAIL)) {
            nativeMethod = url.substring(0, url.indexOf(WebConstants.COLON_SEPARATOR));
        }


        switch (nativeMethod) {

            case TAG_CALL_CHECK_GPS: // GPS 요청

                NativeMethods.class.getMethod(nativeMethod, String.class, NativeMethods.NativeMethodsRequestGPSDelegate.class)
                        .invoke(NativeMethods.class.newInstance(), url, new NativeMethods.NativeMethodsRequestGPSDelegate() {

                            @Override
                            public void onCheckGPS(String callBack) {
                                jsResponderDelegate.onCheckGPS(callBack);
                            }
                        });

                break;

            case TAG_CALL_TEL: // 전화 걸기
            case TAG_CALL_MAIL: // 메일 발송

            case TAG_RUN_CAMERA:

                NativeMethods.class.getMethod(nativeMethod, NativeMethods.NativeMethodsRequestCameraDelegate.class)
                        .invoke(NativeMethods.class.newInstance(), new NativeMethods.NativeMethodsRequestCameraDelegate() {
                            @Override
                            public void onImageUpload() {
                                jsResponderDelegate.onImageUpload();
                            }
                        });
                break;

            default:

                jsResponderDelegate.nativeFuncNotExistException("NT_FUNC_NOT_EXIST", "함수가 존재하지 않습니다.", nativeMethod);

                break;

        }

        return true;
    }
}
