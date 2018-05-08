package com.swein.framework.module.fcmpush.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.toast.ToastUtil;

public class FirebaseIntentReceiver extends BroadcastReceiver {


    private static final String TAG = "FirebaseIntentReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {

        // TODO Auto-generated method stub

        ILog.iLogDebug(TAG, intent.getAction());

        if (intent.getAction().equals("notification_clicked")) {

            Bundle bundle = intent.getBundleExtra("firebase");

            final String title = bundle.getString("title") != null ? bundle.getString("title"): "";
            final String body = bundle.getString("body") != null ? bundle.getString("body"): "";
            final String link = bundle.getString("link") != null ? bundle.getString("link"): "";

            ThreadUtil.startUIThread(0, new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showShortToastNormal(context, title + "\n" + body + "\n" + link);

                    Uri uri = Uri.parse("https://" + link);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }


    }
}
