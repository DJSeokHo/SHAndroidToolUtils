package com.swein.framework.module.appanalysisreport.demo.example.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.impl.ExceptionData;
import com.swein.framework.module.appanalysisreport.data.model.impl.OperationData;
import com.swein.framework.module.appanalysisreport.data.parser.StackTraceParser;
import com.swein.framework.module.appanalysisreport.demo.example.home.AppAnalysisExampleHomeActivity;
import com.swein.framework.module.appanalysisreport.demo.example.splash.AppAnalysisExampleSplashActivity;
import com.swein.framework.module.appanalysisreport.reporttracker.ReportTracker;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.R;

import java.util.UUID;

public class AppAnalysisExampleLoginActivity extends Activity {

    private EditText editTextID;
    private EditText editTextPassword;

    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_login);

        /*
            do not miss this part when app started
         */
        ReportTracker.getInstance().init(this);

        AppAnalysisData appAnalysisData = new OperationData.Builder()
                .setUuid(UUID.randomUUID().toString())
                .setClassFileName(this.getClass().getName())
                .setViewUINameOrMethodName("onCreate()")
                .setDateTime(DateUtil.getCurrentDateTimeString())
                .setOperationType(AAConstants.OPERATION_TYPE.NONE)
                .setEventGroup(AAConstants.EVENT_GROUP_CHANGE_SCREEN)
                .build();
        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

        editTextID = findViewById(R.id.editTextID);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppAnalysisData appAnalysisData = new OperationData.Builder()
                        .setUuid(UUID.randomUUID().toString())
                        .setClassFileName(AppAnalysisExampleLoginActivity.this.getClass().getName())
                        .setViewUINameOrMethodName(buttonLogin.getText().toString())
                        .setDateTime(DateUtil.getCurrentDateTimeString())
                        .setOperationType(AAConstants.OPERATION_TYPE.C)
                        .setEventGroup(AAConstants.EVENT_GROUP_LOGIN)
                        .build();
                ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisExampleLoginActivity.this, appAnalysisData);

                if(!checkInput()) {

                    return;
                }

                ThreadUtil.startUIThread(2000, new Runnable() {
                    @Override
                    public void run() {
                        /*
                            just make a response exception
                         */
                        Throwable throwable = new Throwable();
                        AppAnalysisData appAnalysisData = new ExceptionData.Builder()
                                .setUuid(UUID.randomUUID().toString())
                                .setDateTime(DateUtil.getCurrentDateTimeString())
                                .setClassFileName(StackTraceParser.getClassFileNameFromThrowable(throwable))
                                .setLineNumber(StackTraceParser.getLineNumberFromThrowable(throwable))
                                .setMethodName(StackTraceParser.getMethodNameFromThrowable(throwable))
                                .setExceptionMessage("해당 사용자 이상 입니다.")
                                .setEventGroup(AAConstants.EVENT_GROUP_LOGIN)
                                .build();
                        ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisExampleLoginActivity.this, appAnalysisData);

                        ActivityUtil.startNewActivityWithoutFinish(AppAnalysisExampleLoginActivity.this, AppAnalysisExampleHomeActivity.class);
                    }
                });


            }
        });


        ActivityUtil.startNewActivityWithoutFinish(this, AppAnalysisExampleSplashActivity.class);



    }

    private boolean checkInput() {

        AppAnalysisData appAnalysisData = new OperationData.Builder()
                .setUuid(UUID.randomUUID().toString())
                .setClassFileName(this.getClass().getName())
                .setViewUINameOrMethodName("checkInput()")
                .setDateTime(DateUtil.getCurrentDateTimeString())
                .setOperationType(AAConstants.OPERATION_TYPE.NONE)
                .setEventGroup(AAConstants.EVENT_GROUP_LOGIN)
                .build();
        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

        if("".equals(editTextID.getText()) || "".equals(editTextPassword.getText())) {

            return false;
        }

        return true;
    }
}
