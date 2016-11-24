package com.swein.framework.helper.information.app.usage.tracker.type;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.swein.framework.helper.information.app.usage.tracker.behavior.type.TrackerSendEventReport;
import com.swein.framework.helper.information.app.usage.tracker.type.superclass.AppTracker;
import com.swein.shandroidtoolutils.R;


/**
 * Created by seokho on 24/11/2016.
 */

public class AppEventTracker extends AppTracker {

    private static AppEventTracker instance = new AppEventTracker();
    public static AppEventTracker getInstance() {
        return instance;
    }

    public void setTracker(Context context) {
        if (null == this.tracker) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);
            tracker = analytics.newTracker(R.xml.global_tracker);
        }

        //init send report behavior
        sendReportBehavior = new TrackerSendEventReport();
    }

    public Tracker getEventTracker() {
        return this.tracker;
    }

}
