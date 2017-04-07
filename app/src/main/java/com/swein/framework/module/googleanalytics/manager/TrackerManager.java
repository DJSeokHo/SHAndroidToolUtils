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

    private final static String DATE_FORMAT = "yyyy.MM.dd - HH:mm:ss";

    private final static String APP_NAME = "SHAndroidToolUtils";

    private final static String CRASH_REPORT = "[ " + APP_NAME + " Crash Report ]\n";

    private static Tracker tracker;


    /**
     * must init at application that aop can send exception report
     * @param context: context
     */
    public static void initGoogleAnalyticsTracker(Context context) {
        tracker = getTracker( context );
    }

    public static void sendScreenViewReport(Context context) {

        if(tracker == null) {
            tracker = getTracker( context );
        }

        if(context == null) {
            context.getApplicationContext();
        }

        tracker.setScreenName( context.getClass().getName() );

        //send report
        ILog.iLogDebug( "sendScreenViewReport : ", context.getClass().getName() );

        tracker.send( new HitBuilders.ScreenViewBuilder()
                              .setNewSession()
                              .build() );

        //should init after send
        tracker.setScreenName( "" );
    }

    public static void sendEventReport(Context context, String category, String action, boolean isWithScreen) {

        if(tracker == null) {
            tracker = getTracker( context );
        }

        if(isWithScreen) {
            tracker.setScreenName( context.getClass().getName() );
        }

        //send report
        ILog.iLogDebug( "sendEventReport : ", category
                + "\n" + action);

        tracker.send( new HitBuilders.EventBuilder()
                        .setCategory( category )
                        .setAction( action )
                        .build()
                        );

        //should init after send
        tracker.setScreenName( null );


    }

    public static void sendEventReport(Context context, String category, String action, String label, long value, boolean isWithScreen) {

        if(tracker == null) {
            tracker = getTracker( context );
        }

        if(isWithScreen) {
            tracker.setScreenName( context.getClass().getName() );
        }

        //send report
        ILog.iLogDebug( "sendEventReport : ", category
                + "\n" + action
                + "\n" + label
                + "\n" + value);

        tracker.send( new HitBuilders.EventBuilder()
                      .setCategory( category )
                      .setAction( action )
                      .setLabel( label )
                      .setValue( value )
                      .build());

        //should init after send
        tracker.setScreenName( null );
    }

    public static void sendExceptionReport(Context context, Throwable exception, boolean isFatal, boolean isWithScreen) {

        if(tracker == null) {
            tracker = getTracker( context );
        }

        if(isWithScreen) {
            tracker.setScreenName( context.getClass().getName() );
        }

        //add device info
        Map<String, String> userInfo = new HashMap<>();
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);

            if ( packageInfo != null ) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                userInfo.put("versionName", versionName);
                userInfo.put("versionCode", versionCode);
            }

        }
        catch ( PackageManager.NameNotFoundException e ) {
            ILog.iLogException( "setTrackingReport", "an error occured when collect package info " + e );
        }

        Field[] fields = Build.class.getDeclaredFields();
        for ( Field field : fields ) {
            try {

                field.setAccessible(true);
                userInfo.put(field.getName(), field.get(null).toString());
            }
            catch ( Exception e ) {
                ILog.iLogException( "setTrackingReport", "an error occured when collect crash info " + e );
            }
        }

        StringBuffer stringBuffer = new StringBuffer();
        for ( Map.Entry<String, String> entry : userInfo.entrySet() ) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuffer.append("[- " + key + " = " + value + " -]\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        exception.printStackTrace(printWriter);
        Throwable cause = exception.getCause();

        while ( cause != null ) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        printWriter.close();
        String result = "\n[ Stack Trace ]\n" + writer.toString();
        stringBuffer.append(result);


        String            time              = "[- " + DateUtil.getCurrentDateFromFastDateFormat( DATE_FORMAT ) + " -]\n";
        String            device            = "[- " + stringBuffer.toString() + " -]\n";
        String            crashException    = "[- " + exception + " -]\n";
        StackTraceElement stackTraceElement = exception.getStackTrace()[0];
        String            location          = "[- " + stackTraceElement.getFileName() + ": Line " + stackTraceElement.getLineNumber() + " -]\n";

        String description = location + CRASH_REPORT + time + device + crashException;

        //send report
        ILog.iLogDebug( "sendExceptionReport : ", isFatal
                + "\n\n" + description);

        tracker.send(new HitBuilders.ExceptionBuilder()
                     .setFatal( isFatal )
                     .setDescription( description )
                     .build());

        //should init after send
        tracker.setScreenName( null );
        userInfo.clear();
    }


    public static void sendTryCatchExceptionReport(String exceptionDescription, String ScreenName, boolean isFatal, boolean isWithScreen) {

        if(tracker == null) {
            return;
        }

        if(isWithScreen) {
            tracker.setScreenName( ScreenName );
        }

        //add device info
        Map<String, String> userInfo = new HashMap<>();

        Field[] fields = Build.class.getDeclaredFields();
        for ( Field field : fields ) {
            try {

                field.setAccessible(true);
                userInfo.put(field.getName(), field.get(null).toString());
            }
            catch ( Exception e ) {
                ILog.iLogException( "setTrackingReport", "an error occured when collect crash info " + e );
            }
        }

        StringBuffer stringBuffer = new StringBuffer();
        for ( Map.Entry<String, String> entry : userInfo.entrySet() ) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuffer.append("[- " + key + " = " + value + " -]\n");
        }

        String            time              = "[- " + DateUtil.getCurrentDateFromFastDateFormat( DATE_FORMAT ) + " -]\n";
        String            device            = "[- " + stringBuffer.toString() + " -]\n";
        String            crashException    = "[- " + exceptionDescription + " -]\n";

        String description = CRASH_REPORT + time + device + crashException;

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

}
