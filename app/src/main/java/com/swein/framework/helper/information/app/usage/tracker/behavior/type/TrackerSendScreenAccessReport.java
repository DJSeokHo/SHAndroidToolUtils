package com.swein.framework.helper.information.app.usage.tracker.behavior.type;

import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.swein.framework.helper.information.app.usage.tracker.behavior.interfaces.SendReportBehavior;
import com.swein.framework.helper.information.app.usage.tracker.report.TrackingReport;
import com.swein.framework.helper.information.app.usage.tracker.type.AppScreenAccessTracker;

/**
 * Created by seokho on 24/11/2016.
 */

public class TrackerSendScreenAccessReport implements SendReportBehavior {

    @Override
    public void sendReport() {

        //send report
        Log.d("Tracker : ", TrackingReport.getInstance().getScreen());
        AppScreenAccessTracker.getInstance().getScreenTracker().setScreenName(TrackingReport.getInstance().getScreen());
        AppScreenAccessTracker.getInstance().getScreenTracker().send(new HitBuilders.ScreenViewBuilder().build());
        AppScreenAccessTracker.getInstance().getScreenTracker().setScreenName(null);
        TrackingReport.getInstance().clear();

    }

}
