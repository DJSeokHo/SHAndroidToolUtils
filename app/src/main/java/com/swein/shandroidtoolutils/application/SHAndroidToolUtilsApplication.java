package com.swein.shandroidtoolutils.application;

import android.app.Application;

import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
import com.swein.framework.module.appanalysisreport.reporttracker.Reporter;


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


        // app analysis report
        Reporter.getInstance().init(getApplicationContext(), ReportProperty.REPORT_RECORD_MANAGE_TYPE.FOR_TEST);

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
