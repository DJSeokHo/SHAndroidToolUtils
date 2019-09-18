package com.swein.framework.module.filedownloadupload.ftp;

import com.swein.framework.module.filedownloadupload.ftp.delegate.FTPManagerConnectDelegate;
import com.swein.framework.module.filedownloadupload.ftp.delegate.FTPManagerCreateOrChangeDelegate;
import com.swein.framework.module.filedownloadupload.ftp.delegate.FTPManagerDeleteDirectoryDelegate;
import com.swein.framework.module.filedownloadupload.ftp.delegate.FTPManagerDeleteFileDelegate;
import com.swein.framework.module.filedownloadupload.ftp.delegate.FTPManagerUploadFileDelegate;
import com.swein.framework.tools.util.debug.log.ILog;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * need
 * implementation 'commons-net:commons-net:3.5'
 */
public class FTPManager {

    private final static String TAG = "FTPManager";

    private static FTPManager instance = new FTPManager();
    public static FTPManager getInstance() {
        return instance;
    }

    private FTPManager() {}

    private FTPClient ftpClient = null;

    public boolean isNetworkDisconnected = false;

    public void connect(String host, int port, FTPManagerConnectDelegate ftpManagerConnectDelegate, String user, String pw) {

        if(ftpClient != null) {

            if(ftpClient.isConnected()) {
                disconnect();
            }
        }

        try {
            ftpClient = new FTPClient();
            ILog.iLogDebug(TAG, "connecting to the ftp server " + host + " ：" + port);
            ftpClient.connect(host, port);

            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ILog.iLogDebug(TAG, "login to the ftp server");
                boolean status = ftpClient.login(user, pw);

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();

                if(status) {
                    ftpManagerConnectDelegate.onSuccess();
                }
                else {
                    ftpManagerConnectDelegate.onFailed();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            ILog.iLogDebug(TAG, "Error: could not connect to host " + host);
            ftpManagerConnectDelegate.onError();
        }
    }

    public void disconnect() {

        if (ftpClient == null) {
            return;
        }

        try {
            ftpClient.logout();
            ftpClient.disconnect();
            ftpClient = null;

            ILog.iLogDebug(TAG, "disconnect success");
        }
        catch (Exception e) {
            e.printStackTrace();
            ILog.iLogDebug(TAG, "Error occurred while disconnecting from ftp server.");
        }
    }

    public void createOrChangeDir(String directory, FTPManagerCreateOrChangeDelegate ftpManagerCreateOrChangeDelegate) {

        if(ftpClient == null) {
            return;
        }

        if(!ftpClient.isConnected()) {
            return;
        }

        if(directory == null || "".equals(directory)) {
            return;
        }

        try {

            if(ftpClient.changeWorkingDirectory(directory)) {
                ILog.iLogDebug(TAG, "just change directory");
                ftpManagerCreateOrChangeDelegate.onSuccess(directory);
                return;
            }

            if(directory.charAt(directory.length() - 1) == '/') {
                directory = directory.substring(0, directory.length() - 2);
            }

            if(directory.charAt(0) == '/') {
                directory = directory.substring(1, directory.length() - 1);
            }

            String[] dirArray =  directory.split("/");
            StringBuilder stringBuilder=new StringBuilder();

            for(String dir : dirArray) {
                stringBuilder.append("/");
                stringBuilder.append(dir);

                if(ftpClient.changeWorkingDirectory(stringBuilder.toString())) {
                    continue;
                }

                if(!ftpClient.makeDirectory(stringBuilder.toString())){
                    ILog.iLogDebug(TAG, "create dir failed " + stringBuilder.toString());
                    ftpManagerCreateOrChangeDelegate.onFailed();
                    return;
                }

                ILog.iLogDebug(TAG, "create dir success " + stringBuilder.toString());
            }

            // change to target directory
            ILog.iLogDebug(TAG, "create and change directory");
            if(ftpClient.changeWorkingDirectory(stringBuilder.toString())) {
                ftpManagerCreateOrChangeDelegate.onSuccess(stringBuilder.toString());
            }
            else {
                ftpManagerCreateOrChangeDelegate.onFailed();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            ftpManagerCreateOrChangeDelegate.onError();
        }
    }

    public void uploadFile(String srcFilePath, String desFileName, FTPManagerUploadFileDelegate ftpManagerUploadFileDelegate) {

        if(ftpClient == null) {
            return;
        }

        if(!ftpClient.isConnected()) {
            return;
        }

        File file = new File(srcFilePath);
        if(!file.exists()) {
            ftpManagerUploadFileDelegate.onFailed();
            return;
        }

        ILog.iLogDebug(TAG, "uploadFile start");

        boolean status;
        long fileLength;
        float progress = 0;

        int currentProgress = 0;

        try {
            FileInputStream srcFileStream = new FileInputStream(srcFilePath);

            fileLength = file.length();

            byte[] bytes = new byte[2048];
            int c;
            while ((c = srcFileStream.read(bytes)) != -1) {

                if(isNetworkDisconnected) {
                    ftpManagerUploadFileDelegate.onFailed();
                    isNetworkDisconnected = false;
                    return;
                }

                progress += c;

                int temp = (int)(progress / (float) fileLength * 100.0f);
                if(temp != currentProgress) {
                    ftpManagerUploadFileDelegate.onProgress(String.valueOf((int)(progress / (float) fileLength * 100.0f)) + "%");
                }
                currentProgress = temp;
            }

            status = ftpClient.storeFile(desFileName, srcFileStream);

            srcFileStream.close();

            if(status) {
                ftpManagerUploadFileDelegate.onSuccess();
            }
            else {
                ftpManagerUploadFileDelegate.onFailed();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            ILog.iLogDebug(TAG, "upload failed: " + e.getLocalizedMessage());
            ftpManagerUploadFileDelegate.onError();
        }
    }

    public void deleteFile(String ftpFileName, FTPManagerDeleteFileDelegate ftpManagerDeleteFileDelegate) {

        if(ftpClient == null) {
            return ;
        }

        if(!ftpClient.isConnected()) {
            return;
        }

        boolean flag;

        try {
            flag = ftpClient.deleteFile(ftpFileName);

            ILog.iLogDebug(TAG, "removeFile " + flag);

            if(flag) {
                ftpManagerDeleteFileDelegate.onSuccess();
            }
            else {
                ftpManagerDeleteFileDelegate.onFailed();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
            ftpManagerDeleteFileDelegate.onError();
        }
    }

    public void deleteDirectory(String directory, FTPManagerDeleteDirectoryDelegate ftpManagerDeleteDirectoryDelegate) {

        if(ftpClient == null) {
            return;
        }

        if(!ftpClient.isConnected()) {
            return;
        }

        if(directory.charAt(0) != '/') {
            directory = "/" + directory;
        }

        ILog.iLogDebug(TAG, "removeDir " + directory);

        try {

            if(ftpClient.removeDirectory(directory)) {
                ftpManagerDeleteDirectoryDelegate.onSuccess();
            }
            else {
                ftpManagerDeleteDirectoryDelegate.onFailed();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            ftpManagerDeleteDirectoryDelegate.onError();
        }
    }

    public int getFileCountsFromDirectory(String directory) {

        if(ftpClient == null) {
            return 0;
        }

        if(!ftpClient.isConnected()) {
            return 0;
        }

        FTPFile[] ftpFiles;

        try {

            ftpFiles = ftpClient.listFiles(directory);

            if(ftpFiles == null) {
                ILog.iLogDebug(TAG, "null");
                return 0;
            }

            return ftpFiles.length;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
