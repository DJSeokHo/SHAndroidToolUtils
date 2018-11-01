package com.swein.framework.module.exceptionreport;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.swein.framework.module.exceptionreport.constants.EConstants;
import com.swein.framework.module.exceptionreport.controller.ExceptionReportController;
import com.swein.framework.tools.util.dialog.DialogUtil;
import com.swein.shandroidtoolutils.R;

import java.util.List;

public class ExceptionReportDemoActivity extends Activity {

    private Button buttonMakeNormalException;
    private Button buttonMakeAPIException;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_report_demo);

        buttonMakeNormalException = findViewById(R.id.buttonMakeNormalException);
        buttonMakeAPIException = findViewById(R.id.buttonMakeAPIException);


        buttonMakeNormalException.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                methodMakeNormalException();

            }
        });

        buttonMakeAPIException.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                methodMakeErrorResponse();

            }
        });
    }

    private void methodMakeNormalException() {

        try {
            List list = null;
            list.get(0);
        }
        catch (Exception e) {

            final ExceptionReportController exceptionReportController = new ExceptionReportController(this, EConstants.REPORT_WAY.EMAIL);
            exceptionReportController.setReport(e);

            DialogUtil.createNormalDialogWithTwoButton(ExceptionReportDemoActivity.this, "오류 보고", "오류 정보를 이메일으로 담당자에게 보내시겠습니까?", false, "네", "아니요",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            exceptionReportController.sendReport();

                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

        }

    }

    private void methodMakeErrorResponse() {

        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[0];

        final ExceptionReportController exceptionReportController = new ExceptionReportController(this, EConstants.REPORT_WAY.EMAIL);
        exceptionReportController.setReport(stackTraceElement.getClassName(), String.valueOf(stackTraceElement.getLineNumber()), stackTraceElement.getMethodName(), "SystemOid is wrong");

        DialogUtil.createNormalDialogWithTwoButton(ExceptionReportDemoActivity.this, "오류 보고", "오류 정보를 이메일으로 담당자에게 보내시겠습니까?", false, "네", "아니요",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exceptionReportController.sendReport();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

    }


}
