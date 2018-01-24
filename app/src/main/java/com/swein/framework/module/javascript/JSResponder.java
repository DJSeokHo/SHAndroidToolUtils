package com.swein.framework.module.javascript;

import android.content.Context;

import com.swein.framework.module.javascript.nativemethods.NativeMethods;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by seokho on 19/01/2018.
 */

public class JSResponder {

    public interface JSResponderDelegate {
        void onCalendarSelectFinished(String date, String btnType);
    }

    private final static String JS_NATIVE_CALL_SCHEME = "nativecall";
    private final static String JS_NATIVE_CALL_SEPARATOR = "://";
    private final static String TAG_CALL_METHOD = "nativeMethod";


    private Context context;
    private String url;

    public JSResponder(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    /**
     * {@link NativeMethods}
     *
     * @see NativeMethods#nativeMethod(Context, String, NativeMethods.NativeMethodsDelegate) (Context, String, NativeMethods.NativeMethodsDelegate)
     */
    public void response(final JSResponderDelegate jsResponderDelegate) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        String nativeMethod = url.substring(0, url.indexOf("?")).replace(JS_NATIVE_CALL_SCHEME + JS_NATIVE_CALL_SEPARATOR, "");

        switch (nativeMethod) {
            // use calendar
            case TAG_CALL_METHOD:

                NativeMethods.class.getMethod(nativeMethod, Context.class, String.class, NativeMethods.NativeMethodsDelegate.class)
                        .invoke(NativeMethods.class.newInstance(), context, url, new NativeMethods.NativeMethodsDelegate() {
                            @Override
                            public void onCalendarSelectFinished(String date, String btnType) {
                                jsResponderDelegate.onCalendarSelectFinished(date, btnType);
                            }
                        });

                break;
        }
    }
}
