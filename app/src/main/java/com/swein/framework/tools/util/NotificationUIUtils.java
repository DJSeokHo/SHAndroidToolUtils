package com.swein.framework.tools.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import com.swein.shandroidtoolutils.R;

import static android.app.Notification.DEFAULT_SOUND;

/**
 * Created by seokho on 22/11/2016.
 */

public class NotificationUIUtils {


    public static void sendNormalNotification(Context context, int notificationId, String ticker, String title, String message, int iconId, boolean autoCancel) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(context)
                    .setAutoCancel(autoCancel)
                    .setTicker(ticker)
                    .setSmallIcon(iconId)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setDefaults(DEFAULT_SOUND)
                    .setWhen(System.currentTimeMillis())
                    .build();
        }
        notificationManager.notify(notificationId, notification);
    }

    public static void sendContentIntentNotification(Context context, int notificationId, String ticker, String title, String message, int iconId, boolean autoCancel, PendingIntent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(context)
                    .setAutoCancel(autoCancel)
                    .setTicker(ticker)
                    .setSmallIcon(iconId)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setDefaults(DEFAULT_SOUND)
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(intent)
                    .build();
        }
        notificationManager.notify(notificationId, notification);
    }
}
