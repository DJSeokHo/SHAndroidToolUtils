package com.swein.framework.module.appanalysisreport.data.db.recordmanager;

import com.swein.framework.module.appanalysisreport.constants.AAConstants;

public class RecordManager {

    private RecordManager() {}

    private static RecordManager instance = new RecordManager();

    public static RecordManager getInstance() {
        return instance;
    }

    /**
     * run this when app start
     */
    public void checkReportRecord() {

        AAConstants.REPORT_RECORD_MANAGE_TYPE reportRecordManageType = AAConstants.REPORT_RECORD_MANAGE_TYPE.FOR_TEST;

        switch (reportRecordManageType) {
            case TODAY:

                break;

            case ONE_WEEK:

                break;

            case ONE_MONTH:

                break;

            case RECORD_MAX_ONE_K:

                break;

            case RECORD_MAX_FIVE_K:

                break;

            case RECORD_TEN_K:

                break;

            case FOR_TEST:

                break;
        }
    }
}
