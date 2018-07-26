package com.swein.framework.module.phonecallrecoder.callreceiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.swein.framework.tools.util.debug.log.ILog;

/**
 * add to manifest

 <receiver android:name=".IPCallerReceiver">
     <intent-filter>
        <action android:name="android.intent.action.NEW_OUTGOING_CALL"/>
     </intent-filter>
 </receiver>

 */
public class PhoneCallReceiver extends BroadcastReceiver {

    public interface PhoneCallReceiverDelegate {
        void onReceiveCallNumber(String number);
    }

    private final static String TAG = "PhoneCallReceiver";

    private PhoneCallReceiverDelegate phoneCallReceiverDelegate;

    public PhoneCallReceiver(PhoneCallReceiverDelegate phoneCallReceiverDelegate) {
        this.phoneCallReceiverDelegate = phoneCallReceiverDelegate;
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    public void onReceive(Context context, Intent intent) {

        String number = getResultData();

        ILog.iLogDebug(TAG, number);

        phoneCallReceiverDelegate.onReceiveCallNumber(number);

    }
}