package com.swein.shandroidtoolutils.application;

import android.app.Application;

import com.swein.framework.helper.information.app.usage.manager.AppTrackerManager;
import com.swein.framework.helper.information.app.usage.tracker.report.TrackingReport;

/**
 * Created by seokho on 15/11/2016.
 */

public class SHAndroidToolUtilsApplication extends Application {

    private final static String TAG = "SHAndroidToolUtilsApplication";

    @Override
    public void onCreate() {

        CrashExceptionHandler.getInstance().init(getApplicationContext());

        TrackingReport.getInstance().setTrackingReport(AppTrackerManager.TRACK_SCREEN + TAG);

        AppTrackerManager.getInstance().createScreenAccessTracker(this);
        AppTrackerManager.getInstance().sendScreenAccessReport();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
