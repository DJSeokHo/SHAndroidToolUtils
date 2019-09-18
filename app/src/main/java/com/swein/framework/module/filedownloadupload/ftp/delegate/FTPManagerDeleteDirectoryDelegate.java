package com.swein.framework.module.filedownloadupload.ftp.delegate;

public interface FTPManagerDeleteDirectoryDelegate {

    void onSuccess();
    void onFailed();
    void onError();
}
