package com.swein.framework.module.knoxmdm.mdm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.swein.framework.module.knoxmdm.constants.Constants;
import com.swein.framework.module.knoxmdm.manager.KnoxMDMManager;
import com.swein.framework.module.knoxmdm.manager.interfaces.KnoxMDMManagerDelegate;
import com.swein.framework.module.knoxmdm.share.KnoxMDMPreference;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.dialog.DialogUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

/**
 *
 * order is :
 *
 * device admin first, then KLM licence, then ELM licence
 *
 */
public class KnoxMDMActivity extends Activity {

    private final static String TAG = "KnoxMDMActivity";

    private ProgressDialog progressDialog;

    private Button buttonActivateDeviceAdmin;
    private Button buttonDeactivateDeviceAdmin;

    private Button buttonActivateKLMLicense;
    private Button buttonDeactivateKLMLicense;

    private Button buttonActivateELMLicense;
    private Button buttonDeactivateELMLicense;

    private Switch switchDisableCamera;
    private Switch switchDisableScreenCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knox_mdm);

        KnoxMDMManager.getInstance().register(this, knoxMDMManagerDelegate);
        findView();

    }

    @Override
    protected void onResume() {
        checkMDMUI();
        super.onResume();
    }

    private void findView() {

        buttonActivateDeviceAdmin = findViewById(R.id.buttonActivateDeviceAdmin);
        buttonDeactivateDeviceAdmin = findViewById(R.id.buttonDeactivateDeviceAdmin);

        buttonActivateDeviceAdmin.setOnClickListener(onClickListener);
        buttonDeactivateDeviceAdmin.setOnClickListener(onClickListener);


        buttonActivateKLMLicense = findViewById(R.id.buttonActivateKLMLicense);
        buttonDeactivateKLMLicense = findViewById(R.id.buttonDeactivateKLMLicense);

        buttonActivateKLMLicense.setOnClickListener(onClickListener);
        buttonDeactivateKLMLicense.setOnClickListener(onClickListener);



        buttonActivateELMLicense = findViewById(R.id.buttonActivateELMLicense);
        buttonDeactivateELMLicense = findViewById(R.id.buttonDeactivateELMLicense);

        buttonActivateELMLicense.setOnClickListener(onClickListener);
        buttonDeactivateELMLicense.setOnClickListener(onClickListener);



        switchDisableCamera = findViewById(R.id.switchDisableCamera);
        switchDisableScreenCapture = findViewById(R.id.switchDisableScreenCapture);

        switchDisableCamera.setOnClickListener(onClickListener);
        switchDisableScreenCapture.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.buttonActivateDeviceAdmin:

                    if (KnoxMDMManager.getInstance().isDeviceAdminActivated(KnoxMDMActivity.this)) {
                        ToastUtil.showShortToastNormal(KnoxMDMActivity.this, "Device admin already activated");
                    }
                    else {
                        activateDeviceAdmin();
                    }

                    break;

                case R.id.buttonDeactivateDeviceAdmin:

                    if(KnoxMDMManager.getInstance().isDeviceAdminActivated(KnoxMDMActivity.this)) {
                        if(KnoxMDMManager.getInstance().deactivateDeviceAdmin(KnoxMDMActivity.this)) {
                            ToastUtil.showShortToastNormal(KnoxMDMActivity.this, "Device admin deactivated success");
                        }
                    }
                    else {
                        ToastUtil.showShortToastNormal(KnoxMDMActivity.this, "Device admin already deactivated");
                    }

                    break;

                case R.id.buttonActivateKLMLicense:

                    if (!KnoxMDMManager.getInstance().isKnoxSdkSupported()) {
                        showDeviceUnSupportedProblem();
                        return;
                    }

                    if(KnoxMDMPreference.isKLMLicenseActivated(KnoxMDMActivity.this)) {
                        ToastUtil.showShortToastNormal(KnoxMDMActivity.this, "Device KLM already activated");
                        return;
                    }

                    activateKnoxLicense();

                    break;

                case R.id.buttonDeactivateKLMLicense:


                    try {
                        if(!KnoxMDMPreference.isKLMLicenseActivated(KnoxMDMActivity.this)) {
                            ToastUtil.showShortToastNormal(KnoxMDMActivity.this, "Device KLM already deactivated");
                            return;
                        }

                        KnoxMDMManager.getInstance().setCamera(KnoxMDMActivity.this, true);
                        KnoxMDMManager.getInstance().setScreenCapture(KnoxMDMActivity.this, true);

                        KnoxMDMPreference.setKLMLicenseActivated(KnoxMDMActivity.this, false);

                        deactivateKnoxLicense();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case R.id.buttonActivateELMLicense:

                    if (!KnoxMDMManager.getInstance().isLegacySdk()) {
                        return;
                    }

                    if(KnoxMDMPreference.isELMLicenseActivated(KnoxMDMActivity.this)) {

                        ToastUtil.showShortToastNormal(KnoxMDMActivity.this, "Device ELM already activated");

                        return;
                    }

                    KnoxMDMManager.getInstance().setCamera(KnoxMDMActivity.this, true);
                    KnoxMDMManager.getInstance().setScreenCapture(KnoxMDMActivity.this, true);

                    activateBackwardLicense();

                    break;

                case R.id.buttonDeactivateELMLicense:

                    try {

                        if(!KnoxMDMPreference.isELMLicenseActivated(KnoxMDMActivity.this)) {
                            ToastUtil.showShortToastNormal(KnoxMDMActivity.this, "Device ELM already deactivated");
                            return;
                        }

                        if(KnoxMDMManager.getInstance().isLegacySdk()) {
                            KnoxMDMManager.getInstance().deactivateKnoxLicense(KnoxMDMActivity.this, Constants.BACKWARD_LICENSE_KEY);
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case R.id.switchDisableCamera:

                    if(!KnoxMDMPreference.isKLMLicenseActivated(KnoxMDMActivity.this)) {
                        return;
                    }

                    if(!KnoxMDMManager.getInstance().isLegacySdk()) {

                    }

                    if (switchDisableCamera.isChecked()){
                        KnoxMDMManager.getInstance().setCamera(KnoxMDMActivity.this, false);
                    }
                    else {
                        KnoxMDMManager.getInstance().setCamera(KnoxMDMActivity.this, true);
                    }

                    break;

                case R.id.switchDisableScreenCapture:

                    if (switchDisableScreenCapture.isChecked()){
                        KnoxMDMManager.getInstance().setScreenCapture(KnoxMDMActivity.this, false);
                    }
                    else {
                        KnoxMDMManager.getInstance().setScreenCapture(KnoxMDMActivity.this, true);
                    }

                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister activator manager callback
        KnoxMDMManager.getInstance().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle activity result from Knox automatically
        KnoxMDMManager.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    private KnoxMDMManagerDelegate knoxMDMManagerDelegate = new KnoxMDMManagerDelegate() {

        @Override
        public void onDeviceAdminActivated() {
            setDeviceAdminActivated();
        }

        @Override
        public void onDeviceAdminActivationCancelled() {
            showDeviceAdminActivationProblem();
        }

        @Override
        public void onKnoxLicenseActivated() {

            KnoxMDMPreference.setKLMLicenseActivated(KnoxMDMActivity.this, true);

            ILog.iLogDebug(TAG, "KNOX KLM license activation success");

            setKLMLicenseActivationSuccess();

        }

        @Override
        public void onBackwardLicenseActivated() {
            KnoxMDMPreference.setELMLicenseActivated(KnoxMDMActivity.this, true);

            ILog.iLogDebug(TAG, "KNOX ELM license activation success");

            setELMLicenseActivationSuccess();
        }

        @Override
        public void onLicenseActivateFailed(int errorType, String errorMessage) {
            hideLoadingDialog();
            showLicenseActivationProblem(errorType, errorMessage);
        }
    };

    /**
     * make phone be a device admin
     */
    private void activateDeviceAdmin() {

        KnoxMDMManager.getInstance().activateDeviceAdmin(this, null);

    }

    /**
     * make phone get a Samsung Knox license KLM
     */
    private void activateKnoxLicense() {
        showLoadingDialog();
        if (KnoxMDMPreference.isKLMLicenseActivated(this)) {
            knoxMDMManagerDelegate.onKnoxLicenseActivated();
        }
        else {
            KnoxMDMManager.getInstance().activateKnoxLicense(this, Constants.KNOX_LICENSE_KEY);
        }
    }

    private void deactivateKnoxLicense() {
        KnoxMDMManager.getInstance().deactivateKnoxLicense(this, Constants.KNOX_LICENSE_KEY);

    }

    /**
     * make phone get a Samsung Knox license ELM
     */
    private void activateBackwardLicense() {
        if (KnoxMDMPreference.isELMLicenseActivated(this)) {
            knoxMDMManagerDelegate.onBackwardLicenseActivated();
        }
        else {
            KnoxMDMManager.getInstance().activateBackwardLicense(this, Constants.BACKWARD_LICENSE_KEY);
        }
    }


    /**
     * check state
     */
    private void setDeviceAdminActivated() {
        ToastUtil.showShortToastNormal(this, "Device admin activation success");
    }

    /**
     * KLM license success
     */
    private void setKLMLicenseActivationSuccess() {

        hideLoadingDialog();
        // success
        ToastUtil.showShortToastNormal(this, "KNOX KLM license activation success");

        checkMDMUI();
    }

    /**
     * ELM license success
     */
    private void setELMLicenseActivationSuccess() {

        hideLoadingDialog();
        // success
        ToastUtil.showShortToastNormal(this, "KNOX ELM license activation success");

        checkMDMUI();
    }

    private void checkMDMUI() {

        if(switchDisableCamera.isEnabled()) {
            if(KnoxMDMManager.getInstance().isCameraDisabled(this)) {
                switchDisableCamera.setChecked(true);
            }
            else {
                switchDisableCamera.setChecked(false);
            }
        }

        if(switchDisableScreenCapture.isEnabled()) {
            if(KnoxMDMManager.getInstance().isScreenCaptureDisabled(this)) {
                switchDisableScreenCapture.setChecked(true);
            }
            else {
                switchDisableScreenCapture.setChecked(false);
            }
        }

    }

    private void showDeviceUnSupportedProblem() {
        DialogUtil.createNormalDialogWithOneButton(this, "Device Problem", "This device does not support by KNOX Standard SDK.", false, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
    }

    private void showDeviceAdminActivationProblem() {
        DialogUtil.createNormalDialogWithTwoButton(this, "Setup Problem", "Device Administration is required to enable. Please enable it to use this app.", false, "Retry", "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activateDeviceAdmin();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
    }

    private void showLicenseActivationProblem(int errorType, String errorMessage) {

        DialogUtil.createNormalDialogWithOneButton(this, "License Activation Problem", String.format("Something wrong while license activation.\\n Code : %s (%d)", errorMessage, errorType), false, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

    }

    private void showLoadingDialog() {
        progressDialog = DialogUtil.createProgressDialog(this, "License Activating", "Loading...", false, false);
        progressDialog.show();
    }

    private void hideLoadingDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}