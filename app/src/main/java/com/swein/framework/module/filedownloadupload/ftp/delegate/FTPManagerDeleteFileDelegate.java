package com.swein.framework.module.filedownloadupload.ftp.delegate;

public interface FTPManagerDeleteFileDelegate {

    void onSuccess();
    void onFailed();
    void onError();

}
