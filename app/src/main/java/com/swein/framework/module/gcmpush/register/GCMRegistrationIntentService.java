package com.swein.framework.module.gcmpush.register;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

/**
 *
 * send request registration to GCM
 *
 * Created by seokho on 07/02/2018.
 */
public class GCMRegistrationIntentService extends IntentService {

    private static final String TAG = "GCMRegistration";

    public GCMRegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        registerGoogleCloudMessage();
    }

    /**
     * Google Cloud Message register and get instanceID, token
     */
//    private void registerGoogleCloudMessage() {
//
//        final String token;
//        final String instanceId;
//
//        final WeakReference<Context> contextWeakReference = new WeakReference<Context>(getApplication());
//
//        try {
//            InstanceID instanceID = InstanceID.getInstance(contextWeakReference.get());
//            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//            instanceId = instanceID.getId();
//
//            ILog.iLogDebug(TAG, "token:" + token);
//            ILog.iLogDebug(TAG, "instanceId:" + instanceId);
//
//            // TODO save token and instanceId to your server
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            ILog.iLogError(TAG, "GCM Registration Error");
//
//        }
//    }
}