package com.swein.framework.module.videosplash.demo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.swein.framework.module.videosplash.customview.CustomVideoView;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.framework.tools.util.window.WindowUtil;
import com.swein.shandroidtoolutils.R;

public class VideoSplashActivity extends Activity {

    private CustomVideoView customVideoView;
    private Button buttonStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.hideTitleBarWithFullScreen(this);
        setContentView(R.layout.activity_video_splash);

        customVideoView = findViewById(R.id.customVideoView);
        buttonStart = findViewById(R.id.buttonStart);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showCustomShortToastNormal(VideoSplashActivity.this, "start");
            }
        });

        customVideoView = findViewById(R.id.customVideoView);

        String uriString = "android.resource://" + getPackageName() + "/" + R.raw.aaa;

        customVideoView.setVideoURI(Uri.parse(uriString));

        customVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                customVideoView.start();
            }
        });

//        customVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                customVideoView.start();
//            }
//        });

    }
}
