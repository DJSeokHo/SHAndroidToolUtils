package com.swein.shandroidtoolutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.swein.framework.tools.util.handler.HandlerUtils;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        HandlerUtils.handlerSendMessageDelay(3000, 1);
    }
}
