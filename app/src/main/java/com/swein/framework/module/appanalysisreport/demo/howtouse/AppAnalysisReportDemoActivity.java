package com.swein.framework.module.appanalysisreport.demo.howtouse;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.db.recordmanager.RecordManager;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.impl.DeviceUserData;
import com.swein.framework.module.appanalysisreport.data.model.impl.ExceptionData;
import com.swein.framework.module.appanalysisreport.data.model.impl.OperationData;
import com.swein.framework.module.appanalysisreport.data.parser.StackTraceParser;
import com.swein.framework.module.appanalysisreport.reporttracker.ReportTracker;
import com.swein.framework.tools.util.appinfo.AppInfoUtil;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.device.DeviceInfoUtil;
import com.swein.framework.tools.util.dialog.DialogUtil;
import com.swein.framework.tools.util.uuid.Installation;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppAnalysisReportDemoActivity extends Activity {

    private final static String TAG = "AppAnalysisReportDemoActivity";

    private Button buttonCrash;
    private Button buttonException;

    private Button buttonLongClick;
    private Button buttonClick;

    private Button buttonResetDB;
    private Button buttonSendReport;

    private Spinner spinnerManageDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_analysis_report_demo);

        /*
            do not miss this part when app started
         */
        ReportTracker.getInstance().init(this);


        buttonCrash = findViewById(R.id.buttonCrash);
        buttonCrash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                List l = null;
                l.get(5).toString();
            }
        });

        buttonException = findViewById(R.id.buttonException);
        buttonException.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Throwable throwable = new Throwable();

                AppAnalysisData appAnalysisData = new ExceptionData.Builder()
                        .setUuid(UUID.randomUUID().toString())
                        .setUserID(AAConstants.TEST_USER_ID)
                        .setDateTime(DateUtil.getCurrentDateTimeString())
                        .setClassFileName(StackTraceParser.getClassFileNameFromThrowable(throwable))
                        .setLineNumber(StackTraceParser.getLineNumberFromThrowable(throwable))
                        .setMethodName(StackTraceParser.getMethodNameFromThrowable(throwable))
                        .setExceptionMessage("just a normal exception, maybe wrong response for api request")
                        .setEventGroup(AAConstants.EVENT_GROUP_LOGIN)
                        .build();
                ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisReportDemoActivity.this, appAnalysisData);
            }
        });

        buttonLongClick = findViewById(R.id.buttonLongClick);
        buttonLongClick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AppAnalysisData appAnalysisData = new OperationData.Builder()
                        .setUuid(UUID.randomUUID().toString())
                        .setUserID(AAConstants.TEST_USER_ID)
                        .setClassFileName(AppAnalysisReportDemoActivity.class.getName())
                        .setViewUINameOrMethodName(buttonLongClick.getText().toString())
                        .setDateTime(DateUtil.getCurrentDateTimeString())
                        .setOperationType(AAConstants.OPERATION_TYPE.LC)
                        .setEventGroup(AAConstants.EVENT_GROUP_LOGIN)
                        .build();

                ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisReportDemoActivity.this, appAnalysisData);
                return true;
            }
        });

        buttonClick = findViewById(R.id.buttonClick);
        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppAnalysisData appAnalysisData = new OperationData.Builder()
                        .setUuid(UUID.randomUUID().toString())
                        .setUserID(AAConstants.TEST_USER_ID)
                        .setClassFileName(AppAnalysisReportDemoActivity.class.getName())
                        .setViewUINameOrMethodName(buttonClick.getText().toString())
                        .setDateTime(DateUtil.getCurrentDateTimeString())
                        .setOperationType(AAConstants.OPERATION_TYPE.C)
                        .setEventGroup(AAConstants.EVENT_GROUP_REGISTER)
                        .build();

                ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisReportDemoActivity.this, appAnalysisData);
            }
        });

        buttonResetDB = findViewById(R.id.buttonResetDB);
        buttonResetDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppAnalysisReportDBController appAnalysisReportDBController = new AppAnalysisReportDBController(AppAnalysisReportDemoActivity.this);
                appAnalysisReportDBController.clearDataBase();
            }
        });

        buttonSendReport = findViewById(R.id.buttonSendReport);
        buttonSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogUtil.createNormalDialogWithThreeButton(AppAnalysisReportDemoActivity.this,
                        "리포트", "개인 정보 보함해서 같이 보내시겠습니까?", false, "같이 보내기", "취소",  "익명 보내기",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ReportTracker.getInstance().sendAppAnalysisReportByEmail(AppAnalysisReportDemoActivity.this, false);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ReportTracker.getInstance().sendAppAnalysisReportByEmail(AppAnalysisReportDemoActivity.this, true);
                            }
                        });

            }
        });

        spinnerManageDB = findViewById(R.id.spinnerManageDB);
        List<String> dataList = getData();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.view_holder_spinner_drop_item_light_bg, dataList);
        spinnerManageDB.setAdapter(adapter);
        spinnerManageDB.setSelection(dataList.size() - 1);
        spinnerManageDB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                AAConstants.REPORT_RECORD_MANAGE_TYPE reportRecordManageType;

                switch (position) {

                    default:

                    case 0:
                        reportRecordManageType = AAConstants.REPORT_RECORD_MANAGE_TYPE.TODAY;
                        break;

                    case 1:
                        reportRecordManageType = AAConstants.REPORT_RECORD_MANAGE_TYPE.ONE_WEEK;
                        break;

                    case 2:
                        reportRecordManageType = AAConstants.REPORT_RECORD_MANAGE_TYPE.ONE_MONTH;
                        break;

                    case 3:
                        reportRecordManageType = AAConstants.REPORT_RECORD_MANAGE_TYPE.RECORD_MAX_ONE_K;
                        break;

                    case 4:
                        reportRecordManageType = AAConstants.REPORT_RECORD_MANAGE_TYPE.RECORD_MAX_FIVE_K;
                        break;

                    case 5:
                        reportRecordManageType = AAConstants.REPORT_RECORD_MANAGE_TYPE.RECORD_MAX_TEN_K;
                        break;

                    case 6:
                        reportRecordManageType = AAConstants.REPORT_RECORD_MANAGE_TYPE.FOR_TEST;
                        break;
                }

                RecordManager.getInstance().checkReportRecord(AppAnalysisReportDemoActivity.this, reportRecordManageType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AppAnalysisData appAnalysisData = new DeviceUserData.Builder()
                .setUserID(AAConstants.TEST_USER_ID)
                .setUserEmail(AAConstants.TEST_USER_EMAIL)
                .setDeviceModel(DeviceInfoUtil.getDeviceModel())
                .setDeviceUUID(Installation.id(this))
                .setOsVersion(DeviceInfoUtil.getDeviceOSVersion())
                .setAppName(AppInfoUtil.getPackageName(this))
                .setAppVersion(AppInfoUtil.getVersionName(this))
                .setOther("")
                .build();

        ReportTracker.getInstance().saveAppAnalysisIntoDB(AppAnalysisReportDemoActivity.this, appAnalysisData);

    }

    private List<String> getData() {

        List<String> dataList = new ArrayList<>();
        dataList.add("오늘만 기록 저장");
        dataList.add("1주일 기록 저장");
        dataList.add("1개월 기록 저장");
        dataList.add("최대 1000 개 기록 저장");
        dataList.add("최대 5000 개 기록 저장");
        dataList.add("최대 10000 개 기록 저장");
        dataList.add("test");

        return dataList;
    }

}
