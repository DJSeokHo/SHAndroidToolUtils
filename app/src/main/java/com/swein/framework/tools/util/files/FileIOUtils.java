package com.swein.framework.tools.util.files;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by seokho on 10/11/2016.
 */

public class FileIOUtils {

    /**
     * get image file with path
     * @param imagePath
     * @return
     */
    public static File getImageFileFromImagePath(String imagePath) {

        File imageFile = new File(imagePath);
        return imageFile;

    }

    /**
     * get path of image file
     * @param imageFile
     * @return
     */
    public static String getImagePathFromImageFile(File imageFile) {

        return imageFile.getAbsolutePath();

    }

    /**
     * copy file of directory (function A + function B used)
     * @param fromPath
     * @param toPath
     */
    public static void DirectoryFileCopy(String fromPath, String toPath) {
        DirectoryCopy(fromPath, toPath);
    }


    /**
     * function A
     * @param fromPath
     * @param toPath
     * @return
     */
    private static int DirectoryCopy(String fromPath, String toPath) {
        //target dir
        File[] currentFiles;
        File root = new File(fromPath);

        //check SD card and File
        //not exists than return out
        if(!root.exists())
        {
            return -1;
        }

        //if exists than get
        currentFiles = root.listFiles();

        //target dir
        File targetDir = new File(toPath);

        //create dir
        if( !targetDir.exists() ) {

            targetDir.mkdirs();

        }

        for( int i= 0; i < currentFiles.length; i++ ) {
            if( currentFiles[i].isDirectory() ) {
                //if sub directory then enter
                DirectoryCopy(currentFiles[i].getPath() + "/", toPath + currentFiles[i].getName() + "/");

            }
            else {
                //if file then copy
                copySdcardFile(currentFiles[i].getPath(), toPath + currentFiles[i].getName());

            }
        }
        return 0;
    }

    /**
     * function B
     * @param fromPath
     * @param toPath
     * @return
     */
    private static int copySdcardFile(String fromPath, String toPath)
    {

        try {
            InputStream fosfrom = new FileInputStream(fromPath);
            OutputStream fosto = new FileOutputStream(toPath);
            byte bt[] = new byte[1024];
            int c;

            while ( (c = fosfrom.read(bt)) > 0 ) {

                fosto.write(bt, 0, c);

            }

            fosfrom.close();
            fosto.close();
            return 0;

        }
        catch ( Exception ex ) {

            return -1;

        }
    }

}
