package com.swein.framework.module.easyscreenrecord;

import android.app.Application;
import android.content.Intent;

/**
 * Created by seokho on 02/02/2017.
 */

public class EasyScreenRecordApplication extends Application {

    public static Intent screenRecordServiceIntent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
