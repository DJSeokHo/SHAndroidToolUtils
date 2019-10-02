package com.swein.framework.module.locationapi;

import android.content.Context;
import android.location.Location;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.swein.framework.tools.util.debug.log.ILog;

/**
 * need
 * implementation 'com.google.android.gms:play-services-location:17.0.0'
 */
public class LocationAPI {

    public interface LocationAPIDelegate {
        void onLocation(Location location);
    }

    private final static String TAG = "LocationAPI";

    private LocationAPI() {}

    private static LocationAPI instance = new LocationAPI();
    public static LocationAPI getInstance() {
        return instance;
    }

    private FusedLocationProviderClient fusedLocationProviderClient;
    private  LocationCallback locationCallback;

    public void requestLocation(Context context, LocationAPIDelegate locationAPIDelegate) {

        if(fusedLocationProviderClient == null) {
            fusedLocationProviderClient = new FusedLocationProviderClient(context);
        }

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setSmallestDisplacement(10);

        if(locationCallback == null) {
            locationCallback = new LocationCallback() {

                @Override
                public void onLocationAvailability(LocationAvailability locationAvailability) {
                    super.onLocationAvailability(locationAvailability);
                }

                @Override
                public void onLocationResult(LocationResult locationResult) {
                    for(Location location : locationResult.getLocations()) {
                        ILog.iLogDebug(TAG, location.getLatitude() + " " + location.getLongitude());
                        locationAPIDelegate.onLocation(location);
                        clear();
                    }
                }
            };
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void clear() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        locationCallback = null;
        fusedLocationProviderClient = null;
    }

}
