package com.swein.framework.module.easyscreenrecord.data.global.applicaiton;

import android.os.Environment;

import java.io.File;


/**
 * Created by seokho on 02/02/2017.
 */

public class ESRContent {

    public static final String APP_NAME_CN = "简易录屏";

    public static final String SCREEN_RECORD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static final String SCREEN_RECORD_PATH = "/" + "DCIM" + "/" + "ScreenRecord" + "/";

    public static final String SCREEN_RECORD_FILE_TYPE = ".mp4";

    public static final int SCREEN_RECORD_VIDEO_BIT_RATE = 5 * 1024 * 1024;

    public static final int SCREEN_RECORD_VIDEO_FRAME_RATE = 60;

    public static String SCREEN_RECORD_FILE_PATH = getDirectory();

    public static String SCREEN_RECORD_FILE_FULL_PATH;

    public static String getDirectory() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String dir = SCREEN_RECORD_ROOT + SCREEN_RECORD_PATH;

            File file = new File(dir);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return null;
                }
            }

            return dir;
        } else {
            return null;
        }
    }
}
