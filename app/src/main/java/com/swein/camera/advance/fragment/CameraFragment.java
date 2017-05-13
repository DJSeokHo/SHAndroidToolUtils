package com.swein.camera.advance.fragment;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.fragment.FragmentUtils;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CameraFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener {

    private static final String TAG = CameraFragment.class.getSimpleName();

    private TextureView textureView;
    private Size previewSize;
    private String cameraId;

    private CameraDevice cameraDevice;
    private Button buttonAdvanceCapture;

    private CaptureRequest previewCaptureRequest;
    private CaptureRequest.Builder previewCaptureRequestBuilder;
    private CameraCaptureSession cameraCaptureSession;
    private CameraCaptureSession.CaptureCallback cameraCaptureSessionCaptureCallback
            = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
            super.onCaptureStarted(session, request, timestamp, frameNumber);

        }
    };


    private CameraDevice.StateCallback cameraDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            ILog.iLogDebug(TAG, "Camera Opened");
            cameraDevice = camera;
            createCameraPreviewSession();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            ILog.iLogDebug(TAG, "Camera Disconnected");
            camera.close();
            cameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            ILog.iLogDebug(TAG, "Camera Error");
            camera.close();
            cameraDevice = null;
        }
    };

    /**
     * @see #setupCamera
     * @see #openCamera()
     */
    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            setupCamera(width, height);
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    private HandlerThread backgroundThread;

    /**
     * @see #openCamera()
     * @see #createCameraPreviewSession()
     */
    private Handler backgroundHandler;

    /**
     * @see #onResume()
     * step1
     * set preview size and camera
     * @param width
     * @param height
     * @see #getPreferredPreviewSize
     */
    private void setupCamera(int width, int height) {
        ILog.iLogDebug(TAG, "setupCamera");
        CameraManager cameraManager = (CameraManager)getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            //find camera for us
            for(String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                //not use front facing camera
                if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraMetadata.LENS_FACING_FRONT) {
                    continue;
                }

                StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                previewSize = getPreferredPreviewSize(streamConfigurationMap.getOutputSizes(SurfaceTexture.class), width, height);
                this.cameraId = cameraId;
                ILog.iLogDebug(TAG, "cameraId is : " + this.cameraId + " " + previewSize.getWidth() + " " + previewSize.getHeight());
                return;
            }
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**#step 2
     * @see #onResume()
     * open camera
     */
    private void openCamera() {
        CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraManager.openCamera(cameraId, cameraDeviceStateCallback, backgroundHandler);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**@see #onPause()
     * close camera
     */
    private void closeCamera() {
        if(cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }

        if(cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    /**step1
     * get preview size
     * @return
     */
    private Size getPreferredPreviewSize(Size[] mapSizes, int width, int height) {
        ILog.iLogDebug(TAG, "getPreferredPreviewSize");
        List<Size> sizeList = new ArrayList<>();
        for(Size option : mapSizes) {
            if(width > height) {
                if(option.getWidth() > width && option.getHeight() > height) {
                    sizeList.add(option);
                }
            }
            else {
                if(option.getWidth() > height && option.getHeight() > width) {
                    sizeList.add(option);
                }
            }
        }

        if(sizeList.size() > 0) {
            return Collections.min(sizeList, new Comparator<Size>() {
                @Override
                public int compare(Size lhs, Size rhs) {
                    return Long.signum(lhs.getWidth() * lhs.getHeight() - rhs.getWidth() * rhs.getHeight());
                }
            });
        }
        ILog.iLogDebug(TAG, "mapSizes is : " + mapSizes[0].getWidth() + " " + mapSizes[0].getHeight());

        return mapSizes[0];
    }

    /**
     * step3
     */
    private void createCameraPreviewSession() {
        try {
            SurfaceTexture surfaceTexture = this.textureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());

            Surface previewSurface = new Surface(surfaceTexture);
            previewCaptureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            previewCaptureRequestBuilder.addTarget(previewSurface);

            cameraDevice.createCaptureSession(Arrays.asList(previewSurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if(cameraDevice == null) {
                        return;
                    }

                    try {
                        previewCaptureRequest = previewCaptureRequestBuilder.build();
                        cameraCaptureSession = session;
                        cameraCaptureSession.setRepeatingRequest(
                                previewCaptureRequest,
                                cameraCaptureSessionCaptureCallback,
                                backgroundHandler);
                    }
                    catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    ILog.iLogDebug(TAG, "create camera session failed");
                }
            }, null);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see #onResume()
     * step 4
     */
    private void openBackgroundThread() {
        backgroundThread = new HandlerThread("Camera2 background thread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    /**
     * @see #onPause()
     */
    private void closeBackgroundThread() {
        backgroundThread.quitSafely();
        try {
            backgroundThread.join();
            backgroundThread = null;
            backgroundHandler = null;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = FragmentUtils.inflateFragment(inflater, R.layout.fragment_camera, container, savedInstanceState, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        view.findViewById(R.id.buttonAdvanceCapture).setOnClickListener(this);
        textureView = (TextureView) view.findViewById(R.id.textureView);
        buttonAdvanceCapture = (Button) view.findViewById(R.id.buttonAdvanceCapture);
        buttonAdvanceCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        openBackgroundThread();

        if(textureView.isAvailable()) {
            setupCamera(textureView.getWidth(), textureView.getHeight());
            openCamera();
        }
        else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }

    }

    @Override
    public void onPause() {
        closeCamera();
        closeBackgroundThread();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,  @NonNull int[] grantResults) {

    }


    @Override
    public void onClick(View v) {

    }
}
