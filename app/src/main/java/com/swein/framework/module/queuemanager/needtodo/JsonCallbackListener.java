package com.swein.framework.module.queuemanager.needtodo;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonCallbackListener implements CallBackListener{

    private IJsonDataTransforListener iJsonDataTransforListener;
    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonCallbackListener(IJsonDataTransforListener iJsonDataTransforListener) {
        this.iJsonDataTransforListener = iJsonDataTransforListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        final String response = getContent(inputStream);

        handler.post(new Runnable() {
            @Override
            public void run() {
                iJsonDataTransforListener.onSuccess(response);
            }
        });
    }

    private String getContent(InputStream inputStream) {

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {

                try {
                    inputStream.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }

            return stringBuilder.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public void onFailure() {

    }
}
