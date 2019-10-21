package com.swein.shandroidtoolutils.application;

import android.app.Application;


/**
 * Created by seokho on 15/11/2016.
 */

public class SHAndroidToolUtilsApplication extends Application {

    //app context
    private static SHAndroidToolUtilsApplication app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

//        initGoogleAnalyticsTracker(getApplicationContext());

//        getAnalytics(getApplicationContext(), true);
        //will auto send crash report when application crashed

        // google
//        CrashExceptionHandler.getInstance().init(getApplicationContext());

    }

    /**
     * @return app context
     */
    public static SHAndroidToolUtilsApplication getApp() {
        return app;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
