package com.swein.framework.tools.util.thread;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by seokho on 19/12/2016.
 */

public class ThreadUtils {

    private final static String TAG = "ThreadUtils";

    private static Handler handle = new Handler( Looper.getMainLooper() );

    public static void createThreadWithoutUI(int delayMillis, final Runnable runnable) {

        new Thread(){
            public void run(){

                runnable.run();

            }
        }.start();
    }

    public static void createThreadWithUI(final int delayMillis, final Runnable runnable) {

        new Thread(){
            public void run(){

                handle.postDelayed( runnable , delayMillis );

            }
        }.start();
    }
}