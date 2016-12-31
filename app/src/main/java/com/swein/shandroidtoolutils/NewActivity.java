package com.swein.shandroidtoolutils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.swein.data.singleton.key.KeyData;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.framework.tools.util.handler.HandlerUtils;

public class NewActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        textView = (TextView) findViewById(R.id.textView);

        textView.setText(ActivityUtils.getBundleDataFromPreActivity(this).getString(KeyData.BUNDLE_TRANSMIT_STRING_VALUE));

        HandlerUtils.handlerSendMessageDelay(3000, 1);
    }
}
