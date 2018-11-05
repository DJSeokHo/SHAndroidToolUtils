package com.swein.framework.module.easyscreenrecord.framework.util.storage.files;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.swein.framework.module.easyscreenrecord.framework.util.debug.log.ILog;

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
     * @param path
     * @return
     */
    public static File getFileFromPath(String path) {

        File file = new File(path);
        return file;

    }

    /**
     * get path of file
     * @param file
     * @return
     */
    public static String getPathFromImageFile(File file) {

        return file.getAbsolutePath();

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
                ILog.iLogDebug(FileIOUtils.class.getName(), "file length is error");
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


    /**
     * get path from uri what head is "content://......."
     * @param context
     * @param uri
     * @return
     */
    public static String getPathFromContentUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[] {
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static ParcelFileDescriptor getParcelFileDescriptor(Context context, Uri uri){
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "rw");
            return parcelFileDescriptor;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
