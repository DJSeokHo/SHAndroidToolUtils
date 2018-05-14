package com.swein.framework.module.camera.custom.camera1.fragment;


import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.swein.framework.module.camera.custom.camera1.cameracontroller.CameraController;
import com.swein.framework.module.camera.custom.camera1.constants.CameraOneConstants;
import com.swein.framework.module.camera.custom.camera1.preview.CameraPreview;
import com.swein.framework.tools.window.WindowUtil;
import com.swein.shandroidtoolutils.R;


public class CameraOneFragment extends Fragment {

    private CameraController cameraController;

    private View rootView;

    private FrameLayout frameLayout;
    private ImageButton buttonSwitchCamera;
    private ImageButton buttonCapture;

    private CameraOneConstants.CameraId cameraId = CameraOneConstants.CameraId.BACK;

    public CameraOneFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraController = new CameraController(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_camera_one, container, false);

        findView();

        return rootView;
    }


    private void findView() {

        frameLayout = (FrameLayout) rootView.findViewById(R.id.fl_camera);
        buttonSwitchCamera = (ImageButton) rootView.findViewById(R.id.btn_switch_camera);
        buttonCapture = (ImageButton) rootView.findViewById(R.id.btn_capture);

        buttonSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraController.checkAngle(false);
            }
        });
    }

    private void switchCamera() {
        if(CameraOneConstants.CameraId.BACK == cameraId) {
            cameraId = CameraOneConstants.CameraId.FRONT;
        }
        else {
            cameraId = CameraOneConstants.CameraId.BACK;
        }

        releaseCameraPreview();
        setCameraPreview();
    }

    @Override
    public void onResume() {
        WindowUtil.fullScreen(getActivity());
        setCameraPreview();
        super.onResume();
    }

    private void setCameraPreview() {
        cameraController.openCamera(cameraId.ordinal());
        CameraPreview cameraPreview = new CameraPreview(getContext(), cameraController.getCamera(), 90);

        frameLayout.addView(cameraPreview);
    }


    @Override
    public void onPause() {
        releaseCameraPreview();
        super.onPause();
    }

    private void releaseCameraPreview() {
        frameLayout.removeAllViews();
        cameraController.releaseCamera();
    }

    @Override
    public void onDestroy() {
        cameraController = null;
        super.onDestroy();
    }
}
