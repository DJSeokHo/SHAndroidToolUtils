package com.swein.framework.module.appanalysisreport.applicationhandler;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;

import com.swein.framework.module.appanalysisreport.applicationhandler.ui.AppCrashReportActivity;
import com.swein.framework.module.appanalysisreport.data.parser.ReportParser;
import com.swein.framework.module.appanalysisreport.reportproperty.ReportProperty;
import com.swein.framework.module.appanalysisreport.reporttracker.Reporter;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 23/11/2016.
 */

public class CrashExceptionReportHandler implements Thread.UncaughtExceptionHandler {

    private final static String TAG = "CrashExceptionReportHandler";

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    @SuppressLint("StaticFieldLeak")
    private static CrashExceptionReportHandler instance = new CrashExceptionReportHandler();

    private Context context;

    private CrashExceptionReportHandler() {}

    public static CrashExceptionReportHandler getInstance() {
        return instance;
    }

    /**
     * init
     * @param context context
     */
    public void init(Context context) {
        this.context = context;
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * when uncaught exception
     * @param thread thread
     * @param exception exception
     */
    @Override
    public void uncaughtException(Thread thread, Throwable exception) {

        if ( !handleException(exception) && uncaughtExceptionHandler != null ) {

            uncaughtExceptionHandler.uncaughtException(thread, exception);
        }
        else {

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }


            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(context, AppCrashReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("crash", true);
            PendingIntent restartIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 1500, restartIntent);
            }

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            System.gc();

        }
    }

    /**
     * custom defined exception handler
     * @param exception exception
     * @return boolean
     */
    private boolean handleException(final Throwable exception) {
        if( null == exception ) {
            return false;
        }

        new Thread() {

            @Override
            public void run() {
                Looper.prepare();
                ToastUtil.showShortToastNormal(context, context.getString(R.string.exception_save));

                Reporter.getInstance().trackException(
                        ReportParser.getLocationFromThrowable(exception.getCause()),
                        ReportParser.getExceptionMessage(exception),
                        ReportProperty.EVENT_GROUP_CRASH,
                        "",
                        "");

                Looper.loop();
            }
        }.start();

        /*
         * here is crash exception handler.
         * if app crash because exception from somewhere that you don't catch
         * here will go to send exception crash report view.
         */


        return true;

    }
}
