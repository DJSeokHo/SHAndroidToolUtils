package com.swein.shandroidtoolutils.application;

import android.app.Application;

import com.swein.framework.module.analytics.handler.CrashExceptionHandler;

import static com.swein.framework.module.analytics.data.Tracker.getAnalytics;

/**
 * Created by seokho on 15/11/2016.
 */

public class SHAndroidToolUtilsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        getAnalytics(getApplicationContext(), true);

        //will auto send crash report when application crashed
        CrashExceptionHandler.getInstance().init( getApplicationContext() );

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
