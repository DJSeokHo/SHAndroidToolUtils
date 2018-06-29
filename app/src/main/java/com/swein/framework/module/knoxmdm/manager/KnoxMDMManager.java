package com.swein.framework.module.knoxmdm.manager;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.swein.framework.module.knoxmdm.constants.Constants;
import com.swein.framework.module.knoxmdm.knoxlicenseactivator.device.KnoxDeviceAdminReceiver;
import com.swein.framework.module.knoxmdm.knoxlicenseactivator.receiver.KnoxSDKLicenseReceiver;
import com.swein.framework.module.knoxmdm.manager.interfaces.KnoxMDMManagerDelegate;

public class KnoxMDMManager {

    private static final int REQUEST_CODE_KNOX = 101;

    private static KnoxMDMManager knoxMDMManager;

    public static KnoxMDMManager getInstance() {

        if (knoxMDMManager == null) {
            knoxMDMManager = new KnoxMDMManager();
        }

        return knoxMDMManager;
    }

    private KnoxMDMManagerDelegate knoxMDMManagerDelegate;

    public void register(Context context, KnoxMDMManagerDelegate knoxMDMManagerDelegate) {

        this.knoxMDMManagerDelegate = knoxMDMManagerDelegate;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EnterpriseLicenseManager.ACTION_LICENSE_STATUS);
        intentFilter.addAction(KnoxSDKLicenseReceiver.ACTION_ELM_LICENSE_STATUS);
        intentFilter.addAction(KnoxSDKLicenseReceiver.ACTION_KLM_LICENSE_STATUS);
        context.registerReceiver(knoxLicenseReceiver, intentFilter);
    }

    public void unregister(Context context) {
        knoxMDMManagerDelegate = null;
        context.unregisterReceiver(knoxLicenseReceiver);
    }

    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_KNOX) {
            if (knoxMDMManagerDelegate != null) {
                if (resultCode == Activity.RESULT_OK) {
                    knoxMDMManagerDelegate.onDeviceAdminActivated();
                }
                else if (resultCode == Activity.RESULT_CANCELED) {
                    knoxMDMManagerDelegate.onDeviceAdminActivationCancelled();
                }
            }
        }
    }

    public void activateDeviceAdmin(Activity activity, String description) {

        ComponentName componentName = new ComponentName(activity, KnoxDeviceAdminReceiver.class);
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);

        if (description != null) {
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, description);
        }

        activity.startActivityForResult(intent, REQUEST_CODE_KNOX);
    }

    public boolean deactivateDeviceAdmin(Context context) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        if (devicePolicyManager != null) {

            ComponentName componentName = new ComponentName(context, KnoxDeviceAdminReceiver.class);
            devicePolicyManager.removeActiveAdmin(componentName);

            return true;
        }

        return false;
    }

    public void activateKnoxLicense(Context context, String licenseKey) {

        KnoxEnterpriseLicenseManager knoxEnterpriseLicenseManager = KnoxEnterpriseLicenseManager.getInstance(context);
        knoxEnterpriseLicenseManager.activateLicense(licenseKey);

    }

    public void deactivateKnoxLicense(Context context, String licenseKey) {

        KnoxEnterpriseLicenseManager knoxEnterpriseLicenseManager = KnoxEnterpriseLicenseManager.getInstance(context);
        knoxEnterpriseLicenseManager.deActivateLicense(licenseKey);

    }

    public void activateBackwardLicense(Context context, String licenseKey) {

        EnterpriseLicenseManager enterpriseLicenseManager = EnterpriseLicenseManager.getInstance(context);
        enterpriseLicenseManager.activateLicense(licenseKey);

    }

    public boolean isDeviceAdminActivated(Context context) {

        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, KnoxDeviceAdminReceiver.class);
        return devicePolicyManager != null && devicePolicyManager.isAdminActive(componentName);
    }

    public boolean isKnoxSdkSupported() {
        try {

            EnterpriseDeviceManager.getAPILevel();
            return true;

        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 카메라 Knox SDK 으로 차단
     *
     * @param activity
     * @param state
     */
    public void setCamera(Activity activity, boolean state) {

        RestrictionPolicy restrictionPolicy = EnterpriseDeviceManager.getInstance(activity).getRestrictionPolicy();
        restrictionPolicy.setCameraState(state);
    }

    /**
     * 화면 켑처 Knox SDK 으로 차단
     *
     * @param activity
     * @param state
     */
    public void setScreenCapture(Activity activity, boolean state) {

        RestrictionPolicy restrictionPolicy = EnterpriseDeviceManager.getInstance(activity).getRestrictionPolicy();
        restrictionPolicy.setScreenCapture(state);

    }

    public boolean isCameraDisabled(Activity activity) {
        RestrictionPolicy restrictionPolicy = EnterpriseDeviceManager.getInstance(activity).getRestrictionPolicy();
        return !restrictionPolicy.isCameraEnabled(false);
    }

    public boolean isScreenCaptureDisabled(Activity activity) {
        RestrictionPolicy restrictionPolicy = EnterpriseDeviceManager.getInstance(activity).getRestrictionPolicy();
        return !restrictionPolicy.isScreenCaptureEnabled(false);
    }

    public boolean isMdmApiSupported(int requiredVersion) {
        try {
            return EnterpriseDeviceManager.getAPILevel() < requiredVersion;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isLegacySdk() {
        try {
            return EnterpriseDeviceManager.getAPILevel() < Constants.KNOX_VERSION_CODES_2_8;
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }

        return false;
    }

    private KnoxSDKLicenseReceiver knoxLicenseReceiver = new KnoxSDKLicenseReceiver() {

        @Override
        public void onKnoxLicenseActivated(Context context) {
            if (knoxMDMManagerDelegate != null) {
                knoxMDMManagerDelegate.onKnoxLicenseActivated();
            }
        }

        @Override
        public void onBackwardLicenseActivated(Context context) {
            if (knoxMDMManagerDelegate != null) {
                knoxMDMManagerDelegate.onBackwardLicenseActivated();
            }
        }

        @Override
        public void onLicenseActivationFailed(Context context, int errorCode) {
            if (knoxMDMManagerDelegate != null) {
                knoxMDMManagerDelegate.onLicenseActivateFailed(errorCode, getErrorMessage(errorCode));
            }
        }
    };

    private String getErrorMessage(int errorCode) {

        switch (errorCode) {
            case EnterpriseLicenseManager.ERROR_INTERNAL:
                return "ERROR_INTERNAL";

            case EnterpriseLicenseManager.ERROR_INTERNAL_SERVER:
                return "ERROR_INTERNAL_SERVER";

            case EnterpriseLicenseManager.ERROR_INVALID_LICENSE:
                return "ERROR_INVALID_LICENSE";

            case EnterpriseLicenseManager.ERROR_INVALID_PACKAGE_NAME:
                return "ERROR_INVALID_PACKAGE_NAME";

            case EnterpriseLicenseManager.ERROR_LICENSE_TERMINATED:
                return "ERROR_LICENSE_TERMINATED";

            case EnterpriseLicenseManager.ERROR_NETWORK_DISCONNECTED:
                return "ERROR_NETWORK_DISCONNECTED";

            case EnterpriseLicenseManager.ERROR_NETWORK_GENERAL:
                return "ERROR_NETWORK_GENERAL";

            case EnterpriseLicenseManager.ERROR_NOT_CURRENT_DATE:
                return "ERROR_NOT_CURRENT_DATE";

            case EnterpriseLicenseManager.ERROR_NULL_PARAMS:
                return "ERROR_NULL_PARAMS";

            case EnterpriseLicenseManager.ERROR_SIGNATURE_MISMATCH:
                return "ERROR_SIGNATURE_MISMATCH";

            case EnterpriseLicenseManager.ERROR_USER_DISAGREES_LICENSE_AGREEMENT:
                return "ERROR_USER_DISAGREES_LICENSE_AGREEMENT";

            case EnterpriseLicenseManager.ERROR_VERSION_CODE_MISMATCH:
                return "ERROR_VERSION_CODE_MISMATCH";

            case EnterpriseLicenseManager.ERROR_UNKNOWN:

            default:
                return "ERROR_UNKNOWN";
        }
    }
}