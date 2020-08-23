package com.swein.framework.module.cameramodule.scan;

import android.os.Bundle;
import android.util.Size;

import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.common.util.concurrent.ListenableFuture;
import com.swein.constants.Constants;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * add apply plugin: 'com.google.gms.google-services' at last line to module level build.gradle
 * add classpath 'com.google.gms:google-services:4.3.3' to project level build.gradle
 */
public class ScannerActivity extends FragmentActivity {

    private final static String TAG = "ScannerActivity";

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
    private ExecutorService cameraExecutor = Executors.newSingleThreadExecutor();

    private Camera camera;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        checkBundle();

        findView();
        initCamera();
    }

    private void checkBundle() {
        Bundle bundle = getIntent().getBundleExtra(Constants.BUNDLE_KEY);
        if(bundle != null) {
            type = bundle.getInt("type");
        }
        else {
            finish();
        }
    }

    private void findView() {
        previewView = findViewById(R.id.previewView);
    }

    private void initCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {

            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                startCamera(cameraProvider);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, ContextCompat.getMainExecutor(this));
    }

    private void startCamera(ProcessCameraProvider cameraProvider) {
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        int width = previewView.getWidth();
        int height = previewView.getHeight();

        Size size;
        if(width > 600) {
            float rate = 600f / (float)width;
            ILog.iLogDebug(TAG, "rate is " + rate);

            width = (int)((float)width * rate);
            height = (int)((float)height * rate);
        }

        ILog.iLogDebug(TAG, width + " " + height);

        size = new Size(width, height);

        Preview preview = new Preview.Builder().setTargetResolution(size).build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(size)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        if(type == 0) {
            imageAnalysis.setAnalyzer(cameraExecutor, new QrCodeAnalyzer(result -> {
                previewView.post(() -> {
                    ILog.iLogDebug(TAG, "scanned: " + result.getText());
                    ToastUtil.showCustomLongToastNormal(this, result.getText());
                    finish();
                });
                return null;
            }));
        }
        else if(type == 1) {
            imageAnalysis.setAnalyzer(cameraExecutor, new TextAnalyzer(new TextAnalyzer.TextAnalyzerDelegate() {
                @Override
                public void onTextDetected(String result) {
                    ILog.iLogDebug(TAG, "scanned: " + result);
                    finish();
                }

                @Override
                public int getRotation() {

                    int angle = 0;
                    if(camera != null) {
                        angle = camera.getCameraInfo().getSensorRotationDegrees();
                    }

//                ILog.iLogDebug(TAG, "angle is " + angle);
                    return angle;
                }
            }));
        }

        cameraProvider.unbindAll();

        camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
        preview.setSurfaceProvider(previewView.createSurfaceProvider());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}