package com.swein.framework.tools.util.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by seokho on 19/12/2016.
 */

public class ThreadUtil {

    /**
     * ThreadPoolExecutor
     * corePoolSize: 线程池的核心线程数，默认情况下， 核心线程会在线程池中一直存活， 即使处于闲置状态. 但如果将allowCoreThreadTimeOut设置为true的话, 那么核心线程也会有超时机制， 在keepAliveTime设置的时间过后， 核心线程也会被终止.
     * maximumPoolSize: 最大的线程数， 包括核心线程， 也包括非核心线程， 在线程数达到这个值后，新来的任务将会被阻塞.
     * keepAliveTime: 超时的时间， 闲置的非核心线程超过这个时长，讲会被销毁回收， 当allowCoreThreadTimeOut为true时，这个值也作用于核心线程.
     * unit：超时时间的时间单位.
     * workQueue：线程池的任务队列， 通过execute方法提交的runnable对象会存储在这个队列中.
     * threadFactory: 线程工厂, 为线程池提供创建新线程的功能.
     * handler: 任务无法执行时，回调handler的rejectedExecution方法来通知调用者.
     *
     * FixedThreadPool
     * 只有核心线程数，并且没有超时机制，因此核心线程即使闲置时，也不会被回收，因此能更快的响应外界的请求.
     * 这种形式可以控制最大并发数量，超出的线程会等待
     *
     * CachedThreadPool
     * 没有核心线程，非核心线程数量没有限制， 超时为60秒.
     * 适用于执行大量耗时较少的任务，当线程闲置超过60秒时就会被系统回收掉，当所有线程都被系统回收后，它几乎不占用任何系统资源.
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程
     *
     * ScheduledThreadPool
     * 核心线程数是固定的，非核心线程数量没有限制， 没有超时机制.
     * 主要用于执行定时任务和具有固定周期的重复任务.
     * 创建一个定长线程池，支持定时及周期性任务执行
     *
     * SingleThreadExecutor
     * 只有一个核心线程，并没有超时机制.
     * 意义在于统一所有的外界任务到一个线程中， 这使得在这些任务之间不需要处理线程同步的问题.
     * 它只会用唯一的工作线程来执行任务，可以理解为线程数量为1的FixedThreadPool
     */
    // fixed number thread pool, can reuse
    static private ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // only one thread can run at one time
    static private ExecutorService executorSequential = Executors.newSingleThreadExecutor();

    // sync UI
    private static Handler handle = new Handler(Looper.getMainLooper());

    @Override
    protected void finalize() throws Throwable {
        if (executor != null && executor.isShutdown()) {
            executor.shutdown();
        }
        if (executorSequential != null && !executorSequential.isShutdown()) {
            executorSequential.shutdown();
        }
        super.finalize();
    }

    public static void startUIThread(int delayMillis, Runnable runnable) {
        handle.postDelayed(runnable, delayMillis);
    }

    public static void startThread(final Runnable runnable) {
        executor.submit(runnable);
    }

    public static void startSingleThread(final Runnable runnable) {
        executorSequential.submit(runnable);
    }

//    public static Handler startUIThread(int delayMillis, Runnable runnable) {
//        handle.postDelayed(runnable, delayMillis);
//        return handle;
//    }

//    public static Future<?> startThread(final Runnable runnable) {
//        Future<?> future = executor.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    new Thread(runnable).start();
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        });
//
//        return future;
//    }
//
//
//
//    public static Future<?> startSingleThread(final Runnable runnable) {
//        Future<?> future = executorSequential.submit(new Runnable() {
//            @Override
//            public void run() {
//                runnable.run();
//            }
//        });
//
//        return future;
//    }

    //***************** [Do not delete this part] **********************
    //    public static Future< ? > startThreadWithExceptionReport( Runnable r, Context context ) {
    //
    //        Future< ? > future = executor.submit( r );
    //
    //        try {
    //            future.get();
    //        }
    //        catch ( InterruptedException | ExecutionException exception ) {
    //           TrackerManager.sendExceptionReport( context, exception, true, true );
    //        }
    //
    //        return future;
    //    }

    //    public static Future< ? > startSeqThread( Runnable r ) {
    //        return executorSequential.submit( r );
    //    }

//    public static Future<?> startSeqThread(final Runnable runnable) {
//        Future<?> future = executor.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    runnable.run();
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        });
//
//        return future;
//    }

    //***************** [Do not delete this part] **********************
    //    public static Future< ? > startSeqThreadWithExceptionReport( Runnable r, Context context ) {
    //
    //        Future< ? > future = executorSequential.submit( r );
    //
    //        try {
    //            future.get();
    //        }
    //        catch ( InterruptedException | ExecutionException exception ) {
    //            TrackerManager.sendExceptionReport( context, exception, true, true );
    //        }
    //
    //        return future;
    //    }
}
