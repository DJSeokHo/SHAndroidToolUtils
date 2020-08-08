package com.swein.framework.module.navermap;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.swein.framework.module.basicpermission.BasicPermissionActivity;
import com.swein.framework.module.basicpermission.PermissionManager;
import com.swein.framework.module.basicpermission.RequestPermission;
import com.swein.framework.module.location.SHLocation;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

public class NaverMapDemoActivity extends BasicPermissionActivity implements OnMapReadyCallback {

    private final static String TAG = "NaverMapDemoActivity";

    private FrameLayout frameLayoutMap;
    private Button button;

    private NaverMap naverMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_map_demo);
        findView();
        setListener();
        initMap();
    }

    private void findView() {
        frameLayoutMap = findViewById(R.id.frameLayoutMap);
        button = findViewById(R.id.button);
    }

    private void setListener() {
        button.setOnClickListener(view -> {

            if(naverMap == null) {
                return;
            }

            requestLocation();
        });
    }

    @RequestPermission(permissionCode= PermissionManager.PERMISSION_REQUEST_LOCATION)
    private void requestLocation() {

        if(PermissionManager.getInstance().requestPermission(this, true, PermissionManager.PERMISSION_REQUEST_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            ILog.iLogDebug(TAG, "open big map");

            SHLocation.getInstance().init(this);

            if(!SHLocation.getInstance().isLocationServiceEnable(this)) {
                SHLocation.getInstance().openLocationServiceSetting(this);
                return;
            }

            SHLocation.getInstance().requestLocation((latitude, longitude, time) -> {

                ILog.iLogDebug(TAG, latitude + " " + longitude);
                SHLocation.latitude = latitude;
                SHLocation.longitude = longitude;


                Marker marker = new Marker();
                marker.setPosition(new LatLng(latitude, longitude));
                marker.setMap(naverMap);

                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude)).animate(CameraAnimation.Fly, 1000);
                naverMap.moveCamera(cameraUpdate);

                SHLocation.getInstance().clear();
            }, true);
        }
    }

    private void initMap() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.frameLayoutMap);

        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.frameLayoutMap, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        ILog.iLogDebug(TAG, "onMapReady");
        this.naverMap = naverMap;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}