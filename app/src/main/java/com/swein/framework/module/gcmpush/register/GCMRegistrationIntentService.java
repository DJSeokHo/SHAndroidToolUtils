package com.swein.framework.module.gcmpush.register;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.swein.framework.module.gcmpush.constants.GCMConstants;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

/**
 *
 * send request registration to GCM
 *
 * Created by seokho on 07/02/2018.
 */
public class GCMRegistrationIntentService extends IntentService {

    private static final String TAG = "GCMRegistrationIntentService";


    public GCMRegistrationIntentService() {
        super("");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        registerGoogleCloudMessage();
    }

    /**
     * Google Cloud Message register and get instanceID, token
     */
    private void registerGoogleCloudMessage() {

        Intent intent;
        String token;

        try {
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            ILog.iLogDebug(TAG, "token:" + token);

            // TODO Update UI register complete
            intent = new Intent(GCMConstants.REGISTRATION_SUCCESS);
            intent.putExtra("token", token);
        }
        catch (Exception e) {
            e.printStackTrace();
            ILog.iLogError(TAG, "GCM Registration Error");

            intent = new Intent(GCMConstants.REGISTRATION_ERROR);
        }

        // TODO Send BoardCast
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}