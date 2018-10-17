package com.swein.framework.module.appanalysisreport.demo.example.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.swein.framework.module.aop.authlogin.AuthLogin;
import com.swein.framework.module.aop.networkcheck.NetworkCheckAnnotation;
import com.swein.framework.module.aop.runningtime.RunningTime;
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

public class AppAnalysisExampleMainActivity extends AppCompatActivity {

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

    @RunningTime
    @NetworkCheckAnnotation
    private void checkNetwork() {
        ILog.iLogDebug(TAG, "checkNetwork");
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        authLogin();
    }

    @AuthLogin(userID = "shd")
//    @AuthLogin(userID = "sh")
    private void authLogin() {

        ILog.iLogDebug(TAG, "authLogin");
        ActivityUtil.startNewActivityWithoutFinish(this, AppAnalysisExampleHomeActivity.class);
    }

}
