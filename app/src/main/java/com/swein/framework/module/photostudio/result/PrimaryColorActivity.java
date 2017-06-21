package com.swein.framework.module.photostudio.result;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.swein.framework.module.photostudio.matrix.ColorMatrixClass;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtils;
import com.swein.shandroidtoolutils.R;

public class PrimaryColorActivity extends AppCompatActivity {

    final private static String TAG = "PrimaryColorActivity";

    private ImageView imageView;
    private SeekBar seekBarHue;
    private SeekBar seekBarSaturation;
    private SeekBar seekBarLum;

    final private static int MAX_VALUE = 255;
    final private static int MIN_VALUE = 127;

    private float hue;
    private float saturation;
    private float lum;

    private Bitmap originalBitmap;

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {

                case R.id.seekBarHue:
                    hue = (progress - MIN_VALUE) * 1.0f / MIN_VALUE * 180;
                    ILog.iLogDebug(TAG, "hue is " + hue);
                    break;

                case R.id.seekBarSaturation:
                    saturation = progress * 1.0f / MAX_VALUE;
                    ILog.iLogDebug(TAG, "saturation is " + saturation);
                    break;

                case R.id.seekBarLum:
                    lum = progress * 1.0f / MAX_VALUE;
                    ILog.iLogDebug(TAG, "lum is " + lum);
                    break;

            }

            ThreadUtils.startThread(new Runnable() {
                @Override
                public void run() {

                }
            });


            ThreadUtils.startUIThread(0, new Runnable() {
                @Override
                public void run() {

                    final Bitmap bitmap = ColorMatrixClass.getInstance().getConcatMatrix(
                            Bitmap.createBitmap(originalBitmap),
                            ColorMatrixClass.getInstance().setHue(hue, hue, hue),
                            ColorMatrixClass.getInstance().setSaturation(saturation),
                            ColorMatrixClass.getInstance().setLunMatrix(lum, lum, lum, 1));

                    ThreadUtils.startUIThread(0, new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            });

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_color);

        imageView = (ImageView)findViewById(R.id.imageView);

        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dsc).copy(Bitmap.Config.ARGB_8888, true);

        seekBarHue = (SeekBar)findViewById(R.id.seekBarHue);
        seekBarSaturation = (SeekBar) findViewById(R.id.seekBarSaturation);
        seekBarLum = (SeekBar)findViewById(R.id.seekBarLum);

        seekBarHue.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBarSaturation.setOnSeekBarChangeListener(onSeekBarChangeListener);
        seekBarLum.setOnSeekBarChangeListener(onSeekBarChangeListener);

        //set max
        seekBarHue.setMax(MAX_VALUE);
        seekBarSaturation.setMax(MAX_VALUE);
        seekBarLum.setMax(MAX_VALUE);

        //init state
        seekBarHue.setProgress(MIN_VALUE);
        seekBarSaturation.setProgress(MIN_VALUE);
        seekBarLum.setProgress(MAX_VALUE);

    }

}
