package com.swein.framework.tools.util.handler;

import android.os.Handler;
import android.os.Message;

import com.swein.data.singleton.example.ExampleSingtonClass;
import com.swein.framework.tools.util.debug.log.ILog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by seokho on 27/12/2016.
 */

public class HandlerUtils {

    private Handler handler = null;

    private HandlerUtils() {}

    private static HandlerUtils instance = new HandlerUtils();

    public static HandlerUtils getInstance() {
        return instance;
    }


    public void runHandlerMethodWithMessage(final int message, final Runnable runnable) {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == message) {
                    runnable.run();
                    clearHandler();
                }
            }
        };
    }

    public void handlerSendMessage(final int message) {

        if(null == handler) {
            ILog.iLogException(HandlerUtils.class.getName(), "try runHandlerMethodWithMessage first");
            return;
        }
        handler.sendEmptyMessage(message);

    }

    public void handlerSendMessageDelay(final int message, long delay, long period) {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(message);
            }
        }, delay, period);

    }

    private void clearHandler() {
        handler = null;
    }

}
