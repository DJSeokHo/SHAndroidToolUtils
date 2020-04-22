package com.swein.framework.module.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.swein.framework.tools.util.debug.log.ILog;

import java.util.ArrayList;
import java.util.List;

public class SHSQLiteController extends SQLiteOpenHelper {

    private final static String TAG = "SHSQLiteController";

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "Test.db";
    private final static String TABLE_NAME = "TEST_TABLE";
    private final static String TABLE_COL_UUID = "UUID";
    private final static String TABLE_COL_CONTENT = "CONTENT";
    private final static String TABLE_COL_CREATE_DATE = "CREATE_DATE";

    private Context context;

    public SHSQLiteController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME)
                .append('(')
                .append(TABLE_COL_UUID).append(" TEXT NOT NULL PRIMARY KEY,")
                .append(TABLE_COL_CONTENT).append(" TEXT,")
                .append(TABLE_COL_CREATE_DATE).append(" DATE")
                .append(')');
        db.execSQL(stringBuilder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }


    public void insert(String uuid, String content, String date) {
        SQLiteDatabase db = null;
        try {

            db = getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_COL_UUID, uuid);
            contentValues.put(TABLE_COL_CONTENT, content);
            contentValues.put(TABLE_COL_CREATE_DATE, date);

            db.insertOrThrow(TABLE_NAME, null, contentValues);
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

    public List<DataModel> getData(int offset, int limit) {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + TABLE_COL_CREATE_DATE + " DESC" + " LIMIT " + limit + " OFFSET " + offset, null);

        ArrayList<DataModel> dataModelArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {
            DataModel dataModel = new DataModel();
            dataModel.uuid = cursor.getString(cursor.getColumnIndex(TABLE_COL_UUID));
            dataModel.content = cursor.getString(cursor.getColumnIndex(TABLE_COL_CONTENT));
            dataModel.date = cursor.getString(cursor.getColumnIndex(TABLE_COL_CREATE_DATE));
            dataModelArrayList.add(dataModel);
        }

        close();

        return dataModelArrayList;
    }

    public List<DataModel> getAllData() {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + TABLE_COL_CREATE_DATE + " DESC", null);

        ArrayList<DataModel> dataModelArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {
            DataModel dataModel = new DataModel();
            dataModel.uuid = cursor.getString(cursor.getColumnIndex(TABLE_COL_UUID));
            dataModel.content = cursor.getString(cursor.getColumnIndex(TABLE_COL_CONTENT));
            dataModel.date = cursor.getString(cursor.getColumnIndex(TABLE_COL_CREATE_DATE));
            dataModelArrayList.add(dataModel);
        }

        close();
        return dataModelArrayList;
    }

    public List<DataModel> getTestData(String targetDate) {


        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM TEST_TABLE WHERE strftime('%Y%m%d', TEST_TABLE.CREATE_DATE) = strftime('%Y%m%d', '" + targetDate + "')", null);
//        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM TEST_TABLE WHERE strftime('%Y%m%d', date(TEST_TABLE.CREATE_DATE)) = strftime('%Y%m%d', date(" + targetDate + "))", null);
//        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM TEST_TABLE WHERE TEST_TABLE.CREATE_DATE = '" + targetDate + "'", null);

        ArrayList<DataModel> dataModelArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {
            DataModel dataModel = new DataModel();
            dataModel.uuid = cursor.getString(cursor.getColumnIndex(TABLE_COL_UUID));
            dataModel.content = cursor.getString(cursor.getColumnIndex(TABLE_COL_CONTENT));
            dataModel.date = cursor.getString(cursor.getColumnIndex(TABLE_COL_CREATE_DATE));
            dataModelArrayList.add(dataModel);
        }

        close();
        return dataModelArrayList;
    }

    public void deleteDatabase() {
        ILog.iLogDebug(TAG, "deleteDatabase");
        close();
        context.deleteDatabase(this.getDatabaseName());
    }

    public class DataModel {
        public String uuid;
        public String content;
        public String date;

        @Override
        public String toString() {
            return uuid + " " + content + " " + date;
        }
    }

}
