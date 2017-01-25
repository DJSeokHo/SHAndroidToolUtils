package com.swein.shandroidtoolutils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.swein.framework.tools.interfaces.volley.VolleyInterface;
import com.swein.framework.tools.manager.volley.VolleyManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewRequest, imageViewLoader;
    TextView textView;
    Button btnReload;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewRequest = (ImageView) findViewById(R.id.imageViewRequest);
        imageViewLoader = (ImageView) findViewById(R.id.imageViewLoader);
        textView = (TextView) findViewById(R.id.textView);
        btnReload = (Button) findViewById(R.id.btnReload);

        requestQueue = VolleyManager.initRequestQueue(MainActivity.this);

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setText("Loading...");
                imageViewRequest.setImageBitmap(null);

                VolleyManager.volleyStringRequest(
                        requestQueue,
                        VolleyManager.getUrlByRequestMethodSSL(Request.Method.GET, "your host", "your path", "your file name"),
                        Request.Method.GET,
                        "string",
                        null,
                        false,
                        new VolleyInterface(MainActivity.this) {
                            @Override
                            public void onComplete(String result) {
                                textView.setText(result);
                            }

                            @Override
                            public void onComplete(JSONObject result) {

                            }

                            @Override
                            public void onComplete(JSONArray result) {

                            }

                            @Override
                            public void onComplete(Bitmap result) {

                            }

                            @Override
                            public void onException(VolleyError error) {

                            }
                        }
                );

                VolleyManager.volleyImageRequest(
                        requestQueue,
                        VolleyManager.getUrlByRequestMethodSSL(Request.Method.GET, "your host", "your path", "your file name"),
                        0,
                        0,
                        "image",
                        ImageView.ScaleType.CENTER_INSIDE,
                        Bitmap.Config.RGB_565,
                        false,
                        new VolleyInterface(MainActivity.this) {
                            @Override
                            public void onComplete(String result) {

                            }

                            @Override
                            public void onComplete(JSONObject result) {

                            }

                            @Override
                            public void onComplete(JSONArray result) {

                            }

                            @Override
                            public void onComplete(Bitmap result) {
                                imageViewRequest.setImageBitmap(result);
                            }

                            @Override
                            public void onException(VolleyError error) {

                            }
                        }
                    );

                VolleyManager.volleyImageLoaderWithBitmapCache(
                        requestQueue,
                        VolleyManager.getUrlByRequestMethodSSL(Request.Method.GET, "your host", "your path", "your file name"),
                        imageViewLoader,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher
                        );

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyManager.cancelRequestQueueWithTag(requestQueue, "string");
        VolleyManager.cancelRequestQueueWithTag(requestQueue, "image");
    }
}
