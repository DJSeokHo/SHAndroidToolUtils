package com.swein.framework.module.easyscreenrecord;

import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.swein.framework.module.easyscreenrecord.data.singleton.ScreenRecordData;
import com.swein.framework.module.easyscreenrecord.manager.permission.PermissionManager;
import com.swein.framework.module.easyscreenrecord.manager.screenrecord.ScreenRecordManager;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

import static com.swein.framework.module.easyscreenrecord.data.global.key.NotificationKey.FROM_APP_UI_NOTIFICATION_CENTER;
import static com.swein.framework.module.easyscreenrecord.data.global.key.NotificationKey.TURN_OFF_SCREEN_RECORD_VIDEO_MODE;


public class EasyScreenRecordingActivity extends AppCompatActivity {

    private int backKeyPressedTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_screen_recording);

        getPermission();

        ScreenRecordManager.initScreenRecordView(this);

        ScreenRecordManager.prepareScreenRecord(this);

        checkNotification();

    }

    public void getPermission() {

        //request storage and audio permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionManager.doRequestPermissions(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ScreenRecordData.getInstance().setMediaProjectionManager((MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE));
        }
    }

    //Only need when version > M in Activity
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);

    }

    private void checkNotification() {
        if( !getIntent().hasExtra(FROM_APP_UI_NOTIFICATION_CENTER) ) {
            return;
        }

        if( getIntent().getStringExtra(FROM_APP_UI_NOTIFICATION_CENTER).equals(TURN_OFF_SCREEN_RECORD_VIDEO_MODE) ) {

            ScreenRecordManager.requestStopScreenRecord(this);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ScreenRecordManager.onRequestScreenRecordResult(this, requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {

        if (backKeyPressedTimes == 0) {
            ToastUtil.showCustomShortToastNormal(this, "double click back to out");
            backKeyPressedTimes = 1;
            new Thread() {
                @Override
                public void run() {
                    try {

                        ScreenRecordManager.requestStopScreenRecord(EasyScreenRecordingActivity.this);

                        Thread.sleep(1500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        backKeyPressedTimes = 0;
                    }
                }
            }.start();
            return;
        }
        else{
            finish();
        }

        super.onBackPressed();

    }
}
