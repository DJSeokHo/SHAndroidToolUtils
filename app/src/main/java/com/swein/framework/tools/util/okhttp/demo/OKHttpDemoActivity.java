package com.swein.framework.tools.util.okhttp.demo;

import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.okhttp.OKHttpWrapper;
import com.swein.shandroidtoolutils.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class OKHttpDemoActivity extends AppCompatActivity {

    private final static String TAG = "OKHttpDemoActivity";


    OkHttpClient okHttpClient = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_demo);

        OKHttpWrapper.getInstance().requestGet("https://www.baidu.com", new OKHttpWrapper.OKHttpWrapperDelegate() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                OKHttpWrapper.getInstance().cancelCall(call);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    ILog.iLogDebug(TAG, OKHttpWrapper.getInstance().getStringResponse(response));

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    OKHttpWrapper.getInstance().cancelCall(call);
                }
            }
        });

        OKHttpWrapper.getInstance().requestDownloadFile("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564642051087&di=a5a0ecddbceeaf44e2b55d0439eaed44&imgtype=0&src=http%3A%2F%2Fen.pimg.jp%2F017%2F747%2F873%2F1%2F17747873.jpg",
                new OKHttpWrapper.OKHttpWrapperDelegate() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        OKHttpWrapper.getInstance().cancelCall(call);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                        try {
                            OKHttpWrapper.getInstance().storageFileResponse(response, Environment.getExternalStorageDirectory().toString() + "/default.png");
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally {
                            OKHttpWrapper.getInstance().cancelCall(call);
                        }
                    }
                });
    }


}
