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
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScanBankCardActivity extends FragmentActivity {

    private final static String TAG = "ScanBankCardActivity";

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
    private ExecutorService cameraExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_bank_card);

        findView();
        initCamera();
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

        Preview preview = new Preview.Builder().setTargetResolution(new Size(width, height)).build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setTargetResolution(new Size(width, height))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();

        imageAnalysis.setAnalyzer(cameraExecutor, new QrCodeAnalyzer(result -> {
            previewView.post(() -> {
                ILog.iLogDebug(TAG, "scanned: " + result.getText());
                finish();
            });
            return null;
        }));

        cameraProvider.unbindAll();

        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
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