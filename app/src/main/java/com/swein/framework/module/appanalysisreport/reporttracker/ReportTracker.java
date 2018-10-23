package com.swein.framework.module.appanalysisreport.reporttracker;

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
import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
import com.swein.framework.tools.util.appinfo.AppInfoUtil;
import com.swein.framework.tools.util.device.DeviceInfoUtil;
import com.swein.framework.tools.util.email.EmailUtil;
import com.swein.framework.tools.util.uuid.Installation;

import java.io.File;

/**
 * how to check report in your SQLite Client
 *
 *
 * SELECT
 * TB_OPERATION_REPORT.*,
 * TB_EXCEPTION_REPORT.DATE_TIME, TB_EXCEPTION_REPORT.LOCATION, TB_EXCEPTION_REPORT.EVENT_GROUP, TB_EXCEPTION_REPORT.MESSAGE, TB_EXCEPTION_REPORT.NOTE
 * FROM
 * TB_OPERATION_REPORT
 * LEFT JOIN TB_EXCEPTION_REPORT
 * ON
 * TB_OPERATION_REPORT.UUID = TB_EXCEPTION_REPORT.OPERATION_RELATE_ID;
 *
 */
public class ReportTracker {

    private ReportTracker() {}

    @SuppressLint("StaticFieldLeak")
    private static ReportTracker instance = new ReportTracker();

    public static ReportTracker getInstance() {
        return instance;
    }

    /**
     * add this method to your application
     * @param context context
     */
    public void init(Context context, ReportProperty.REPORT_RECORD_MANAGE_TYPE recordLimit) {

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

    public String trackOperation(String location, String eventGroup, ReportProperty.OPERATION_TYPE operationType, String note) {
        AppAnalysisData appAnalysisData = new OperationData(
                location, eventGroup, operationType, note
        );
        saveAppAnalysisIntoDB(appAnalysisData);

        return ((OperationData) appAnalysisData).getUuid();
    }

    public void sendAppAnalysisReportByEmail(Context context, boolean anonymous, String userID) {

        File file = new AppAnalysisReportDBController(context).copyDBFileToOutsideFolderForTemp();

        String title = ReportProperty.APP_ANALYSIS_REPORT_TITLE;

        String content;

        if(anonymous) {
            content = ReportProperty.APP_ANALYSIS_REPORT_CONTENT;
        }
        else {
            content = userID + " " + ReportProperty.APP_ANALYSIS_REPORT_CONTENT;
        }

        EmailUtil.mailToWithFile(context, file, new String[]{ReportProperty.EMAIL_RECEIVER, ReportProperty.EMAIL_RECEIVER},
                title, content);

    }

    private void saveAppAnalysisIntoDB(AppAnalysisData appAnalysisData) {
        AppAnalysisDAO.getInstance().insertAppAnalysisIntoDB(appAnalysisData);
    }

}
