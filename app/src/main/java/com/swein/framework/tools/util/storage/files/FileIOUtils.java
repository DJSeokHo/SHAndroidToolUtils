package com.swein.framework.tools.util.storage.files;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

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

    private static byte[] getByte(Context context, Uri uri) throws Exception
    {

        byte[] buf;
        byte[] videoBytes = null;

        if(uri != null)
        {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            FileInputStream fis = getFileInputStream(context, uri);

            buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf))) {
                byteArrayOutputStream.write(buf, 0, n);
            }

            videoBytes = byteArrayOutputStream.toByteArray();
        }
        return videoBytes;
    }

    /**
     * file to byte
     * @param file
     * @return
     * @throws Exception
     */
    public static byte[] getByte(File file) throws Exception
    {
        byte[] bytes = null;
        if(file!=null)
        {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if(length > Integer.MAX_VALUE)
            {
                Log.e(FileIOUtils.class.getName(), "this file is max");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
            {
                offset += numRead;
            }

            if(offset < bytes.length)
            {
                Log.d(FileIOUtils.class.getName(), "file length is error");
                return null;
            }
            is.close();
        }
        return bytes;
    }

    /**
     * function A
     * @param fromPath
     * @param toPath
     * @return
     */
    public static int DirectoryCopy(String fromPath, String toPath) {
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
    public static int copySdcardFile(String fromPath, String toPath)
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

    public void copyFileUsingFileChannels(File source, File dest)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    public Uri getUriFromFile(File file) {
        return Uri.fromFile(file);
    }



    public static void copyFileToUriUsingFileChannels(Context context, File source, Uri dest)
            throws IOException {

        FileDescriptor outputFileDescriptor = context.getContentResolver().openFileDescriptor(dest, "w").getFileDescriptor();

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(outputFileDescriptor).getChannel();

            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }



    public static void copyUriToUriUsingFileChannels(Context context, Uri source, Uri dest)
            throws IOException {

        FileDescriptor inputFileDescriptor = context.getContentResolver().openFileDescriptor(dest, "r").getFileDescriptor();
        FileDescriptor outputFileDescriptor = context.getContentResolver().openFileDescriptor(dest, "w").getFileDescriptor();

        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(inputFileDescriptor).getChannel();
            outputChannel = new FileOutputStream(outputFileDescriptor).getChannel();

            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }


    public static FileDescriptor UriFromFileToFileDescriptor(Context context, File file) {

        FileDescriptor fileDescriptor = null;

        try {
            fileDescriptor = context.getContentResolver().openFileDescriptor(Uri.fromFile(file), "r").getFileDescriptor();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return fileDescriptor;
    }

    private static FileOutputStream getFileOutputStream(Context context, Uri uri) {
        try {
            return new FileOutputStream(context.getContentResolver().openFileDescriptor( uri, "w").getFileDescriptor());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FileInputStream getFileInputStream(Context context, Uri uri) {
        try {
            return new FileInputStream(context.getContentResolver().openFileDescriptor( uri, "r").getFileDescriptor());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
