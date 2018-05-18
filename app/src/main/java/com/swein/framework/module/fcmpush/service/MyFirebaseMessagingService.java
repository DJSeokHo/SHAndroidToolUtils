package com.swein.framework.module.fcmpush.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.swein.framework.module.fcmpush.activity.FirebaseCloudMessage;
import com.swein.framework.module.fcmpush.receiver.FirebaseIntentReceiver;
import com.swein.framework.module.mdmcustom.api.SHMDMDeviceManager;
import com.swein.framework.tools.eventsplitshot.eventcenter.EventCenter;
import com.swein.framework.tools.eventsplitshot.subject.ESSArrows;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.notification.NotificationUIUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;

import java.util.HashMap;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(FirebaseCloudMessage.example) {

            if (remoteMessage.getData().size() > 0) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("onMessageReceived", remoteMessage.getData().toString());
                EventCenter.getInstance().sendEvent(ESSArrows.ESS_UPDATE_UI, this, hashMap);
            }
            else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("onMessageReceived", "message data part is null");
                EventCenter.getInstance().sendEvent(ESSArrows.ESS_UPDATE_UI, this, hashMap);
            }

            return;
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            /*
            data for app run background
             */
            ILog.iLogDebug(TAG, "getData : " + remoteMessage.getData());

            final SHMDMDeviceManager shmdmDeviceManager = new SHMDMDeviceManager(this);

            if(!shmdmDeviceManager.isActive()) {

                return;
            }

            switch (remoteMessage.getData().get("command")) {

                case "disable_camera":

                    ThreadUtil.startThread(new Runnable() {
                        @Override
                        public void run() {
                            shmdmDeviceManager.disableCamera();
                            EventCenter.getInstance().sendEvent(ESSArrows.ESS_DEVICE_DISABLE_CAMERA, this, null);
                        }
                    });

                    break;

                case "enable_camera":

                    ThreadUtil.startThread(new Runnable() {
                        @Override
                        public void run() {
                            shmdmDeviceManager.enableCamera();
                            EventCenter.getInstance().sendEvent(ESSArrows.ESS_DEVICE_ENABLE_CAMERA, this, null);
                        }
                    });

                    break;

                case "disable_screen_capture":

                    ThreadUtil.startThread(new Runnable() {
                        @Override
                        public void run() {
                            shmdmDeviceManager.disableScreenCapture();
                            EventCenter.getInstance().sendEvent(ESSArrows.ESS_DEVICE_DISABLE_SCREEN_CAPTURE, this, null);
                        }
                    });

                    break;

                case "enable_screen_capture":

                    ThreadUtil.startThread(new Runnable() {
                        @Override
                        public void run() {
                            shmdmDeviceManager.enableScreenCapture();
                            EventCenter.getInstance().sendEvent(ESSArrows.ESS_DEVICE_ENABLE_SCREEN_CAPTURE, this, null);
                        }
                    });

                    break;
            }

            return;
        }



        ILog.iLogDebug(TAG, "push from sender id : " + remoteMessage.getFrom());

        String title = "";
        String body = "";
        String link = "";

        Intent intent = new Intent(this, FirebaseIntentReceiver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction("notification_clicked");

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            /*
            notification just for app visible
            if app run background
            you should use data
             */
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
            link = remoteMessage.getNotification().getClickAction();

            ILog.iLogDebug(TAG, "getNotification : " + remoteMessage.getNotification().getTitle());
            ILog.iLogDebug(TAG, "getNotification : " + remoteMessage.getNotification().getBody());
            ILog.iLogDebug(TAG, "getNotification : " + remoteMessage.getNotification().getClickAction());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            /*
            data for app run background
             */
            ILog.iLogDebug(TAG, "getData : " + remoteMessage.getData());


        }

        Bundle bundle = new Bundle();

        bundle.putString("title", title);
        bundle.putString("body", body);
        bundle.putString("link", link);

        intent.putExtra("firebase", bundle);

        NotificationUIUtil.sendNotification(this, 0,
                title, "", body,
                true, true,
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));


    }
}
