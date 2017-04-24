package com.swein.framework.module.googleanalytics.aop.monitor.processtimer;

import java.util.concurrent.TimeUnit;

/**
 * Method process time measuring
 *
 * Created by seokho on 24/04/2017.
 */

public class ProcessTimer {

    private long startTime;
    private long endTime;
    private long elapsedTime;

    public ProcessTimer() {
    }

    private void reset() {
        startTime = 0;
        endTime = 0;
        elapsedTime = 0;
    }

    public void start() {
        reset();
        startTime = System.nanoTime();
    }

    public void stop() {
        if (startTime != 0) {
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        } else {
            reset();
        }
    }

    public long getTotalTimeMillis() {
        return (elapsedTime != 0) ? TimeUnit.NANOSECONDS.toMillis( endTime - startTime) : 0;
    }

}
