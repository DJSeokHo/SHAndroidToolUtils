package com.swein.shandroidtoolutils.tools.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by seokho on 10/11/2016.
 */

public class BitmapUtils {

    public static Bitmap getBitmapFromFile(File file) {

        Bitmap bitmap;

        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

        return bitmap;

    }

    public static Bitmap getBitmapFromPath(String path) {
        Bitmap bitmap;

        bitmap = BitmapFactory.decodeFile(path);

        return bitmap;
    }

}
