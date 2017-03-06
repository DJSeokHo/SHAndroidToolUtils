package com.swein.framework.tools.manager.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by seokho on 06/03/2017.
 */

public class ThreadManager {

    static private ExecutorService executor           = Executors.newFixedThreadPool( Runtime.getRuntime().availableProcessors());
    static private ExecutorService executorSequential = Executors.newSingleThreadExecutor();

    @Override
    protected void finalize() throws Throwable {
        if(executor != null && executor.isShutdown())
            executor.shutdown();
        if(executorSequential != null && executorSequential.isShutdown())
            executorSequential.shutdown();
        super.finalize();
    }

    private static Handler handle = new Handler( Looper.getMainLooper() );

    public static Handler startUIThread( int delayMillis , Runnable r ) {

        handle.postDelayed( r , delayMillis );

        return handle;
    }

    public static Future<?> startThread( Runnable r ) {
        return executor.submit( r );
    }

    public static Future<?> startSeqThread( Runnable r ) {
        return executorSequential.submit( r );
    }

}
