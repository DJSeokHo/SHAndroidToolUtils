package com.swein.framework.module.fcmpush.service;

import android.content.Intent;
import android.graphics.Bitmap;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.swein.framework.module.fcmpush.activity.FirebaseCloudMessage;
import com.swein.framework.module.fcmpush.receiver.FirebaseIntentReceiver;
import com.swein.framework.module.noticenotification.NoticeNotificationManager;
import com.swein.framework.module.noticenotification.constants.NoticeConstants;
import com.swein.framework.tools.util.bitmaps.BitmapUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.eventsplitshot.eventcenter.EventCenter;
import com.swein.framework.tools.util.eventsplitshot.subject.ESSArrows;
import com.swein.shandroidtoolutils.MainActivity;
import com.swein.shandroidtoolutils.R;

import java.util.HashMap;
import java.util.Map;

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

            Map<String, String> map = remoteMessage.getData();

            String msgType = map.get("msgType");
            String title = map.get("title");
            String message = map.get("message");
            String longMessage = map.get("longMessage");
            String imageUrl = map.get("imageUrl");
            String bigIcon = map.get("bigIcon");
            String badge = map.get("badge");

            switch (msgType) {
                case "SHORT_NORMAL": {

                    NoticeNotificationManager.getInstance().createNoticeNotificationBefore8_0(getApplicationContext(), NoticeConstants.Type.SHORT_NORMAL,
                            title, message, null, true, R.mipmap.ic_launcher, null, null, null,
                            MainActivity.class, 1, 1);

                    break;
                }
                case "SHORT_BIG": {

                    Bitmap bitmap = BitmapUtil.getBitmapFromUrl(bigIcon);
                    NoticeNotificationManager.getInstance().createNoticeNotificationBefore8_0(getApplicationContext(), NoticeConstants.Type.SHORT_BIG,
                            title, message, null, true, R.mipmap.ic_launcher, bitmap, null, null,
                            MainActivity.class, 1, 1);
                    break;
                }
                case "LONG_BIG": {

                    Bitmap bitmap = BitmapUtil.getBitmapFromUrl(bigIcon);

                    NoticeNotificationManager.getInstance().createNoticeNotificationBefore8_0(getApplicationContext(), NoticeConstants.Type.LONG_BIG,
                            title, message, null, true, R.mipmap.ic_launcher, bitmap, longMessage, null,
                            MainActivity.class, 3, 3);
                    break;
                }
                case "BIG_IMAGE": {

                    Bitmap icon = BitmapUtil.getBitmapFromUrl(bigIcon);
                    Bitmap image = BitmapUtil.getBitmapFromUrl(imageUrl);

                    NoticeNotificationManager.getInstance().createNoticeNotificationBefore8_0(getApplicationContext(), NoticeConstants.Type.BIG_IMAGE,
                            title, message, null, true, R.mipmap.ic_launcher, icon, null, image,
                            MainActivity.class, 4, 4);
                    break;
                }

                case "HEADS_UP": {
                    Bitmap icon = BitmapUtil.getBitmapFromUrl(bigIcon);

                    NoticeNotificationManager.getInstance().createNoticeNotificationBefore8_0(getApplicationContext(), NoticeConstants.Type.HEADS_UP,
                            title, message, null, true, R.mipmap.ic_launcher, icon, null, null,
                            MainActivity.class, 5, 5);
                    break;
                }
            }
            /*
            data for app run background
             */
            ILog.iLogDebug(TAG, "getData : " + remoteMessage.getData());

//            final SHMDMDeviceManager shmdmDeviceManager = new SHMDMDeviceManager(this);
//
//            if(!shmdmDeviceManager.isActive()) {
//
//                return;
//            }
//
//            switch (remoteMessage.getData().get("command")) {
//
//                case "disable_camera":
//
//                    ThreadUtil.startThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            shmdmDeviceManager.disableCamera();
//                            EventCenter.getInstance().sendEvent(ESSArrows.ESS_DEVICE_DISABLE_CAMERA, this, null);
//                        }
//                    });
//
//                    break;
//
//                case "enable_camera":
//
//                    ThreadUtil.startThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            shmdmDeviceManager.enableCamera();
//                            EventCenter.getInstance().sendEvent(ESSArrows.ESS_DEVICE_ENABLE_CAMERA, this, null);
//                        }
//                    });
//
//                    break;
//
//                case "disable_screen_capture":
//
//                    ThreadUtil.startThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            shmdmDeviceManager.disableScreenCapture();
//                            EventCenter.getInstance().sendEvent(ESSArrows.ESS_DEVICE_DISABLE_SCREEN_CAPTURE, this, null);
//                        }
//                    });
//
//                    break;
//
//                case "enable_screen_capture":
//
//                    ThreadUtil.startThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            shmdmDeviceManager.enableScreenCapture();
//                            EventCenter.getInstance().sendEvent(ESSArrows.ESS_DEVICE_ENABLE_SCREEN_CAPTURE, this, null);
//                        }
//                    });
//
//                    break;
//            }

            return;
        }



        ILog.iLogDebug(TAG, "push from sender id : " + remoteMessage.getFrom());

        String title = "";
        String body = "";
        String imageUrl = "";

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
            imageUrl = remoteMessage.getNotification().getClickAction();

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
//
//        Bundle bundle = new Bundle();
//
//        bundle.putString("title", title);
//        bundle.putString("body", body);
//        bundle.putString("link", link);
//
//        intent.putExtra("firebase", bundle);


    }
}
