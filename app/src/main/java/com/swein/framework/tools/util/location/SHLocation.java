package com.swein.framework.tools.util.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.location.model.LocationModel;

/**
 * target api <= 22 just use this
 * <p>
 * but of api > 22, must request permission dynamic
 * <p>
 * Created by seokho on 2018/3/14.
 */

public class SHLocation {

    private final static String TAG = "SHLocation";

    /* gps max wait time */
    public static final int GPS_WATI_TIME_ONE_MINUTES = 1000 * 60 * 1;

    /* if network accuracy small than 100 meter than will use it */
    public static final float NETWORK_ACCURACY_MIN = 200;

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
            showLocation(location, shLocationDelegate);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            requestLocation();
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
            showLocation(location, shLocationDelegate);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            requestLocation();
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

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            if(location.hasVerticalAccuracy()) {
//                locationModel.verticalAccuracy = location.getVerticalAccuracyMeters();
//            }
//        }

        return locationModel;
    }

    public SHLocation(Context context, SHLocationDelegate shLocationDelegate, boolean requestLocationJustOnce) {
        this.requestLocationJustOnce = requestLocationJustOnce;
        this.context = context;
        this.shLocationDelegate = shLocationDelegate;
    }

    public void requestLocation() {

        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        if (locationManager == null) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkLocationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsLocationListener);
    }

    private void showLocation(Location location, SHLocationDelegate shLocationDelegate) {
        ILog.iLogDebug(TAG, location.getLatitude() + " " + location.getLongitude());
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
