package com.swein.framework.module.queuemanager.needtodo;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonHttpRequest implements IHttpRequest {

    private String url;
    private byte[] data;
    private CallBackListener callBackListener;
    private HttpURLConnection httpURLConnection;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public void setListener(CallBackListener listener) {
        this.callBackListener = listener;
    }

    @Override
    public void execute() {
        URL url;
        try {
            url = new URL(this.url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(6000); // 连接超时
            httpURLConnection.setUseCaches(false); // 缓存
            httpURLConnection.setInstanceFollowRedirects(true); // 可否重定向
            httpURLConnection.setReadTimeout(3000); // 响应超时
            httpURLConnection.setDoInput(true); // 是否可以写入数据
            httpURLConnection.setDoOutput(true); //是否可以输出数据
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.connect();

            // 发送数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(data); // 写入数据
            bufferedOutputStream.flush(); // 刷新缓冲区，发送数据
            outputStream.close();
            bufferedOutputStream.close();


            // 写入数据
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                callBackListener.onSuccess(inputStream);
            }
            else {
                throw new RuntimeException("Request failed");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Request failed");
        }
        finally {
            httpURLConnection.disconnect();
        }
    }
}
