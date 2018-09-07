package com.swein.framework.module.screenrecording.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.IBinder;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.toast.ToastUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by seokho on 24/10/2016.
 */

public class RecordService extends Service {

    private final static String TAG = "RecordService";

    private final static int MAX_WIDTH = 720;
    private final static int MAX_HEIGHT = 1080;

    private MediaProjection mediaProjection;
    private MediaRecorder mediaRecorder;
    private VirtualDisplay virtualDisplay;

    private boolean running;
    private int width;
    private int height;
    private int dpi;

    @Override
    public IBinder onBind(Intent intent) {
        ILog.iLogDebug(TAG, "onBind");

        return new RecordBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ILog.iLogDebug(TAG, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initParam();
    }

    public void initParam() {

        //initRecordSize
        this.width = MAX_WIDTH;
        this.height = MAX_HEIGHT;

        mediaRecorder = new MediaRecorder();
        HandlerThread serviceThread = new HandlerThread("service_thread", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        serviceThread.start();
        running = false;
    }

    public void setMediaProjection(MediaProjection project) {
        ILog.iLogDebug(TAG, "setMediaProjection");
        mediaProjection = project;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRecordConfig(int width, int height, int dpi) {
        ILog.iLogDebug(TAG, "setRecordConfig");
        this.width = width;
        this.height = height;
        this.dpi = dpi;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean startRecord() {
        ILog.iLogDebug(TAG, "startRecord");

        if (mediaProjection == null || running) {
            return false;
        }

        initRecorder();
        createVirtualDisplay();
        mediaRecorder.start();
        running = true;
        return true;
    }

    public boolean stopRecord() {
        ILog.iLogDebug(TAG, "stopRecord");

        if (!running) {
            return false;
        }

        running = false;
        mediaRecorder.stop();
        mediaRecorder.reset();
        virtualDisplay.release();
        mediaProjection.stop();

        return true;
    }

    private void createVirtualDisplay() {
        ILog.iLogDebug(TAG, "createVirtualDisplay");
        virtualDisplay = mediaProjection.createVirtualDisplay("MainScreen", width, height, dpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), null, null);
    }

    private void initRecorder() {
        ILog.iLogDebug(TAG, "initRecorder");

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(getDirectory() + System.currentTimeMillis() + ".mp4");
        mediaRecorder.setVideoSize(width, height);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
        mediaRecorder.setVideoFrameRate(60);

        try {
            mediaRecorder.prepare();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDirectory() {

        ILog.iLogDebug(TAG, "getDirectory");

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "ScreenRecord" + "/";

            File file = new File(dir);

            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }

            ToastUtil.showShortToastNormal(getApplicationContext(), "start recoding...");
            ILog.iLogDebug(TAG, "file path is : " + dir);

            return dir;
        }
        else {
            return null;
        }
    }

    public class RecordBinder extends Binder {
        public RecordService getRecordService() {
            return RecordService.this;
        }
    }

}
