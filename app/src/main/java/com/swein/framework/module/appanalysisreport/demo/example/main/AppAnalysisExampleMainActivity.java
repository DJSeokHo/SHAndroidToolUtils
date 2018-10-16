package com.swein.framework.module.appanalysisreport.demo.example.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.swein.framework.module.aop.authlogin.AuthLogin;
import com.swein.framework.module.aop.networkcheck.NetworkCheckAnnotation;
import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.impl.OperationData;
import com.swein.framework.module.appanalysisreport.demo.example.home.AppAnalysisExampleHomeActivity;
import com.swein.framework.module.appanalysisreport.reporttracker.ReportTracker;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.R;

import java.util.UUID;

public class AppAnalysisExampleMainActivity extends Activity {

    private final static String TAG = "AppAnalysisExampleMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_main);


        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadUtil.startUIThread(0, new Runnable() {
                    @Override
                    public void run() {

                        checkNetwork();
                    }
                });

            }
        });

        AppAnalysisData appAnalysisData = new OperationData.Builder()
                .setUuid(UUID.randomUUID().toString())
                .setClassFileName(this.getClass().getName())
                .setViewUINameOrMethodName("onCreate()")
                .setDateTime(DateUtil.getCurrentDateTimeString())
                .setOperationType(AAConstants.OPERATION_TYPE.NONE)
                .setEventGroup(AAConstants.EVENT_GROUP_CHANGE_SCREEN)
                .build();
        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

    }

    @NetworkCheckAnnotation
    private void checkNetwork() {
        ILog.iLogDebug(TAG, "checkNetwork");

        authLogin();
    }

//    @AuthLogin(userID = "shd")
    @AuthLogin(userID = "sh")
    private void authLogin() {

        ILog.iLogDebug(TAG, "authLogin");
        ActivityUtil.startNewActivityWithoutFinish(this, AppAnalysisExampleHomeActivity.class);
    }

}
