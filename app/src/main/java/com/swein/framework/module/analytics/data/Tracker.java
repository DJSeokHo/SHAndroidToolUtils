package com.swein.framework.module.analytics.data;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;


/**
 * Created by seokho on 23/03/2017.
 */

public class Tracker {

    private static com.google.android.gms.analytics.Tracker tracker;
    private static final String PROPERTY_ID = "UA-87415315-2";

    private static GoogleAnalytics analytics;

    synchronized public static com.google.android.gms.analytics.Tracker getTracker(Context context) {

        getAnalytics( context );

        if (tracker == null) {

            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            //            tracker = analytics.newTracker( R.xml.global_tracker );
            tracker = analytics.newTracker( PROPERTY_ID );

            // session timeout 120 seconds (2 minutes)
            tracker.setSessionTimeout( 120 );

            tracker.enableExceptionReporting( true );

            //set 100% sample rate, and make sure that report on site should be the same sample rate
            tracker.setSampleRate( 100.0d );

            //            //anonymize IP if need
            //            tracker.setAnonymizeIp(true);
        }
        return tracker;
    }

    public static GoogleAnalytics getAnalytics(Context context) {

        analytics = GoogleAnalytics.getInstance( context );

//        //Manual mode to send Data to Google Analytics immediately if a phone not with Google Play Service
//        analytics.setLocalDispatchPeriod( 0 );

//        //send Data to Google Analytics every 10 minutes, default is 2 minutes
//        analytics.setLocalDispatchPeriod( 10 );

        return analytics;
    }

    public static GoogleAnalytics getAnalytics(Context context, boolean autoActivityReports) {

        analytics = GoogleAnalytics.getInstance( context );


//        //Manual mode to send Data to Google Analytics immediately if a phone not with Google Play Service
//        analytics.setLocalDispatchPeriod( 0 );
        if(autoActivityReports) {
            //Auto track screen vie
            analytics.enableAutoActivityReports( (Application)context );
        }
        //send Data to Google Analytics every 10 minutes
        //            analytics.setLocalDispatchPeriod( 10 );

        return analytics;
    }

}
