package com.swein.framework.module.appanalysisreport.demo.example.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.impl.OperationData;
import com.swein.framework.module.appanalysisreport.reporttracker.ReportTracker;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.R;

import java.util.UUID;

public class AppAnalysisExampleSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_splash);

        AppAnalysisData appAnalysisData = new OperationData.Builder()
                .setUuid(UUID.randomUUID().toString())
                .setUserID(AAConstants.TEST_USER_ID)
                .setClassFileName(this.getClass().getName())
                .setViewUINameOrMethodName("onCreate()")
                .setDateTime(DateUtil.getCurrentDateTimeString())
                .setOperationType(AAConstants.OPERATION_TYPE.NONE)
                .setEventGroup(AAConstants.EVENT_GROUP_CHANGE_SCREEN)
                .build();
        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

        ThreadUtil.startUIThread(2000, new Runnable() {
            @Override
            public void run() {

                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        AppAnalysisData appAnalysisData = new OperationData.Builder()
                .setUuid(UUID.randomUUID().toString())
                .setUserID(AAConstants.TEST_USER_ID)
                .setClassFileName(this.getClass().getName())
                .setViewUINameOrMethodName("onDestroy()")
                .setDateTime(DateUtil.getCurrentDateTimeString())
                .setOperationType(AAConstants.OPERATION_TYPE.NONE)
                .setEventGroup(AAConstants.EVENT_GROUP_CHANGE_SCREEN)
                .build();
        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);
        super.onDestroy();
    }

    public void onBackPressed() {

        return;
    }
}
