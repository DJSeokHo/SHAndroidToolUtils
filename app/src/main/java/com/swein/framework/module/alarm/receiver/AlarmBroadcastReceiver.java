package com.swein.framework.module.alarm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.swein.framework.tools.util.debug.log.ILog;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private final static String TAG = "AlarmBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if ("startAlarm".equals(intent.getAction())) {
            ILog.iLogDebug(TAG, "闹钟！！！！" + intent.toString());
            Toast.makeText(context, "闹钟提醒", Toast.LENGTH_LONG).show();
            // 处理闹钟事件
            // 振动、响铃、或者跳转页面等
        }
    }
}
