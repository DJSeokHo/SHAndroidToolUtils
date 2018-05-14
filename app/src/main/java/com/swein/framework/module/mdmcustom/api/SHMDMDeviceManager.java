package com.swein.framework.module.mdmcustom.api;

import android.annotation.TargetApi;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.swein.framework.module.mdmcustom.receiver.SHMDMReceiver;
import com.swein.framework.tools.util.toast.ToastUtil;


public class SHMDMDeviceManager {

    private Context context;
    private ComponentName componentName;
    private DevicePolicyManager devicePolicyManager;

    private DeviceAdminReceiver deviceAdminReceiver;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SHMDMDeviceManager(Context context){
        this.context = context;
        devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(context, SHMDMReceiver.class);
    }

    public boolean isActive(){
        return devicePolicyManager.isAdminActive(componentName);
    }

    @TargetApi(Build.VERSION_CODES.M)
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
        if(isActive()) {
            devicePolicyManager.removeActiveAdmin(componentName);
        }
    }

    public void disableCamera(){
        if(!devicePolicyManager.getCameraDisabled(componentName)) {
            devicePolicyManager.setCameraDisabled(componentName, true);
        }
    }

    public void enableCamera() {
        if(devicePolicyManager.getCameraDisabled(componentName)) {
            devicePolicyManager.setCameraDisabled(componentName, false);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void disableScreenCapture() {
        if(!devicePolicyManager.getScreenCaptureDisabled(componentName)) {
            devicePolicyManager.setScreenCaptureDisabled(componentName, true);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void enableScreenCapture() {
        if(devicePolicyManager.getScreenCaptureDisabled(componentName)) {
            devicePolicyManager.setScreenCaptureDisabled(componentName, false);
        }
    }

    public boolean getCamera() {
        return devicePolicyManager.getCameraDisabled(componentName);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean getScreenCapture() {
        return devicePolicyManager.getScreenCaptureDisabled(null);
    }
}
