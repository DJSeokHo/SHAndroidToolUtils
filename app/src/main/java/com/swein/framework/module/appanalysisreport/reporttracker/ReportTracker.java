package com.swein.framework.module.appanalysisreport.reporttracker;

import android.content.Context;

import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.dao.AppAnalysisDAO;
import com.swein.framework.tools.util.email.EmailUtil;

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

    }


    public void saveAppAnalysisIntoDB(Context context, AppAnalysisData appAnalysisData) {
        AppAnalysisDAO.getInstance().insertAppAnalysisIntoDB(context, appAnalysisData);
    }

    public void sendAppAnalysisReportByEmail(Context context, boolean anonymous) {

        File file = new AppAnalysisReportDBController(context).copyDBFileToOutsideFolderForTemp();

        String title = "AppNameAppAnalysisReport";

        String content;

        if(anonymous) {
            content = "AppNameAppAnalysisReport";
        }
        else {
            content = "user : " + AAConstants.TEST_USER_ID;
        }

        EmailUtil.mailToWithFile(context, file, new String[]{AAConstants.EMAIL_RECEIVER, AAConstants.EMAIL_RECEIVER},
                title, content);

    }

}
