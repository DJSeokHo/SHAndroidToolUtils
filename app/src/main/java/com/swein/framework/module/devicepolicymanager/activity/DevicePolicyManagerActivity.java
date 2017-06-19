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
    private Button buttonScreenLock;
    private Button buttonScreenLockTime;
    private Button buttonResetPassword;
    private Button buttonCameraAble;
    private Button buttonCameraDisAble;
    private Button buttonClearStorage;
    private Button buttonClearStorageAndSDCard;

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.buttonActivate){
                getDeviceManage.activate();
            }
            else if (id == R.id.buttonScreenLock){
                getDeviceManage.lock();
            }
            else if (id == R.id.buttonScreenLockTime){
                getDeviceManage.setmaxtimelock(0);
            }
            else if (id == R.id.buttonResetPassword){
                getDeviceManage.setpassword("6666");
            }
            else if (id == R.id.buttonCameraAble){
                if (getDeviceManage.getcamera() != true)
                    getDeviceManage.setcamera(true);
            }
            else if (id == R.id.buttonCameraDisAble){
                if (getDeviceManage.getcamera() != false)
                    getDeviceManage.setcamera(false);
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
        buttonScreenLock = (Button) findViewById(R.id.buttonScreenLock);
        buttonScreenLockTime = (Button) findViewById(R.id.buttonScreenLockTime);
        buttonResetPassword = (Button) findViewById(R.id.buttonResetPassword);
        buttonCameraAble = (Button) findViewById(R.id.buttonCameraAble);
        buttonCameraDisAble = (Button) findViewById(R.id.buttonCameraDisAble);
        buttonClearStorage = (Button) findViewById(R.id.buttonClearStorage);
        buttonClearStorageAndSDCard = (Button) findViewById(R.id.buttonClearStorageAndSDCard);

        buttonActivate.setOnClickListener(listener);
        buttonScreenLock.setOnClickListener(listener);
        buttonScreenLockTime.setOnClickListener(listener);
        buttonResetPassword.setOnClickListener(listener);
        buttonCameraAble.setOnClickListener(listener);
        buttonCameraDisAble.setOnClickListener(listener);
        buttonClearStorage.setOnClickListener(listener);
        buttonClearStorageAndSDCard.setOnClickListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateButtonStates();
    }

    void updateButtonStates() {
        boolean active = getDeviceManage.isactive();
        buttonActivate.setEnabled(!active);
        buttonScreenLock.setEnabled(active);
        buttonScreenLockTime.setEnabled(active);
        buttonResetPassword.setEnabled(active);
        buttonCameraAble.setEnabled(active);
        buttonCameraDisAble.setEnabled(active);
        buttonClearStorage.setEnabled(active);
        buttonClearStorageAndSDCard.setEnabled(active);
    }

}
