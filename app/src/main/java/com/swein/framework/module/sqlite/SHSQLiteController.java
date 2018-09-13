package com.swein.framework.module.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.swein.framework.tools.util.debug.log.ILog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SHSQLiteController extends SQLiteOpenHelper {

    private final static String TAG = "SHSQLiteController";

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "RolTech.db";
    private final static String TABLE_NAME = "TB_OTP";
    private final static String OTP_TABLE_COL_UUID = "UUID";
    private final static String OTP_TABLE_COL_NUMBER = "NUMBER";
    private final static String OTP_TABLE_COL_CREATE_DATE = "CREATE_DATE";

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
                .append(OTP_TABLE_COL_UUID).append(" TEXT NOT NULL PRIMARY KEY,")
                .append(OTP_TABLE_COL_NUMBER).append(" TEXT,")
                .append(OTP_TABLE_COL_CREATE_DATE).append(" TEXT")
                .append(')');
        db.execSQL(stringBuilder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }


    public void insert(String uuid, String otpNumber) {
        SQLiteDatabase db = null;
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            db = getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(OTP_TABLE_COL_UUID, uuid);
            contentValues.put(OTP_TABLE_COL_NUMBER, otpNumber);
            contentValues.put(OTP_TABLE_COL_CREATE_DATE, formatter.format(new Date(System.currentTimeMillis())));

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

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + OTP_TABLE_COL_CREATE_DATE + " DESC" + " LIMIT " + limit + " OFFSET " + offset, null);

        ArrayList<DataModel> dataModelArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {
            DataModel dataModel = new DataModel();
            dataModel.uuid = cursor.getString(cursor.getColumnIndex(OTP_TABLE_COL_UUID));
            dataModel.otpNumber = cursor.getString(cursor.getColumnIndex(OTP_TABLE_COL_NUMBER));
            dataModel.date = cursor.getString(cursor.getColumnIndex(OTP_TABLE_COL_CREATE_DATE));
            dataModelArrayList.add(dataModel);
        }

        close();

        return dataModelArrayList;
    }

    public List<DataModel> getAllData() {

        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + OTP_TABLE_COL_CREATE_DATE + " DESC", null);

        ArrayList<DataModel> dataModelArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {
            DataModel dataModel = new DataModel();
            dataModel.uuid = cursor.getString(cursor.getColumnIndex(OTP_TABLE_COL_UUID));
            dataModel.otpNumber = cursor.getString(cursor.getColumnIndex(OTP_TABLE_COL_NUMBER));
            dataModel.date = cursor.getString(cursor.getColumnIndex(OTP_TABLE_COL_CREATE_DATE));
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
        public String otpNumber;
        public String date;

        @Override
        public String toString() {
            return uuid + " " + otpNumber + " " + date;
        }
    }


}
