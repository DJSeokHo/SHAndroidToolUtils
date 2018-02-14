package com.swein.framework.module.sound.effert;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by seokho on 14/02/2018.
 */

public class SoundEffect {

    private int currentPlayResourceId;

    private final static int MAX_STREAMS = 10;

    private SoundPool soundPool;
    private HashMap<Integer, Integer> hashMap;
    private float volume;

    private SoundEffect(){}

    private static SoundEffect instance = new SoundEffect();

    public static SoundEffect getInstance() {
        return instance;
    }

    public void initSoundEffect(Context context) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(MAX_STREAMS).build();

            hashMap = new HashMap<>();

            AudioManager manager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
            int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int currentVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
            volume = currentVolume / (float) maxVolume;

        }
    }

    public void addResources(int key, int resourceId) {
        hashMap.put(key, resourceId);
    }

    public void play(Context context, int key) {
        soundPool.load(context, hashMap.get(key), 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                currentPlayResourceId = soundPool.play(sampleId, volume, volume, 0, 0, 1);
            }
        });
    }
}
