package com.swein.autotest.ui.espresso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.swein.shandroidtoolutils.R;

public class EspressoTestWebViewActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "extra_url";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espresso_test_web_view);
        webView = (WebView) findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();
        settings.setDisplayZoomControls(true);
        settings.setEnableSmoothTransition(true);
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }

        });
        String url = getIntent().getStringExtra(EXTRA_URL);
        if (url != null && url.contains("http"))
            webView.loadUrl(url);
    }
}
