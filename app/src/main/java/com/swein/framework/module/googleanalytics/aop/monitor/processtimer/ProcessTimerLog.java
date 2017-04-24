package com.swein.framework.module.googleanalytics.aop.monitor.processtimer;


import com.swein.framework.tools.util.debug.log.ILog;

/**
 * Created by seokho on 24/04/2017.
 */

public class ProcessTimerLog {

    private ProcessTimerLog() {}

    /**
     * Send a debug log message
     *
     * @param tag Source of a log message.
     * @param message The message you would like logged.
     */
    public static void log(String tag, String message) {
        ILog.iLogDebug( tag, message );
    }

}
