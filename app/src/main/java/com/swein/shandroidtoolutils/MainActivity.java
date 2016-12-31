package com.swein.shandroidtoolutils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.swein.data.bundle.BundleData;
import com.swein.data.singleton.key.KeyData;
import com.swein.framework.sfa.SAF;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.handler.HandlerUtils;
import com.swein.framework.tools.util.json.JSonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private TextView textView, textViewSub;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textViewSub = (TextView) findViewById(R.id.textViewSub);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        final JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(new JSONObject().put("one", "1"));
            jsonArray.put(new JSONObject().put("two", "2"));
            jsonArray.put(new JSONObject().put("three", "3"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HandlerUtils.createHandlerMethodWithMessage(new Runnable() {
            @Override
            public void run() {
                String string = JSonUtils.jsonStringToJSonString(jsonArray);
                textView.setText(string);

                Map map = JSonUtils.jsonStringToMap(string);

                ILog.iLogDebug(MainActivity.this, map.get("one").toString());
            }
        });

        HandlerUtils.createHandlerMethodWithMessage(new Runnable() {
            @Override
            public void run() {
                String string = JSonUtils.jsonStringToJSonString(jsonArray);
                textViewSub.setText(string);

                Map map = JSonUtils.jsonStringToMap(string);

                ILog.iLogDebug(MainActivity.this, map.get("one").toString());
            }
        });

        HandlerUtils.handlerSendMessage();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerUtils.createHandlerMethodWithMessage(new Runnable() {
                    @Override
                    public void run() {
                        String string = JSonUtils.jsonStringToJSonString(jsonArray);
                        textViewSub.setText(string);

                        Map map = JSonUtils.jsonStringToMap(string);

                        ILog.iLogDebug(MainActivity.this, map.get("one").toString());
                    }
                });

                Bundle bundle = new Bundle();
                bundle.putString(KeyData.BUNDLE_TRANSMIT_STRING_VALUE, "Hello, Bundle");
                BundleData bundleData = new BundleData(bundle);

                ActivityUtils.startNewActivityWithoutFinishWithBundleData(MainActivity.this, NewActivity.class, bundleData);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
