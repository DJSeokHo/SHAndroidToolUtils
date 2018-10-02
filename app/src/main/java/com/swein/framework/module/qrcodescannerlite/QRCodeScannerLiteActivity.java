package com.swein.framework.module.qrcodescannerlite;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.SurfaceView;

import com.google.zxing.Result;
import com.google.zxing.client.android.BaseCaptureActivity;
import com.swein.framework.module.qrcodescannerlite.scannerview.AutoScannerView;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.toast.ToastUtil;
import com.swein.shandroidtoolutils.R;

public class QRCodeScannerLiteActivity extends BaseCaptureActivity {

    private static final String TAG = "QRCodeScannerLiteActivity";

    private SurfaceView surfaceView;
    private AutoScannerView autoScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner_lite);
        surfaceView = findViewById(R.id.previewView);
        autoScannerView = findViewById(R.id.autoscannerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoScannerView.setCameraManager(cameraManager);
    }

    @Override
    public SurfaceView getSurfaceView() {
        return (surfaceView == null) ? (SurfaceView) findViewById(R.id.previewView) : surfaceView;
    }

    @Override
    public void dealDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        ILog.iLogDebug(TAG, "dealDecode " + rawResult.getText() + " " + barcode + " " + scaleFactor);
        playBeepSoundAndVibrate(true, true);
        ToastUtil.showCustomShortToastNormal(this, rawResult.getText());
        reScan();
    }

}
