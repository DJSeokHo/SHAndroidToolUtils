package com.swein.framework.module.mdmcustom.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

import com.swein.framework.tools.util.toast.ToastUtil;

public class CustomMDMReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        ToastUtil.showShortToastNormal(context, "device manager:usable");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        ToastUtil.showShortToastNormal(context, "device manager:un-usable");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "device manager 'activate canceled'，feature is not supported";
    }

}
