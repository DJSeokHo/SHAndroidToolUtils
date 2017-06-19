package com.swein.framework.module.devicepolicymanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.swein.framework.module.devicepolicymanager.api.GetDeviceManage;
import com.swein.shandroidtoolutils.R;

public class DevicePolicyManagerActivity extends AppCompatActivity {

    private GetDeviceManage getDeviceManage;
    private Button buttonActivate;
    private Button buttonRemoveActivate;
    private Button buttonScreenLock;
    private Button buttonScreenLockTime;
    private Button buttonResetPassword;
    private Button buttonCameraAble;
    private Button buttonCameraDisAble;
    private Button buttonClearStorage;
    private Button buttonClearStorageAndSDCard;

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.buttonActivate){
                getDeviceManage.activate();
            }
            else if (id == R.id.buttonRemoveActivate) {
                if(getDeviceManage.isActive()) {
                    getDeviceManage.removeActivate();
                    buttonActivate.setEnabled(true);

                    buttonRemoveActivate.setEnabled(false);
                    buttonScreenLock.setEnabled(false);
                    buttonScreenLockTime.setEnabled(false);
                    buttonResetPassword.setEnabled(false);
                    buttonCameraAble.setEnabled(false);
                    buttonCameraDisAble.setEnabled(false);
                    buttonClearStorage.setEnabled(false);
                    buttonClearStorageAndSDCard.setEnabled(false);
                }
            }
            else if (id == R.id.buttonScreenLock){
                getDeviceManage.lock();
            }
            else if (id == R.id.buttonScreenLockTime){
                getDeviceManage.setMaxTimeLock(5000);
            }
            else if (id == R.id.buttonResetPassword){
                getDeviceManage.setPassword("6666");
            }
            else if (id == R.id.buttonCameraAble){
                if (getDeviceManage.getCamera() != true)
                    getDeviceManage.setCamera(true);
            }
            else if (id == R.id.buttonCameraDisAble){
                if (getDeviceManage.getCamera() != false)
                    getDeviceManage.setCamera(false);
            }
            else if (id == R.id.buttonClearStorage){
                getDeviceManage.wipe(0);
            }
            else if (id == R.id.buttonClearStorageAndSDCard){
                getDeviceManage.wipe(1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_policy_manager);

        getDeviceManage = new GetDeviceManage(DevicePolicyManagerActivity.this);

        buttonActivate = (Button) findViewById(R.id.buttonActivate);
        buttonRemoveActivate = (Button) findViewById(R.id.buttonRemoveActivate);
        buttonScreenLock = (Button) findViewById(R.id.buttonScreenLock);
        buttonScreenLockTime = (Button) findViewById(R.id.buttonScreenLockTime);
        buttonResetPassword = (Button) findViewById(R.id.buttonResetPassword);
        buttonCameraAble = (Button) findViewById(R.id.buttonCameraAble);
        buttonCameraDisAble = (Button) findViewById(R.id.buttonCameraDisAble);
        buttonClearStorage = (Button) findViewById(R.id.buttonClearStorage);
        buttonClearStorageAndSDCard = (Button) findViewById(R.id.buttonClearStorageAndSDCard);

        buttonActivate.setOnClickListener(onClickListener);
        buttonRemoveActivate.setOnClickListener(onClickListener);
        buttonScreenLock.setOnClickListener(onClickListener);
        buttonScreenLockTime.setOnClickListener(onClickListener);
        buttonResetPassword.setOnClickListener(onClickListener);
        buttonCameraAble.setOnClickListener(onClickListener);
        buttonCameraDisAble.setOnClickListener(onClickListener);
        buttonClearStorage.setOnClickListener(onClickListener);
        buttonClearStorageAndSDCard.setOnClickListener(onClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateButtonStates();
    }

    void updateButtonStates() {
        boolean active = getDeviceManage.isActive();
        buttonActivate.setEnabled(!active);
        buttonRemoveActivate.setEnabled(active);
        buttonScreenLock.setEnabled(active);
        buttonScreenLockTime.setEnabled(active);
        buttonResetPassword.setEnabled(active);
        buttonCameraAble.setEnabled(active);
        buttonCameraDisAble.setEnabled(active);
        buttonClearStorage.setEnabled(active);
        buttonClearStorageAndSDCard.setEnabled(active);
    }

}
