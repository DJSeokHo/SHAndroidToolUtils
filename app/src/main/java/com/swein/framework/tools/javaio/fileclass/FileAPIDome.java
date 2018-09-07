package com.swein.framework.tools.javaio.fileclass;

import com.swein.framework.tools.util.debug.log.ILog;

import java.io.File;

public class FileAPIDome {

    private final static String TAG = "FileAPIDome";

    public FileAPIDome() {

        listDirectory(new File("/Users/seokho/Desktop/worksapce/SWein/Android/SHAndroidToolUtils"));
    }

    /**
     * get all file of directory
     * @param dir directory
     */
    private void listDirectory(File dir) {

        if(!dir.exists()) {
            ILog.iLogDebug(TAG, "not exists");
            return;
        }

        if(!dir.isDirectory()) {
            ILog.iLogDebug(TAG, "not directory");
            return;
        }

        File[] files = dir.listFiles();

        if(files != null || 0 < files.length) {
            for(File file : files) {
                if(file.isDirectory()) {
                    listDirectory(file);
                }
                else {
                    System.out.println(file);
                }
            }
        }

    }
}
