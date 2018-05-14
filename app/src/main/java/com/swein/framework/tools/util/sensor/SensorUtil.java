package com.swein.framework.tools.util.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorUtil {

    public static void isLandscape(Activity activity, final Runnable landscape, final Runnable portrait) {

        SensorManager sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);

        assert sensorManager != null;
        sensorManager.registerListener(new SensorEventListener() {
            int orientation = -1;

            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[1] < 6.5 && event.values[1] > -6.5) {
                    if (orientation != 1) {
                        landscape.run();
                    }
                    orientation = 1;
                }
                else {
                    if (orientation != 0) {
                        portrait.run();
                    }
                    orientation = 0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub

            }
        }, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

    }

}
