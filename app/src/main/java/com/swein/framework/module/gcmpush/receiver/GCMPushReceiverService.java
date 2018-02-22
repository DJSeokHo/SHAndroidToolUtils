package com.swein.framework.module.gcmpush.receiver;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;
import com.swein.framework.module.gcmpush.boardcast.NotificationBroadcastReceiver;
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

        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Bundle bundle = new Bundle();
        bundle.putString(GCMConstants.NOTIFICATION_CLICKED_KEY, GCMConstants.NOTIFICATION_CLICKED);

        intent.putExtra(GCMConstants.NOTIFICATION_CLICKED_BUNDLE_KEY, bundle);
//        NotificationUIUtil.sendNotification(this, GCMConstants.notificationId,
//                "Google Cloud Message", "Sub Text", message,
//                true, true,
//                PendingIntent.getActivity(this, GCMConstants.notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT));

        NotificationUIUtil.sendNotification(this, GCMConstants.notificationId,
                "Google Cloud Message", "Sub Text", message,
                true, true,
                PendingIntent.getBroadcast(this, GCMConstants.notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
