package com.swein.framework.module.appanalysisreport.data.db.deviceuser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.swein.framework.module.appanalysisreport.data.db.AppAnalysisReportDBController;
import com.swein.framework.module.appanalysisreport.data.model.impl.DeviceUserData;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DeviceUserDBController extends AppAnalysisReportDBController {

    public DeviceUserDBController(Context context) {
        super(context);
    }

    public void insert(DeviceUserData deviceUserData) {
        SQLiteDatabase db = null;
        try {

            db = getWritableDatabase(DB_KEY);
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_COL_DEVICE_UUID, deviceUserData.getDeviceUUID());
            contentValues.put(TABLE_COL_DEVICE_MODEL, deviceUserData.getDeviceModel());
            contentValues.put(TABLE_COL_OS_VERSION, deviceUserData.getOsVersion());
            contentValues.put(TABLE_COL_APP_NAME, deviceUserData.getAppName());
            contentValues.put(TABLE_COL_APP_VERSION, deviceUserData.getAppVersion());
            contentValues.put(TABLE_COL_OTHER, deviceUserData.getOther());

            db.replace(DEVICE_USER_TABLE_NAME, null, contentValues);
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

    public List<DeviceUserData> getData() {

        Cursor cursor = getReadableDatabase(DB_KEY).rawQuery("SELECT * FROM " + DEVICE_USER_TABLE_NAME, null);

        ArrayList<DeviceUserData> deviceUserDataArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {
            DeviceUserData deviceUserData = new DeviceUserData(
                    cursor.getString(cursor.getColumnIndex(TABLE_COL_DEVICE_UUID)),
                    cursor.getString(cursor.getColumnIndex(TABLE_COL_DEVICE_MODEL)),
                    cursor.getString(cursor.getColumnIndex(TABLE_COL_OS_VERSION)),
                    cursor.getString(cursor.getColumnIndex(TABLE_COL_APP_NAME)),
                    cursor.getString(cursor.getColumnIndex(TABLE_COL_APP_VERSION)),
                    cursor.getString(cursor.getColumnIndex(TABLE_COL_OTHER))
            );

            deviceUserDataArrayList.add(deviceUserData);
        }
        close();

        return deviceUserDataArrayList;
    }

}
