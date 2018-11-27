package com.swein.framework.module.noticenotification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.swein.framework.module.noticenotification.constants.NoticeConstants;

import io.reactivex.annotations.Nullable;

public class NoticeNotificationManager {

    private static NoticeNotificationManager instance = new NoticeNotificationManager();

    public static NoticeNotificationManager getInstance() {
        return instance;
    }

    private NoticeNotificationManager() {}

    /**
     *
     * @param context context
     * @param noticeID noticeID
     */
    public void cancelNoticeNotification(Context context, int noticeID) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancel(noticeID);

    }


    /**
     * how to use
     *
     * NoticeNotificationManager.getInstance().createNoticeNotification4_4(NoticeNotificationActivity.this, NoticeConstants.Type.NORMAL,
     * "normal title", "normal message", "coming", true, R.mipmap.ic_launcher, bitmap, null, null,
     * MainActivity.class, 1, 1);
     *
     * NoticeNotificationManager.getInstance().createNoticeNotification4_4(NoticeNotificationActivity.this, NoticeConstants.Type.LONG_TEXT,
     * "long title", "long message", "coming", true, R.mipmap.ic_launcher, bitmap, longMessage, null,
     * MainActivity.class, 2, 2);
     *
     * NoticeNotificationManager.getInstance().createNoticeNotification4_4(NoticeNotificationActivity.this, NoticeConstants.Type.BIG_IMAGE,
     * "big image title", "big image message", "coming", true, R.mipmap.ic_launcher, bitmap, null, bitmap,
     * MainActivity.class, 3, 3);
     *
     * @param context context
     * @param type NORMAL, LONG_TEXT, BIG_IMAGE
     * @param title title
     * @param message message or summary
     * @param ticker up animation
     * @param autoCancel autoCancel
     * @param smallIcon smallIcon
     * @param largeIcon largeIcon
     * @param longMessage longMessageList for LONG_TEXT type
     * @param bigImage bigImage for BIG_IMAGE type
     * @param targetClass targetClass
     * @param noticeID noticeID
     * @param requestCode requestCode
     */
    public void createNoticeNotification4_4Or5_0(Context context, NoticeConstants.Type type,
                                            String title, String message, @Nullable String ticker, boolean autoCancel, int smallIcon, @Nullable Bitmap largeIcon,
                                            @Nullable String longMessage, @Nullable Bitmap bigImage,
                                            Class<?> targetClass, int noticeID, int requestCode) {


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Intent intent = new Intent(context, targetClass);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, Notification.FLAG_AUTO_CANCEL);

        switch (type) {
            case NORMAL: {

                builder.setContentTitle(title)
                        .setContentText(message);
                builder.setContentIntent(pendingIntent);
                break;
            }
            case LONG_TEXT: {

                android.support.v4.app.NotificationCompat.BigTextStyle style = new android.support.v4.app.NotificationCompat.BigTextStyle();

                style.setBigContentTitle(title);

                if (longMessage != null && !longMessage.equals("")) {
                    style.bigText(longMessage);
                }

                style.setSummaryText(message);

                builder.setStyle(style);
                builder.setContentIntent(pendingIntent);
                break;
            }
            case BIG_IMAGE: {

                android.support.v4.app.NotificationCompat.BigPictureStyle style = new android.support.v4.app.NotificationCompat.BigPictureStyle();
                style.setBigContentTitle(title);
                style.setSummaryText(message);
                style.bigPicture(bigImage);

                builder.setStyle(style);
                builder.setContentIntent(pendingIntent);
                break;
            }
            case HEADS_UP: {

                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                    return;
                }

                builder.setContentTitle(title)
                        .setContentText(message)
                        .setFullScreenIntent(pendingIntent, true);

                break;
            }
        }

        builder.setSmallIcon(smallIcon)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(autoCancel)
                .setDefaults(Notification.DEFAULT_ALL);

        if(ticker != null && !ticker.equals("")) {
            builder.setTicker(ticker); // up animation
        }

        if(largeIcon != null) {
            builder.setLargeIcon(largeIcon);
        }

        assert notificationManager != null;
        notificationManager.notify(noticeID, builder.build());
    }


}
