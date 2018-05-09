package com.swein.framework.module.mdmcustom.api;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.swein.framework.module.mdmcustom.receiver.CustomMDMReceiver;
import com.swein.framework.tools.util.toast.ToastUtil;


public class CustomMDMDeviceManager {

    private Context context;
    private ComponentName componentName;
    private DevicePolicyManager devicePolicyManager;

    public CustomMDMDeviceManager(Context context){
        this.context = context;
        devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(context, CustomMDMReceiver.class);
    }

    public boolean isActive(){
        return devicePolicyManager.isAdminActive(componentName);
    }

    public void activate(){

        if(!devicePolicyManager.isAdminActive(componentName)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "device activate");
            context.startActivity(intent);
        }
        else {
            ToastUtil.showShortToastNormal(context, "device already activated");
        }

    }

    public void removeActivate() {
        devicePolicyManager.removeActiveAdmin(componentName);
    }

    public void setCamera(boolean enabled){
        devicePolicyManager.setCameraDisabled(componentName, !enabled);
    }

    public boolean getCamera(){
        return !devicePolicyManager.getCameraDisabled(componentName);
    }

}
