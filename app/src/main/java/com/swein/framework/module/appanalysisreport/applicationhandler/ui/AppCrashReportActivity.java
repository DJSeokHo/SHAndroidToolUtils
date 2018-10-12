package com.swein.framework.module.appanalysisreport.applicationhandler.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.impl.DeviceUserData;
import com.swein.framework.module.appanalysisreport.demo.howtouse.AppAnalysisReportDemoActivity;
import com.swein.framework.module.appanalysisreport.reporttracker.ReportTracker;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.appinfo.AppInfoUtil;
import com.swein.framework.tools.util.device.DeviceInfoUtil;
import com.swein.framework.tools.util.dialog.DialogUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.framework.tools.util.uuid.Installation;
import com.swein.shandroidtoolutils.MainActivity;
import com.swein.shandroidtoolutils.R;

public class AppCrashReportActivity extends Activity {

    private Button buttonRestart;
    private Button buttonExit;
    private Button buttonSendExceptionEmail;
    private Button buttonSendExceptionApi;

    private Button buttonResetDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_crash_report);

        buttonRestart = findViewById(R.id.buttonRestart);
        buttonExit = findViewById(R.id.buttonExit);
        buttonSendExceptionEmail = findViewById(R.id.buttonSendExceptionEmail);
        buttonSendExceptionApi = findViewById(R.id.buttonSendExceptionApi);

        buttonResetDB = findViewById(R.id.buttonResetDB);
        buttonResetDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppAnalysisReportDBController appAnalysisReportDBController = new AppAnalysisReportDBController(AppCrashReportActivity.this);
                appAnalysisReportDBController.clearDataBase();
            }
        });

        buttonSendExceptionEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppAnalysisData appAnalysisData = new DeviceUserData.Builder()
                        .setDeviceModel(DeviceInfoUtil.getDeviceModel())
                        .setDeviceUUID(Installation.id(AppCrashReportActivity.this))
                        .setOsVersion(DeviceInfoUtil.getDeviceOSVersion())
                        .setAppName(AppInfoUtil.getPackageName(AppCrashReportActivity.this))
                        .setAppVersion(AppInfoUtil.getVersionName(AppCrashReportActivity.this))
                        .setOther("")
                        .build();

                ReportTracker.getInstance().saveAppAnalysisIntoDB(AppCrashReportActivity.this, appAnalysisData);


                DialogUtil.createNormalDialogWithThreeButton(AppCrashReportActivity.this,
                        "리포트", "개인 정보 보함해서 같이 보내시겠습니까?", false, "같이 보내기", "취소",  "익명 보내기",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ReportTracker.getInstance().sendAppAnalysisReportByEmail(AppCrashReportActivity.this, false);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ReportTracker.getInstance().sendAppAnalysisReportByEmail(AppCrashReportActivity.this, true);
                            }
                        });

            }
        });

        buttonSendExceptionApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showCustomShortToastNormal(AppCrashReportActivity.this, "개발중");
            }
        });

        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.startNewActivityWithFinish(AppCrashReportActivity.this, MainActivity.class);
            }
        });

        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
