package com.swein.framework.module.fcmpush.message;

import com.swein.framework.tools.util.debug.log.ILog;

import java.util.HashMap;

public class FCMMessageParser {

    private final static String TAG = "FCMMessageParser";

    private final static String MESSAGE_KEY = "message";
    private final static String TOKEN_KEY = "token";
    private final static String NOTIFICATION_KEY = "notification";
    private final static String TITLE_KEY = "title";
    private final static String BODY_KEY = "body";


    public static String getCustomMessage(Object pushMessage) {

        /*

        {
          "message":{
            "token":"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
            "notification":{
              "title":"Portugal vs. Denmark",
              "body":"great match!"
            }
          }
        }

         */
        HashMap<String, Object> message = (HashMap<String, Object>) ((HashMap<String, Object>)pushMessage).get(MESSAGE_KEY);

        String token = (String) message.get(TOKEN_KEY);
        HashMap<String, String> notification = (HashMap<String, String>) message.get(NOTIFICATION_KEY);

        String title = notification.get(TITLE_KEY);
        String body = notification.get(BODY_KEY);

        ILog.iLogDebug(TAG, token + " " + title + " " + body);

        return "";
    }

}
