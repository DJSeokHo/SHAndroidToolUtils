package com.swein.framework.module.gcmpush.receiver;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.swein.framework.module.gcmpush.activity.GoogleCloudMessageActivity;
import com.swein.framework.module.gcmpush.constants.GCMConstants;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.notification.NotificationUIUtil;

/**
 * Created by seokho on 07/02/2018.
 */

public class GCMPushReceiverService extends GcmListenerService {

    private final static String TAG = "GCMPushReceiverService";

    @Override
    public void onMessageReceived(String s, Bundle bundle) {

        String message = bundle.getString("message");
        ILog.iLogDebug(TAG, message + " " + bundle.toString() + " " + s + " " + GCMConstants.notificationId);
        sendNotification(message);
        GCMConstants.notificationId++;
    }

    private void sendNotification(String message) {

        Intent intent = new Intent(this, GoogleCloudMessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        NotificationUIUtil.sendNotification(this, GCMConstants.notificationId,
                "Google Cloud Message", "Sub Text", message,
                true, true,
                PendingIntent.getActivity(this, GCMConstants.notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT));

    }
}
