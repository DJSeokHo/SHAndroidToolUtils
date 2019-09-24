package com.swein.framework.template.webview.controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.swein.framework.module.easyscreenrecord.framework.util.debug.log.ILog;
import com.swein.framework.template.webview.constants.WebConstants;
import com.swein.framework.template.webview.javascript.JSResponder;
import com.swein.framework.tools.util.bitmaps.BitmapUtil;
import com.swein.framework.tools.util.cookie.CookieUtil;
import com.swein.framework.tools.util.endec.EndecUtil;
import com.swein.framework.module.location.SHLocation;
import com.swein.framework.tools.util.storage.files.FileIOUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by seokho on 17/01/2018.
 */

public class WebViewController {

    private final static String TAG = "WebViewController";

    private WebView webView;

    private Context context;

    private ValueCallback<Uri> valueCallbackUri;
    private ValueCallback<Uri[]> valueCallbackUris;

    private static Uri imageUploadUri;
    private File sdcardTempFile;

    private static String locationCallBackMethod = "";

    public WebViewController(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;

        setupWebViewSetting();
        setupWebChromeClient();
        setupWebViewClient();
//        setupWebViewCookie();
    }

    private void setupWebViewCookie() {
        CookieManager.getInstance().setAcceptCookie(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }

        // TODO your domain
        CookieManager.getInstance().setCookie("your domain", "APP_VERSION=" + getVerName(context));
    }

    private String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
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

    /**
     * javascript 호출함수 responder
     *
     * @param url
     * @return
     */
    private boolean loadUrlAndOverride(String url) {

        JSResponder calendarJsResponder = new JSResponder(context, url);

        try {

            return calendarJsResponder.response(new JSResponder.JSResponderDelegate() {

                @Override
                public void onCheckGPS(String callBack) {

                    locationCallBackMethod = callBack;

                    checkGpsEnable();

                }

                @Override
                public void onImageUpload() {
                    dispatchTakePictureIntent();
                }


                @Override
                public void nativeFuncNotExistException(String errorCode, String errorMsg, String nativeFuncName) {
                    runNativeFuncNotExistException(errorCode, errorMsg, nativeFuncName);
                }

            });

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void runNativeFuncNotExistException(String errorCode, String errorMsg, String nativeFuncName) {
        webView.loadUrl(WebConstants.getNativeFuncNotExistExceptionUrl(errorCode, errorMsg, nativeFuncName));
    }

    private void setupWebViewClient() {

        webView.setWebViewClient(new WebViewClient() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                ILog.iLogDebug(TAG, "onReceivedError " + request.getUrl() + " " + error.getErrorCode() + " " + error.getDescription().toString());
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                ILog.iLogDebug(TAG, "onReceivedHttpError " + request.getUrl() + " " + errorResponse.getReasonPhrase() + " " + errorResponse.getStatusCode());
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                ILog.iLogDebug(TAG, "onReceivedSslError " + handler.toString() + " " + error.getUrl() + " " + error.toString());
            }

            /**
             *
             * @param view
             * @param request
             * @return True if the host application wants to leave the current WebView and handle the url itself, otherwise return false.
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return loadUrlAndOverride(request.getUrl().toString());

            }

            /**
             *
             * @param view
             * @param url
             * @return True if the host application wants to leave the current WebView and handle the url itself, otherwise return false.
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return loadUrlAndOverride(url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                CookieUtil.syncCookies(context);

                // TODO you can get your content from cookie from here
                String your_content = CookieUtil.getValueFromCookie(url, "your key");

            }

        });
    }

    /**
     * 파일 선택 액티비티 시작
     *
     * @param url
     * @param fileUri
     */
    private void showFileChooserActivity(String url, Uri fileUri, String acceptType) {

        if (acceptType == null) {

            return;
        }

        if (acceptType.equals("")) {
            acceptType = "image";
        }

        Intent intent = new Intent(Intent.ACTION_PICK);
        Intent chooseIntent = Intent.createChooser(intent, "파일 선택");

        Intent cameraIntent = null;
        String[] acceptTypes = acceptType.split(",");


        if (acceptTypes.length == 1) {
            if (acceptType.indexOf("image") >= 0) {
                if (fileUri == null) {
                    fileUri = FileIOUtil.getOutputMediaFileUri(1);
                    imageUploadUri = fileUri;
                }
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});


                if (context != null) {
                    ((Activity) context).startActivityForResult(chooseIntent, 101);
                }
            }

        }
        else if (acceptTypes.length == 2) {

            if (acceptType.indexOf("image") >= 0) {

                if (fileUri == null) {
                    imageUploadUri = FileIOUtil.getOutputMediaFileUri(1);
                }
                intent.setType("image/*");

                cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUploadUri);
                chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

                if (context != null) {
                    ((Activity) context).startActivityForResult(chooseIntent, 101);
                }
            }
        }
    }

    private void setupWebChromeClient() {

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }

