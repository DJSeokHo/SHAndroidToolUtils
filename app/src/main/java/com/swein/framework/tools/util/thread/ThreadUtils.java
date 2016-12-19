package com.swein.framework.tools.util.thread;

import android.os.Handler;

import com.swein.framework.tools.util.debug.log.SeokHoTest;

/**
 * Created by seokho on 19/12/2016.
 */

public class ThreadUtils {

    private final static String TAG = "ThreadUtils";

    public static void createThreadWithoutUI(final Runnable runnable) {

        new Thread(){
            public void run(){

                for(int i = 0; i < 10000; i++) {

                    runnable.run();

                }

            }
        }.start();
    }

    public static void createThreadWithUI(final Runnable runnable) {

        final Handler handler = new Handler();

        new Thread(){
            public void run(){

                for(int i = 0; i < 10000; i++) {

                    handler.post(runnable);

                }
            }
        }.start();
    }
}
