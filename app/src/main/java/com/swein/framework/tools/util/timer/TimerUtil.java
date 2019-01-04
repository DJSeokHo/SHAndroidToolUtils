package com.swein.framework.tools.util.timer;

import android.os.CountDownTimer;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by seokho on 09/02/2018.
 */

public class TimerUtil {

    /**
     *
     * @param onTick
     * @param onFinish
     * @param second count down time (unit second)
     * @param interval count down interval (unit second)
     * @return
     */
    public static CountDownTimer countDownTimerTask(Runnable onTick, Runnable onFinish, int second, int interval) {

        CountDownTimer countDownTimer = new CountDownTimer(second * 1000, interval * 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                ILog.iLogDebug("", "onTick " + String.valueOf((millisUntilFinished / 1000)));

                onTick.run();
            }

            @Override
            public void onFinish() {
                ILog.iLogDebug("", "onFinish");

                onFinish.run();

            }
        };

        countDownTimer.start();

        return countDownTimer;
    }
    public static void stopCountDownTimerTask(CountDownTimer countDownTimer) {

        countDownTimer.cancel();
    }

    public static Timer createTimerTask(long delay, long period, final Runnable runnable) {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ThreadUtil.startUIThread(0, new Runnable() {
                    @Override
                    public void run() {
                        runnable.run();
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(task, delay, period);

        return timer;
    }

}
