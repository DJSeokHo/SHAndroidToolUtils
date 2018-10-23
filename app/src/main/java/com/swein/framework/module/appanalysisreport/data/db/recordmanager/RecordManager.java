package com.swein.framework.module.appanalysisreport.data.db.recordmanager;

import android.content.Context;

import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
import com.swein.framework.module.appanalysisreport.data.db.exception.ExceptionDBController;
import com.swein.framework.module.appanalysisreport.data.db.operation.OperationDBController;

public class RecordManager {

    private RecordManager() {}

    private static RecordManager instance = new RecordManager();

    public static RecordManager getInstance() {
        return instance;
    }

    /**
     * run this when app start
     */
    public void checkReportRecord(Context context, ReportProperty.REPORT_RECORD_MANAGE_TYPE reportRecordManageType) {

        switch (reportRecordManageType) {

            case TODAY:
                deleteInDateTimeRange(context, 1);
                break;

            default:

            case ONE_WEEK:
                deleteInDateTimeRange(context, 7);
                break;

            case ONE_MONTH:
                deleteInDateTimeRange(context, 30);
                break;

            case RECORD_MAX_ONE_K:
                deleteInRecordTotalNumberRange(context, 1000);
                break;

            case RECORD_MAX_FIVE_K:
                deleteInRecordTotalNumberRange(context, 5000);
                break;

            case RECORD_MAX_TEN_K:
                deleteInRecordTotalNumberRange(context, 10000);
                break;

            case FOR_TEST:
                deleteInDateTimeRange(context, 1);
                deleteInRecordTotalNumberRange(context, 10000);

                break;
        }
    }

    private void deleteInDateTimeRange(Context context, int day) {
        new ExceptionDBController(context).deleteInDateTimeRange(day);
        new OperationDBController(context).deleteInDateTimeRange(day);
    }

    private void deleteInRecordTotalNumberRange(Context context, int totalNumber) {
        new ExceptionDBController(context).deleteInRecordTotalNumberRange(totalNumber);
        new OperationDBController(context).deleteInRecordTotalNumberRange(totalNumber);
    }

}
