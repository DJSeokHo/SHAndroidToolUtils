package com.swein.framework.module.camera.custom.camera2.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.swein.framework.module.camera.custom.camera2.custom.AutoFitTextureView;
import com.swein.framework.module.camera.custom.camera2.tool.CameraTwoTool;
import com.swein.framework.module.camera.custom.camera2.tool.CompareSizesByArea;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 1. {@link #activeAutoFitTextureView()}
 * 2. {@link #openCamera(int, int)}
 * 2-1. {@link #requestCameraPermission()}
 * 2-2. {@link #setUpCameraOutputs(int, int)}
 * 3. {@link #createCameraPreviewSession()}
 */
public class CameraTwoFragment extends Fragment {

    private final static String TAG = "CameraTwoFragment";

    private View rootView;
    private AutoFitTextureView autoFitTextureView;
    private ImageButton imageButtonTakePhoto;
    private ImageButton imageButtonSwitchCamera;


    private HandlerThread backgroundThread;
    private Handler backgroundHandler;

    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    // preview
    private Size previewSize;
    private static final int MAX_PREVIEW_WIDTH = 1920;
    private static final int MAX_PREVIEW_HEIGHT = 1080;
    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            ILog.iLogDebug(TAG, "onSurfaceTextureAvailable");
            // open camera here
            openCamera(width, height);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            ILog.iLogDebug(TAG, "onSurfaceTextureSizeChanged");
            // set size here
            configureTransform(width, height);
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    // camera
    private List<String> cameraIdList;
    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final String FRAGMENT_DIALOG = "dialog";
    private ImageReader imageReader; // handles still image capture
    private int sensorOrientation; // Orientation of the camera sensor
    private boolean flashSupported;
    private String currentCameraId;
    private CameraDevice currentCameraDevice;
    private File imageStorageFile;
    private final ImageReader.OnImageAvailableListener onImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        // "onImageAvailable" will be called when a still image is ready to be saved.
        @Override
        public void onImageAvailable(ImageReader reader) {
            Image image = reader.acquireNextImage();
            backgroundHandler.post(new Runnable() {
                @Override
                public void run() {

                    ThreadUtil.startThread(new Runnable() {
                        @Override
                        public void run() {

                            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            FileOutputStream output = null;

                            try {
                                output = new FileOutputStream(imageStorageFile);
                                output.write(bytes);
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            finally {
                                image.close();

                                if (null != output) {

                                    try {
                                        output.close();
                                    }
                                    catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                ThreadUtil.startUIThread(0, new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showShortToastNormal(getContext(), "Saved: " + imageStorageFile);
                                        imageStorageFile = null;
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    };

    private Semaphore cameraOpenCloseLock = new Semaphore(1); // prevent the app from exiting before closing the camera.
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            // This method is called when the camera is opened.  We start camera preview here.
            cameraOpenCloseLock.release();
            currentCameraDevice = cameraDevice;
            createCameraPreviewSession();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraOpenCloseLock.release();
            cameraDevice.close();
            currentCameraDevice = null;
        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            cameraOpenCloseLock.release();
            cameraDevice.close();
            currentCameraDevice = null;
            Activity activity = getActivity();
            if (null != activity) {
                activity.finish();
            }
        }
    };

    // preview request
    private CaptureRequest.Builder previewRequestBuilder; // for the camera preview
    private CaptureRequest previewRequest;
    private CameraCaptureSession captureSession;
    private static final int STATE_PREVIEW = 0; // Camera state: Showing camera preview.
    private static final int STATE_WAITING_LOCK = 1; // Camera state: Waiting for the focus to be locked.
    private static final int STATE_WAITING_PRE_CAPTURE = 2; // Camera state: Waiting for the exposure to be pre-capture state.
    private static final int STATE_WAITING_NON_PRE_CAPTURE = 3; // Camera state: Waiting for the exposure state to be something other than pre-capture.
    private static final int STATE_PICTURE_TAKEN = 4; // Camera state: Picture was taken.
    private int state = STATE_PREVIEW;
    /**
     * A {@link CameraCaptureSession.CaptureCallback} that handles events related to JPEG capture.
     */
    private CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() {

        private void process(CaptureResult result) {
            switch (state) {
                case STATE_PREVIEW: {
                    // We have nothing to do when the camera preview is working normally.
                    break;
                }
                case STATE_WAITING_LOCK: {
                    Integer afState = result.get(CaptureResult.CONTROL_AF_STATE);
                    if (afState == null) {
                        captureStillPicture();
                    }
                    else if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState ||
                            CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState) {
                        // CONTROL_AE_STATE can be null on some devices
                        // CONTROL_AE_STATE can be null when front camera
                        Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                        if (aeState == null ||
                                aeState == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
                            state = STATE_PICTURE_TAKEN;
                            captureStillPicture();
                        }
                        else {
                            runPreCaptureSequence();
                        }
                    }
                    else {
                        // front camera
                        if (result.get(CaptureResult.CONTROL_AE_STATE) == null) {
                            state = STATE_PICTURE_TAKEN;
                            captureStillPicture();
                        }
                        else {
                            runPreCaptureSequence();
                        }
                    }
                    break;
                }
                case STATE_WAITING_PRE_CAPTURE: {
                    // CONTROL_AE_STATE can be null on some devices
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null ||
                            aeState == CaptureResult.CONTROL_AE_STATE_PRECAPTURE ||
                            aeState == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED) {
                        state = STATE_WAITING_NON_PRE_CAPTURE;
                    }
                    break;
                }
                case STATE_WAITING_NON_PRE_CAPTURE: {
                    // CONTROL_AE_STATE can be null on some devices
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null || aeState != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
                        state = STATE_PICTURE_TAKEN;
                        captureStillPicture();
                    }
                    break;
                }
            }
        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            process(partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            process(result);
        }

    };


    public CameraTwoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_camera_two, container, false);
        findView();
        setListener();
        initCameraIdList();
        activeAutoFitTextureView();

        return rootView;
    }

    private void initCameraIdList() {

        if(getActivity() == null) {
            return;
        }

        if(cameraIdList == null) {
            try {
                cameraIdList = new ArrayList<>();

                CameraManager manager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
                if(manager == null) {
                    return;
                }

                cameraIdList.addAll(Arrays.asList(manager.getCameraIdList()));
                currentCameraId = cameraIdList.get(0);
            }
            catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private void activeAutoFitTextureView() {

        if(autoFitTextureView == null) {
            return;
        }

        if(autoFitTextureView.isAvailable()) {
            // open camera here
            openCamera(autoFitTextureView.getWidth(), autoFitTextureView.getHeight());
        }
        else {
            autoFitTextureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    /**
     * Configures the necessary {@link android.graphics.Matrix} transformation to `mTextureView`.
     * This method should be called after the camera preview size is determined in
     * setUpCameraOutputs and also the size of `mTextureView` is fixed.
     *
     * @param viewWidth  The width of `mTextureView`
     * @param viewHeight The height of `mTextureView`
     */
    private void configureTransform(int viewWidth, int viewHeight) {
        Activity activity = getActivity();

        if (null == autoFitTextureView || null == previewSize || null == activity) {
            return;
        }

        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        Matrix matrix = new Matrix();
        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);
        RectF bufferRect = new RectF(0, 0, previewSize.getHeight(), previewSize.getWidth());

        float centerX = viewRect.centerX();
        float centerY = viewRect.centerY();

        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY());
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL);
            float scale = Math.max((float) viewHeight / previewSize.getHeight(), (float) viewWidth / previewSize.getWidth());
            matrix.postScale(scale, scale, centerX, centerY);
            matrix.postRotate(90 * (rotation - 2), centerX, centerY);
        }
        else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180, centerX, centerY);
        }
        autoFitTextureView.setTransform(matrix);
    }

    private void openCamera(int width, int height) {
        if(getActivity() == null) {
            return;
        }
        // 2 - 1 check permission
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
            return;
        }

        // 2 - 2 set camera
        setUpCameraOutputs(width, height);
        // 2 - 3 set size
        configureTransform(width, height);
        // 2 - 4 open camera by CameraManager
        Activity activity = getActivity();
        CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        if(manager == null) {
            return;
        }

        try {
            if (!cameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            manager.openCamera(currentCameraId, stateCallback, backgroundHandler);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
        }
    }

    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            new ConfirmationDialog().show(getChildFragmentManager(), FRAGMENT_DIALOG);
        }
        else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    /**
     * Sets up member variables related to camera.
     *
     * @param width  The width of available size for camera preview
     * @param height The height of available size for camera preview
     */
    @SuppressWarnings("SuspiciousNameCombination")
    private void setUpCameraOutputs(int width, int height) {
        Activity activity = getActivity();
        if(activity == null) {
            return;
        }

        CameraManager cameraManager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        if(cameraManager == null) {
            return;
        }

        try {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(currentCameraId);

            // We don't use a front facing camera in this sample.
//            Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
//            if (facing != null && facing == CameraCharacteristics.LENS_FACING_FRONT) {
//                continue;
//            }

            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (map == null) {
                return;
            }

            // For still image captures, we use the largest available size.
            Size largest = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new CompareSizesByArea());
            imageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), ImageFormat.JPEG, /*maxImages*/2);
            imageReader.setOnImageAvailableListener(onImageAvailableListener, backgroundHandler);

            // Find out if we need to swap dimension to get the preview size relative to sensor
            // coordinate.
            int displayRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            //noinspection ConstantConditions
            sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            boolean swappedDimensions = false;
            switch (displayRotation) {
                case Surface.ROTATION_0:
                case Surface.ROTATION_180:
                    if (sensorOrientation == 90 || sensorOrientation == 270) {
                        swappedDimensions = true;
                    }
                    break;
                case Surface.ROTATION_90:
                case Surface.ROTATION_270:
                    if (sensorOrientation == 0 || sensorOrientation == 180) {
                        swappedDimensions = true;
                    }
                    break;
                default:
                    ILog.iLogDebug(TAG, "Display rotation is invalid: " + displayRotation);
            }

            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            int rotatedPreviewWidth = width;
            int rotatedPreviewHeight = height;
            int maxPreviewWidth = displaySize.x;
            int maxPreviewHeight = displaySize.y;

            if (swappedDimensions) {
                rotatedPreviewWidth = height;
                rotatedPreviewHeight = width;
                maxPreviewWidth = displaySize.y;
                maxPreviewHeight = displaySize.x;
            }

            if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
                maxPreviewWidth = MAX_PREVIEW_WIDTH;
            }

            if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
                maxPreviewHeight = MAX_PREVIEW_HEIGHT;
            }

            // Danger, W.R.! Attempting to use too large a preview size could  exceed the camera
            // bus' bandwidth limitation, resulting in gorgeous previews but the storage of
            // garbage capture data.
            previewSize = CameraTwoTool.chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class),
                    rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                    maxPreviewHeight, largest);

            // We fit the aspect ratio of TextureView to the size of preview we picked.
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                autoFitTextureView.setAspectRatio(previewSize.getWidth(), previewSize.getHeight());
            }
            else {
                autoFitTextureView.setAspectRatio(previewSize.getHeight(), previewSize.getWidth());
            }

            // Check if the flash is supported.
            Boolean available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            flashSupported = available == null ? false : available;
            ILog.iLogDebug(TAG, "flash ??? " + flashSupported);

        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            // Currently an NPE is thrown when the Camera2API is used but not supported on the
            // device this code runs.
            ToastUtil.showShortToastNormal(getContext(), "This device doesn\\'t support Camera2 API.");
        }
    }

    /**
     * Creates a new {@link CameraCaptureSession} for camera preview.
     */
    private void createCameraPreviewSession() {

        try {

            SurfaceTexture surfaceTexture = autoFitTextureView.getSurfaceTexture();

            // We configure the size of default buffer to be the size of camera preview we want.
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());

            // This is the output Surface we need to start preview.
            Surface surface = new Surface(surfaceTexture);

            // We set up a CaptureRequest.Builder with the output Surface.
            previewRequestBuilder = currentCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            previewRequestBuilder.addTarget(surface);

            // Here, we create a CameraCaptureSession for camera preview.
            currentCameraDevice.createCaptureSession(Arrays.asList(surface, imageReader.getSurface()),
                    new CameraCaptureSession.StateCallback() {

                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            // The camera is already closed
                            if (null == currentCameraDevice) {
                                return;
                            }

                            // When the session is ready, we start displaying the preview.
                            captureSession = cameraCaptureSession;
                            try {
                                // Auto focus should be continuous for camera preview.
                                previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

                                // Flash is automatically enabled when necessary.
                                if (flashSupported) {
                                    previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                                }

                                // Finally, we start displaying the camera preview.
                                previewRequest = previewRequestBuilder.build();
                                captureSession.setRepeatingRequest(previewRequest, captureCallback, backgroundHandler);
                            }
                            catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                            ThreadUtil.startUIThread(0, new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showShortToastNormal(getContext(), "Failed");
                                }
                            });
                        }
                    }, null);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Capture a still picture. This method should be called when we get a response in
     * {@link #captureCallback} from both {@link #lockFocus()}.
     */
    private void captureStillPicture() {
        try {
            final Activity activity = getActivity();
            if (null == activity || null == currentCameraDevice) {
                return;
            }
            // This is the CaptureRequest.Builder that we use to take a picture.
            final CaptureRequest.Builder captureBuilder = currentCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureBuilder.addTarget(imageReader.getSurface());

            // Use the same AE and AF modes as the preview.
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            if (flashSupported) {
                captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            }

            // Orientation
            int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, getOrientation(rotation));

            CameraCaptureSession.CaptureCallback captureCallback = new CameraCaptureSession.CaptureCallback() {

                @Override
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    ThreadUtil.startUIThread(0, new Runnable() {
                        @Override
                        public void run() {

                            unlockFocus();
                        }
                    });
                }
            };

            captureSession.stopRepeating();
            captureSession.abortCaptures();
            captureSession.capture(captureBuilder.build(), captureCallback, null);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {

        showProgress();

        ThreadUtil.startThread(new Runnable() {
            @Override
            public void run() {
                createImageFile();
                lockFocus();
            }
        });
    }

    private void createImageFile() {
        if(getActivity() == null) {
            return;
        }

        imageStorageFile = new File(getActivity().getExternalFilesDir(null), DateUtil.getCurrentDateTimeStringWithNoSpace("_") + ".jpg");
        ILog.iLogDebug(TAG, imageStorageFile.getPath());
    }

    private void lockFocus() {
        ThreadUtil.startUIThread(0, new Runnable() {
            @Override
            public void run() {
                imageButtonSwitchCamera.setVisibility(View.GONE);
            }
        });

        try {
            // This is how to tell the camera to lock focus.
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START);
            // Tell #captureCallback to wait for the lock.
            state = STATE_WAITING_LOCK;
            captureSession.capture(previewRequestBuilder.build(), captureCallback, backgroundHandler);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void unlockFocus() {
        try {
            // Reset the auto-focus trigger
            previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
            if (flashSupported) {
                previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            }

            captureSession.capture(previewRequestBuilder.build(), captureCallback, backgroundHandler);
            // After this, the camera will go back to the normal state of preview.
            state = STATE_PREVIEW;
            captureSession.setRepeatingRequest(previewRequest, captureCallback, backgroundHandler);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }

        imageButtonSwitchCamera.setVisibility(View.VISIBLE);
        hideProgress();
    }

    private void showProgress() {
        rootView.findViewById(R.id.frameLayoutProgress).setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        rootView.findViewById(R.id.frameLayoutProgress).setVisibility(View.GONE);
    }

    private void runPreCaptureSequence() {
        try {
            // This is how to tell the camera to trigger.
            previewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START);
            // Tell #captureCallback to wait for the pre-capture sequence to be set.
            state = STATE_WAITING_PRE_CAPTURE;
            captureSession.capture(previewRequestBuilder.build(), captureCallback, backgroundHandler);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private int getOrientation(int rotation) {
        // Sensor orientation is 90 for most devices, or 270 for some devices (eg. Nexus 5X)
        // We have to take that into account and rotate JPEG properly.
        // For devices with orientation of 90, we simply return our mapping from ORIENTATIONS.
        // For devices with orientation of 270, we need to rotate the JPEG 180 degrees.
        return (ORIENTATIONS.get(rotation) + sensorOrientation + 270) % 360;
    }

    /**
     * Closes the current {@link CameraDevice}.
     */
    private void closeCamera() {
        ILog.iLogDebug(TAG, "closeCamera");

        try {
            cameraOpenCloseLock.acquire();
            if (null != captureSession) {
                captureSession.close();
                captureSession = null;
            }

            if (null != currentCameraDevice) {
                currentCameraDevice.close();
                currentCameraDevice = null;
            }

            if (null != imageReader) {
                imageReader.close();
                imageReader = null;
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        }
        finally {
            cameraOpenCloseLock.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();

        // reopen camera here
        activeAutoFitTextureView();
    }

    @Override
    public void onPause() {
        // close camera here
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    private void findView() {
        autoFitTextureView = rootView.findViewById(R.id.autoFitTextureView);
        imageButtonTakePhoto = rootView.findViewById(R.id.imageButtonTakePhoto);
        imageButtonSwitchCamera = rootView.findViewById(R.id.imageButtonSwitchCamera);
    }

    private void setListener() {
        imageButtonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // take photo here
                takePicture();
            }
        });

        imageButtonSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });
    }

    private void startBackgroundThread() {
        backgroundThread = new HandlerThread("CameraBackground");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
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

    private void switchCamera() {
        imageButtonTakePhoto.setVisibility(View.GONE);
        imageButtonSwitchCamera.setVisibility(View.GONE);

        ThreadUtil.startThread(new Runnable() {
            @Override
            public void run() {

                closeCamera();
                stopBackgroundThread();

                for(String cameraId : cameraIdList) {
                    if(!currentCameraId.equals(cameraId)) {
                        currentCameraId = cameraId;
                        break;
                    }
                }

                startBackgroundThread();

                ThreadUtil.startUIThread(0, new Runnable() {
                    @Override
                    public void run() {
                        activeAutoFitTextureView();
                        ThreadUtil.startUIThread(500, new Runnable() {
                            @Override
                            public void run() {
                                imageButtonTakePhoto.setVisibility(View.VISIBLE);
                                imageButtonSwitchCamera.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
        });


    }

    /**
     * Shows OK/Cancel confirmation dialog about camera permission.
     */
    public static class ConfirmationDialog extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage("请求相机权限")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            parent.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Activity activity = parent.getActivity();
                                    if (activity != null) {
                                        activity.finish();
                                    }
                                }
                            })
                    .create();
        }
    }
}