            //+4.t1 4.4.3 4.4.4  4.4.0~4.4.2
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture) {
                valueCallbackUri = uploadFile;
                showFileChooserActivity(null, null, acceptType);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

                valueCallbackUris = filePathCallback;
                String acceptType = fileChooserParams.getAcceptTypes()[0];
                showFileChooserActivity(null, null, acceptType);

                return true;
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

    public void pause() {
        if (webView == null) {
            return;
        }

        webView.onPause();
    }

    public void resume() {
        if (webView == null) {
            return;
        }

        webView.onResume();
    }

    public void destroy() {

        if (webView == null) {
            return;
        }

        webView.onPause();
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);

        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();

        webView.destroy();

        webView = null;
    }

    private void dispatchTakePictureIntent() {

//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                imageUploadUri = FileProvider.getUriForFile(context,
//                        BuildConfig.APPLICATION_ID + ".path",
//                        photoFile);
//
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUploadUri);
//                ((Activity)context).startActivityForResult(takePictureIntent, CLConstants.REQUEST_CODE_IMAGE_CHOOSER_BASE_64);
//            }
//        }

        int systemVersion = Build.VERSION.SDK_INT;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        sdcardTempFile = null;
        try {
            sdcardTempFile = createImageFile();
            Uri uri;
            if (systemVersion < 24) {
                uri = Uri.fromFile(sdcardTempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
            else {
                // 7.0
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, sdcardTempFile.getAbsolutePath());
                uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
            ((Activity)context).startActivityForResult(intent, 103);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);

        return image;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (103 == requestCode && resultCode == Activity.RESULT_OK) {

            BitmapUtil.rotateImage(sdcardTempFile.getPath());
            BitmapUtil.ResizeImages(sdcardTempFile.getPath(), 100);

            String url = WebConstants.requestSetImage(EndecUtil.fileToBase64(sdcardTempFile.getPath()));
            webView.loadUrl(url);

            sdcardTempFile = null;

            return;
        }

        // 취지 정보 요청할 때 일단 GPS 상태 확인 결과
        if (102 == requestCode) {

            getCurrentLocationAndTime();

            return;
        }

        if (imageUploadUri != null) {
            File imageFile = new File(imageUploadUri.getPath());
            if (!imageFile.exists()) {
                imageUploadUri = null;
            }
        }

        if (imageUploadUri != null) {
            File imageFile = new File(imageUploadUri.getPath());

            if (imageFile.exists() && imageFile.length() > 0) {
                requestCode = 101;
            }
            else {
                if (!imageFile.exists()) {
                    if (data != null && data.getData() != null) {
                        String path = data.getData().getPath();
                        if (path.indexOf("image") >= 0) {
                            requestCode = 101;
                        }
                        imageUploadUri = null;
                    }
                }
            }
        }

        if (101 == requestCode) {

            //롤리팝 이전버전 사진
            if (data != null && valueCallbackUri != null) {
                String path = FileIOUtil.getPathFromUri(context, data.getData());
                BitmapUtil.rotateImage(path);
                File file = new File(path);
                Uri fileUri = Uri.fromFile(file);
                valueCallbackUri.onReceiveValue(fileUri);
            }
            //롤리팝 이후버전 사진
            else if (data != null && valueCallbackUris != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Uri uri = Uri.parse(data.getDataString());
                    BitmapUtil.rotateImage(FileIOUtil.getPathFromUri(context, uri));
                    valueCallbackUris.onReceiveValue(new Uri[]{Uri.parse(data.getDataString())});
                }
            }
            //롤리팝 이전버전 사진 미리 파일경로를 정한 경우
            else if (valueCallbackUri != null) {
                if (resultCode == Activity.RESULT_OK && imageUploadUri != null) {
                    BitmapUtil.rotateImage(imageUploadUri.getPath());
                    valueCallbackUri.onReceiveValue(imageUploadUri);
                } else {
                    valueCallbackUri.onReceiveValue(null);
                }
            }
            //롤리팝 이후버전 사진 미리 파일경로를 정한 경우
            else if (valueCallbackUris != null) {
                if (resultCode == Activity.RESULT_OK && imageUploadUri != null) {
                    BitmapUtil.rotateImage(imageUploadUri.getPath());
                    valueCallbackUris.onReceiveValue(new Uri[]{imageUploadUri});
                } else {
                    valueCallbackUris.onReceiveValue(null);
                }
            }

            valueCallbackUri = null;
            valueCallbackUris = null;
            imageUploadUri = null;
        }
    }


    /**
     * GPS 상태확인
     */
    private void checkGpsEnable() {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            final String message = "현재 위치 정보 설정이 잠겨있습니다. 위치 정보 설정창으로 이동하시겠습니까?";

            builder.setMessage(message)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface d, int id) {
                            ((Activity) context).startActivityForResult(new Intent(action), 102);
                            d.dismiss();
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface d, int id) {

                            // 취소할 때....
                            d.cancel();

                        }
                    });
            builder.create().show();
        }
        else {

            getCurrentLocationAndTime();

        }
    }

    private void getCurrentLocationAndTime() {

        if(locationCallBackMethod.equals("") || "chDeliveryDetailViewModal.fnCurrentInfoCallBack".equals(locationCallBackMethod)) {

            return;
        }

        SHLocation shLocation = new SHLocation(context, new SHLocation.SHLocationDelegate() {

            // 위치정보 받기
            @Override
            public void onLocation(double longitude, double latitude, long time) {


            }

        },  false);

        shLocation.requestLocation();

    }

}
