package com.swein.framework.tools.util.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
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

}
