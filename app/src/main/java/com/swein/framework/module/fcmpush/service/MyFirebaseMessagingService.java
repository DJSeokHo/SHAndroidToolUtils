package com.swein.framework.module.fcmpush.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.swein.framework.tools.util.debug.log.ILog;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        ILog.iLogDebug(TAG, "push from sender id : " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            ILog.iLogDebug(TAG, "getNotification : " + remoteMessage.getNotification().getTitle());
            ILog.iLogDebug(TAG, "getNotification : " + remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            ILog.iLogDebug(TAG, "getData : " + remoteMessage.getData());
        }

    }
}
