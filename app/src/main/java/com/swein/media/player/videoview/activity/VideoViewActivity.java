package com.swein.media.player.videoview.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.swein.framework.tools.util.toast.ToastUtils;
import com.swein.shandroidtoolutils.R;

import java.io.File;

public class VideoViewActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaController;

    private Button buttonPlayVideoView;
    private Button buttonStopVideoView;
    private Button buttonPauseVideoView;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_video_view );


        buttonPlayVideoView = (Button) findViewById( R.id.buttonPlayVideoView );
        buttonStopVideoView = (Button) findViewById( R.id.buttonStopVideoView );
        buttonPauseVideoView = (Button) findViewById( R.id.buttonPauseVideoView );



        videoView = (VideoView) findViewById( R.id.videoView );

        mediaController = new MediaController( this );

        final File video = new File( Environment.getExternalStorageDirectory() + "/DCIM/xzq.mp4" );

        buttonPlayVideoView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                if(video.exists()) {
                    videoView.start();
                }
            }
        } );

        buttonStopVideoView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                if(video.exists()) {
                    videoView.stopPlayback();
                }

            }
        } );

        buttonPauseVideoView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                if(video.exists()) {
                    if(videoView.isPlaying()) {
                        videoView.pause();
                    }
                    else {
                        videoView.start();
                    }
                }

            }
        } );

        videoView.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion( MediaPlayer mp ) {
                ToastUtils.showCustomShortToastNormal( VideoViewActivity.this, "End" );
            }
        } );



        if(video.exists()) {
            videoView.setVideoPath( video.getAbsolutePath() );


            videoView.requestFocus();
        }

    }
}
