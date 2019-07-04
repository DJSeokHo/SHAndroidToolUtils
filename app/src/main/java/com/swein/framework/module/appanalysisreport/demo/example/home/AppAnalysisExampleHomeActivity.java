package com.swein.framework.module.appanalysisreport.demo.example.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.swein.framework.module.appanalysisreport.data.parser.LoggerParser;
import com.swein.framework.module.appanalysisreport.demo.example.home.controller.WebViewController;
import com.swein.framework.module.appanalysisreport.logger.Logger;
import com.swein.framework.module.appanalysisreport.loggerproperty.LoggerProperty;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.framework.tools.util.volley.SHVolley;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

public class AppAnalysisExampleHomeActivity extends Activity {

    private Button buttonAPI;
    private Button buttonCrash;

    private String operationRelateID;

    private WebViewController webViewController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_home);

        Logger.getInstance().trackOperation(
                LoggerParser.getLocationFromThrowable(new Throwable()),
                LoggerProperty.EVENT_GROUP_CHANGE_SCREEN,
                LoggerProperty.OPERATION_TYPE.NONE,
                ""
        );

        webViewController = new WebViewController(this, (WebView) findViewById(R.id.webView));
        webViewController.loadUrl("file:///android_asset/test.html");

        buttonAPI = findViewById(R.id.buttonAPI);
        buttonCrash = findViewById(R.id.buttonCrash);

        buttonAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operationRelateID = Logger.getInstance().trackOperation(
                        LoggerParser.getLocationFromThrowable(new Throwable()),
                        LoggerProperty.EVENT_GROUP_REQUEST_API,
                        LoggerProperty.OPERATION_TYPE.C,
                        "request 3 address");

                requestAPI();

            }
        });

        buttonCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Logger.getInstance().trackOperation(
                        LoggerParser.getLocationFromThrowable(new Throwable()),
                        LoggerProperty.EVENT_GROUP_REQUEST_API,
                        LoggerProperty.OPERATION_TYPE.C,
                        "");

                List list = null;
                ILog.iLogDebug(">>>", list.get(5));

            }
        });

    }

    private void requestAPI() {

        final List<String> list = new ArrayList<>();

        list.add("https://www.google.com");
        list.add("https://www.baidu.com");
        list.add("123.com");

        for (final String s : list) {

            ThreadUtil.startThread(new Runnable() {
                @Override
                public void run() {

                    SHVolley.getInstance().requestUrlGet(AppAnalysisExampleHomeActivity.this, s, new SHVolley.SHVolleyDelegate() {
                        @Override
                        public void onResponse(String response) {

                            ThreadUtil.startUIThread(0, new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showCustomShortToastNormal(AppAnalysisExampleHomeActivity.this, "success " + s);
                                }
                            });

                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Logger.getInstance().trackException(
                                    LoggerParser.getLocationFromThrowable(new Throwable()),
                                    LoggerParser.getExceptionMessage(error),
                                    LoggerProperty.EVENT_GROUP_REQUEST_API,
                                    operationRelateID,
                                    list.size() + "  개 api 중 1 개 오류 발생: " + s
                            );
                        }
                    });

                }
            });


        }

    }

}
