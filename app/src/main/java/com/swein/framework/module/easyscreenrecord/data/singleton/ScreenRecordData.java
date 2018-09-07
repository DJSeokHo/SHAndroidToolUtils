package com.swein.framework.module.easyscreenrecord.data.singleton;

import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;

/**
 * Created by seokho on 27/10/2016.
 */

public class ScreenRecordData {

    private int width;
    private int height;
    private int dpi;

    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;
    private MediaRecorder mediaRecorder;
    private VirtualDisplay virtualDisplay;

    private ScreenRecordData() {}

    private static ScreenRecordData instance = null;

    private static Object obj= new Object();

    public static ScreenRecordData getInstance() {

        if(null == instance){

            synchronized(ScreenRecordData.class){

                if(null == instance){

                    instance = new ScreenRecordData();

                }
            }
        }

        return instance;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public int getDpi() {
        return this.dpi;
    }

    public void setMediaProjection(MediaProjection mediaProjection) {
        this.mediaProjection = mediaProjection;
    }

    public MediaProjection getMediaProjection() {
        return this.mediaProjection;
    }

    public void setMediaRecorder(MediaRecorder mediaRecorder) {
        this.mediaRecorder = mediaRecorder;
    }

    public MediaRecorder getMediaRecorder() {
        return this.mediaRecorder;
    }

    public void setVirtualDisplay(VirtualDisplay virtualDisplay) {
        this.virtualDisplay = virtualDisplay;
    }

    public VirtualDisplay getVirtualDisplay() {
        return this.virtualDisplay;
    }

    public void setMediaProjectionManager(MediaProjectionManager mediaProjectionManager) {
        this.mediaProjectionManager = mediaProjectionManager;
    }

    public MediaProjectionManager getMediaProjectionManager() {
        return this.mediaProjectionManager;
    }
}
