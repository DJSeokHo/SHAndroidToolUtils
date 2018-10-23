package com.swein.framework.module.appanalysisreport.data.db;

import android.content.Context;

import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
import com.swein.framework.tools.util.dbcrypt.SQLCipherHelper;
import com.swein.framework.tools.util.storage.files.FileIOUtil;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.io.File;
import java.io.IOException;

public class AppAnalysisReportDBController extends SQLiteOpenHelper {

    private final static String TAG = "AppAnalysisReportDBController";

    private final static int DB_VERSION = 1;

    protected final static String DB_KEY = "878905o5i4ifi3i33332opjfif93934tif9303033jof3oltkth31";
    /* 사용자&기기 */
    protected final static String DEVICE_USER_TABLE_NAME = "TB_DEVICE_USER";

    protected final static String TABLE_COL_DEVICE_UUID = "DEVICE_UUID";
    protected final static String TABLE_COL_DEVICE_MODEL = "DEVICE_MODEL";
    protected final static String TABLE_COL_OS_VERSION = "OS_VERSION";
    protected final static String TABLE_COL_APP_NAME = "APP_NAME";
    protected final static String TABLE_COL_APP_VERSION = "APP_VERSION";
    protected final static String TABLE_COL_OTHER = "OTHER";
    protected final static String TABLE_COL_NOTE = "NOTE";

    /* 공통 */
    protected final static String TABLE_COL_UUID = "UUID";
    protected final static String TABLE_COL_DATE_TIME = "DATE_TIME";
    protected final static String TABLE_COL_LOCATION = "LOCATION";
    protected final static String TABLE_COL_EVENT_GROUP = "EVENT_GROUP";

    /* 행동 */
    protected final static String OPERATION_REPORT_TABLE_NAME = "TB_OPERATION_REPORT";

    protected final static String TABLE_COL_OPERATION_TYPE = "OPERATION_TYPE";

    /* 오류 */
    protected final static String EXCEPTION_REPORT_TABLE_NAME = "TB_EXCEPTION_REPORT";

    protected final static String TABLE_COL_OPERATION_RELATE_ID = "OPERATION_RELATE_ID";
    protected final static String TABLE_COL_MESSAGE = "MESSAGE";


    public AppAnalysisReportDBController(Context context) {
        super(context, ReportProperty.DB_FILE_TEMP_NAME, null, DB_VERSION);
        net.sqlcipher.database.SQLiteDatabase.loadLibs( context );
        SQLCipherHelper.autoEncryptDB(this, context, ReportProperty.DB_FILE_TEMP_NAME, DB_KEY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createDeviceUserTable(db);
        createOperationReportTable(db);
        createExceptionReportTable(db);
    }

    private void createDeviceUserTable(SQLiteDatabase db) {

        String stringBuilder = "CREATE TABLE IF NOT EXISTS " +
                DEVICE_USER_TABLE_NAME +
                '(' +
                    TABLE_COL_DEVICE_UUID + " TEXT NOT NULL PRIMARY KEY," +
                    TABLE_COL_DEVICE_MODEL + " TEXT," +
                    TABLE_COL_OS_VERSION + " TEXT," +
                    TABLE_COL_APP_NAME + " TEXT," +
                    TABLE_COL_APP_VERSION + " TEXT," +
                    TABLE_COL_OTHER + " TEXT" +
                ')';

        db.execSQL(stringBuilder);
    }

    private void createOperationReportTable(SQLiteDatabase db) {

        String stringBuilder = "CREATE TABLE IF NOT EXISTS " +
                OPERATION_REPORT_TABLE_NAME +
                '(' +
                    TABLE_COL_UUID + " TEXT NOT NULL PRIMARY KEY," +
                    TABLE_COL_DATE_TIME + " TEXT," +
                    TABLE_COL_LOCATION + " TEXT," +
                    TABLE_COL_OPERATION_TYPE + " TEXT," +
                    TABLE_COL_EVENT_GROUP + " TEXT," +
                    TABLE_COL_NOTE + " TEXT" +
                ')';

        db.execSQL(stringBuilder);
    }

    private void createExceptionReportTable(SQLiteDatabase db) {

        String stringBuilder = "CREATE TABLE IF NOT EXISTS " +
                EXCEPTION_REPORT_TABLE_NAME +
                '(' +
                    TABLE_COL_UUID + " TEXT NOT NULL PRIMARY KEY," +
                    TABLE_COL_DATE_TIME + " TEXT," +
                    TABLE_COL_LOCATION + " TEXT," +
                    TABLE_COL_MESSAGE + " TEXT," +
                    TABLE_COL_EVENT_GROUP + " TEXT," +
                    TABLE_COL_OPERATION_RELATE_ID + " TEXT," +
                    TABLE_COL_NOTE + " TEXT" +
                ')';

        db.execSQL(stringBuilder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DEVICE_USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OPERATION_REPORT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXCEPTION_REPORT_TABLE_NAME);

        onCreate(db);
    }

    public void deleteDatabase(Context context) {
        close();
        context.deleteDatabase(ReportProperty.DB_FILE_TEMP_NAME);
    }

    public void clearDataBase() {
        SQLiteDatabase db = getWritableDatabase(DB_KEY);
        db.execSQL("DROP TABLE IF EXISTS " + DEVICE_USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OPERATION_REPORT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXCEPTION_REPORT_TABLE_NAME);

        createDeviceUserTable(db);
        createOperationReportTable(db);
        createExceptionReportTable(db);

        close();
    }

    public void deleteDBFileToOutsideFolderForTemp() {
        File file = new File(ReportProperty.DB_FILE_TEMP_PATH, ReportProperty.DB_FILE_TEMP_NAME);
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
        File folder = new File(ReportProperty.DB_FILE_TEMP_PATH);
        if(!folder.exists()) {
            folder.mkdir();
        }

        File file = new File(ReportProperty.DB_FILE_TEMP_PATH, ReportProperty.DB_FILE_TEMP_NAME);

        if(file.exists()) {
            boolean success = file.delete();
            if(!success) {
                return null;
            }
        }

        try {
            FileIOUtil.copyFileUsingFileChannels(new File(getReadableDatabase(DB_KEY).getPath()), file);
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
