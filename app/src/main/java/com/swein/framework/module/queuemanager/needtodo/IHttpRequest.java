package com.swein.framework.module.queuemanager.needtodo;

public interface IHttpRequest {

    void setUrl(String url);

    void setData(byte[] data);

    void setListener(CallBackListener listener);

    void execute();

}
