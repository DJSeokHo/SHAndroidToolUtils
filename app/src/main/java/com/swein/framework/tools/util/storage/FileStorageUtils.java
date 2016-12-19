package com.swein.framework.tools.util.storage;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by seokho on 19/12/2016.
 */

public class FileStorageUtils {

    public static void saveFile(Bitmap bm, String fileName, String path) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

}
