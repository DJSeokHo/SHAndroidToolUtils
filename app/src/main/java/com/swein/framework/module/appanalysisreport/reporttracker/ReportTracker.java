package com.swein.framework.module.appanalysisreport.reporttracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.dao.AppAnalysisDAO;

import java.io.File;

public class ReportTracker {

    private final static String TAG = "ReportTracker";

    private ReportTracker() {}

    private static ReportTracker instance = new ReportTracker();

    public static ReportTracker getInstance() {
        return instance;
    }


    public void saveAppAnalysisIntoDB(Context context, AppAnalysisData appAnalysisData) {
        AppAnalysisDAO.getInstance().insertAppAnalysisIntoDB(context, appAnalysisData);
    }

    public void sendAppAnalysisReportByEmail(Context context) {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        File file = new AppAnalysisReportDBController(context).copyDBFileToOutsideFolderForTemp();

        if(file == null) {
            return;
        }

        email.setType("application/octet-stream");

        String[] emailReceiver = new String[]{"djseokho@gmail.com", "djseokho@gmail.com"};
        String emailTitle = "AppAnalysisReport";
        String emailContent = "AppAnalysisReport";
        email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReceiver);
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailTitle);
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailContent);
        email.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        context.startActivity(Intent.createChooser(email, "select email app please"));

    }

}
