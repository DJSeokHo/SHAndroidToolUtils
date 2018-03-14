package com.swein.framework.tools.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.swein.framework.tools.util.debug.log.ILog;

import java.util.List;

/**
 *
 * target api <= 22 just use this
 *
 * but of api > 22, must request permission dynamic
 *
 * Created by seokho on 2018/3/14.
 */

public class SHLocation {

    private final static String TAG = "SHLocation";

    public interface SHLocationDelegate {
        void onLocation(double longitude, double latitude, long time);
    }

    public void getLocation(Context context, final SHLocationDelegate shLocationDelegate) {

        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);

        String locationProvider;

        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {

            locationProvider = LocationManager.NETWORK_PROVIDER;

        }
        else if (providers.contains(LocationManager.GPS_PROVIDER)) {

            locationProvider = LocationManager.GPS_PROVIDER;

        }
        else if (providers.contains(LocationManager.PASSIVE_PROVIDER)) {

            locationProvider = LocationManager.PASSIVE_PROVIDER;

        }
        else {

            return;
        }

        // last known location...if you need
//        Location location = locationManager.getLastKnownLocation(locationProvider);
//        if (location != null) {
//            showLocation(location, shLocationDelegate);
//            return;
//        }

        locationManager.requestLocationUpdates(locationProvider, 0, 0, new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onLocationChanged(Location location) {
                showLocation(location, shLocationDelegate);

                // if just want to request location information once...
                locationManager.removeUpdates(this);
            }
        });
    }

    private void showLocation(Location location, SHLocationDelegate shLocationDelegate) {
        ILog.iLogDebug(TAG, location.getLatitude() + " " + location.getLongitude());
        shLocationDelegate.onLocation(location.getLongitude(), location.getLatitude(), System.currentTimeMillis());
    }
}
