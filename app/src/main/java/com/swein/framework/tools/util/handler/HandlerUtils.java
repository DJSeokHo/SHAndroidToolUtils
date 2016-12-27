package com.swein.framework.tools.util.handler;

import android.os.Handler;
import android.os.Message;

/**
 * Created by seokho on 27/12/2016.
 */

public class HandlerUtils {

    public static void runHandlerMethodWithMessage(final int message, final Runnable runnable) {

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == message) {
                    runnable.run();
                }
            }
        };

        handler.sendEmptyMessage(message);

    }

}
