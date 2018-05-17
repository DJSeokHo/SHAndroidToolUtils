package com.swein.framework.module.camera.custom.camera1.fragment;


import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.swein.framework.module.camera.custom.camera1.cameracontroller.CameraController;
import com.swein.framework.module.camera.custom.camera1.cameracontroller.delegate.CameraControllerDelegate;
import com.swein.framework.module.camera.custom.camera1.constants.CameraOneConstants;
import com.swein.framework.module.camera.custom.camera1.preview.CameraOnePreview;
import com.swein.framework.tools.util.device.DeviceUtil;
import com.swein.framework.tools.window.WindowUtil;
import com.swein.shandroidtoolutils.R;


public class CameraOneFragment extends Fragment {

    private CameraController cameraController;

    private View rootView;

    private FrameLayout frameLayout;

    private ImageView imageViewImagePreview;

    private CameraOneConstants.CameraId cameraId = CameraOneConstants.CameraId.BACK;

    public CameraOneFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_camera_one, container, false);

        cameraController = new CameraController(getActivity(), new CameraControllerDelegate() {
            @Override
            public void captureFinished(String path) {
                imageViewImagePreview.setImageBitmap(BitmapFactory.decodeFile(path));
            }
        });

        findView();

        return rootView;
    }


    private void findView() {

        frameLayout = (FrameLayout) rootView.findViewById(R.id.fl_camera);
        imageViewImagePreview = (ImageView) rootView.findViewById(R.id.img_preview);

        ImageButton imageButtonSwitchCamera = (ImageButton) rootView.findViewById(R.id.img_btn_switch_camera);
        ImageButton imageButtonCapture = (ImageButton) rootView.findViewById(R.id.img_btn_capture);
        final ImageButton imageButtonFlashLight = (ImageButton) rootView.findViewById(R.id.img_btn_flash_light);

        imageButtonSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });

        imageButtonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraController.checkAngle(false);
            }
        });

        imageButtonFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Camera camera = cameraController.getCamera();
                    if(Camera.Parameters.FLASH_MODE_TORCH.equals(camera.getParameters().getFlashMode())) {
                        DeviceUtil.turnOffFlashLight(camera);
                        imageButtonFlashLight.setImageResource(R.drawable.flash_off);
                    }
                    else {
                        DeviceUtil.turnOnFlashLight(camera);
                        imageButtonFlashLight.setImageResource(R.drawable.flash_on);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

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
        CameraOnePreview cameraPreview = new CameraOnePreview(getContext(), cameraController.getCamera(), 90);

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