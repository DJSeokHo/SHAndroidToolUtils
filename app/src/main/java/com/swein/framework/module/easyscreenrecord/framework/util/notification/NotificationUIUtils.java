package com.swein.framework.module.easyscreenrecord.framework.util.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 22/11/2016.
 */

public class NotificationUIUtils {

    public static void sendNotification(Context context, int id, String title, String subText, String message, boolean autoCancel, boolean defaultSound, PendingIntent pendingIntent) {

        NotificationCompat.Builder builder;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        if(defaultSound) {

            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(bitmap)
//                   .setSubText(subText)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(autoCancel)
                    .setContentIntent(pendingIntent)
                    .setSound(sound);

        }
        else {
            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(bitmap)
//                   .setSubText(subText)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(autoCancel)
                    .setContentIntent(pendingIntent);
        }


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());

        if(!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }


    public static void sendNotification(Context context, int id, String title, String subText, String message, boolean autoCancel, boolean defaultSound, PendingIntent pendingIntent,
                                        String channelID, String channelGroupID, String channelGroupName) {

        // must set if Android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelId(context, channelID, channelGroupID, channelGroupName);
        }

        NotificationCompat.Builder builder;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        if(defaultSound) {
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            builder = new NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(bitmap)
                    .setSubText(subText)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(autoCancel)
                    .setContentIntent(pendingIntent)
                    .setSound(sound);

        }
        else {
            builder = new NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(bitmap)
                    .setSubText(subText)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(autoCancel)
                    .setContentIntent(pendingIntent);
        }


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }

    public static void clearAllNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private static void createChannelId(Context context, String channelId, String channelGroupID, String channelGroupName){

        NotificationChannelGroup ncp1 = new NotificationChannelGroup(channelGroupID, channelGroupName);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannelGroup(ncp1);

        NotificationChannel chan = new NotificationChannel(channelId,
                channelGroupName,
                NotificationManager.IMPORTANCE_DEFAULT);
        chan.setLightColor(Color.GREEN);
        chan.setGroup(channelGroupID);

        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(chan);
    }

}
