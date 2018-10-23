package com.swein.framework.module.appanalysisreport.reporttracker;

import android.content.Context;

import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.dao.AppAnalysisDAO;
import com.swein.framework.module.appanalysisreport.data.model.impl.DeviceUserData;
import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
import com.swein.framework.tools.util.appinfo.AppInfoUtil;
import com.swein.framework.tools.util.device.DeviceInfoUtil;
import com.swein.framework.tools.util.email.EmailUtil;
import com.swein.framework.tools.util.uuid.Installation;

import java.io.File;

public class ReportTracker {

    private ReportTracker() {}

    private static ReportTracker instance = new ReportTracker();

    public static ReportTracker getInstance() {
        return instance;
    }

    public void init(Context context) {

        /*
            do not miss this part when app started
         */
        AppAnalysisReportDBController appAnalysisReportDBController = new AppAnalysisReportDBController(context);
        appAnalysisReportDBController.deleteDBFileToOutsideFolderForTemp();

        AppAnalysisData appAnalysisData = new DeviceUserData(
                Installation.id(context),
                DeviceInfoUtil.getDeviceModel(),
                DeviceInfoUtil.getDeviceOSVersion(),
                AppInfoUtil.getPackageName(context),
                AppInfoUtil.getVersionName(context),
                ""
        );

        ReportTracker.getInstance().saveAppAnalysisIntoDB(context, appAnalysisData);
    }


    public void saveAppAnalysisIntoDB(Context context, AppAnalysisData appAnalysisData) {
        AppAnalysisDAO.getInstance().insertAppAnalysisIntoDB(context, appAnalysisData);
    }

    public void sendAppAnalysisReportByEmail(Context context, boolean anonymous) {

        File file = new AppAnalysisReportDBController(context).copyDBFileToOutsideFolderForTemp();

        String title = ReportProperty.APP_ANALYSIS_REPORT_TITLE;

        String content;

        if(anonymous) {
            content = ReportProperty.APP_ANALYSIS_REPORT_CONTENT;
        }
        else {
            content = ReportProperty.TEST_USER_ID + " " + ReportProperty.APP_ANALYSIS_REPORT_CONTENT;
        }

        EmailUtil.mailToWithFile(context, file, new String[]{ReportProperty.EMAIL_RECEIVER, ReportProperty.EMAIL_RECEIVER},
                title, content);

    }

}
