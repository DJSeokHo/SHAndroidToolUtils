package com.swein.framework.module.fcmpush.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;
import com.swein.framework.module.sound.effert.SoundEffect;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.eventsplitshot.eventcenter.EventCenter;
import com.swein.framework.tools.util.eventsplitshot.subject.ESSArrows;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 *
 * reference documentation
 *
 * https://github.com/firebase/quickstart-android/blob/master/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/MainActivity.java
 * https://console.firebase.google.com/project/your_project_name/notification
 * https://firebase.google.com/docs/cloud-messaging/android/client
 *
 */
public class FirebaseCloudMessage extends Activity {

    private final static String TAG = "FirebaseCloudMessage";

    public static boolean example = false;

    private TextView textViewPushContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_cloud_message);

        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this);

        // Get token
        String token = FirebaseInstanceId.getInstance().getToken();
        ILog.iLogDebug(TAG, token);

        example = true;

        textViewPushContent = findViewById(R.id.textViewPushContent);

        SoundEffect.getInstance().initSoundEffect(this);
        SoundEffect.getInstance().addResources(1, R.raw.sound_open);

        EventCenter.getInstance().addEventObserver(ESSArrows.ESS_UPDATE_UI, this, new EventCenter.EventRunnable() {
            @Override
            public void run(String arrow, Object poster, final HashMap<String, Object> data) {
                ThreadUtil.startUIThread(0, new Runnable() {
                    @Override
                    public void run() {
                        String content = (String) data.get("onMessageReceived");
                        try {
                            JSONObject jsonObject = new JSONObject(content);
                            String title = jsonObject.getString("title");
                            String body = jsonObject.getString("body");
                            textViewPushContent.setText(title + "\n" + body);
                            SoundEffect.getInstance().play(FirebaseCloudMessage.this, 1);

                            Vibrator vibrator = (Vibrator)FirebaseCloudMessage.this.getSystemService(FirebaseCloudMessage.this.VIBRATOR_SERVICE);
                            vibrator.vibrate(2000);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });



    }

}