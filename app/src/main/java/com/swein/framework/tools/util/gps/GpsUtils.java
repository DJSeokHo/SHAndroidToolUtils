package com.swein.framework.tools.util.gps;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

/**
 * Created by seokho on 24/04/2017.
 */

public class GpsUtils {

    public static boolean isGPSTurnOn( Context context ) {
        LocationManager locationManager = (LocationManager)context.
                getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void turnOnGPS( Context context ) {
        Intent intent = new Intent();
        intent.setAction( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try
        {
            context.startActivity(intent);


        }
        catch(ActivityNotFoundException ex)
        {
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                context.startActivity(intent);
            }
            catch (Exception e) {
            }
        }
    }

}
