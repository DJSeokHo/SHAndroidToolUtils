package com.swein.framework.module.appanalysisreport.logger;

import android.annotation.SuppressLint;
import android.content.Context;

import com.swein.framework.module.appanalysisreport.applicationhandler.CrashExceptionReportHandler;
import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.impl.DeviceUserData;
import com.swein.framework.module.appanalysisreport.data.model.impl.ExceptionData;
import com.swein.framework.module.appanalysisreport.data.model.impl.OperationData;
import com.swein.framework.module.appanalysisreport.loggerproperty.LoggerProperty;
import com.swein.framework.tools.util.appinfo.AppInfoUtil;
import com.swein.framework.tools.util.device.DeviceInfoUtil;
import com.swein.framework.tools.util.email.EmailUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.uuid.Installation;

import java.io.File;

/**
 * how to check report in your SQLite Client
 *
 *
SELECT
TB_OPERATION_REPORT.*,
TB_EXCEPTION_REPORT.DATE_TIME, TB_EXCEPTION_REPORT.LOCATION, TB_EXCEPTION_REPORT.EVENT_GROUP, TB_EXCEPTION_REPORT.MESSAGE, TB_EXCEPTION_REPORT.NOTE
FROM
TB_OPERATION_REPORT
LEFT JOIN TB_EXCEPTION_REPORT
ON
TB_OPERATION_REPORT.UUID = TB_EXCEPTION_REPORT.OPERATION_RELATE_ID;
 *
 */
public class Logger {

    private AppAnalysisReportDBController appAnalysisReportDBController;
    private Context context;

    private Logger() {}

    @SuppressLint("StaticFieldLeak")
    private static Logger instance = new Logger();

    public static Logger getInstance() {
        return instance;
    }

    /**
     * add this method to your application
     * @param context context
     */
    public void init(Context context, LoggerProperty.REPORT_RECORD_MANAGE_TYPE recordLimit) {
        this.context = context;
        CrashExceptionReportHandler.getInstance().init(context);

        open();

        AppAnalysisReportDBController appAnalysisReportDBController = new AppAnalysisReportDBController(context);
        appAnalysisReportDBController.deleteDBFileToOutsideFolderForTemp();

        saveAppAnalysisIntoDB(new DeviceUserData(
                Installation.id(context),
                DeviceInfoUtil.getDeviceModel(),
                DeviceInfoUtil.getDeviceOSVersion(),
                AppInfoUtil.getPackageName(context),
                AppInfoUtil.getVersionName(context),
                ""
        ));

        checkReportRecord(recordLimit);
    }


    public void trackException(String location, String exceptionMessage, String eventGroup, String operationRelateID, String note) {

        if("".equals(operationRelateID)) {

            saveAppAnalysisIntoDB(new ExceptionData(
                    location, exceptionMessage, eventGroup,appAnalysisReportDBController.getLastOperationUUID(), note
            ));

            return;
        }

        saveAppAnalysisIntoDB(new ExceptionData(
                location, exceptionMessage, eventGroup, operationRelateID, note
        ));
    }

    public String trackOperation(String location, String eventGroup, LoggerProperty.OPERATION_TYPE operationType, String note) {
        AppAnalysisData appAnalysisData = new OperationData(
                location, eventGroup, operationType, note
        );
        saveAppAnalysisIntoDB(appAnalysisData);

        return ((OperationData) appAnalysisData).getUuid();
    }

    public void sendAppAnalysisReportByEmail(Context context, boolean anonymous, String userID) {

        File file = new AppAnalysisReportDBController(context).copyDBFileToOutsideFolderForTemp();

        String title = LoggerProperty.APP_ANALYSIS_REPORT_TITLE;

        String content;

        if(anonymous) {
            content = LoggerProperty.APP_ANALYSIS_REPORT_CONTENT;
        }
        else {
            content = userID + " " + LoggerProperty.APP_ANALYSIS_REPORT_CONTENT;
        }

        EmailUtil.mailToWithFile(context, file, new String[]{LoggerProperty.EMAIL_RECEIVER, LoggerProperty.EMAIL_RECEIVER},
                title, content);

    }

    private void saveAppAnalysisIntoDB(final AppAnalysisData appAnalysisData) {

        if(appAnalysisData instanceof DeviceUserData) {
            ThreadUtil.startThread(new Runnable() {
                @Override
                public void run() {
                    appAnalysisReportDBController.insertDeviceUser((DeviceUserData) appAnalysisData);
                }
            });
        }
        else if(appAnalysisData instanceof OperationData) {
            ThreadUtil.startThread(new Runnable() {
                @Override
                public void run() {
                    appAnalysisReportDBController.insertOperation((OperationData) appAnalysisData);
                }
            });
        }
        else if(appAnalysisData instanceof ExceptionData) {
            ThreadUtil.startThread(new Runnable() {
                @Override
                public void run() {
                    appAnalysisReportDBController.insertException((ExceptionData) appAnalysisData);
                }
            });
        }

    }

    private void open() {

        if(appAnalysisReportDBController == null) {
            appAnalysisReportDBController = new AppAnalysisReportDBController(context);
        }

        appAnalysisReportDBController.openDB();
    }

    /**
     * add this method into finalize method of application
     */
    public void close() {

        if(appAnalysisReportDBController == null) {
            appAnalysisReportDBController = new AppAnalysisReportDBController(context);
        }

        appAnalysisReportDBController.closeDataBase();
        appAnalysisReportDBController = null;
    }

    public void clear() {

        if(appAnalysisReportDBController == null) {
            appAnalysisReportDBController = new AppAnalysisReportDBController(context);
        }

        appAnalysisReportDBController.clearDataBase();

    }

    private void checkReportRecord(LoggerProperty.REPORT_RECORD_MANAGE_TYPE reportRecordManageType) {

        switch (reportRecordManageType) {

            case TODAY:
                deleteInDateTimeRange(1);
                break;

            default:

            case ONE_WEEK:
                deleteInDateTimeRange(7);
                break;

            case ONE_MONTH:
                deleteInDateTimeRange(30);
                break;

            case RECORD_MAX_ONE_K:
                deleteInRecordTotalNumberRange(1000);
                break;

            case RECORD_MAX_FIVE_K:
                deleteInRecordTotalNumberRange(5000);
                break;

            case RECORD_MAX_TEN_K:
                deleteInRecordTotalNumberRange(10000);
                break;

            case FOR_TEST:
                deleteInDateTimeRange(1);
                deleteInRecordTotalNumberRange(10000);

                break;
        }
    }

    private void deleteInDateTimeRange(int day) {
        appAnalysisReportDBController.deleteExceptionInDateTimeRange(day);
        appAnalysisReportDBController.deleteOperationInDateTimeRange(day);
    }

    private void deleteInRecordTotalNumberRange(int totalNumber) {
        appAnalysisReportDBController.deleteExceptionInRecordTotalNumberRange(totalNumber);
        appAnalysisReportDBController.deleteOperationInRecordTotalNumberRange(totalNumber);
    }

}
