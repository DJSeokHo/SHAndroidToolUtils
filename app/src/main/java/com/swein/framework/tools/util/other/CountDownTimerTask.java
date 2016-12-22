package com.swein.framework.tools.util.other;

import android.os.Handler;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by seokho on 15/12/2016.
 */

public class CountDownTimerTask {

    private final static String TAG = "CountDownTimerTask";

    public static void countdownTimerTaskWithUI(int unit, final int time, final Runnable runnable) {

        final Calendar limit = Calendar.getInstance();
        limit.add(unit, time);

        final Handler handler = new Handler();

        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                for(int i = 0; i < 10000; i++) {

                    handler.post(runnable);

                }
                timer.cancel();

            }
        };

        /**
         * public void schedule(TimerTask task, long delay, long period)
         * @param task   task to be scheduled.
         * @param delay  delay in milliseconds before task is to be executed.
         * @param period time in milliseconds between successive task executions.
         */
        timer.schedule(timerTask, 0, time * 1000);
    }

    public static void countdownTimerTaskWithoutUI(int unit, final int time, final Runnable runnable) {

        final Calendar limit = Calendar.getInstance();
        limit.add(unit, time);

        final Handler handler = new Handler();

        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                for(int i = 0; i < 10000; i++) {

                    runnable.run();

                }
                timer.cancel();

            }
        };

        /**
         * public void schedule(TimerTask task, long delay, long period)
         * @param task   task to be scheduled.
         * @param delay  delay in milliseconds before task is to be executed.
         * @param period time in milliseconds between successive task executions.
         */
        timer.schedule(timerTask, 0, time * 1000);
    }
}
