package com.swein.framework.module.devicepolicymanager.api;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.swein.framework.module.devicepolicymanager.receiver.DeviceReceiver;

/**
 * Created by seokho on 19/06/2017.
 */

public class GetDeviceManage {

    private Activity mActivity;
    private ComponentName componentName;
    private DevicePolicyManager devicePolicyManager;

    public GetDeviceManage(Activity activity){
        mActivity = activity;
        componentName = new ComponentName(mActivity, DeviceReceiver.class);
        devicePolicyManager = (DevicePolicyManager) activity.getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    public boolean isactive(){
        return devicePolicyManager.isAdminActive(componentName);
    }

    public void activate(){
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "click 'activate' to turn on device manager. if not activated, feature is not supported");
        mActivity.startActivityForResult(intent, Activity.RESULT_OK);
    }

    public void lock(){
        devicePolicyManager.lockNow();
    }

    public void setmaxtimelock(long time){
        devicePolicyManager.setMaximumTimeToLock(componentName, time);
    }

    public void setpassword(String password){
        devicePolicyManager.resetPassword(password, 0);
    }

    public void setcamera(boolean enabled){
        devicePolicyManager.setCameraDisabled(componentName, !enabled);
    }

    public boolean getcamera(){
        return !devicePolicyManager.getCameraDisabled(componentName);
    }

    public void wipe(int flags){
        devicePolicyManager.wipeData(flags);
    }

}
