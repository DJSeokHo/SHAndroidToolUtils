package com.swein.framework.module.easyscreenrecord.framework.util.handler;

import android.os.Handler;
import android.os.Message;

import com.swein.framework.tools.util.debug.log.ILog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by seokho on 27/12/2016.
 */

public class HandlerUtils {

    private static Handler handler = null;

    private static int message = -1;

    private static void createHandlerMessage() {

        if(-1 != message) {
            ILog.iLogDebug(HandlerUtils.class.getName(), "Already created a message " + message);
            return;
        }
//        message = RandomNumberUtils.getRandomIntegerNumber(100, 200);

    }

    private static int getCreatedHandlerMessage() {

        if(-1 != message) {
            return message;
        }

        return -1;
    }

    public static void createHandlerMethodWithMessage(final Runnable runnable) {

        if(null != handler) {
            return;
        }

        createHandlerMessage();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if(msg.what == getCreatedHandlerMessage()) {
                    runnable.run();
                    clearHandlerMessage();
                }
            }
        };
    }

    public static void handlerSendMessage() {

        if(null == handler) {
            ILog.iLogError(HandlerUtils.class.getName(), "try runHandlerMethodWithMessage first");
            return;
        }

        handler.sendEmptyMessage(getCreatedHandlerMessage());

    }

    public static void handlerSendMessageDelay(long delay, long period) {

        if(null == handler) {
            ILog.iLogError(HandlerUtils.class.getName(), "try runHandlerMethodWithMessage first");
            return;
        }

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(message);
                timer.cancel();
            }
        }, delay, period);

    }

    private static void clearHandlerMessage() {
        handler = null;
        message = -1;
    }

}
