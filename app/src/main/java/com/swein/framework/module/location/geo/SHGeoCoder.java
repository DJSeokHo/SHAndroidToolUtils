package com.swein.framework.module.location.geo;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SHGeoCoder {

    private Geocoder geocoder;

    public SHGeoCoder(Context context, Locale locale) {
        geocoder = new Geocoder(context, locale);
    }

    public SHGeoCoder(Context context) {
        geocoder = new Geocoder(context);
    }

    public List<Address> getFromLocation(double latitude, double longitude, int maxResults) throws IOException {

        return geocoder.getFromLocation(latitude, longitude, maxResults);

    }

    public String getCountryName(Address address) {
        return address.getCountryName();
    }

    public String getPostalCode(Address address) {
        return address.getPostalCode();
    }

    public String getCountryCode(Address address) {
        return address.getCountryCode();
    }

    public String getAdminArea(Address address) {
        return address.getAdminArea();
    }

    public String getSubAdminArea(Address address) {
        return address.getSubAdminArea();
    }

    public String getThoroughfare(Address address) {
        return address.getThoroughfare();
    }

    public String getSubLocality(Address address) {
        return address.getSubLocality();
    }

}
