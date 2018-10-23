package com.swein.framework.module.appanalysisreport.demo.example.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.db.recordmanager.RecordManager;
import com.swein.framework.module.appanalysisreport.demo.example.home.AppAnalysisExampleHomeActivity;
import com.swein.framework.module.appanalysisreport.demo.example.splash.AppAnalysisExampleSplashActivity;
import com.swein.framework.module.appanalysisreport.reporttracker.ReportTracker;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.dialog.DialogUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.R;

public class AppAnalysisExampleLoginActivity extends Activity {

    private EditText editTextID;
    private EditText editTextPassword;

    private Button buttonLogin;
    private Button buttonSendExceptionEmail;
    private Button buttonResetDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_login);

        /*
            do not miss this part when app started
         */
        ReportTracker.getInstance().init(this);

//        AppAnalysisData appAnalysisData = new OperationData.Builder()
//                .setUuid(UUID.randomUUID().toString())
//                .setClassFileName(this.getClass().getName())
//                .setViewUINameOrMethodName("onCreate()")
//                .setDateTime(DateUtil.getCurrentDateTimeString())
//                .setOperationType(AAConstants.OPERATION_TYPE.NONE)
//                .setEventGroup(AAConstants.EVENT_GROUP_CHANGE_SCREEN)
//                .build();
//        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

        editTextID = findViewById(R.id.editTextID);
        editTextPassword = findViewById(R.id.editTextPassword);


        buttonSendExceptionEmail = findViewById(R.id.buttonSendExceptionEmail);
        buttonSendExceptionEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AppAnalysisData appAnalysisData = new DeviceUserData.Builder()
//                        .setDeviceModel(DeviceInfoUtil.getDeviceModel())
//                        .setDeviceUUID(Installation.id(AppAnalysisExampleLoginActivity.this))
//                        .setOsVersion(DeviceInfoUtil.getDeviceOSVersion())
//                        .setAppName(AppInfoUtil.getPackageName(AppAnalysisExampleLoginActivity.this))
//                        .setAppVersion(AppInfoUtil.getVersionName(AppAnalysisExampleLoginActivity.this))
//                        .setOther("")
//                        .build();
//
//                ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisExampleLoginActivity.this, appAnalysisData);


                DialogUtil.createNormalDialogWithThreeButton(AppAnalysisExampleLoginActivity.this,
                        "리포트", "개인 정보 보함해서 같이 보내시겠습니까?", false, "같이 보내기", "취소",  "익명 보내기",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ReportTracker.getInstance().sendAppAnalysisReportByEmail(AppAnalysisExampleLoginActivity.this, false);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ReportTracker.getInstance().sendAppAnalysisReportByEmail(AppAnalysisExampleLoginActivity.this, true);
                            }
                        });

            }
        });

        buttonResetDB = findViewById(R.id.buttonResetDB);
        buttonResetDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppAnalysisReportDBController appAnalysisReportDBController = new AppAnalysisReportDBController(AppAnalysisExampleLoginActivity.this);
                appAnalysisReportDBController.clearDataBase();

            }
        });



        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AppAnalysisData appAnalysisData = new OperationData.Builder()
//                        .setUuid(UUID.randomUUID().toString())
//                        .setClassFileName(AppAnalysisExampleLoginActivity.this.getClass().getName())
//                        .setViewUINameOrMethodName(buttonLogin.getText().toString())
//                        .setDateTime(DateUtil.getCurrentDateTimeString())
//                        .setOperationType(AAConstants.OPERATION_TYPE.C)
//                        .setEventGroup(AAConstants.EVENT_GROUP_LOGIN)
//                        .build();
//                ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisExampleLoginActivity.this, appAnalysisData);

                if(!checkInput()) {

                    return;
                }

                loginWithAPI();

            }
        });


        ActivityUtil.startNewActivityWithoutFinish(this, AppAnalysisExampleSplashActivity.class);

        RecordManager.getInstance().checkReportRecord(this, AAConstants.REPORT_RECORD_MANAGE_TYPE.FOR_TEST);

    }

    private void loginWithAPI() {

        String id = editTextID.getText().toString();

        if(!"sh".equals(id)) {

//            Throwable throwable = new Throwable();
//            AppAnalysisData appAnalysisData = new ExceptionData.Builder()
//                    .setUuid(UUID.randomUUID().toString())
//                    .setDateTime(DateUtil.getCurrentDateTimeString())
//                    .setClassFileName(StackTraceParser.getClassFileNameFromThrowable(throwable))
//                    .setLineNumber(StackTraceParser.getLineNumberFromThrowable(throwable))
//                    .setMethodName(StackTraceParser.getMethodNameFromThrowable(throwable))
//                    .setExceptionMessage("id not right")
//                    .setEventGroup(AAConstants.EVENT_GROUP_LOGIN)
//                    .build();
//            ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisExampleLoginActivity.this, appAnalysisData);

            return;
        }

        ThreadUtil.startThread(new Runnable() {

            @Override
            public void run() {

                String response = "success";
                        /*
                            just make a response exception
                         */
//                Throwable throwable = new Throwable();
//                AppAnalysisData appAnalysisData = new ExceptionData.Builder()
//                        .setUuid(UUID.randomUUID().toString())
//                        .setDateTime(DateUtil.getCurrentDateTimeString())
//                        .setClassFileName(StackTraceParser.getClassFileNameFromThrowable(throwable))
//                        .setLineNumber(StackTraceParser.getLineNumberFromThrowable(throwable))
//                        .setMethodName(StackTraceParser.getMethodNameFromThrowable(throwable))
//                        .setExceptionMessage(response)
//                        .setEventGroup(AAConstants.EVENT_GROUP_LOGIN)
//                        .build();
//                ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisExampleLoginActivity.this, appAnalysisData);

                ThreadUtil.startUIThread(2000, new Runnable() {
                    @Override
                    public void run() {

                        ActivityUtil.startNewActivityWithoutFinish(AppAnalysisExampleLoginActivity.this, AppAnalysisExampleHomeActivity.class);
                    }
                });
            }
        });
    }

    private boolean checkInput() {

//        AppAnalysisData appAnalysisData = new OperationData.Builder()
//                .setUuid(UUID.randomUUID().toString())
//                .setClassFileName(StackTraceParser.getClassFileNameFromThread(Thread.currentThread()))
//                .setViewUINameOrMethodName(StackTraceParser.getMethodNameFromThread(Thread.currentThread()))
//                .setDateTime(DateUtil.getCurrentDateTimeString())
//                .setOperationType(AAConstants.OPERATION_TYPE.NONE)
//                .setEventGroup(AAConstants.EVENT_GROUP_LOGIN)
//                .build();
//        ReportTracker.getInstance().saveAppAnalysisIntoDB(this, appAnalysisData);

        if("".equals(editTextID.getText()) || "".equals(editTextPassword.getText()) || "ID".equals(editTextID.getText()) || "Password".equals(editTextPassword.getText())) {

            return false;
        }

        return true;
    }
}
