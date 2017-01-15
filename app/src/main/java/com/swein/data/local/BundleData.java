package com.swein.data.local;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by seokho on 31/12/2016.
 */

public class BundleData<T> {

    private Bundle bundle;

    private ArrayList<Bundle> bundleArrayList;

    private Map<String, Bundle> bundleMap;

    public BundleData() {}

    public BundleData(Bundle bundle) {
        this.bundle = bundle;
        this.bundleArrayList = new ArrayList<>();
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void addBundleIntoBundleList(Bundle bundle) {
        this.bundleArrayList.add(bundle);
    }

    public ArrayList<Bundle> getBundleList() {
        return this.bundleArrayList;
    }

    public void addBundleIntoBundleMap(String key, Bundle bundle) {
        this.bundleMap.put(key, bundle);
    }

    public Map<String, Bundle> getBundleMap() {
        return this.bundleMap;
    }


}
