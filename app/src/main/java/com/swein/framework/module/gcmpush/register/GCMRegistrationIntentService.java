package com.swein.framework.module.gcmpush.register;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

import java.lang.ref.WeakReference;

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
        registerGoogleCloudMessage();
    }

    /**
     * Google Cloud Message register and get instanceID, token
     */
    private void registerGoogleCloudMessage() {

        final String token;
        final String instanceId;

        final WeakReference<Context> contextWeakReference = new WeakReference<Context>(getApplication());

        try {
            InstanceID instanceID = InstanceID.getInstance(contextWeakReference.get());
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            instanceId = instanceID.getId();

            ILog.iLogDebug(TAG, "token:" + token);
            ILog.iLogDebug(TAG, "instanceId:" + instanceId);

            ThreadUtil.startUIThread(0, new Runnable() {
                @Override
                public void run() {
                    // here for UI update
                    ToastUtil.showCustomShortToastNormal(contextWeakReference.get(), instanceId + " " + token);
                }
            });

        }
        catch (Exception e) {
            e.printStackTrace();
            ILog.iLogError(TAG, "GCM Registration Error");

        }


    }
}