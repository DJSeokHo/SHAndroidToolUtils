package com.swein.framework.module.googleanalytics.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.debug.log.ILog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.swein.framework.module.googleanalytics.data.Tracker.getTracker;

/**
 * google analytics view tracker, event tracker, exception tracker manager
 * Created by seokho on 23/03/2017.
 */

public class TrackerManager {

    private final static String TAG = "TrackerManager";

    public final static String DATE_FORMAT = "yyyy.MM.dd - HH:mm:ss";

    public final static String APP_NAME = "SWein";

    public final static String CRASH_REPORT = "[ " + APP_NAME + " Crash Report ]\n";

    private static Tracker tracker;

    private static Map< String, String > userInfo;

    /**
     * must init at application that aop can send exception report
     * @param context: context
     */
    public static void initGoogleAnalyticsTracker(Context context) {
        tracker = getTracker( context );
        userInfo = new HashMap<>();
    }


    public static void sendScreenViewReport( Context context ) {

        checkTrackerUsable(context);

        if ( context == null ) {
            context.getApplicationContext();
        }

        setScreenInfo(context);

        //send report
        ILog.iLogDebug( "sendScreenViewReport : ", context.getClass().getName() );

        tracker.send( new HitBuilders.ScreenViewBuilder()
                              .setNewSession()
                              .build() );

        //should init after send
        tracker.setScreenName( "" );
    }

    public static void sendEventReport( Context context, String category, String action, boolean isWithScreen ) {

        checkTrackerUsable(context);

        if ( isWithScreen ) {
            setScreenInfo(context);
        }

        //send report
        ILog.iLogDebug( "sendEventReport : ", category
                + "\n" + action );

        tracker.send( new HitBuilders.EventBuilder()
                              .setCategory( category )
                              .setAction( action )
                              .setNewSession()
                              .build()
        );

        //should init after send
        tracker.setScreenName( null );


    }

    public static void sendEventReport( Context context, String category, String action, String label, long value, boolean isWithScreen ) {

        checkTrackerUsable(context);

        if ( isWithScreen ) {
            setScreenInfo(context);
        }

        //send report
        ILog.iLogDebug( "sendEventReport : ", category
                + "\n" + action
                + "\n" + label
                + "\n" + value );

        tracker.send( new HitBuilders.EventBuilder()
                              .setCategory( category )
                              .setAction( action )
                              .setLabel( label )
                              .setValue( value )
                              .setNewSession()
                              .build() );

        //should init after send
        tracker.setScreenName( null );
    }

    public static void sendExceptionReport( Context context, Throwable exception, boolean isFatal, boolean isWithScreen ) {

        checkTrackerUsable(context);

        if ( isWithScreen ) {
            setScreenInfo(context);
        }

        setPackageInfo(context);
        setDeviceInfo();

        StringBuffer stringBuffer = new StringBuffer();
        collectInfo(stringBuffer);
        setStackTraceInfo(stringBuffer, exception);

        StackTraceElement stackTraceElement = exception.getStackTrace()[0];
        String            location          = "[- " + stackTraceElement.getFileName() + ": Line " + stackTraceElement.getLineNumber() + " -]\n";

        String description = location +
                CRASH_REPORT +
                "[- " + DateUtil.getCurrentDateFromFastDateFormat( DATE_FORMAT ) + " -]\n" +
                "[- " + stringBuffer.toString() + " -]\n" +
                "[- " + exception + " -]\n";

        //send report
        ILog.iLogDebug( TAG, "sendExceptionReport : " + isFatal
                + "\n\n" + description );

        tracker.send( new HitBuilders.ExceptionBuilder()
                              .setFatal( isFatal )
                              .setDescription( description )
                              .setNewSession()
                              .build() );

        //should init after send
        tracker.setScreenName( null );
        userInfo.clear();
    }

    public static void sendTryCatchExceptionReport(String exceptionDescription, String screenName, boolean isFatal, boolean isWithScreen) {

        if(tracker == null) {
            return;
        }

        if(isWithScreen) {
            setScreenInfo(screenName);
        }

        setDeviceInfo();

        StringBuffer stringBuffer = new StringBuffer();
        collectInfo( stringBuffer );

        String description = CRASH_REPORT +
                "[- " + DateUtil.getCurrentDateFromFastDateFormat( DATE_FORMAT ) + " -]\n" +
                "[- " + stringBuffer.toString() + " -]\n" +
                "[- " + exceptionDescription + " -]\n";

        //send report
        ILog.iLogDebug( "sendTryCatchExceptionReport : ", isFatal
                + "\n\n" + description);

        tracker.send(new HitBuilders.ExceptionBuilder()
                             .setFatal( isFatal )
                             .setDescription( description )
                             .build());

        //should init after send
        tracker.setScreenName( null );
        userInfo.clear();
    }


    private static void setPackageInfo(Context context) {
        //add device info
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo    packageInfo    = packageManager.getPackageInfo( context.getPackageName(), PackageManager.GET_ACTIVITIES );

            if ( packageInfo != null ) {
                String versionName = packageInfo.versionName == null ?
                        "null" :
                        packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                userInfo.put( "versionName", versionName );
                userInfo.put( "versionCode", versionCode );
            }

        }
        catch ( PackageManager.NameNotFoundException e ) {
            ILog.iLogError( "setTrackingReport", "an error occured when collect package info " + e );
        }
    }

    private static void checkTrackerUsable(Context context) {
        if ( tracker == null ) {
            tracker = getTracker( context );
        }
    }

    private static void setScreenInfo(Context context) {
        tracker.setScreenName( context.getClass().getName() );
    }

    private static void setScreenInfo(String screenName) {
        tracker.setScreenName( screenName );
    }

    private static void setDeviceInfo() {
        Field[] fields = Build.class.getDeclaredFields();
        for ( Field field : fields ) {
            try {

                field.setAccessible( true );
                userInfo.put( field.getName(), field.get( null ).toString() );
            }
            catch ( Exception e ) {
                ILog.iLogError( "setTrackingReport", "an error occured when collect crash info " + e );
            }
        }
    }

    private static void collectInfo(StringBuffer stringBuffer) {
        for ( Map.Entry< String, String > entry : userInfo.entrySet() ) {
            String key   = entry.getKey();
            String value = entry.getValue();
            stringBuffer.append( "[- " + key + " = " + value + " -]\n" );
        }
    }

    private static void setStackTraceInfo(StringBuffer stringBuffer, Throwable exception) {
        Writer      writer      = new StringWriter();
        PrintWriter printWriter = new PrintWriter( writer );

        exception.printStackTrace( printWriter );
        Throwable cause = exception.getCause();

        while ( cause != null ) {
            cause.printStackTrace( printWriter );
            cause = cause.getCause();
        }

        printWriter.close();
        String result = "\n[ Stack Trace ]\n" + writer.toString();
        stringBuffer.append( result );
    }
}
