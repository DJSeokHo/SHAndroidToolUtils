package com.swein.framework.helper.information.app.usage.handler;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.swein.framework.helper.information.app.usage.manager.AppTrackerManager;
import com.swein.framework.helper.information.app.usage.tracker.report.TrackingReport;
import com.swein.shandroidtoolutils.R;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by seokho on 23/11/2016.
 */

public class CrashExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final static String TAG = "CrashExceptionHandler";

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private static CrashExceptionHandler INSTANCE = new CrashExceptionHandler();

    private Context context;

    private Map<String, String> userInfo = new HashMap<String, String>();

    private DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd - HH:mm:ss");

    private CrashExceptionHandler() {}

    public static CrashExceptionHandler getInstance() {
        return INSTANCE;
    }

    /**
     * init
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

        AppTrackerManager.getInstance().createCrashTracker(context);
    }

    /**
     * when uncaught exception
     * @param thread
     * @param exception
     */
    @Override
    public void uncaughtException(Thread thread, Throwable exception) {

        if ( !handleException(exception) && uncaughtExceptionHandler != null ) {

            uncaughtExceptionHandler.uncaughtException(thread, exception);
        }
        else {

            try {
                Thread.sleep(2000);
            }
            catch ( InterruptedException e ) {
                Log.e(TAG, "error : ", e);
            }

            //exit
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * custom defined exception handler
     * @param exception
     * @return
     */
    private boolean handleException(Throwable exception) {
        if( null == exception ) {
            return false;
        }

        new Thread() {

            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, context.getString(R.string.exception_count), Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        collectDeviceInfo(context);

        saveCrashInfo(exception);

        return true;

    }

    /**
     * collect device info
     * @param context
     */
    public void collectDeviceInfo(Context context) {
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
            Log.e(TAG, "an error occured when collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for ( Field field : fields ) {
            try {

                field.setAccessible(true);
                userInfo.put(field.getName(), field.get(null).toString());
            }
            catch ( Exception e ) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * show exception information
     * @param exception
     */
    private void saveCrashInfo(Throwable exception) {

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

        String time = "[- " + dateFormat.format(new Date()) + " -]";
        String device = "[- " + stringBuffer.toString() + " -]";
        String crashException = "[- " + exception + " -]";

        //custom crash report
        TrackingReport.getInstance().setTrackingReport(AppTrackerManager.CRASH_REPORT + time + device + crashException, true);
//
        //system crash report
//        TrackingReport.getInstance().setTrackingReport(AppTrackerManager.CRASH_REPORT + exception, true);

        AppTrackerManager.getInstance().sendCrashReport();

    }
}
