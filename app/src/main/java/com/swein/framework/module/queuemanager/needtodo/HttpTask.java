package com.swein.framework.module.queuemanager.needtodo;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class HttpTask implements Runnable {

    private IHttpRequest iHttpRequest;

    public HttpTask(String url, JSONObject requestData, IHttpRequest iHttpRequest, CallBackListener callBackListener) {
        this.iHttpRequest = iHttpRequest;
        iHttpRequest.setUrl(url);
        iHttpRequest.setListener(callBackListener);
        String content = String.valueOf(requestData);
        iHttpRequest.setData(content.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    public void run() {
        iHttpRequest.execute();
    }
}
