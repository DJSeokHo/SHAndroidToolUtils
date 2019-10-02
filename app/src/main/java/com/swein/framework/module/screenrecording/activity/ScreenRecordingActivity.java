package com.swein.framework.module.screenrecording.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.swein.framework.module.screenrecording.services.RecordService;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;


public class ScreenRecordingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ScreenRecordingActivity";

    private static final int RECORD_REQUEST_CODE  = 101;
    private static final int STORAGE_REQUEST_CODE = 102;
    private static final int AUDIO_REQUEST_CODE   = 103;

    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;
    private RecordService recordService;
    private Button btnStrat, btnStartService, btnStopService;
    private ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_recording);

        prepareParam();

    }

    private void prepareParam(){
        ILog.iLogDebug(TAG, "prepareParam");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            doRequestPermissions();
        }
        initUI();
        initParam();
    }

    private void initUI() {

        btnStrat = findViewById(R.id.btnStart);
        btnStartService = findViewById(R.id.btnStartService);
        btnStopService = findViewById(R.id.btnStopService);
        btnStrat.setEnabled(false);

        btnStrat.setOnClickListener(this);
        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        btnStopService.setEnabled(false);
    }

    private void initParam() {
        projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
    }

    public void doRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECORD_AUDIO}, AUDIO_REQUEST_CODE);
        }
    }

    public void startService() {
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName className, IBinder service) {
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                RecordService.RecordBinder binder = (RecordService.RecordBinder) service;
                recordService = binder.getRecordService();
                recordService.setRecordConfig(metrics.widthPixels, metrics.heightPixels, metrics.densityDpi);
                btnStrat.setEnabled(true);
                btnStopService.setEnabled(true);
                btnStrat.setText(recordService.isRunning() ? "Stop Record" : "Start Record");

            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
            }
        };

        bindRecordService();
    }

    public void stopService() {
        recordService.stopRecord();

        Intent intent = new Intent(this, RecordService.class);
        stopService(intent);
        btnStopService.setEnabled(false);
        btnStrat.setEnabled(false);
        btnStrat.setText("Start Record");
    }

    public void bindRecordService() {
        Intent intent = new Intent(this, RecordService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_REQUEST_CODE || requestCode == AUDIO_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {

            ILog.iLogDebug(TAG, resultCode + " " + RESULT_OK + " " + Activity.RESULT_OK);
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            recordService.setMediaProjection(mediaProjection);
            recordService.startRecord();
            btnStrat.setText("Stop Record");

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:

                if (recordService.isRunning()) {
                    recordService.stopRecord();
                    btnStrat.setText("Start Record");

                } else {

                    Intent captureIntent = projectionManager.createScreenCaptureIntent();
                    startActivityForResult(captureIntent, RECORD_REQUEST_CODE);

                }

                break;

            case R.id.btnStartService:

                startService();

                break;

            case R.id.btnStopService:

                stopService();

                break;

        }
    }
}
