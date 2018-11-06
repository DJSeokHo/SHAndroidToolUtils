package com.swein.framework.module.appanalysisreport.demo.example.home.controller;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.swein.framework.module.appanalysisreport.logger.Logger;
import com.swein.framework.module.appanalysisreport.loggerproperty.LoggerProperty;
import com.swein.framework.module.easyscreenrecord.framework.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtil;

/**
 * Created by seokho on 17/01/2018.
 */

public class WebViewController {

    private final static String TAG = "WebViewController";

    private WebView webView;

    private Context context;

    public WebViewController(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;

        setupWebViewSetting();
        setupWebChromeClient();
        setupWebViewClient();
    }


    private void setupWebViewSetting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }

        /* before KITKAT not support web debug */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
    }

    private void setupWebViewClient() {

        webView.setWebViewClient(new WebViewClient() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                ILog.iLogDebug(TAG, "onReceivedError " + request.getUrl() + " " + error.getErrorCode() + " " + error.getDescription().toString());
                Logger.getInstance().trackException(request.getUrl().toString(), error.getDescription().toString(), LoggerProperty.EVENT_GROUP_WEB_VIEW, "", "");
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                ILog.iLogDebug(TAG, "onReceivedHttpError " + request.getUrl() + " " + errorResponse.getReasonPhrase() + " " + errorResponse.getStatusCode());
                Logger.getInstance().trackException(request.getUrl().toString(), errorResponse.getReasonPhrase(), LoggerProperty.EVENT_GROUP_WEB_VIEW, "", "");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                ILog.iLogDebug(TAG, "onReceivedSslError " + handler.toString() + " " + error.getUrl() + " " + error.toString());
                Logger.getInstance().trackException(error.getUrl(), handler.toString(), LoggerProperty.EVENT_GROUP_WEB_VIEW, "", "");
            }

            /**
             *
             * @param view
             * @param request
             * @return True if the host application wants to leave the current WebView and handle the url itself, otherwise return false.
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return false;
            }

            /**
             *
             * @param view
             * @param url
             * @return True if the host application wants to leave the current WebView and handle the url itself, otherwise return false.
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

        });
    }

    private void setupWebChromeClient() {

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }


            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {

                view.setWebChromeClient(this);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(view);
                resultMsg.sendToTarget();

                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                ILog.iLogDebug(TAG, "onConsoleMessage " + consoleMessage.message() + " - " + consoleMessage.lineNumber() + " - " + consoleMessage.sourceId());
                if(consoleMessage.message().startsWith("Uncaught TypeError")) {
                    Logger.getInstance().trackException(consoleMessage.sourceId() + "\n" + consoleMessage.lineNumber(), consoleMessage.message(), LoggerProperty.EVENT_GROUP_WEB_VIEW, String.valueOf(consoleMessage.lineNumber()), "");
                }
                else {
                    Logger.getInstance().trackOperation(consoleMessage.sourceId() + "\n" + consoleMessage.lineNumber(), LoggerProperty.EVENT_GROUP_WEB_VIEW, LoggerProperty.OPERATION_TYPE.NONE, consoleMessage.message());
                    ILog.iLogDebug(TAG, consoleMessage.message());
                }

                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(context).setTitle("").setMessage(message).setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                }).setCancelable(false).create().show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(context).setTitle("").setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

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

    public void loadUrl(String url) {

        if (webView == null) {
            return;
        }

        webView.loadUrl(url);

        ThreadUtil.startUIThread(300, new Runnable() {
            @Override
            public void run() {
                webView.reload();
            }
        });
    }

}
