package com.swein.shandroidtoolutils.application;

import android.app.Application;

import com.swein.framework.module.appanalysisreport.logger.Logger;
import com.swein.framework.module.appanalysisreport.loggerproperty.LoggerProperty;


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
        Logger.getInstance().init(getApplicationContext(), LoggerProperty.REPORT_RECORD_MANAGE_TYPE.ONE_MONTH);

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
        Logger.getInstance().close();
        super.finalize();
    }
}
