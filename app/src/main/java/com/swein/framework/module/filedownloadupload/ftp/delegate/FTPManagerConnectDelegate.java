package com.swein.framework.module.filedownloadupload.ftp.delegate;

public interface FTPManagerConnectDelegate {

    void onSuccess();
    void onFailed();
    void onError();

}
