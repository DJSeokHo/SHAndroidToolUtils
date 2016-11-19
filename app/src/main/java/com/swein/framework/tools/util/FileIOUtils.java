package com.swein.framework.tools.util;

import java.io.File;

/**
 * Created by seokho on 10/11/2016.
 */

public class FileIOUtils {

    public static File getImageFileFromImagePath(String imagePath) {

        File imageFile = new File(imagePath);
        return imageFile;

    }

    public static String getImagePathFromImageFile(File imageFile) {

        return imageFile.getAbsolutePath();

    }

}
