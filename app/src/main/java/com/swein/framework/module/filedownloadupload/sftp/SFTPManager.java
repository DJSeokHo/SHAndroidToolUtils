package com.swein.framework.module.filedownloadupload.sftp;

import android.annotation.SuppressLint;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.swein.framework.tools.util.debug.log.ILog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;


/**
 * need
 * implementation group: 'com.jcraft', name: 'jsch', version: '0.1.52'
 */
public class SFTPManager {

    private final static String TAG="SFTPManager";

    private SFTPManager() {}

    private static SFTPManager instance = new SFTPManager();
    public static SFTPManager getInstance() {
        return instance;
    }


    private String host;
    private int port;
    private String username;
    private String password;
    private ChannelSftp sftp = null;
    private Session sshSession = null;

    public void init(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * connect server via sftp
     */
    public ChannelSftp connect() {
        JSch jsch = new JSch();
        try {
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");

            if (channel != null) {
                channel.connect();
            }
            else {
                ILog.iLogDebug(TAG, "channel connecting failed.");
            }

            sftp = (ChannelSftp) channel;
        }
        catch (JSchException e) {
            e.printStackTrace();
        }
        return sftp;
    }

    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
                ILog.iLogDebug(TAG,"sftp is closed already");
            }
        }
        if (this.sshSession != null) {
            if (this.sshSession.isConnected()) {
                this.sshSession.disconnect();
                ILog.iLogDebug(TAG,"sshSession is closed already");
            }
        }
    }

    public boolean uploadFile(String remotePath, String remoteFileName, String localFileName) {
        FileInputStream in = null;
        try {
            createDir(remotePath);

            File file = new File(localFileName);
            in = new FileInputStream(file);

            sftp.put(in, remoteFileName);

            return true;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (SftpException e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean uploadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        FileInputStream in = null;
        try {
            createDir(remotePath);

            File file = new File(localPath + localFileName);
            in = new FileInputStream(file);

            sftp.put(in, remoteFileName);

            return true;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (SftpException e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean bacthUploadFile(String remotePath, String localPath,
                                   boolean del) {
        try {
            File file = new File(localPath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()
                        && files[i].getName().indexOf("bak") == -1) {
                    synchronized(remotePath){
                        if (this.uploadFile(remotePath, files[i].getName(),
                                localPath, files[i].getName())
                                && del) {
                            deleteFile(localPath + files[i].getName());
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
        return false;

    }

    @SuppressWarnings("rawtypes")
    public boolean batchDownLoadFile(String remotPath, String localPath,
                                     String fileFormat, boolean del) {
        try {
            connect();
            Vector v = listFiles(remotPath);
            if (v.size() > 0) {

                Iterator it = v.iterator();
                while (it.hasNext()) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        if (fileFormat != null && !"".equals(fileFormat.trim())) {
                            if (filename.startsWith(fileFormat)) {
                                if (this.downloadFile(remotPath, filename,
                                        localPath, filename)
                                        && del) {
                                    deleteSFTP(remotPath, filename);
                                }
                            }
                        }
                        else {
                            if (this.downloadFile(remotPath, filename,
                                    localPath, filename)
                                    && del) {
                                deleteSFTP(remotPath, filename);
                            }
                        }
                    }
                }
            }
        }
        catch (SftpException e) {
            e.printStackTrace();
        }
        finally {
            this.disconnect();
        }
        return false;
    }

    public boolean downloadFile(String remotePath, String remoteFileName,
                                String localPath, String localFileName) {
        try {
            sftp.cd(remotePath);
            File file = new File(localPath + localFileName);
            mkdirs(localPath + localFileName);
            sftp.get(remoteFileName, new FileOutputStream(file));
            return true;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (SftpException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        if (!file.isFile()) {
            return false;
        }
        return file.delete();
    }

    public boolean createDir(String createpath) {
        try {
            if (isDirExist(createpath)) {
                this.sftp.cd(createpath);
                Log.d(TAG,createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(createpath)) {
                    sftp.cd(createpath);
                }
                else {
                    sftp.mkdir(createpath);
                    sftp.cd(createpath);
                }
            }
            this.sftp.cd(createpath);
            return true;
        }
        catch (SftpException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("DefaultLocale")
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    public void deleteSFTP(String directory, String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void mkdirs(String path) {
        File f = new File(path);
        String fs = f.getParent();
        f = new File(fs);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    @SuppressWarnings("rawtypes")
    public Vector listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

}
