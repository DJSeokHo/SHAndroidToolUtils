package com.swein.framework.module.knoxmdm.manager.interfaces;

public interface KnoxMDMManagerDelegate {

    void onDeviceAdminActivated();

    void onDeviceAdminActivationCancelled();

    void onKnoxLicenseActivated();

    void onBackwardLicenseActivated();

    void onLicenseActivateFailed(int errorCode, String errorMessage);
}
