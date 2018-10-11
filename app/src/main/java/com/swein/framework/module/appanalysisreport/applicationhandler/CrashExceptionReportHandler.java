package com.swein.framework.module.appanalysisreport.applicationhandler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;

import com.swein.framework.module.appanalysisreport.applicationhandler.ui.AppCrashReportActivity;
import com.swein.framework.module.appanalysisreport.constants.AAConstants;
import com.swein.framework.module.appanalysisreport.data.model.AppAnalysisData;
import com.swein.framework.module.appanalysisreport.data.model.impl.ExceptionData;
import com.swein.framework.module.appanalysisreport.data.parser.StackTraceParser;
import com.swein.framework.module.appanalysisreport.reporttracker.ReportTracker;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by seokho on 23/11/2016.
 */

public class CrashExceptionReportHandler implements Thread.UncaughtExceptionHandler {

    private final static String TAG = "CrashExceptionReportHandler";

    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
    private static CrashExceptionReportHandler instance = new CrashExceptionReportHandler();

    private Context context;

    private CrashExceptionReportHandler() {}

    public static CrashExceptionReportHandler getInstance() {
        return instance;
    }

    /**
     * init
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
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
                Thread.sleep(1500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }


            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(context, AppCrashReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("crash", true);
            PendingIntent restartIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 3000, restartIntent);

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            System.gc();

        }
    }

    /**
     * custom defined exception handler
     * @param exception
     * @return
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

                AppAnalysisData appAnalysisData = new ExceptionData.Builder()
                        .setUuid(UUID.randomUUID().toString())
                        .setUserID(AAConstants.TEST_USER_ID)
                        .setDateTime(DateUtil.getCurrentDateTimeString())
                        .setClassFileName(StackTraceParser.getClassFileNameFromThrowable(exception))
                        .setLineNumber(StackTraceParser.getLineNumberFromThrowable(exception))
                        .setMethodName(StackTraceParser.getMethodNameFromThrowable(exception))
                        .setExceptionMessage(Arrays.toString(exception.getStackTrace()))
                        .setEventGroup(AAConstants.EVENT_GROUP_CRASH).build();

                ReportTracker.getInstance().saveAppAnalysisIntoDB(context, appAnalysisData);

                Looper.loop();
            }
        }.start();

        /**
         * here is crash exception handler.
         * if app crash because exception from somewhere that you don't catch
         * here will go to send exception crash report view.
         */


        return true;

    }
}
