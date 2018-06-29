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
import com.swein.framework.tools.util.dialog.DialogUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

public class KnoxMDMActivity extends Activity {

    private ProgressDialog progressDialog;

    private Button buttonActivateDeviceAdmin;
    private Button buttonDeactivateDeviceAdmin;

    private Switch switchDisableCamera;
    private Switch switchDisableScreenCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knox_mdm);

        if (!KnoxMDMManager.getInstance().isKnoxSdkSupported()) {
            showDeviceUnSupportedProblem();
            return;
        }

        findView();
        KnoxMDMManager.getInstance().register(this, knoxMDMManagerDelegate);

        if (KnoxMDMManager.getInstance().isDeviceAdminActivated(this)) {
            setDeviceAdminActivated();
        }
    }

    @Override
    protected void onResume() {
        checkMDMUI();
        super.onResume();
    }

    private void findView() {

        buttonActivateDeviceAdmin = findViewById(R.id.buttonActivateDeviceAdmin);
        buttonDeactivateDeviceAdmin = findViewById(R.id.buttonDeactivateDeviceAdmin);

        switchDisableCamera = findViewById(R.id.switchDisableCamera);
        switchDisableScreenCapture = findViewById(R.id.switchDisableScreenCapture);

        buttonActivateDeviceAdmin.setOnClickListener(onClickListener);
        buttonDeactivateDeviceAdmin.setOnClickListener(onClickListener);

        switchDisableCamera.setOnClickListener(onClickListener);
        switchDisableScreenCapture.setOnClickListener(onClickListener);

        buttonActivateDeviceAdmin.setEnabled(true);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.buttonActivateDeviceAdmin:
                    activateDeviceAdmin();
                    break;

                case R.id.buttonDeactivateDeviceAdmin:

                    KnoxMDMManager.getInstance().deactivateKnoxLicense(KnoxMDMActivity.this, Constants.KNOX_LICENSE_KEY);

                    if(KnoxMDMManager.getInstance().isLegacySdk()) {
                        KnoxMDMManager.getInstance().deactivateKnoxLicense(KnoxMDMActivity.this, Constants.BACKWARD_LICENSE_KEY);
                    }

                    KnoxMDMManager.getInstance().deactivateDeviceAdmin(KnoxMDMActivity.this);

                    buttonDeactivateDeviceAdmin.setEnabled(false);
                    buttonActivateDeviceAdmin.setEnabled(true);
                    switchDisableCamera.setEnabled(false);
                    switchDisableScreenCapture.setEnabled(false);

                    break;

                case R.id.switchDisableCamera:

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

            saveKnoxLicenseActivationStateToSharedPreference();
            if (KnoxMDMManager.getInstance().isLegacySdk()) {
                activateBackwardLicense();
            }
            else {
                setLicenseActivationSuccess();
            }
        }

        @Override
        public void onBackwardLicenseActivated() {
            saveBackwardLicenseActivationStateToSharedPreference();
            setLicenseActivationSuccess();
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
        buttonActivateDeviceAdmin.setEnabled(false);
        buttonDeactivateDeviceAdmin.setEnabled(true);

        activateKnoxLicense();
    }

    private void setLicenseActivationSuccess() {

        hideLoadingDialog();
        // success
        ToastUtil.showShortToastNormal(this, "KNOX license activation success");

        switchDisableCamera.setEnabled(true);
        switchDisableScreenCapture.setEnabled(true);

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

    private void saveKnoxLicenseActivationStateToSharedPreference() {
        KnoxMDMPreference.setKLMLicenseActivated(this);
    }

    private void saveBackwardLicenseActivationStateToSharedPreference() {
        KnoxMDMPreference.setELMLicenseActivated(this);
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
        DialogUtil.createNormalDialogWithTwoButton(this, "License Activation Problem" , String.format("Something wrong while license activation.\\n Code : %s (%d)", errorMessage, errorType), false, "Retry", "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activateKnoxLicense();
                    }
                },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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
