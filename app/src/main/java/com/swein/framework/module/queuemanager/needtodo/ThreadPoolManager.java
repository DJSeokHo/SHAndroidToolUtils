package com.swein.framework.module.queuemanager.needtodo;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

    private final static String TAG = "ThreadPoolManager";

    private static ThreadPoolManager instance = new ThreadPoolManager();
    public static ThreadPoolManager getInstance() {
        return instance;
    }

    // 线程安全队列
    private LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();

    // 还需要一个失败的任务的队列，以便重试
    // TODO 失败队列

    // 将请求放入队列
    public void addTask(Runnable runnable) {
        if(runnable != null) {
            try {
                queue.put(runnable);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadPoolManager() {

        Log.d(TAG, "ThreadPoolManager init");

        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                addTask(runnable);
            }
        });

        threadPoolExecutor.execute(ddThread);
    }

    // 创建调度线程
    public Runnable ddThread = new Runnable() {

        Runnable runnable;

        @Override
        public void run() {
            while (true) {
                try {
                    runnable = queue.take();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(runnable != null) {
                    threadPoolExecutor.execute(runnable);
                }
            }
        }
    };



}
