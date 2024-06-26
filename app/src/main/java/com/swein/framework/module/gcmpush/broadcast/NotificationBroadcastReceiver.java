package com.swein.framework.module.gcmpush.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.swein.framework.module.gcmpush.activity.GoogleCloudMessageActivity;
import com.swein.framework.module.gcmpush.constants.GCMConstants;
import com.swein.framework.tools.util.toast.ToastUtil;

/**
 *
 * ********* add this part in your AndroidManifest.xml *********
     <receiver android:name=".push.broadcast.NotificationBroadcastReceiver">
         <intent-filter>
            <action android:name="com.android.broadcast.RECEIVER_ACTION"/>
         </intent-filter>
     </receiver>

 * Created by seokho on 22/02/2018.
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getBundleExtra(GCMConstants.NOTIFICATION_CLICKED_BUNDLE_KEY);

        if(bundle == null) {
            return;
        }

        /*
            Activity is running...
         */
        String message = bundle.getString(GCMConstants.NOTIFICATION_CLICKED_KEY);
        if(GoogleCloudMessageActivity.visible) {
            ToastUtil.showCustomShortToastNormal(context, message);
            return;
        }

        /*
            Activity is not running
         */
        Intent activityIntent = new Intent(context, GoogleCloudMessageActivity.class);
        activityIntent.putExtra(GCMConstants.NOTIFICATION_CLICKED_BUNDLE_KEY, bundle);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activityIntent);
    }
}
