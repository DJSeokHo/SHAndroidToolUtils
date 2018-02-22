package com.swein.framework.tools.util.cookie;

import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 *
 * Created by seokho on 22/02/2018.
 */

public class CookieUtil {

    /**
     *
     * put this in here:
     * @see {@link WebViewClient#onPageFinished(WebView, String)}
     *
     *
     * @param url from web view url
     * @param key the key of the value what you want
     *
     * @return the value what you want
     */
    public static String getValueFromCookie(String url, String key) {
        final CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(url);

        if (cookie == null || cookie.length() == 0) {
            return "";
        }

        String value;
        String[] items = cookie.split(";");
        for (String item : items) {
            if(item.trim().contains(key)) {

                String[] target = item.trim().split("=");
                if (target != null && target.length > 1) {
                    value = target[1];

                    return value;
                }
            }
        }

        return "";
    }

}
