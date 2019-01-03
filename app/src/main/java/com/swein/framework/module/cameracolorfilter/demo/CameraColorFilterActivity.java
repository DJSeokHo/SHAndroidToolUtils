package com.swein.framework.module.cameracolorfilter.demo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Size;
import android.util.SparseArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CameraColorFilterActivity extends FragmentActivity {

    private static final int REQUEST_CAMERA_PERMISSIONS = 101;
    private static final int REQUEST_STORAGE_PERMISSIONS = 102;

    /* camera permissions */
    private static final String[] CAMERA_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    private Button buttonCapture;
    private Button buttonRecord;



    /* texture view */
    private TextureView textureView;
    private TextureView.SurfaceTextureListener surfaceTextureListener;
    /* texture view */


    private CameraDevice cameraDevice;
    private CameraDevice.StateCallback cameraDeviceStateCallback;
    private String cameraId;

    private Size previewSize;
    private Size videoSize;

    private MediaRecorder mediaRecorder;
    private boolean isRecording;

    private CaptureRequest.Builder captureRequestBuilder;


    private HandlerThread backgroundHandlerThread;
    private Handler backgroundHandler;
    private final static String BACKGROUND_THREAD_NAME = "CameraBackgroundThread";


    private int totalRotation;

    private static SparseArray ORIENTATIONS = new SparseArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    private static class CompareSizeByArea implements Comparator<Size> {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public int compare(Size o1, Size o2) {
            return Long.signum((long)o1.getWidth() * o1.getHeight() / (long)o2.getWidth() * o2.getHeight());
        }
    }

    private File videoFolder;
    private String videoFileName;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initListenerAndCallback() {

        surfaceTextureListener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                setupCamera(width, height);
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

        cameraDeviceStateCallback = new CameraDevice.StateCallback() {

            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                cameraDevice = camera;

                if(isRecording) {
                    try {
                        createVideoFile();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    startRecord();
                    mediaRecorder.start();
                }
                else {
                    startPreview();
                }

            }

            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {
                camera.close();
                cameraDevice = null;
            }

            @Override
            public void onError(@NonNull CameraDevice camera, int error) {
                camera.close();
                cameraDevice = null;
            }
        };
    }


    private void findView() {
        textureView = findViewById(R.id.textureView);
        buttonCapture = findViewById(R.id.buttonCapture);
        buttonRecord = findViewById(R.id.buttonRecord);

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isRecording) {
                    isRecording = false;
                    buttonRecord.setText("record");
                    mediaRecorder.stop();
                    mediaRecorder.reset();
                    startPreview();
                }
                else {
                    isRecording = true;
                    buttonRecord.setText("stop");

                    try {
                        createVideoFile();
                        startRecord();
                        mediaRecorder.start();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_color_filter);

        checkWriteStoragePermission();
        requestPermissions(CAMERA_PERMISSIONS, REQUEST_CAMERA_PERMISSIONS);

        findView();

        createVideoFolder();

        mediaRecorder = new MediaRecorder();

        isRecording = false;

        initListenerAndCallback();
    }

    @Override
    public void onResume() {
        super.onResume();

        resumeCamera();

    }

    private void resumeCamera() {

        startBackgroundThread();

        if(textureView.isAvailable()) {
            setupCamera(textureView.getWidth(), textureView.getHeight());
            connectCamera();
        }
        else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupCamera(int width, int height) {

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            for(String string : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(string);

                if(cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }

                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

                int deviceOrientation = getWindowManager().getDefaultDisplay().getRotation();
                totalRotation = sensorToDeviceRotation(cameraCharacteristics, deviceOrientation);
                boolean swapRotation = (totalRotation == 90 || totalRotation == 270);

                int rotationWidth = width;
                int rotationHeight = height;

                if(swapRotation) {
                    rotationWidth = height;
                    rotationHeight = width;
                }

                previewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture.class), rotationWidth, rotationHeight);
                videoSize = chooseOptimalSize(map.getOutputSizes(MediaRecorder.class), rotationWidth, rotationHeight);

                cameraId = string;

                return;
            }
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void connectCamera() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraManager.openCamera(cameraId, cameraDeviceStateCallback, backgroundHandler);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startPreview() {
        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());

        Surface previewSurface = new Surface(surfaceTexture);

        try {
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(previewSurface);

            cameraDevice.createCaptureSession(Arrays.asList(previewSurface), new CameraCaptureSession.StateCallback() {

                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        session.setRepeatingRequest(captureRequestBuilder.build(), null, backgroundHandler);
                    }
                    catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    ToastUtil.showCustomShortToastNormal(CameraColorFilterActivity.this, "Unable to setup camera preview");
                }
            }, null);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startRecord() {

        try {

            setupMediaRecorder();
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface previewSurface = new Surface(surfaceTexture);
            Surface recordSurface = mediaRecorder.getSurface();
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);
            captureRequestBuilder.addTarget(previewSurface);
            captureRequestBuilder.addTarget(recordSurface);

            cameraDevice.createCaptureSession(Arrays.asList(previewSurface, recordSurface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        session.setRepeatingRequest(captureRequestBuilder.build(), null, null);
                    }
                    catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            }, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startBackgroundThread() {
        backgroundHandlerThread = new HandlerThread(BACKGROUND_THREAD_NAME);
        backgroundHandlerThread.start();
        backgroundHandler = new Handler(backgroundHandlerThread.getLooper());
    }

    private void stopBackgroundThread() {
        try {
            backgroundHandlerThread.quitSafely();
            backgroundHandlerThread.join();

            backgroundHandlerThread = null;
            backgroundHandler = null;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void closeCamera() {
        if(cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    @Override
    public void onPause() {
        closeCamera();

        stopBackgroundThread();
        super.onPause();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Size chooseOptimalSize(Size[] choices, int width, int height) {
        List<Size> biggest = new ArrayList<>();
        for(Size size : choices) {
            if(size.getHeight() == size.getWidth() * height / width &&
                    size.getWidth() >= width &&
                    size.getHeight() >= height) {
                biggest.add(size);
            }
        }

        if(biggest.size() > 0) {
            return Collections.min(biggest, new CompareSizeByArea());
        }
        else {
            return choices[0];
        }
    }

    private void createVideoFolder() {
        File movieFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        videoFolder = new File(movieFile, "CameraV2Folder");
        if(!videoFolder.exists()) {
            videoFolder.mkdir();
        }
    }

    private File createVideoFile() throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String prepend = "VIDEO_" + timestamp + "_";
        File videoFile = File.createTempFile(prepend, ".mp4", videoFolder);
        videoFileName = videoFile.getAbsolutePath();
        return videoFile;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupMediaRecorder() throws IOException {
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(videoFileName);
        mediaRecorder.setVideoEncodingBitRate(1000000);
        mediaRecorder.setVideoFrameRate(30);
        mediaRecorder.setVideoSize(videoSize.getWidth(), videoSize.getHeight());
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setOrientationHint(totalRotation);
        mediaRecorder.prepare();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static int sensorToDeviceRotation(CameraCharacteristics cameraCharacteristics, int deviceOrientation) {
        int sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        deviceOrientation = (int) ORIENTATIONS.get(deviceOrientation);

        return (sensorOrientation + deviceOrientation + 360) % 360;
    }

    private void checkWriteStoragePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            }
            else {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ToastUtil.showCustomShortToastNormal(this, "can not save video");
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSIONS);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CAMERA_PERMISSIONS) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
        }
        else if(requestCode == 102) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastUtil.showCustomShortToastNormal(this, "can save video");
            }
        }
        else {
            ToastUtil.showCustomShortToastNormal(this, "need camera permission");
            finish();
        }

    }
}
