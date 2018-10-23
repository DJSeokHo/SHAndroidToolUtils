package com.swein.framework.module.appanalysisreport.demo.example.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.R;

public class AppAnalysisExampleSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_splash);

//        AppAnalysisData appAnalysisData = new OperationData.Builder()
//                .setUuid(UUID.randomUUID().toString())
//                .setClassFileName(StackTraceParser.getClassFileNameFromThread(Thread.currentThread()))
//                .setViewUINameOrMethodName(StackTraceParser.getMethodNameFromThread(Thread.currentThread()))
//                .setDateTime(DateUtil.getCurrentDateTimeString())
//                .setOperationType(AAConstants.OPERATION_TYPE.NONE)
//                .setEventGroup(AAConstants.EVENT_GROUP_CHANGE_SCREEN)
//                .build();
//        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

        ThreadUtil.startUIThread(2000, new Runnable() {
            @Override
            public void run() {

                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
//        AppAnalysisData appAnalysisData = new OperationData.Builder()
//                .setUuid(UUID.randomUUID().toString())
//                .setClassFileName(StackTraceParser.getClassFileNameFromThread(Thread.currentThread()))
//                .setViewUINameOrMethodName(StackTraceParser.getMethodNameFromThread(Thread.currentThread()))
//                .setDateTime(DateUtil.getCurrentDateTimeString())
//                .setOperationType(AAConstants.OPERATION_TYPE.NONE)
//                .setEventGroup(AAConstants.EVENT_GROUP_CHANGE_SCREEN)
//                .build();
//        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

        super.onDestroy();
    }

    public void onBackPressed() {

        return;
    }
}
