package com.swein.framework.module.filedownloadupload.ftp.delegate;

public interface FTPManagerUploadFileDelegate {

    void onSuccess();
    void onFailed();
    void onError();
    void onProgress(String progress);
}
