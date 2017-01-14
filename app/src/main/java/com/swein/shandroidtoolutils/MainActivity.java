package com.swein.shandroidtoolutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.swein.data.bundle.BundleData;
import com.swein.data.singleton.DeviceInfo;
import com.swein.data.singleton.key.KeyData;
import com.swein.data.singleton.request.RequestData;
import com.swein.framework.tools.util.activity.ActivityUtils;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.device.DeviceInfoUtils;
import com.swein.framework.tools.util.handler.HandlerUtils;
import com.swein.framework.tools.util.json.JSonUtils;
import com.swein.framework.tools.util.popup.ToastUtils;
import com.swein.framework.tools.util.sound.SoundUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import com.swein.shandroidtoolutils.R;

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
//                HandlerUtils.createHandlerMethodWithMessage(new Runnable() {
//                    @Override
//                    public void run() {
//                        String string = JSonUtils.jsonStringToJSonString(jsonArray);
//                        textViewSub.setText(string);
//
//                        Map map = JSonUtils.jsonStringToMap(string);
//
//                        ILog.iLogDebug(MainActivity.this, map.get("one").toString());
//                    }
//                });
//                HandlerUtils.handlerSendMessageDelay(3000, 1);
                Bundle bundle = new Bundle();
                bundle.putString(KeyData.BUNDLE_TRANSMIT_STRING_VALUE, "Hello, Bundle");
                BundleData bundleData = new BundleData(bundle);

//                ActivityUtils.startNewActivityWithoutFinishWithBundleData(MainActivity.this, NewActivity.class, bundleData);
                ActivityUtils.startNewActivityWithoutFinishForResult(MainActivity.this, NewActivity.class, RequestData.ACTIVITY_REQUEST_CODE);
            }
        });

        SoundUtils.getInstance().createMediaPlayerWithSoundResource(this, R.raw.sound);
        SoundUtils.getInstance().playSoundResource();

        if(DeviceInfoUtils.initDeviceScreenDisplayMetricsPixels(this)) {
            ToastUtils.showCustomLongToastNormal(this, DeviceInfo.getInstance().deviceScreenWidth + " " + DeviceInfo.getInstance().deviceScreenHeight);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        final String string = ActivityUtils.getActivityResultString(requestCode, resultCode, data);

        HandlerUtils.createHandlerMethodWithMessage(new Runnable() {
            @Override
            public void run() {

                textViewSub.setText(string);

            }
        });

        HandlerUtils.handlerSendMessage();

    }

    @Override
    protected void onDestroy() {
        SoundUtils.getInstance().stopPlaySoundResource();
        super.onDestroy();
    }
}
