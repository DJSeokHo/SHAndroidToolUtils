package com.swein.shandroidtoolutils.application;

import android.app.Application;

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
        //        CrashExceptionHandler.getInstance().init( getApplicationContext() );

//        try {
//            List list = null;
//            list.get( 5 );
//        }
//        catch ( Exception e ) {
//            e.printStackTrace();
//        }



    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
