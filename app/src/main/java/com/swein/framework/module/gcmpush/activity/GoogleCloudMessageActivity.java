package com.swein.framework.module.gcmpush.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.swein.framework.module.gcmpush.register.GCMRegistrationIntentService;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;


/**
 * Android tutorial: Push notification using Google Cloud Messaging (GCM)
 */
public class GoogleCloudMessageActivity extends Activity {


    private final static String TAG = "GoogleCloudMessageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_cloud_message);

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

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
