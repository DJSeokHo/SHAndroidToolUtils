package com.swein.framework.module.aop.demo.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.swein.framework.module.aop.authlogin.AuthLogin;
import com.swein.framework.module.aop.networkcheck.NetworkCheckAnnotation;
import com.swein.framework.module.aop.runningtime.RunningTime;
import com.swein.framework.module.appanalysisreport.demo.example.home.AppAnalysisExampleHomeActivity;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.R;

public class AOPExampleMainActivity extends AppCompatActivity {

    private final static String TAG = "AOPExampleMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aop_example_main);


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
//
//        AppAnalysisData appAnalysisData = new OperationData.Builder()
//                .setUuid(UUID.randomUUID().toString())
//                .setClassFileName(LoggerParser.getClassFileNameFromThread(Thread.currentThread()))
//                .setViewUINameOrMethodName(LoggerParser.getMethodNameFromThread(Thread.currentThread()))
//                .setDateTime(DateUtil.getCurrentDateTimeString())
//                .setOperationType(LoggerProperty.OPERATION_TYPE.NONE)
//                .setEventGroup(LoggerProperty.EVENT_GROUP_CHANGE_SCREEN)
//                .build();
//        Logger.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

    }


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
    @RunningTime
    @AuthLogin(userID = "shd")
//    @AuthLogin(userID = "sh")
    private void authLogin() {

        ILog.iLogDebug(TAG, "authLogin");
        ActivityUtil.startNewActivityWithoutFinish(this, AppAnalysisExampleHomeActivity.class);
    }

}
