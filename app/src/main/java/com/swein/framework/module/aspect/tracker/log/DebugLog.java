package com.swein.framework.module.aspect.tracker.log;

import android.util.Log;

/**
 * Created by seokho on 31/03/2017.
 */

public class DebugLog {

    private DebugLog() {}

    /**
     * Send a debug log message
     *
     * @param tag Source of a log message.
     * @param message The message you would like logged.
     */
    public static void log(String tag, String message) {
        Log.d( tag, message);
    }

}
