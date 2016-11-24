package com.swein.framework.helper.information.app.usage.tracker.behavior.type;

import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.swein.framework.helper.information.app.usage.tracker.behavior.interfaces.SendReportBehavior;
import com.swein.framework.helper.information.app.usage.tracker.report.TrackingReport;
import com.swein.framework.helper.information.app.usage.tracker.type.AppCrashTracker;

/**
 * Created by seokho on 24/11/2016.
 */

public class TrackerSendCrashReport implements SendReportBehavior {


    @Override
    public void sendReport() {

        //send report
        Log.d("Tracker : ", TrackingReport.getInstance().getScreen()
                + "\n\n" + TrackingReport.getInstance().getIsFatal()
                + "\n\n" + TrackingReport.getInstance().getDescription());

        AppCrashTracker.getInstance().getCrashTracker().setScreenName(TrackingReport.getInstance().getScreen());
        AppCrashTracker.getInstance().getCrashTracker().send(new HitBuilders.ExceptionBuilder()
                .setFatal(TrackingReport.getInstance().getIsFatal())
                .setDescription(TrackingReport.getInstance().getDescription())
                .build());
        AppCrashTracker.getInstance().getCrashTracker().setScreenName(null);
        TrackingReport.getInstance().clear();
    }

}
