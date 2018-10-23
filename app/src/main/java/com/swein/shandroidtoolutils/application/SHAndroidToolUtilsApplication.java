package com.swein.shandroidtoolutils.application;

import android.app.Application;

import com.swein.framework.module.appanalysisreport.applicationhandler.CrashExceptionReportHandler;


/**
 *
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
//        CrashExceptionHandler.getInstance().init( getApplicationContext() );

        // app analysis report
        CrashExceptionReportHandler.getInstance().init( getApplicationContext() );


//        try {
//            String[] strings = new String[] {"1", "2"};
//            ILog.iLogDebug( this, strings[5] );
//        }
//        catch ( Exception e ) {
//            e.printStackTrace();
//        }
//
//        try {
//            List list = null;
//            list.get( 5 );
//        }
//        catch ( Exception e ) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            int one = 1;
//            int zero = 0;
//            int result = one / zero;
//            ILog.iLogDebug( this, result );
//        }
//        catch ( Exception e ) {
//            e.printStackTrace();
//        }

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

}
