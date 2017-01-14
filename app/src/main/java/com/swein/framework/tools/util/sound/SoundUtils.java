package com.swein.framework.tools.util.sound;

import android.content.Context;
import android.media.MediaPlayer;

import com.swein.data.singleton.example.ExampleSingtonClass;

/**
 * Created by seokho on 14/01/2017.
 */

public class SoundUtils {

    private MediaPlayer mediaPlayer = null;

    private SoundUtils() {}

    private static SoundUtils instance = null;

    private static Object obj= new Object();

    public static SoundUtils getInstance() {

        if(null == instance){

            synchronized(SoundUtils.class){

                if(null == instance){

                    instance = new SoundUtils();

                }
            }
        }

        return instance;
    }


    /**
     *
     * @param context
     * @param soundResource put *.mp3 in to res/raw and must be under your project R(ex: com.your_project.R.raw.*.mp3)
     */
    public void createMediaPlayerWithSoundResource(Context context, int soundResource) {
        mediaPlayer = MediaPlayer.create(context, soundResource);
    }

    public void playSoundResource() {
        mediaPlayer.start();
    }

    public void stopPlaySoundResource() {
        mediaPlayer.stop();
        mediaPlayer = null;
    }

}
