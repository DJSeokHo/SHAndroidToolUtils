package com.swein.framework.helper.information.app.usage.tracker.behavior.type;

import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.swein.framework.helper.information.app.usage.tracker.behavior.interfaces.SendReportBehavior;
import com.swein.framework.helper.information.app.usage.tracker.report.TrackingReport;
import com.swein.framework.helper.information.app.usage.tracker.type.AppEventTracker;

/**
 * Created by seokho on 24/11/2016.
 */

public class TrackerSendEventReport implements SendReportBehavior {


    @Override
    public void sendReport() {

        //send report
        Log.d("Tracker : ", TrackingReport.getInstance().getScreen()
                + " " + TrackingReport.getInstance().getCategory()
                + " " + TrackingReport.getInstance().getAction()
                + " " + TrackingReport.getInstance().getLabel()
                + " " + TrackingReport.getInstance().getValue());

        AppEventTracker.getInstance().getEventTracker().setScreenName(TrackingReport.getInstance().getScreen());
        AppEventTracker.getInstance().getEventTracker().send(new HitBuilders.EventBuilder()
                .setCategory(TrackingReport.getInstance().getCategory())
                .setAction(TrackingReport.getInstance().getAction())
                .setLabel(TrackingReport.getInstance().getLabel())
                .setValue(TrackingReport.getInstance().getValue())
                .build());
        AppEventTracker.getInstance().getEventTracker().setScreenName(null);
        TrackingReport.getInstance().clear();
    }

}
