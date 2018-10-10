package com.swein.framework.module.appanalysisreport.data.db.operation;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.model.impl.OperationData;

import java.util.ArrayList;
import java.util.List;

public class OperationDBController extends AppAnalysisReportDBController {

    public OperationDBController(Context context) {
        super(context);

    }

    public void copy() {
        copyDBForTest();
    }

    public void insert(OperationData operationData) {
        SQLiteDatabase db = null;
        try {

            db = getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_COL_UUID, operationData.getUuid());
            contentValues.put(TABLE_COL_USER_ID, operationData.getUserID());
            contentValues.put(TABLE_COL_DATE_TIME, operationData.getDateTime());
            contentValues.put(TABLE_COL_CLASS_FILE_NAME, operationData.getClassFileName());
            contentValues.put(TABLE_COL_VIEW_UI_NAME, operationData.getViewUIName());
            contentValues.put(TABLE_COL_OPERATION_TYPE, operationData.getOperationType());
            contentValues.put(TABLE_COL_EVENT_GROUP, operationData.getEventGroup());

            db.insertOrThrow(OPERATION_REPORT_TABLE_NAME, null, contentValues);
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

    public List<OperationData> getData(int offset, int limit) {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + OPERATION_REPORT_TABLE_NAME + " ORDER BY " + TABLE_COL_DATE_TIME + " DESC" + " LIMIT " + limit + " OFFSET " + offset, null);

        ArrayList<OperationData> operationDataArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {

            OperationData operationData = new OperationData.Builder()
                .setUuid(cursor.getString(cursor.getColumnIndex(TABLE_COL_UUID)))
                .setUserID(cursor.getString(cursor.getColumnIndex(TABLE_COL_USER_ID)))
                .setDateTime(cursor.getString(cursor.getColumnIndex(TABLE_COL_DATE_TIME)))
                .setClassFileName(cursor.getString(cursor.getColumnIndex(TABLE_COL_CLASS_FILE_NAME)))
                .setViewUIName(cursor.getString(cursor.getColumnIndex(TABLE_COL_VIEW_UI_NAME)))
                .setOperationType(
                        OperationData.getOperationType(cursor.getString(cursor.getColumnIndex(TABLE_COL_OPERATION_TYPE)))
                        )
                .setEventGroup(cursor.getString(cursor.getColumnIndex(TABLE_COL_EVENT_GROUP)))
                .build();

            operationDataArrayList.add(operationData);
        }
        close();

        return operationDataArrayList;
    }
}
