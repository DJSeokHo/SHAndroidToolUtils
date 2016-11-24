package com.swein.framework.helper.information.app.usage.tracker.type.superclass;

import android.content.Context;

import com.google.android.gms.analytics.Tracker;
import com.swein.framework.helper.information.app.usage.tracker.behavior.interfaces.SendReportBehavior;

/**
 * Created by seokho on 24/11/2016.
 */

public abstract class AppTracker {

    //Google Tracker
    protected Tracker tracker;

    protected SendReportBehavior sendReportBehavior;

    public abstract void setTracker(Context context);

    public void doSendReport() {

        //tracker delegate SendReportBehavior to send report
        sendReportBehavior.sendReport();

    }

}
