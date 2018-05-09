package com.swein.framework.tools.util.cookie;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.swein.framework.tools.util.debug.log.ILog;

/**
 *
 * Created by seokho on 22/02/2018.
 */

public class CookieUtil {

    /**
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

    /**
     * sync cookie
     * @param context context
     * @param url link
     * @param value value
     */
    public static void syncCookie(Context context, String url, String value) {

        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        cookieManager.setCookie(url, value);

        String cookie  = cookieManager.getCookie(url);
        ILog.iLogDebug("syncCookie", cookie);

        CookieSyncManager.getInstance().sync();
    }

    /**
     * clear cookie from web view
     * @param context
     */
    public static void clearCookie(Context context) {

        // clear cookie
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        }
        else
        {

            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

}
