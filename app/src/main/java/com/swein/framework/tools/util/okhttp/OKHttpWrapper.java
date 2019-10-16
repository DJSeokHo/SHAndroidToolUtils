package com.swein.framework.tools.util.okhttp;

import android.os.Environment;

import com.swein.framework.tools.util.debug.log.ILog;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OKHttpWrapper {

    public interface OKHttpWrapperDelegate {
        void onFailure(@NotNull Call call, @NotNull IOException e);
        void onResponse(@NotNull Call call, @NotNull Response response);
    }

    private final static String TAG = "OKHttpWrapper";

    private static OKHttpWrapper instance = new OKHttpWrapper();
    public static OKHttpWrapper getInstance() {
        return instance;
    }

    private OkHttpClient okHttpClient;


    private OKHttpWrapper(){}

    public void cancelCall(Call call) {
        if(!call.isCanceled()) {
            call.cancel();
        }
    }


    public String getStringResponse(Response response) throws IOException {
        ILog.iLogDebug(TAG, "onResponse: " + response.toString());

        ResponseBody responseBody = response.body();
        if(responseBody == null) {
            return "";
        }

        return responseBody.string();
    }

    public void storageFileResponse(Response response, String fileName) throws IOException {

        ResponseBody responseBody = response.body();
        if(responseBody == null) {
            return;
        }

        File file = new File(fileName);

        if(!file.exists()) {
            file.createNewFile();
        }

        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            inputStream = responseBody.byteStream();
            fileOutputStream = new FileOutputStream(file);
            while ((len = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, len);
            }
            fileOutputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public void requestGet(String url, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();

        Call call = okHttpClient.newCall(request);

        // in main thread
//        Response response = call.execute();

        // auto in thread
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpWrapperDelegate.onFailure(call, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                okHttpWrapperDelegate.onResponse(call, response);

            }
        });
    }

    public void requestGetWithAuthorizationBearer(String url, OKHttpWrapperDelegate okHttpWrapperDelegate, String token) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).addHeader("Authorization", "Bearer " + token).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpWrapperDelegate.onFailure(call, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                okHttpWrapperDelegate.onResponse(call, response);

            }
        });
    }

    public void requestDownloadFile(String url, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                okHttpWrapperDelegate.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                okHttpWrapperDelegate.onResponse(call, response);

            }
        });
    }

    public void requestPostWithJSON(String url, OKHttpWrapperDelegate okHttpWrapperDelegate, JSONObject jsonObject) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(String.valueOf(jsonObject), mediaType);
        Request.Builder builder = new Request.Builder();

        Request request = builder.url(url).post(requestBody).build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                okHttpWrapperDelegate.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                okHttpWrapperDelegate.onResponse(call, response);

            }
        });
    }

}
