package com.swein.shandroidtoolutils.application;

import android.app.Application;

import com.swein.framework.module.googleanalytics.handler.CrashExceptionHandler;
import com.swein.framework.tools.util.debug.log.ILog;

import java.util.List;

import static com.swein.framework.module.googleanalytics.data.Tracker.getAnalytics;
import static com.swein.framework.module.googleanalytics.manager.TrackerManager.initGoogleAnalyticsTracker;

/**
 * Created by seokho on 15/11/2016.
 */

public class SHAndroidToolUtilsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initGoogleAnalyticsTracker(getApplicationContext());

        getAnalytics(getApplicationContext(), true);
        //will auto send crash report when application crashed
        CrashExceptionHandler.getInstance().init( getApplicationContext() );


        try {
            String[] strings = new String[] {"1", "2"};
            ILog.iLogDebug( this, strings[5] );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        try {
            List list = null;
            list.get( 5 );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }


        try {
            int one = 1;
            int zero = 0;
            int result = one / zero;
            ILog.iLogDebug( this, result );
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
