package com.swein.framework.module.filedownloadupload.ftp.delegate;

public interface FTPManagerCreateOrChangeDelegate {

    void onSuccess(String currentDirectory);
    void onFailed();
    void onError();

}
