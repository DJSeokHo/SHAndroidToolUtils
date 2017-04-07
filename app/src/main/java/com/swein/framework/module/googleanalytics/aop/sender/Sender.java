package com.swein.framework.module.googleanalytics.aop.sender;

import com.swein.framework.tools.util.debug.log.ILog;

/**
 * Created by seokho on 31/03/2017.
 */

public class Sender {

    private Sender() {}

    /**
     * Send a view report
     *
     * @param tag Source of a log message.
     * @param message The message you would like logged.
     */
    public static void sendViewReport(String tag, String message) {
        ILog.iLogDebug( tag, message );
    }

    /**
     * Send a event report
     *
     * @param tag Source of a log message.
     * @param message The message you would like logged.
     */
    public static void sendEventReport(String tag, String message) {
        ILog.iLogDebug( tag, message );
    }

}

