package com.swein.framework.module.noticenotification;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.swein.framework.module.noticenotification.constants.NoticeConstants;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

import io.reactivex.annotations.Nullable;

public class NoticeNotificationManager {

    public interface NoticeNotificationManagerDelegate {
        void input(String string);
    }


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


    }

    /**
     * API must >= android 7.0 (N)
     * than action can be usable
     * @param context
     */
    @TargetApi(Build.VERSION_CODES.N)
    public void createActionNoticeNotification(Context context, String title, String message, boolean autoCancel, boolean headsUp, NoticeNotificationManagerDelegate noticeNotificationManagerDelegate) {

        Intent quickIntent = new Intent();
        quickIntent.setAction("quick.reply.input");

        Notification notification;

        if(headsUp) {
            notification = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(autoCancel)
                    .setFullScreenIntent(PendingIntent.getActivity(context, 1, quickIntent,PendingIntent.FLAG_ONE_SHOT), true)
                    .addAction(
                            new Notification.Action.Builder(
                                    null,
                                    "간편입력",
                                    PendingIntent.getBroadcast(context, 1, quickIntent, PendingIntent.FLAG_ONE_SHOT))
                                    //input edit text view，key is quick_notification_reply
                                    .addRemoteInput(new RemoteInput.Builder("quick_notification_reply").setLabel("입력하세요.").build())
                                    .build())
                    .build();
        }
        else {
            notification = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(autoCancel)
                    .addAction(
                            new Notification.Action.Builder(
                                    null,
                                    "간편입력",
                                    PendingIntent.getBroadcast(context, 1, quickIntent, PendingIntent.FLAG_ONE_SHOT))
                                    //input edit text view，key is quick_notification_reply
                                    .addRemoteInput(new RemoteInput.Builder("quick_notification_reply").setLabel("입력하세요.").build())
                                    .build())
                    .build();
        }



        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.notify(1, notification);


        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle results = RemoteInput.getResultsFromIntent(intent);
                if (results != null) {
                    CharSequence result = results.getCharSequence("quick_notification_reply");
                    if (TextUtils.isEmpty(result)) {
                        ToastUtil.showCustomShortToastNormal(context, "내용이 없습니다.");
                    }
                    else {
                        ToastUtil.showCustomShortToastNormal(context, result.toString());

                        noticeNotificationManagerDelegate.input(result.toString());
                    }
                }
                notificationManager.cancelAll();
                context.unregisterReceiver(this);

            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addCategory(context.getPackageName());
        filter.addAction("quick.reply.input");
        context.registerReceiver(br, filter);

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
    public void createNoticeNotificationBefore8_0(Context context, NoticeConstants.Type type,
                                            String title, String message, @Nullable String ticker, boolean autoCancel, int smallIcon, @Nullable Bitmap largeIcon,
                                            @Nullable String longMessage, @Nullable Bitmap bigImage,
                                            Class<?> targetClass, int noticeID, int requestCode) {


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Intent intent = new Intent(context, targetClass);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, Notification.FLAG_AUTO_CANCEL);

        switch (type) {
            case SHORT_NORMAL: {

                builder.setContentTitle(title)
                        .setContentText(message);
                builder.setContentIntent(pendingIntent);
                largeIcon = null;
                break;
            }
            case SHORT_BIG: {

                builder.setContentTitle(title)
                        .setContentText(message);
                builder.setContentIntent(pendingIntent);
                break;
            }
            case LONG_BIG: {

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


    public void createNoticeNotificationAfter8_0(Context context, NoticeConstants.Type type,
                                                 String title, String message, String subText, boolean autoCancel, int smallIcon, @Nullable Bitmap largeIcon,
                                                 @Nullable String longMessage, @Nullable Bitmap bigImage,
                                                 Class<?> targetClass, int noticeID, int requestCode, String channelID, String channelGroupID, String channelGroupName, boolean defaultSound) {

        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context, channelID);

        if(defaultSound) {
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(sound);
        }


        Intent intent = new Intent(context, targetClass);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, Notification.FLAG_AUTO_CANCEL);

        switch (type) {
            case SHORT_NORMAL: {

                builder.setContentTitle(title)
                        .setContentText(message);
                builder.setContentIntent(pendingIntent);
                largeIcon = null;
                break;
            }
            case SHORT_BIG: {

                builder.setContentTitle(title)
                        .setContentText(message);
                builder.setContentIntent(pendingIntent);
                break;
            }
            case LONG_BIG: {

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
                .setSubText(subText)
                .setContentTitle(title)
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(autoCancel)
                .setDefaults(Notification.DEFAULT_ALL);

        if(largeIcon != null) {
            builder.setLargeIcon(largeIcon);
        }

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        // must set if Android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannelId(notificationManager, channelID, channelGroupID, channelGroupName);
        }

        notificationManager.notify(noticeID, builder.build());

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannelId(NotificationManager notificationManager, String channelId, String channelGroupID, String channelGroupName){

        NotificationChannelGroup ncp1 = new NotificationChannelGroup(channelGroupID, channelGroupName);

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
