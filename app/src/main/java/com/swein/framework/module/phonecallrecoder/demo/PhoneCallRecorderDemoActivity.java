package com.swein.framework.module.phonecallrecoder.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.swein.framework.module.phonecallrecoder.service.PhoneCallRecordService;
import com.swein.framework.tools.util.animation.AnimationUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.preferences.SharedPreferencesUtil;
import com.swein.framework.tools.util.services.ServiceUtil;
import com.swein.framework.tools.util.timer.TimerUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

import java.util.Timer;

/**
     add this permission


     <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>
     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     <uses-permission android:name="android.permission.RECORD_AUDIO"/>
     <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
     <uses-permission android:name="android.permission.CAPTURE_AUDIO_OUTPUT"
     tools:ignore="ProtectedPermissions"/>
     <uses-permission android:name="android.permission.CAPTURE_SECURE_VIDEO_OUTPUT"
     tools:ignore="ProtectedPermissions" />

 */
public class PhoneCallRecorderDemoActivity extends Activity {

    private final static String TAG = "PhoneCallRecorderDemoActivity";

    private final static String CALL_RECORD_STATE_KEY = "CALL_RECORD_STATE_KEY";
    private final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 10;

    private Switch switchService;
    private ImageView imageViewState;

    private float angle;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call_recorder_demo);

        angle = 0.0f;

        switchService = findViewById(R.id.switchService);
        imageViewState = findViewById(R.id.imageViewState);

        switchService.setChecked(SharedPreferencesUtil.getValue(this, CALL_RECORD_STATE_KEY, false));

        switchService.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(SharedPreferencesUtil.getValue(PhoneCallRecorderDemoActivity.this, CALL_RECORD_STATE_KEY, false)) {
                    SharedPreferencesUtil.putValue(PhoneCallRecorderDemoActivity.this, CALL_RECORD_STATE_KEY, false);
                }
                else {
                    SharedPreferencesUtil.putValue(PhoneCallRecorderDemoActivity.this, CALL_RECORD_STATE_KEY, true);
                }

                toggleService();

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.RECORD_AUDIO
                        },
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
            else {
                ILog.iLogDebug(TAG, "Permissions already granted success");
            }
        }

        checkService();

    }

    private void toggleService() {
        if(SharedPreferencesUtil.getValue(PhoneCallRecorderDemoActivity.this, CALL_RECORD_STATE_KEY, false)) {
            // start service
            startService(new Intent(this, PhoneCallRecordService.class));
        }
        else {
            // stop service
            stopService(new Intent(this, PhoneCallRecordService.class));
        }

        checkService();
    }

    private void checkService() {

        if(ServiceUtil.isServiceRunning(this, PhoneCallRecordService.SERVICE_NAME)) {
            setTimerTask();
        }
        else {
            cancelTimerTask();
        }
    }

    private void setTimerTask() {

        timer = TimerUtil.createTimerTask(0, 1500, new Runnable() {
            @Override
            public void run() {
                rotateUnderControlView();
            }
        });
    }

    private void cancelTimerTask() {
        if(timer != null) {
            timer.cancel();
        }
    }

    public void rotateUnderControlView() {
        angle += 90;

        AnimationUtil.setViewRotation(imageViewState, 1500, null, angle - 90, angle);

        if(angle >= 360) {
            angle = 0;
        }
    }

    @Override
    protected void onDestroy() {
        cancelTimerTask();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        if(MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ILog.iLogDebug(TAG, "Permission Granted");
            }
            else {
                ILog.iLogDebug(TAG, "Permission Failed");
                ToastUtil.showShortToastNormal(this, "You must allow permission record audio to your mobile device.");
                finish();
            }
        }

    }
}
