package com.swein.framework.tools.util.handler;

import android.os.Handler;
import android.os.Message;

import com.swein.data.singleton.example.ExampleSingtonClass;
import com.swein.framework.tools.util.debug.log.ILog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by seokho on 27/12/2016.
 */

public class HandlerUtils {


    public final static String UPDATE_BUTTON = "UPDATE_BUTTON";
    public final static String UPDATE_IMAGE_VIEW = "UPDATE_IMAGE_VIEW";
    public final static String UPDATE_TEXT_VIEW = "UPDATE_TEXT_VIEW";
    public final static String UPDATE_EDITTEXT_STATE = "UPDATE_EDITTEXT_STATE";

    private Handler handler = null;
    private final static int HANDLER_MESSAGE_OFFSET = 100;

    private List<Integer> handlerMessageList = new ArrayList<>();
    private List<String> keyList = new ArrayList<>();

    private HandlerUtils() {}

    private static HandlerUtils instance = new HandlerUtils();

    public static HandlerUtils getInstance() {
        return instance;
    }

    public void addMessageIntoHandlerMessageList(String key) {

        keyList.add(key);
        handlerMessageList.add(keyList.indexOf(key) + HANDLER_MESSAGE_OFFSET);
        ILog.iLogDebug(HandlerUtils.class.getName(), keyList.size() + " " + handlerMessageList.size());
        for(int i = 0; i < keyList.size(); i++) {
            ILog.iLogDebug(HandlerUtils.class.getName(), keyList.get(i) + " " + handlerMessageList.get(i) + " " + keyList.indexOf(keyList.get(i)));
        }
    }

    public int getHandlerMessageFromHandlerMessageList(String key) {
        return handlerMessageList.get(keyList.indexOf(key));
    }

    public void removeHandlerMessageFromHandlerMessageList(String key) {

        handlerMessageList.remove(keyList.indexOf(key));
        keyList.remove(key);

        ILog.iLogDebug(HandlerUtils.class.getName(), keyList.size() + " " + handlerMessageList.size());
        for(int i = 0; i < keyList.size(); i++) {
            ILog.iLogDebug(HandlerUtils.class.getName(), keyList.get(i) + " " + handlerMessageList.get(i) + " " + keyList.indexOf(keyList.get(i)));
        }

    }

    public void showHandlerMessageList() {
        ILog.iLogDebug(HandlerUtils.class.getName(), keyList.size() + " " + handlerMessageList.size());
        for(int i = 0; i < keyList.size(); i++) {
            ILog.iLogDebug(HandlerUtils.class.getName(), keyList.get(i) + " " + handlerMessageList.get(i) + " " + keyList.indexOf(keyList.get(i)));
        }
    }

    public void clearHandlerMessageList() {

        keyList.clear();
        handlerMessageList.clear();

    }

    public void runHandlerMethodWithMessage(final String key, final Runnable runnable) {

        addMessageIntoHandlerMessageList(key);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ILog.iLogDebug(HandlerUtils.class.getName(), msg.what + " " + getHandlerMessageFromHandlerMessageList(key));
                if(msg.what == getHandlerMessageFromHandlerMessageList(key)) {
                    runnable.run();
                    clearHandler();
                    removeHandlerMessageFromHandlerMessageList(key);
                }
            }
        };
    }

    public void handlerSendMessage(final String key) {

        if(null == handler) {
            ILog.iLogException(HandlerUtils.class.getName(), "try runHandlerMethodWithMessage first");
            return;
        }

        handler.sendEmptyMessage(getHandlerMessageFromHandlerMessageList(key));

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
