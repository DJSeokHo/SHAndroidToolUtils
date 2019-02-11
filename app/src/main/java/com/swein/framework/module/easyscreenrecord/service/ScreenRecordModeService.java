package com.swein.framework.module.easyscreenrecord.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.HandlerThread;
import android.os.IBinder;

import com.swein.framework.module.easyscreenrecord.data.singleton.DataCenter;
import com.swein.framework.module.easyscreenrecord.data.singleton.ScreenRecordData;
import com.swein.framework.tools.util.debug.log.ILog;

import java.io.IOException;

import static com.swein.framework.module.easyscreenrecord.data.global.applicaiton.ESRContent.SCREEN_RECORD_FILE_FULL_PATH;
import static com.swein.framework.module.easyscreenrecord.data.global.applicaiton.ESRContent.SCREEN_RECORD_FILE_PATH;
import static com.swein.framework.module.easyscreenrecord.data.global.applicaiton.ESRContent.SCREEN_RECORD_FILE_TYPE;
import static com.swein.framework.module.easyscreenrecord.data.global.applicaiton.ESRContent.SCREEN_RECORD_VIDEO_BIT_RATE;
import static com.swein.framework.module.easyscreenrecord.data.global.applicaiton.ESRContent.SCREEN_RECORD_VIDEO_FRAME_RATE;


/**
 * Created by seokho on 25/10/2016.
 */

public class ScreenRecordModeService extends Service implements ScreenRecordInterface{

    private final static int MAX_WIDTH = 720;
    private final static int MAX_HEIGHT = 1080;


    private static boolean running;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ILog.iLogDebug(ScreenRecordModeService.class.getSimpleName(), "onStartCommand");
        setRecordConfig(DataCenter.getInstance().getDisplayMetrics().widthPixels, DataCenter.getInstance().getDisplayMetrics().heightPixels, DataCenter.getInstance().getDisplayMetrics().densityDpi);

//        startForeground(1003, NotificationUIUtils.getContentIntentNotification(this,
//                "record",
//                ESRContent.APP_NAME_CN,
//                "record notification",
//                R.mipmap.ic_launcher,
//                false,
//                IntentUtil.getPendingIntentWithClass(this, EasyScreenRecordingActivity.class)
//        ));

        startRecord();

        running = true;
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initRecordSize();
        prepareMediaRecorder();

        HandlerThread serviceThread = new HandlerThread("service_thread", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        serviceThread.start();
        running = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ILog.iLogDebug(ScreenRecordModeService.class.getSimpleName(), "onDestroy");
        stopRecord();
        stopForeground(true);
    }

    //is service running
    public static boolean isRunning() {
        return running;
    }

    @Override
    public void initRecordSize() {

        ScreenRecordData.getInstance().setWidth(MAX_WIDTH);
        ScreenRecordData.getInstance().setHeight(MAX_HEIGHT);

    }

    @Override
    public void prepareMediaRecorder() {
        ScreenRecordData.getInstance().setMediaRecorder(new MediaRecorder());
    }

    @Override
    public void setRecordConfig(int width, int height, int dpi) {
        ScreenRecordData.getInstance().setWidth(width);
        ScreenRecordData.getInstance().setHeight(height);
        ScreenRecordData.getInstance().setDpi(dpi);
    }

    @Override
    public void prepareRecorder() {
//        setFile();

        ScreenRecordData.getInstance().getMediaRecorder().setAudioSource(MediaRecorder.AudioSource.MIC);
        ScreenRecordData.getInstance().getMediaRecorder().setVideoSource(MediaRecorder.VideoSource.SURFACE);
        ScreenRecordData.getInstance().getMediaRecorder().setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        SCREEN_RECORD_FILE_FULL_PATH = SCREEN_RECORD_FILE_PATH + System.currentTimeMillis() + SCREEN_RECORD_FILE_TYPE;
        ILog.iLogDebug(ScreenRecordModeService.class.getSimpleName(), SCREEN_RECORD_FILE_FULL_PATH);
        ScreenRecordData.getInstance().getMediaRecorder().setOutputFile(SCREEN_RECORD_FILE_FULL_PATH);
        ScreenRecordData.getInstance().getMediaRecorder().setVideoSize(ScreenRecordData.getInstance().getWidth(), ScreenRecordData.getInstance().getHeight());
        ScreenRecordData.getInstance().getMediaRecorder().setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        ScreenRecordData.getInstance().getMediaRecorder().setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        ScreenRecordData.getInstance().getMediaRecorder().setVideoEncodingBitRate(SCREEN_RECORD_VIDEO_BIT_RATE);
        ScreenRecordData.getInstance().getMediaRecorder().setVideoFrameRate(SCREEN_RECORD_VIDEO_FRAME_RATE);
        try {
            ScreenRecordData.getInstance().getMediaRecorder().prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createVirtualDisplay() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ScreenRecordData.getInstance().setVirtualDisplay(
                    ScreenRecordData.getInstance().getMediaProjection().createVirtualDisplay(
                            "MainScreen",
                            ScreenRecordData.getInstance().getWidth(),
                            ScreenRecordData.getInstance().getHeight(),
                            ScreenRecordData.getInstance().getDpi(),
                            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                            ScreenRecordData.getInstance().getMediaRecorder().getSurface(), null, null));
        }
    }

    @Override
    public void setFile() {

    }

    @Override
    public boolean startRecord() {

        if (null == ScreenRecordData.getInstance().getMediaProjection() || running) {
            return false;
        }

        prepareRecorder();
        createVirtualDisplay();
        ScreenRecordData.getInstance().getMediaRecorder().start();
        running = true;
        return true;
    }

    @Override
    public boolean stopRecord() {
        if (!running) {
            return false;
        }
        running = false;

        ScreenRecordData.getInstance().getMediaRecorder().stop();
        ScreenRecordData.getInstance().getMediaRecorder().reset();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ScreenRecordData.getInstance().getVirtualDisplay().release();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && null != ScreenRecordData.getInstance().getMediaProjection()) {
            ScreenRecordData.getInstance().getMediaProjection().stop();
        }

        return true;
    }


}
