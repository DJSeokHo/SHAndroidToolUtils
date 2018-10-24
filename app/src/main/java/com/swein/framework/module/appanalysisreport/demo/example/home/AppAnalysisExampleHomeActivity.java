package com.swein.framework.module.appanalysisreport.demo.example.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.swein.framework.module.appanalysisreport.data.parser.ReportParser;
import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
import com.swein.framework.module.appanalysisreport.reporttracker.Reporter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_home);

        Reporter.getInstance().trackOperation(
                ReportParser.getLocationFromThrowable(new Throwable()),
                ReportProperty.EVENT_GROUP_CHANGE_SCREEN,
                ReportProperty.OPERATION_TYPE.NONE,
                ""
        );

        buttonAPI = findViewById(R.id.buttonAPI);
        buttonCrash = findViewById(R.id.buttonCrash);

        buttonAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operationRelateID = Reporter.getInstance().trackOperation(
                        ReportParser.getLocationFromThrowable(new Throwable()),
                        ReportProperty.EVENT_GROUP_REQUEST_API,
                        ReportProperty.OPERATION_TYPE.C,
                        "request 3 address");

                requestAPI();

            }
        });

        buttonCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Reporter.getInstance().trackOperation(
                        ReportParser.getLocationFromThrowable(new Throwable()),
                        ReportProperty.EVENT_GROUP_REQUEST_API,
                        ReportProperty.OPERATION_TYPE.C,
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

                    SHVolley shVolley = new SHVolley(AppAnalysisExampleHomeActivity.this);
                    shVolley.requestUrlGet(s, new SHVolley.SHVolleyDelegate() {
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
                            Reporter.getInstance().trackException(
                                    ReportParser.getLocationFromThrowable(new Throwable()),
                                    ReportParser.getExceptionMessage(error),
                                    ReportProperty.EVENT_GROUP_REQUEST_API,
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
