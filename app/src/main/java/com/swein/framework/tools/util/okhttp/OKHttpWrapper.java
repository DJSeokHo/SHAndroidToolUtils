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
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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

    public String getStringResponseWithCustomHeader(Response response) throws IOException {
        ILog.iLogDebug(TAG, "onResponse: " + response.toString());

        ResponseBody responseBody = response.body();
        if(responseBody == null) {
            return "";
        }

        return responseBody.string();
    }

    public void requestGet(String url, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();

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

    public void requestGetWithHeader(String url, HashMap<String, String> header, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        Request.Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = builder.get().url(url).build();


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

    public void requestPostWithJSON(String url, OKHttpWrapperDelegate okHttpWrapperDelegate, JSONObject jsonObject) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");
        RequestBody requestBody = RequestBody.create(String.valueOf(jsonObject), mediaType);

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).post(requestBody).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
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

    public void requestPost(String url, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }


        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");
        RequestBody requestBody = RequestBody.create("", mediaType);

        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).post(requestBody).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
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

    public void requestPostWithHeader(String url, HashMap<String, String> header, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }


        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");
        RequestBody requestBody = RequestBody.create("", mediaType);

        Request.Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = builder.post(requestBody).url(url).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
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

    public void requestDeleteWithHeader(String url, HashMap<String, String> header, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }


        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");
        RequestBody requestBody = RequestBody.create("", mediaType);

        Request.Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = builder.delete(requestBody).url(url).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
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

    public void requestPutWithHeader(String url, HashMap<String, String> header, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }


        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded; charset=utf-8");
        RequestBody requestBody = RequestBody.create("", mediaType);

        Request.Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = builder.put(requestBody).url(url).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
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

    public void requestPostImageFile(String url, String fileName, String filePath, HashMap<String, String> header, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        final MediaType MEDIA_TYPE = filePath.endsWith("png") ?
                MediaType.parse("image/png") : MediaType.parse("image/jpeg");

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("profileImageFile", fileName,  RequestBody.create(MEDIA_TYPE, new File(filePath)))
                .build();

        Request.Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = builder.post(requestBody).url(url).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
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

    public void requestPostFormDataId(String url, int affiliateId, HashMap<String, String> header, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        RequestBody requestBody = new FormBody.Builder()
                .add("affiliateId", String.valueOf(affiliateId))
                .build();


        Request.Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = builder.post(requestBody).url(url).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
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

    public void requestPostFormDataEmail(String url, HashMap<String, String> header, String email, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        RequestBody requestBody = new FormBody.Builder()
                .add("email", String.valueOf(email))
                .build();


        Request.Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = builder.post(requestBody).url(url).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
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

    public void requestPostWithMultipartFormDataJSONAndFiles(String url, HashMap<String, String> header,
                                                             String affiliateId, String content, String rateTotal,
                                                             List<String> imageList, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        final

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        multipartBodyBuilder.addFormDataPart("affiliateId", affiliateId);
        multipartBodyBuilder.addFormDataPart("content", content);
        multipartBodyBuilder.addFormDataPart("rateTotal", rateTotal);

        File file;
        for(int i = 0; i < imageList.size(); i++) {
            MediaType mediaType = imageList.get(i).endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image/jpeg");
            file = new File(imageList.get(i));

            multipartBodyBuilder.addFormDataPart("imageFile", file.getName(), RequestBody.create(mediaType, file));
        }

        RequestBody requestBody = multipartBodyBuilder.build();

        Request.Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = builder.post(requestBody).url(url).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
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

    public void requestPostWithMultipartFormDataJSONAndFiles(String url, HashMap<String, String> header,
                                                             String content, String rateTotal,
                                                             List<String> imageList, List<String> imageWillKeepList, OKHttpWrapperDelegate okHttpWrapperDelegate) {

        if(okHttpClient == null) {

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            okHttpClient = builder.build();
        }

        final

        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        multipartBodyBuilder.addFormDataPart("content", content);
        multipartBodyBuilder.addFormDataPart("rateTotal", rateTotal);

        if(imageWillKeepList.isEmpty()) {
            multipartBodyBuilder.addFormDataPart("imageUrl", "");
        }
        else {

            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < imageWillKeepList.size(); i++) {

                stringBuilder.append(imageWillKeepList.get(i));

                if(i < imageWillKeepList.size() - 1) {
                    stringBuilder.append(",");
                }
            }

            ILog.iLogDebug(TAG, "?????/ " + stringBuilder.toString());

            multipartBodyBuilder.addFormDataPart("imageUrl", stringBuilder.toString());
        }

        File file;
        for(int i = 0; i < imageList.size(); i++) {
            MediaType mediaType = imageList.get(i).endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image/jpeg");
            file = new File(imageList.get(i));

            multipartBodyBuilder.addFormDataPart("imageFile", file.getName(), RequestBody.create(mediaType, file));
        }

        RequestBody requestBody = multipartBodyBuilder.build();

        Request.Builder builder = new Request.Builder();

        for (Map.Entry<String, String> entry : header.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = builder.post(requestBody).url(url).build();

        Call call = okHttpClient.newCall(request);

        // auto  thread
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

    /**
     * example:
     *
     * OKHttpWrapper.getInstance().requestDownloadFile("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564642051087&di=a5a0ecddbceeaf44e2b55d0439eaed44&imgtype=0&src=http%3A%2F%2Fen.pimg.jp%2F017%2F747%2F873%2F1%2F17747873.jpg",
     *                 new OKHttpWrapper.OKHttpWrapperDelegate() {
     *                     @Override
     *                     public void onFailure(@NotNull Call call, @NotNull IOException e) {
     *                         OKHttpWrapper.getInstance().cancelCall(call);
     *                     }
     *
     *                     @Override
     *                     public void onResponse(@NotNull Call call, @NotNull Response response) {
     *                         try {
     *                             OKHttpWrapper.getInstance().storageFileResponse(response, Environment.getExternalStorageDirectory().toString() + "/default.png");
     *                         }
     *                         catch (IOException e) {
     *                             e.printStackTrace();
     *                         }
     *                         finally {
     *                             OKHttpWrapper.getInstance().cancelCall(call);
     *                         }
     *                     }
     *                 });
     */
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

    public void clear() {
        if(okHttpClient != null) {
            okHttpClient = null;
        }
    }
}
