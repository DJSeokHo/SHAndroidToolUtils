package com.swein.framework.module.appanalysisreport.reporttracker;

import android.content.Context;

import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.dao.AppAnalysisDAO;

public class ReportTracker {

    private ReportTracker() {}

    private static ReportTracker instance = new ReportTracker();

    public static ReportTracker getInstance() {
        return instance;
    }


    public void saveAppAnalysisIntoDB(Context context, AppAnalysisData appAnalysisData) {
        AppAnalysisDAO.getInstance().insertAppAnalysisIntoDB(context, appAnalysisData);
    }

}
