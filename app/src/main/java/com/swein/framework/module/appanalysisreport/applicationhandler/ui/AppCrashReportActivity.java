package com.swein.framework.module.appanalysisreport.applicationhandler.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.MainActivity;
import com.swein.shandroidtoolutils.R;

public class AppCrashReportActivity extends Activity {

    private Button buttonRestart;
    private Button buttonExit;
    private Button buttonSendExceptionEmail;
    private Button buttonSendExceptionApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_crash_report);

        buttonRestart = findViewById(R.id.buttonRestart);
        buttonExit = findViewById(R.id.buttonExit);
        buttonSendExceptionEmail = findViewById(R.id.buttonSendExceptionEmail);
        buttonSendExceptionApi = findViewById(R.id.buttonSendExceptionApi);

        buttonSendExceptionEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
