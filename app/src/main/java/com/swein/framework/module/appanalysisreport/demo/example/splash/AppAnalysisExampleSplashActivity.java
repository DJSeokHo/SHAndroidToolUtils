package com.swein.framework.module.appanalysisreport.demo.example.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.impl.ExceptionData;
import com.swein.framework.module.appanalysisreport.data.model.impl.OperationData;
import com.swein.framework.module.appanalysisreport.data.parser.StackTraceParser;
import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
import com.swein.framework.module.appanalysisreport.reporttracker.ReportTracker;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

public class AppAnalysisExampleSplashActivity extends AppCompatActivity {

    private final static String TAG = "AppAnalysisExampleSplashActivity";

    private String operationRelateID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_splash);

        AppAnalysisData appAnalysisData = new OperationData(
                StackTraceParser.getLocationFromThrowable(new Throwable()),
                ReportProperty.EVENT_GROUP_CHANGE_SCREEN,
                ReportProperty.OPERATION_TYPE.NONE,
                ""
        );

        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

        autoStartSomeMethod();

        ThreadUtil.startUIThread(2000, new Runnable() {
            @Override
            public void run() {

                finish();
            }
        });
    }

    private void autoStartSomeMethod() {

        AppAnalysisData appAnalysisData = new OperationData(
                StackTraceParser.getLocationFromThrowable(new Throwable()),
                ReportProperty.EVENT_GROUP_AUTO_RUN_METHOD,
                ReportProperty.OPERATION_TYPE.NONE,
                ""
        );

        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

        operationRelateID = ((OperationData) appAnalysisData).getUuid();


        ThreadUtil.startThread(new Runnable() {

            @Override
            public void run() {

                try {
                    int i = 5/0;
                    ILog.iLogDebug(TAG, i);
                }
                catch (Throwable throwable) {

                    AppAnalysisData appAnalysisData = new ExceptionData(
                            StackTraceParser.getLocationFromThrowable(throwable),
                            StackTraceParser.getExceptionMessage(throwable),
                            ReportProperty.EVENT_GROUP_API_ERROR,
                            operationRelateID,
                            "check api success or not"
                    );

                    ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisExampleSplashActivity.this, appAnalysisData);

                    ThreadUtil.startUIThread(100, new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showCustomShortToastNormal(AppAnalysisExampleSplashActivity.this, "초기화 오류");
                        }
                    });
                }

            }
        });

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void onBackPressed() {

        return;
    }
}
