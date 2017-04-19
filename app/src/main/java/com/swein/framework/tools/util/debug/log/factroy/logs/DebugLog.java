package com.swein.framework.tools.util.debug.log.factroy.logs;

import android.util.Log;

import com.swein.framework.tools.util.debug.log.factroy.basiclog.BasicLog;


/**
 * Created by seokho on 19/04/2017.
 */

public class DebugLog implements BasicLog {

    @Override
    public void iLog(String tag, String content) {
        Log.d(tag, content);
    }
}
