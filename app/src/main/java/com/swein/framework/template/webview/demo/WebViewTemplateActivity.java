package com.swein.framework.template.webview.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

import com.swein.framework.template.webview.controller.WebViewController;
import com.swein.shandroidtoolutils.R;

public class WebViewTemplateActivity extends Activity {

    private final static String TAG = "MainActivity";

    private WebViewController webViewController;

    boolean closeFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_templete);

        webViewController = new WebViewController(this, (WebView) findViewById(R.id.webView));

        webViewController.loadUrl("file:///android_asset/test.html");


    }

    @Override
    protected void onPause() {
        webViewController.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        webViewController.resume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown( keyCode, event );
        }


        if(!closeFlag){
            Toast.makeText(this.getApplicationContext(), getString(R.string.click_back_twice_to_get_off), Toast.LENGTH_SHORT).show();
            closeFlag = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    closeFlag = false;
                }
            }, 3000);
        }
        else{
            webViewController.destroy();
            finish();
        }

        return false;

    }

}
