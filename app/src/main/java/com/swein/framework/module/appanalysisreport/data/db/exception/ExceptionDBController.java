package com.swein.framework.module.appanalysisreport.data.db.exception;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.model.impl.ExceptionData;

import java.util.ArrayList;
import java.util.List;

public class ExceptionDBController extends AppAnalysisReportDBController {

    public ExceptionDBController(Context context) {
        super(context);
    }

    public void copy() {
        copyDBForTest();
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

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + OPERATION_REPORT_TABLE_NAME + " ORDER BY " + TABLE_COL_DATE_TIME + " DESC" + " LIMIT " + limit + " OFFSET " + offset, null);

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
}
