package com.swein.framework.helper.information.app.usage.tracker.type;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.swein.framework.helper.information.app.usage.tracker.behavior.type.TrackerSendCrashReport;
import com.swein.framework.helper.information.app.usage.tracker.type.superclass.AppTracker;
import com.swein.shandroidtoolutils.R;


/**
 * Created by seokho on 24/11/2016.
 */

public class AppCrashTracker extends AppTracker {

    private static AppCrashTracker instance = new AppCrashTracker();
    public static AppCrashTracker getInstance() {
        return instance;
    }

    public void setTracker(Context context) {
        if (null == this.tracker) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            tracker = analytics.newTracker(R.xml.global_tracker);
        }

        //init send report behavior
        sendReportBehavior = new TrackerSendCrashReport();
    }

    public Tracker getCrashTracker() {
        return this.tracker;
    }

}
