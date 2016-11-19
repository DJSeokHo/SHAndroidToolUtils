package com.swein.shandroidtoolutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swein.framework.tools.util.ToastUtils;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToastUtils.showCustomLongToastNormal(this, "text");
        ToastUtils.showCustomLongToastWithImageResourceId(this, "image and text", R.mipmap.ic_launcher);
    }
}
