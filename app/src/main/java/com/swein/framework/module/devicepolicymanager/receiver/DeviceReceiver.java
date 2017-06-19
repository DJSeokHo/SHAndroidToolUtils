package com.swein.framework.module.devicepolicymanager.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

import com.swein.framework.tools.util.toast.ToastUtils;

/**
 * Created by seokho on 19/06/2017.
 */

public class DeviceReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "device manager:usable");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "device manager:un-usable");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "device manager 'activate canceled'，feature is not supported";
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        showToast(context, "device manager:password changed");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        showToast(context, "device manager：change password failed");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        showToast(context, "device manager：change password success");
    }

    @Override
    public void onPasswordExpiring(Context context, Intent intent) {
        showToast(context, "device manager：password has expired");
    }

    public void showToast(Context context, CharSequence text){
        ToastUtils.showShortToastNormal(context, text);
    }

}
