package com.swein.framework.module.easyscreenrecord.manager.handler;

import android.os.Handler;
import android.os.Message;

import com.swein.framework.module.easyscreenrecord.data.singleton.DataCenter;

import static com.swein.framework.module.easyscreenrecord.data.global.message.handler.HandlerMessageData.SCREEN_RECORD_END;
import static com.swein.framework.module.easyscreenrecord.data.global.message.handler.HandlerMessageData.SCREEN_RECORD_START;


/**
 * Created by seokho on 03/02/2017.
 */

public class HandlerManager {

    private static Handler handler;

    public static void createHandlerMethodWithMessage(final Runnable runnable) {

        if(null != handler) {
            return;
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {

                    case SCREEN_RECORD_START:
                        runnable.run();
                        break;

                    case SCREEN_RECORD_END:
                        runnable.run();
                        break;

                    default:
                        break;
                }
            }
        };
    }

    public static void sendScreenRecordStartMessage() {

       try {
           handler.sendEmptyMessage(SCREEN_RECORD_START);
           DataCenter.getInstance().setRecordModeOn(true);
       }
       catch (Exception e) {
           e.printStackTrace();
       }

    }

    public static void sendScreenRecordEndMessage() {

        try {
            handler.sendEmptyMessage(SCREEN_RECORD_END);
            DataCenter.getInstance().setRecordModeOn(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
