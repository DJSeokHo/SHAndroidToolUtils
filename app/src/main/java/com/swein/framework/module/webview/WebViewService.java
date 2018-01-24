package com.swein.framework.module.webview;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.swein.framework.module.javascript.JSResponder;

import java.lang.ref.WeakReference;

/**
 *
 * Created by seokho on 24/01/2018.
 */

public class WebViewService {

    private WebView webView;
    private View progressBar;

    private WeakReference<Context> contextWeakReference;

    public WebViewService(Context context, WebView webView, View progressBar) {
        this.contextWeakReference = new WeakReference<Context>(context);
        this.webView = webView;
        this.progressBar = progressBar;

        setupWebViewSetting();
        setupWebChromeClient();
        setupWebViewClient();

    }

    private void setupWebViewSetting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);

    }

    private void loadUrlAndOverride(String url) {

        Log.d("url", url);

        JSResponder calendarJsResponder = new JSResponder(contextWeakReference.get(), url);

        try {
            calendarJsResponder.response(new JSResponder.JSResponderDelegate() {
                @Override
                public void onCalendarSelectFinished(String date, String btnType) {
                    webView.loadUrl("load some url and do something");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setupWebViewClient() {

        webView.setWebViewClient(new WebViewClient() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                loadUrlAndOverride(request.getUrl().toString());

                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                loadUrlAndOverride(url);

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

        });
    }

    private void setupWebChromeClient() {

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                new AlertDialog.Builder(contextWeakReference.get()).setTitle("").setMessage(message).setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                }).setCancelable(false).create().show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(contextWeakReference.get()).setTitle("").setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                }).create().show();
                return true;
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, false);
            }

        });
    }

    public void loadUrl() {
        webView.loadUrl("url");
    }

    public void pause() {
        webView.onPause();
    }

    public void resume() {
        webView.onResume();
    }

    public boolean webViewGoBack() {

        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return false;
    }

    public void destroy() {
        if (webView == null) {
            return;
        }

        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);

        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();

        webView = null;
    }

}
