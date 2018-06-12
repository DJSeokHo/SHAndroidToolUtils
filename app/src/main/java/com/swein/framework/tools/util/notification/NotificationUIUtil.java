package com.swein.framework.tools.util.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.swein.shandroidtoolutils.R;

/**
 * Created by seokho on 22/11/2016.
 */

public class NotificationUIUtil {

   public static void sendNotification(Context context, int id, String title, String subText, String message, boolean autoCancel, boolean defaultSound, PendingIntent pendingIntent) {

       NotificationCompat.Builder builder;

       if(defaultSound) {
           Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

           builder = new NotificationCompat.Builder(context)
                   .setSmallIcon(R.drawable.aperture)
                   .setSubText(subText)
                   .setContentTitle(title)
                   .setContentText(message)
                   .setAutoCancel(autoCancel)
                   .setContentIntent(pendingIntent)
                   .setSound(sound);

       }
       else {
           builder = new NotificationCompat.Builder(context)
                   .setSmallIcon(R.drawable.aperture)
                   .setSubText(subText)
                   .setContentTitle(title)
                   .setContentText(message)
                   .setAutoCancel(autoCancel)
                   .setContentIntent(pendingIntent);
       }


       NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
       notificationManager.notify(id, builder.build());

   }


    public static void sendNotification(Context context, int id, String title, String subText, String message, boolean autoCancel, boolean defaultSound, PendingIntent pendingIntent,
                                        String channelID, String channelGroupID, String channelGroupName) {

        // must set if Android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelId(context, channelID, channelGroupID, channelGroupName);
        }

        NotificationCompat.Builder builder;

        if(defaultSound) {
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            builder = new NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.drawable.aperture)
                    .setSubText(subText)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(autoCancel)
                    .setContentIntent(pendingIntent)
                    .setSound(sound);

        }
        else {
            builder = new NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.drawable.aperture)
                    .setSubText(subText)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(autoCancel)
                    .setContentIntent(pendingIntent);
        }


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());

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
