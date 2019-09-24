package com.swein.framework.module.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.module.location.model.LocationModel;

/**
 * target api <= 22 just use this
 * <p>
 * but of api > 22, must request permission dynamic
 * <p>
 * Created by seokho on 2018/3/14.
 */

public class SHLocation {

    private final static String TAG = "SHLocation";
    private final static int LOCATION_SERVICE_REQUEST_CODE = 801;

    private static SHLocation instance = new SHLocation();
    public static SHLocation getInstance() {
        return instance;
    }

    public interface SHLocationDelegate {
        void onLocation(double longitude, double latitude, long time);
    }

    private LocationManager locationManager;
    private Context context;
    private SHLocationDelegate shLocationDelegate;


    private Location bestLocation;

    private boolean requestLocationJustOnce;

    private LocationListener networkLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            if(bestLocation == null) {
                bestLocation = location;
            }
            else {
                bestLocation = getBestLocation(bestLocation, location);
            }

            LocationModel locationModel = getLocationModel(location);

            if(requestLocationJustOnce) {
                clear();
            }

            ILog.iLogDebug(TAG, locationModel.toJSONString());
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private LocationListener gpsLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            if(bestLocation == null) {
                bestLocation = location;
            }
            else {
                bestLocation = getBestLocation(bestLocation, location);
            }

            LocationModel locationModel = getLocationModel(bestLocation);

            if(requestLocationJustOnce) {
                clear();
            }

            ILog.iLogDebug(TAG, locationModel.toJSONString());
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private LocationModel getLocationModel(Location location) {

        LocationModel locationModel = new LocationModel();
        locationModel.provider = location.getProvider();
        locationModel.latitude = location.getLatitude();
        locationModel.longitude = location.getLongitude();

        if(location.hasAccuracy()) {
            locationModel.accuracy = location.getAccuracy();
        }

        return locationModel;
    }

    private SHLocation() {
    }

    public void init(Context context) {
        this.context = context;
    }

    public boolean isLocationServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;
    }

    public void openLocationServiceSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(intent , LOCATION_SERVICE_REQUEST_CODE);
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == LOCATION_SERVICE_REQUEST_CODE) {
            if (!isLocationServiceEnable(activity)) {
                // not open gps
            }
            else {
                // open gps
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void requestLocation(SHLocationDelegate shLocationDelegate, boolean requestLocationJustOnce) {

        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        if (locationManager == null) {
            return;
        }

        this.requestLocationJustOnce = requestLocationJustOnce;
        this.shLocationDelegate = shLocationDelegate;

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkLocationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsLocationListener);
    }

    private void showLocation(Location location) {
        shLocationDelegate.onLocation(location.getLongitude(), location.getLatitude(), System.currentTimeMillis());
    }

    private Location getBestLocation(Location knownPosition, Location newPosition) {

        if(!knownPosition.hasAccuracy() && !newPosition.hasAccuracy()) {
            return newPosition;
        }

        if(knownPosition.getAccuracy() < newPosition.getAccuracy()) {
            return knownPosition;
        }
        else {
            return newPosition;
        }
    }

    public void clear() {
        locationManager.removeUpdates(networkLocationListener);
        locationManager.removeUpdates(gpsLocationListener);

        bestLocation = null;
    }

    @Override
    protected void finalize() throws Throwable {

        clear();

        super.finalize();
    }
}
