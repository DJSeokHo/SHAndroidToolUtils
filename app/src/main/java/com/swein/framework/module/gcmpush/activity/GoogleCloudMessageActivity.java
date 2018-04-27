package com.swein.framework.module.gcmpush.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.swein.framework.module.gcmpush.constants.GCMConstants;
import com.swein.framework.module.gcmpush.register.GCMRegistrationIntentService;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;


/**
 * Android tutorial: Push notification using Google Cloud Messaging (GCM)
 *
 * reference documentation
 * https://developers.google.com/cloud-messaging/downstream
 * https://console.developers.google.com/apis/credentials?project=your_project_name
 */
public class GoogleCloudMessageActivity extends Activity {


    private final static String TAG = "GoogleCloudMessageActivity";


    public static boolean visible = false;

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

        getDataFromPushMessage();

    }

    private void getDataFromPushMessage() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(GCMConstants.NOTIFICATION_CLICKED_BUNDLE_KEY);
        if(bundle == null) {
            return;
        }

        String message = bundle.getString(GCMConstants.NOTIFICATION_CLICKED_KEY);

        ToastUtil.showCustomShortToastNormal(this, message);
    }


    @Override
    protected void onResume() {
        visible = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        visible = false;
        super.onPause();
    }
}
