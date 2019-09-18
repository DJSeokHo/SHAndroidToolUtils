package com.swein.framework.module.filedownloadupload.networkstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.eventsplitshot.eventcenter.EventCenter;
import com.swein.framework.tools.util.eventsplitshot.subject.ESSArrows;

public class NetWorkStateReceiver extends BroadcastReceiver {

    private final static String TAG = "NetWorkStateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            ILog.iLogDebug(TAG, "wifiState:" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
            }
        }

        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {

            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {

                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        ILog.iLogDebug("TAG", getConnectionType(info.getType()) + "connect");
                    }
                }
                else {
                    EventCenter.getInstance().sendEvent(ESSArrows.NETWORK_DISCONNECTED, this, null);
                    ILog.iLogDebug("TAG", getConnectionType(info.getType()) + "disconnect");
                }
            }
        }

    }

    private String getConnectionType(int type) {
        String connType = "";
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "MOBILE";
        }
        else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI";
        }
        return connType;
    }

}
