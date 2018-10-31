package com.swein.framework.module.appanalysisreport.logger;

import android.annotation.SuppressLint;
import android.content.Context;

import com.swein.framework.module.appanalysisreport.applicationhandler.CrashExceptionReportHandler;
import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.db.recordmanager.RecordManager;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.dao.AppAnalysisDAO;
import com.swein.framework.module.appanalysisreport.data.model.impl.DeviceUserData;
import com.swein.framework.module.appanalysisreport.data.model.impl.ExceptionData;
import com.swein.framework.module.appanalysisreport.data.model.impl.OperationData;
import com.swein.framework.module.appanalysisreport.loggerproperty.LoggerProperty;
import com.swein.framework.tools.util.appinfo.AppInfoUtil;
import com.swein.framework.tools.util.device.DeviceInfoUtil;
import com.swein.framework.tools.util.email.EmailUtil;
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

        CrashExceptionReportHandler.getInstance().init(context);

        AppAnalysisDAO.getInstance().init(context);

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

        RecordManager.getInstance().checkReportRecord(context, recordLimit);
    }


    public void trackException(String location, String exceptionMessage, String eventGroup, String operationRelateID, String note) {

        if("".equals(operationRelateID)) {

            saveAppAnalysisIntoDB(new ExceptionData(
                    location, exceptionMessage, eventGroup, AppAnalysisDAO.getInstance().getLastOperationUUID(), note
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

    private void saveAppAnalysisIntoDB(AppAnalysisData appAnalysisData) {
        AppAnalysisDAO.getInstance().insertAppAnalysisIntoDB(appAnalysisData);
    }

}
