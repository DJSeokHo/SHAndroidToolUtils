package com.swein.framework.tools.manager.handler;

import android.os.Handler;
import android.os.Message;

/**
 * Created by seokho on 03/02/2017.
 */

public class HandlerManager {

    private static Handler handler;

    public static void createHandlerMethodWithMessage(final int message, final Runnable runnable) {

        if(null != handler) {
            return;
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if(msg.what == message) {
                    runnable.run();
                }
            }
        };
    }

    public static void sendMessage(int message) {

       try {
           handler.sendEmptyMessage(message);
       }
       catch (Exception e) {
           e.printStackTrace();
       }
    }
}
