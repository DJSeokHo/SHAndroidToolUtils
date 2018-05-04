package com.swein.framework.module.fcmpush.activity;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

/**
 *
 * reference documentation
 *
 * https://github.com/firebase/quickstart-android/blob/master/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/MainActivity.java
 * https://console.firebase.google.com/project/your_project_name/notification
 * https://firebase.google.com/docs/cloud-messaging/android/client
 *
 */
public class FirebaseCloudMessage extends Activity {

    private final static String TAG = "FirebaseCloudMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_cloud_message);

        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);

        // Get token
        String token = FirebaseInstanceId.getInstance().getToken();
        ILog.iLogDebug(TAG, token);
    }
}