package com.swein.framework.module.appanalysisreport.data.db.exception;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.model.impl.ExceptionData;
import com.swein.framework.tools.util.debug.log.ILog;

import java.util.ArrayList;
import java.util.List;

public class ExceptionDBController extends AppAnalysisReportDBController {

    private final static String TAG = "ExceptionDBController";

    public ExceptionDBController(Context context) {
        super(context);
    }

    public void insert(ExceptionData exceptionData) {
        SQLiteDatabase db = null;
        try {

            db = getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_COL_UUID, exceptionData.getUuid());
            contentValues.put(TABLE_COL_USER_ID, exceptionData.getUserID());
            contentValues.put(TABLE_COL_DATE_TIME, exceptionData.getDateTime());
            contentValues.put(TABLE_COL_CLASS_FILE_NAME, exceptionData.getClassFileName());
            contentValues.put(TABLE_COL_METHOD_NAME, exceptionData.getMethodName());
            contentValues.put(TABLE_COL_LINE_NUMBER, exceptionData.getLineNumber());
            contentValues.put(TABLE_COL_MESSAGE, exceptionData.getExceptionMessage());
            contentValues.put(TABLE_COL_EVENT_GROUP, exceptionData.getEventGroup());

            db.insertOrThrow(EXCEPTION_REPORT_TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
            close();
        }
        finally {
            if (db != null) {
                db.endTransaction();
                close();
            }
        }
    }

    public List<ExceptionData> getData(int offset, int limit) {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + EXCEPTION_REPORT_TABLE_NAME + " ORDER BY " + TABLE_COL_DATE_TIME + " DESC" + " LIMIT " + limit + " OFFSET " + offset, null);

        ArrayList<ExceptionData> exceptionModelArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {

            ExceptionData exceptionData = new ExceptionData.Builder()
                    .setUuid(cursor.getString(cursor.getColumnIndex(TABLE_COL_UUID)))
                    .setUserID(cursor.getString(cursor.getColumnIndex(TABLE_COL_USER_ID)))
                    .setDateTime(cursor.getString(cursor.getColumnIndex(TABLE_COL_DATE_TIME)))
                    .setClassFileName(cursor.getString(cursor.getColumnIndex(TABLE_COL_CLASS_FILE_NAME)))
                    .setLineNumber(cursor.getString(cursor.getColumnIndex(TABLE_COL_LINE_NUMBER)))
                    .setMethodName(cursor.getString(cursor.getColumnIndex(TABLE_COL_METHOD_NAME)))
                    .setExceptionMessage(cursor.getString(cursor.getColumnIndex(TABLE_COL_MESSAGE)))
                    .setEventGroup(cursor.getString(cursor.getColumnIndex(TABLE_COL_EVENT_GROUP)))
                    .build();

            exceptionModelArrayList.add(exceptionData);
        }
        close();

        return exceptionModelArrayList;
    }


    public void deleteInDateTimeRange(int day) {
        /*
            DELETE FROM TB_EXCEPTION_REPORT
            WHERE TB_EXCEPTION_REPORT.UUID IN (SELECT TB_EXCEPTION_REPORT.UUID FROM TB_EXCEPTION_REPORT
            WHERE strftime('%s','now') - strftime('%s', TB_EXCEPTION_REPORT.DATE_TIME) > (86400 * 7));
         */
        String stringBuilder = "DELETE FROM " + EXCEPTION_REPORT_TABLE_NAME +
                " WHERE " + EXCEPTION_REPORT_TABLE_NAME + "." + TABLE_COL_UUID + " IN " +
                "(" +
                "SELECT " + EXCEPTION_REPORT_TABLE_NAME + "." + TABLE_COL_UUID + " FROM " + EXCEPTION_REPORT_TABLE_NAME +
                " WHERE strftime('%s','now') - strftime('%s', " + EXCEPTION_REPORT_TABLE_NAME + "." + TABLE_COL_DATE_TIME + ") > (" + AAConstants.SECONDS_IN_DAY * day + ")" +
                ");";

        getWritableDatabase().execSQL(stringBuilder);
        close();
    }

    public void deleteInRecordTotalNumberRange(int totalNumber) {
        /*
            DELETE FROM TB_EXCEPTION_REPORT WHERE
            (SELECT COUNT(TB_EXCEPTION_REPORT.UUID) FROM TB_EXCEPTION_REPORT
            ) > 5000 AND TB_EXCEPTION_REPORT.UUID IN
            (SELECT TB_EXCEPTION_REPORT.UUID FROM TB_EXCEPTION_REPORT
            ORDER BY TB_EXCEPTION_REPORT.DATE_TIME DESC LIMIT
            (SELECT COUNT(TB_EXCEPTION_REPORT.UUID) FROM TB_EXCEPTION_REPORT) OFFSET 5000 );
         */
        String stringBuilder = "DELETE FROM " + EXCEPTION_REPORT_TABLE_NAME + " WHERE " +
                "(" +
                    "SELECT COUNT(" + EXCEPTION_REPORT_TABLE_NAME + "." + TABLE_COL_UUID + ") FROM " + EXCEPTION_REPORT_TABLE_NAME +
                ") > " + totalNumber + " AND " + EXCEPTION_REPORT_TABLE_NAME + "." + TABLE_COL_UUID + " IN " +
                "(" +
                    "SELECT " + EXCEPTION_REPORT_TABLE_NAME + "." + TABLE_COL_UUID + " FROM " + EXCEPTION_REPORT_TABLE_NAME +
                    " ORDER BY " + EXCEPTION_REPORT_TABLE_NAME + "." + TABLE_COL_DATE_TIME + " DESC LIMIT " +
                    "(SELECT COUNT(" + EXCEPTION_REPORT_TABLE_NAME + "." + TABLE_COL_UUID + ") FROM " + EXCEPTION_REPORT_TABLE_NAME + ") OFFSET " + totalNumber +
                ");";

        getWritableDatabase().execSQL(stringBuilder);
        close();
    }
}
