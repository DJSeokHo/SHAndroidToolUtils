package com.swein.framework.tools.util.storage;

import android.os.Environment;

import com.swein.data.global.symbol.Symbol;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * Created by seokho on 19/12/2016.
 */

public class FileStorageUtil {


    /**
     * add these permission at manifests first
     *
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     * <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
     */

    public static String createExternalStorageDirectoryFolder(String folderName, String rootPath) {

        File fileDirectory = new File(rootPath + Symbol.VIRGULE + folderName);

        if(!fileDirectory.exists()) {
            fileDirectory.mkdir();
        }

        return rootPath + Symbol.VIRGULE + folderName;

    }

    public static void writeExternalStorageDirectoryFileWithContentAddtion(String filePath, String fileName, String content) {

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(filePath + fileName);

            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                RandomAccessFile randomAccessFile = null;
                randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(file.length());
                randomAccessFile.write(content.getBytes());
                randomAccessFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeExternalStorageDirectoryFileWithContent(String filePath, String fileName, String content) {

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(filePath + fileName);

            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(content.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeExternalStorageDirectoryFileWithJSONObject(String filePath, String fileName, JSONObject jsonObject) {

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(filePath, fileName);

            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(jsonObject.toString().getBytes("UTF-8"));
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readExternalStorageDirectoryFile(String filePath, String fileName) {


        FileInputStream fileInputStream;
        StringBuilder stringBuilder = new StringBuilder(Symbol.NULL_STRING);
        try {
            fileInputStream = new FileInputStream(filePath + fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String string;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();

    }

    public static JSONObject readExternalStorageDirectoryFileJSONObject(String filePath, String fileName) {

        JSONObject jsonObject = null;

        try {

            File file = new File( filePath, fileName );
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while( (line = bufferedReader.readLine()) != null ){
                stringBuilder.append(line);
            }

            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();

            jsonObject = new JSONObject(stringBuilder.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch ( JSONException e ) {
            e.printStackTrace();
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
