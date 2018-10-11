package com.swein.framework.module.appanalysisreport.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.tools.util.storage.files.FileIOUtil;

import java.io.File;
import java.io.IOException;

public class AppAnalysisReportDBController extends SQLiteOpenHelper {

    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "AppAnalysisReport.db";

    /* 공통 */
    protected final static String TABLE_COL_UUID = "UUID";
    protected final static String TABLE_COL_USER_ID = "USER_ID";
    protected final static String TABLE_COL_DATE_TIME = "DATE_TIME";
    protected final static String TABLE_COL_CLASS_FILE_NAME = "CLASS_FILE_NAME";
    protected final static String TABLE_COL_EVENT_GROUP = "EVENT_GROUP";

    /* 행동 */
    protected final static String OPERATION_REPORT_TABLE_NAME = "TB_OPERATION_REPORT";

    protected final static String TABLE_COL_VIEW_UI_NAME = "VIEW_UI_NAME";
    protected final static String TABLE_COL_OPERATION_TYPE = "OPERATION_TYPE";

    /* 오류 */
    protected final static String EXCEPTION_REPORT_TABLE_NAME = "TB_EXCEPTION_REPORT";

    protected final static String TABLE_COL_METHOD_NAME = "METHOD_NAME";
    protected final static String TABLE_COL_LINE_NUMBER = "LINE_NUMBER";
    protected final static String TABLE_COL_MESSAGE = "MESSAGE";


    public AppAnalysisReportDBController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createOperationReportTable(db);
        createExceptionReportTable(db);
    }

    private void createOperationReportTable(SQLiteDatabase db) {

        String stringBuilder = "CREATE TABLE IF NOT EXISTS " +
                OPERATION_REPORT_TABLE_NAME +
                '(' +
                TABLE_COL_UUID + " TEXT NOT NULL PRIMARY KEY," +
                TABLE_COL_USER_ID + " TEXT," +
                TABLE_COL_DATE_TIME + " TEXT," +
                TABLE_COL_CLASS_FILE_NAME + " TEXT," +
                TABLE_COL_VIEW_UI_NAME + " TEXT," +
                TABLE_COL_OPERATION_TYPE + " TEXT," +
                TABLE_COL_EVENT_GROUP + " TEXT" +
                ')';
        db.execSQL(stringBuilder);
    }

    private void createExceptionReportTable(SQLiteDatabase db) {

        String stringBuilder = "CREATE TABLE IF NOT EXISTS " +
                EXCEPTION_REPORT_TABLE_NAME +
                '(' +
                TABLE_COL_UUID + " TEXT NOT NULL PRIMARY KEY," +
                TABLE_COL_USER_ID + " TEXT," +
                TABLE_COL_DATE_TIME + " TEXT," +
                TABLE_COL_CLASS_FILE_NAME + " TEXT," +
                TABLE_COL_METHOD_NAME + " TEXT," +
                TABLE_COL_LINE_NUMBER + " TEXT," +
                TABLE_COL_MESSAGE + " TEXT," +
                TABLE_COL_EVENT_GROUP + " TEXT" +
                ')';
        db.execSQL(stringBuilder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OPERATION_REPORT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXCEPTION_REPORT_TABLE_NAME);

        onCreate(db);
    }

    public void deleteDatabase(Context context) {
        close();
        context.deleteDatabase(this.getDatabaseName());
    }

    public void clearDataBase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + OPERATION_REPORT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXCEPTION_REPORT_TABLE_NAME);

        createOperationReportTable(db);
        createExceptionReportTable(db);

        close();
    }

    public void deleteDBFileToOutsideFolderForTemp() {
        File file = new File(AAConstants.DB_FILE_TEMP_PATH, AAConstants.DB_FILE_TEMP_NAME);
        if(file.exists()) {
            file.delete();
        }
    }

    public File copyDBFileToOutsideFolderForTemp() {

        /*
         * ************ DB copy just for debugging. Do not delete this part ************ from here [DB test issue]
         * //check here before release !!!!warning: [this part] only for debugging, commented out code between [this part] in release
         *
         */
        File folder = new File(AAConstants.DB_FILE_TEMP_PATH);
        if(!folder.exists()) {
            folder.mkdir();
        }

        File file = new File(AAConstants.DB_FILE_TEMP_PATH, AAConstants.DB_FILE_TEMP_NAME);

        if(file.exists()) {
            boolean success = file.delete();
            if(!success) {
                return null;
            }
        }

        try {
            FileIOUtil.copyFileUsingFileChannels(new File(getReadableDatabase().getPath()), file);
        }
        catch (IOException e) {
            e.printStackTrace();
            close();
        }
        finally {
            close();
        }

        return file;
    }

}
