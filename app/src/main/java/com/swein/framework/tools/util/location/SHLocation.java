package com.swein.framework.tools.util.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.swein.framework.tools.util.debug.log.ILog;

/**
 * target api <= 22 just use this
 * <p>
 * but of api > 22, must request permission dynamic
 * <p>
 * Created by seokho on 2018/3/14.
 */

public class SHLocation {

    private final static String TAG = "SHLocation";
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    public interface SHLocationDelegate {
        void onLocation(double longitude, double latitude, long time);
    }

    private LocationManager locationManager;
    private Location bestLocation;
    private Context context;
    private SHLocationDelegate shLocationDelegate;

    private LocationListener networkLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            bestLocation = getBestLocation(location, bestLocation);

            if (bestLocation == null) {
                bestLocation = getBestLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER), bestLocation);

            }

            if (bestLocation == null) {
                bestLocation = getBestLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER), bestLocation);
            }

            /* if request just once */
            locationManager.removeUpdates(this);

            showLocation(bestLocation, shLocationDelegate);
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

            bestLocation = getBestLocation(location, bestLocation);

            if (bestLocation == null) {
                bestLocation = getBestLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER), bestLocation);

            }

            if (bestLocation == null) {
                bestLocation = getBestLocation(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER), bestLocation);
            }

            /* if request just once */
            locationManager.removeUpdates(this);

            showLocation(bestLocation, shLocationDelegate);
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

    public SHLocation(Context context, SHLocationDelegate shLocationDelegate) {
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

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, networkLocationListener);
    }

    private void showLocation(Location location, SHLocationDelegate shLocationDelegate) {
        ILog.iLogDebug(TAG, location.getLatitude() + " " + location.getLongitude());
        shLocationDelegate.onLocation(location.getLongitude(), location.getLatitude(), System.currentTimeMillis());
    }

    private Location getBestLocation(Location newLocation, Location currentBestLocation) {

        if (newLocation == null) {
            return null;
        }

        if (currentBestLocation == null) {
            // A new location is always better than no location
            return newLocation;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {

            return newLocation;
            // If the new location is more than two minutes older, it must be worse
        }
        else if (isSignificantlyOlder) {

            return currentBestLocation;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {

            return newLocation;
        }
        else if (isNewer && !isLessAccurate) {

            return newLocation;
        }
        else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {

            return newLocation;
        }

        return currentBestLocation;
    }

    private boolean isSameProvider(String provider1, String provider2) {

        if (provider1 == null) {

            return provider2 == null;
        }

        return provider1.equals(provider2);
    }

    @Override
    protected void finalize() throws Throwable {

        locationManager.removeUpdates(networkLocationListener);
        locationManager.removeUpdates(gpsLocationListener);

        super.finalize();
    }
}
