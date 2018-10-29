package com.swein.framework.module.appanalysisreport.demo.example.login;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.parser.ReportParser;
import com.swein.framework.module.appanalysisreport.demo.example.home.AppAnalysisExampleHomeActivity;
import com.swein.framework.module.appanalysisreport.demo.example.splash.AppAnalysisExampleSplashActivity;
import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
import com.swein.framework.module.appanalysisreport.reporttracker.Reporter;
import com.swein.framework.tools.util.activity.ActivityUtil;
import com.swein.framework.tools.util.dialog.DialogUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

public class AppAnalysisExampleLoginActivity extends Activity {

    private EditText editTextID;
    private EditText editTextPassword;

    private Button buttonLogin;
    private Button buttonSendExceptionEmail;
    private Button buttonResetDB;

    private String operationRelateID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_example_login);

        Reporter.getInstance().trackOperation(
                ReportParser.getLocationFromThrowable(new Throwable()),
                ReportProperty.EVENT_GROUP_CHANGE_SCREEN,
                ReportProperty.OPERATION_TYPE.NONE,
                ""
        );

        editTextID = findViewById(R.id.editTextID);
        editTextPassword = findViewById(R.id.editTextPassword);

        editTextID.setText("sh");
        editTextPassword.setText("qq");

        buttonSendExceptionEmail = findViewById(R.id.buttonSendExceptionEmail);
        buttonSendExceptionEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogUtil.createNormalDialogWithThreeButton(AppAnalysisExampleLoginActivity.this,
                        "리포트", "개인 정보 보함해서 같이 보내시겠습니까?", false, "같이 보내기", "취소",  "익명 보내기",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Reporter.getInstance().sendAppAnalysisReportByEmail(AppAnalysisExampleLoginActivity.this, false, ReportProperty.TEST_USER_ID);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Reporter.getInstance().sendAppAnalysisReportByEmail(AppAnalysisExampleLoginActivity.this, true, ReportProperty.TEST_USER_ID);
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

                Reporter.getInstance().trackOperation(
                        ReportParser.getLocationFromThrowable(new Throwable()),
                        ReportProperty.EVENT_GROUP_LOGIN,
                        ReportProperty.OPERATION_TYPE.C,
                        "click buttonLogin to login"
                );

                if(!checkInput()) {
                    ToastUtil.showCustomShortToastNormal(AppAnalysisExampleLoginActivity.this, "check id or password");
                    return;
                }

                loginWithAPI();

            }
        });


        ActivityUtil.startNewActivityWithoutFinish(this, AppAnalysisExampleSplashActivity.class);

    }

    private boolean checkInput() {

        operationRelateID = Reporter.getInstance().trackOperation(
                ReportParser.getLocationFromThrowable(new Throwable()),
                ReportProperty.EVENT_GROUP_LOGIN,
                ReportProperty.OPERATION_TYPE.NONE,
                "check input id and password"
        );


        if("".equals(editTextID.getText().toString()) || "".equals(editTextPassword.getText().toString()) || "ID".equals(editTextID.getText().toString()) || "Password".equals(editTextPassword.getText().toString())) {

            Reporter.getInstance().trackException(
                    ReportParser.getLocationFromThrowable(new Throwable()),
                    "input id or password was wrong",
                    ReportProperty.EVENT_GROUP_LOGIN,
                    operationRelateID,
                    ""
            );

            return false;
        }

        return true;
    }


    private void loginWithAPI() {

        operationRelateID = Reporter.getInstance().trackOperation(
                ReportParser.getLocationFromThrowable(new Throwable()),
                ReportProperty.EVENT_GROUP_LOGIN,
                ReportProperty.OPERATION_TYPE.NONE,
                "login api started"
        );

        String id = editTextID.getText().toString();

        if(!"sh".equals(id)) {

            Reporter.getInstance().trackException(
                    ReportParser.getLocationFromThrowable(new Throwable()),
                    "id not right",
                    ReportProperty.EVENT_GROUP_LOGIN,
                    operationRelateID,
                    ""
            );
            ToastUtil.showCustomShortToastNormal(AppAnalysisExampleLoginActivity.this, "id not right");
            return;
        }

        ToastUtil.showCustomShortToastNormal(AppAnalysisExampleLoginActivity.this, "login success");

        ThreadUtil.startThread(new Runnable() {

            @Override
            public void run() {

                ThreadUtil.startUIThread(2000, new Runnable() {
                    @Override
                    public void run() {

                        ActivityUtil.startNewActivityWithoutFinish(AppAnalysisExampleLoginActivity.this, AppAnalysisExampleHomeActivity.class);
                    }
                });
            }
        });
    }


}
