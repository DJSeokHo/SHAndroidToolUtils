package com.swein.framework.module.photostudio.result;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.swein.shandroidtoolutils.R;

public class PrimaryColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private ImageView imageView;
    private SeekBar seekBarHue;
    private SeekBar seekBarSaturation;
    private SeekBar seekBarLum;

    final private static int MAX_VALUE = 255;
    final private static int MIN_VALUE = 127;

    private float hue;
    private float stauration;
    private float lum;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_color);

        imageView = (ImageView)findViewById(R.id.imageView);
        seekBarHue = (SeekBar)findViewById(R.id.seekBarHue);
        seekBarSaturation = (SeekBar) findViewById(R.id.seekBarSaturation);
        seekBarLum = (SeekBar)findViewById(R.id.seekBarLum);

        seekBarHue.setOnSeekBarChangeListener(this);
        seekBarSaturation.setOnSeekBarChangeListener(this);
        seekBarLum.setOnSeekBarChangeListener(this);

        //set max
        seekBarHue.setMax(MAX_VALUE);
        seekBarSaturation.setMax(MAX_VALUE);
        seekBarLum.setMax(MAX_VALUE);

        //init state
        seekBarHue.setProgress(MIN_VALUE);
        seekBarSaturation.setProgress(MIN_VALUE);
        seekBarLum.setProgress(MIN_VALUE);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {

            case R.id.seekBarHue:
                hue = (progress - MIN_VALUE) * 1.0f / MIN_VALUE * 180;
                break;

            case R.id.seekBarSaturation:
                stauration = progress * 1.0f / MIN_VALUE;
                break;

            case R.id.seekBarLum:
                lum = progress * 1.0f / MIN_VALUE;
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
