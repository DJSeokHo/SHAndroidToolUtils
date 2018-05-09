package com.swein.framework.module.mdmcustom.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.swein.framework.module.mdmcustom.api.CustomMDMDeviceManager;
import com.swein.shandroidtoolutils.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomMDMFragment extends Fragment {

    private CustomMDMDeviceManager customMDMDeviceManager;
    private Button buttonCameraEnable;
    private Button buttonCameraDisable;

    public CustomMDMFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDeviceManage();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_custom_mdm, container, false);

        findView(rootView);

        return rootView;
    }

    private void findView(View rooView) {

        buttonCameraEnable = (Button) rooView.findViewById(R.id.buttonCameraEnable);
        buttonCameraDisable = (Button) rooView.findViewById(R.id.buttonCameraDisable);

        buttonCameraEnable.setEnabled(false);
        buttonCameraDisable.setEnabled(false);

        buttonCameraEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!customMDMDeviceManager.getCamera()) {
                    customMDMDeviceManager.setCamera(true);
                }
            }
        });
        buttonCameraDisable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customMDMDeviceManager.getCamera()) {
                    customMDMDeviceManager.setCamera(false);
                }
            }
        });
    }

    private void initDeviceManage() {
        customMDMDeviceManager = new CustomMDMDeviceManager(getContext());
        customMDMDeviceManager.activate();
    }

    @Override
    public void onResume() {
        if (customMDMDeviceManager.isActive()) {
            buttonCameraEnable.setEnabled(true);
            buttonCameraDisable.setEnabled(true);
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (customMDMDeviceManager.isActive()) {
            customMDMDeviceManager.removeActivate();
            buttonCameraEnable.setEnabled(false);
            buttonCameraDisable.setEnabled(false);
        }
        super.onDestroy();
    }
}
