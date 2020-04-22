package com.swein.framework.module.sqlite.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.swein.framework.module.sqlite.SHSQLiteController;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SQLiteDemoActivity extends AppCompatActivity {

    private final static String TAG = "SQLiteDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lite_demo);


        SHSQLiteController shsqLiteController = new SHSQLiteController(this);
//        shsqLiteController.deleteDatabase();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");



//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.DAY_OF_MONTH, 22);
//        calendar.set(Calendar.HOUR_OF_DAY, 7);
//        calendar.set(Calendar.MINUTE, 30);
//        calendar.set(Calendar.SECOND, 0);
//
//        ILog.iLogDebug(TAG, calendar.getTime().toString());
//        String date = formatter.format(calendar.getTime());
//        shsqLiteController.insert(UUIDUtil.getUUIDString(), "haha", date);
//
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.DAY_OF_MONTH, 23);
//        calendar.set(Calendar.HOUR_OF_DAY, 7);
//        calendar.set(Calendar.MINUTE, 30);
//        calendar.set(Calendar.SECOND, 0);
//
//        ILog.iLogDebug(TAG, calendar.getTime().toString());
//        date = formatter.format(calendar.getTime());
//        shsqLiteController.insert(UUIDUtil.getUUIDString(), "hehe", date);




        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_MONTH, 23);


        List<SHSQLiteController.DataModel> list = new ArrayList<>(shsqLiteController.getTestData(formatter.format(calendar.getTime())));

        ILog.iLogDebug(TAG, list.size());

        if(list.isEmpty()) {
            return;
        }

        for(SHSQLiteController.DataModel dataModel : list) {
            ILog.iLogDebug(TAG, dataModel.uuid + " " + dataModel.content + " " + dataModel.date);
        }

    }
}
