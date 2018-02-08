package com.swein.framework.module.gcmpush.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.swein.framework.module.gcmpush.constants.GCMConstants;
import com.swein.framework.module.gcmpush.register.GCMRegistrationIntentService;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;


/**
 * Android tutorial: Push notification using Google Cloud Messaging (GCM)
 */
public class GoogleCloudMessageActivity extends Activity {


    private final static String TAG = "GoogleCloudMessageActivity";

    private BroadcastReceiver registrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_cloud_message);

        registrationBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                ILog.iLogDebug(TAG, intent == null);

                if(intent == null) {
                    return;
                }

                // Check type of intent filter
                if(intent.getAction().endsWith(GCMConstants.REGISTRATION_SUCCESS)) {
                    String token = intent.getStringExtra("token");
                    ToastUtil.showShortToastNormal(GoogleCloudMessageActivity.this, "GCM token:" + token);
                }
                else if(intent.getAction().equals(GCMConstants.REGISTRATION_ERROR)) {
                    ToastUtil.showShortToastNormal(GoogleCloudMessageActivity.this, "GCM Registration Error");
                }
            }
        };


        // Check status of Google play service in device
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(ConnectionResult.SUCCESS != resultCode) {

            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                ToastUtil.showShortToastNormal(GoogleCloudMessageActivity.this, "Device have no google play service");

                GooglePlayServicesUtil.showErrorNotification(resultCode, this);
            }
            else {
                ToastUtil.showShortToastNormal(GoogleCloudMessageActivity.this, "Device not support google play service");
            }

        }
        else {
            Intent intent = new Intent(this, GCMRegistrationIntentService.class);
            startService(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver,
                new IntentFilter(GCMConstants.REGISTRATION_SUCCESS));
        LocalBroadcastManager.getInstance(this).registerReceiver(registrationBroadcastReceiver,
                new IntentFilter(GCMConstants.REGISTRATION_ERROR));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(registrationBroadcastReceiver);
    }
}
