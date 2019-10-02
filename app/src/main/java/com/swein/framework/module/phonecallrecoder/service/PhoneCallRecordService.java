package com.swein.framework.module.phonecallrecoder.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;

import android.os.IBinder;
import androidx.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.swein.framework.module.phonecallrecoder.callreceiver.PhoneCallReceiver;
import com.swein.framework.module.phonecallrecoder.phonestatelistener.PhoneCallStateListener;
import com.swein.shandroidtoolutils.R;

public class PhoneCallRecordService extends Service {

    private final static String TAG = "PhoneCallRecordService";

    private PhoneCallStateListener phoneStateListener;

    private PhoneCallReceiver phoneCallReceiver;

    public static final String SERVICE_NAME = "com.swein.framework.module.phonecallrecoder.service.PhoneCallRecordService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        createPhoneStateListener();
        registerPhoneCallReceiver();
        super.onCreate();
    }

    /**
     * to get call number
     */
    private void registerPhoneCallReceiver() {
        IntentFilter filter = new IntentFilter();
        // android.intent.action.NEW_OUTGOING_CALL
        filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        phoneCallReceiver = new PhoneCallReceiver(new PhoneCallReceiver.PhoneCallReceiverDelegate() {
            @Override
            public void onReceiveCallNumber(String number) {
                if(phoneStateListener != null) {
                    phoneStateListener.setCallNumber(number);
                }
            }
        });
        this.registerReceiver(phoneCallReceiver, filter);
    }

    /**
     * phone call state listener
     */
    private void createPhoneStateListener() {

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneCallStateListener(this);

        if(telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneCallStateListener.LISTEN_CALL_STATE);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotification();
        startForeground(101, createNotification());
        return super.onStartCommand(intent, flags, startId);
    }

    private Notification createNotification() {

        Notification.Builder builder = new Notification.Builder(this);

//        Intent intent = new Intent(this, MainActivity.class);
//        builder.setContentIntent(PendingIntent.getActivity(this, 0, intent, 0))

        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher))
                .setContentTitle("Phone Call Recorder")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("recording when you call")
                .setAutoCancel(false)
                .setWhen(System.currentTimeMillis());

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;

        return notification;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        destroyPhoneStateListener();
        unregisterReceiver(phoneCallReceiver);
        super.onDestroy();
    }

    private void destroyPhoneStateListener() {
        if(phoneStateListener != null) {
            ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
            phoneStateListener = null;
        }
    }



}
