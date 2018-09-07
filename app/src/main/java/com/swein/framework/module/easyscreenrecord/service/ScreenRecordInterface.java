package com.swein.framework.module.easyscreenrecord.service;

/**
 * Created by seokho on 03/02/2017.
 */

public interface ScreenRecordInterface {

    void initRecordSize();

    void prepareMediaRecorder();

    void setRecordConfig(int width, int height, int dpi);

    void prepareRecorder();

    void createVirtualDisplay();

    void setFile();

    boolean startRecord();

    boolean stopRecord();

}
