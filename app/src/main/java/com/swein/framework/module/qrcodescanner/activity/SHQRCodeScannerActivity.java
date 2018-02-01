package com.swein.framework.module.qrcodescanner.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.zxing.Result;
import com.swein.framework.module.qrcodescanner.customscanner.CustomScanner;
import com.swein.framework.module.qrcodescanner.scanresult.model.ScanResultItem;
import com.swein.framework.module.qrcodescanner.scanresult.resultitemviewholder.ScanResultItemViewHolder;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.shandroidtoolutils.R;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SHQRCodeScannerActivity extends Activity {


    private ZXingScannerView zXingScannerView;

    private ImageButton buttonFlash;
    private ImageButton buttonQR;
    private ImageButton buttonReset;
    private FrameLayout progressBar;
    private HorizontalScrollView resultView;
    private FlexboxLayout flexboxLayout;
    private TextView countTextView;

    private CustomScanner customScanner;

    private boolean isFlashOn = false;
    private boolean enableQRScan = false;

    private ScanResultItemViewHolder.ScanResultItemViewHolderDelegate scanResultItemViewHolderDelegate = new ScanResultItemViewHolder.ScanResultItemViewHolderDelegate() {
        @Override
        public void onDeleteClicked(View view) {
            flexboxLayout.removeView(view);
            toggleResultView();
        }
    };

    private ZXingScannerView.ResultHandler resultHandler = new ZXingScannerView.ResultHandler() {
        @Override
        public void handleResult(Result result) {

            if(!getQRScanState()) {
                zXingScannerView.resumeCameraPreview(resultHandler);
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            ScanResultItemViewHolder scanResultItemViewHolder = new ScanResultItemViewHolder(
                    SHQRCodeScannerActivity.this,
                    new ScanResultItem(result.getText(), DateUtil.getCurrentDateTimeString()), scanResultItemViewHolderDelegate);

            flexboxLayout.addView(scanResultItemViewHolder.getItemView());

            toggleResultView();

            zXingScannerView.resumeCameraPreview(resultHandler);
            progressBar.setVisibility(View.GONE);

            disableQRScan();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shqrcode_scanner);

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.scannerContainer);
        buttonFlash = (ImageButton) findViewById(R.id.buttonFlash);
        buttonQR = (ImageButton) findViewById(R.id.buttonQR);
        buttonReset = (ImageButton) findViewById(R.id.buttonReset);
        progressBar = (FrameLayout) findViewById(R.id.progressBar);
        resultView = (HorizontalScrollView) findViewById(R.id.resultView);
        flexboxLayout = (FlexboxLayout) findViewById(R.id.flexboxLayout);
        countTextView = (TextView) findViewById(R.id.countTextView);

        customScanner = new CustomScanner(this);


        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flexboxLayout.removeAllViews();

                toggleResultView();
            }
        });

        buttonFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFlash();
            }
        });

        buttonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getQRScanState()) {
                    disableQRScan();
                }
                else {
                    enableQRScan();
                }
            }
        });


        zXingScannerView = new ZXingScannerView(this) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {

                return customScanner;
            }
        };

        zXingScannerView.setResultHandler(resultHandler);

        contentFrame.addView(zXingScannerView);

    }

    private void enableQRScan() {
        enableQRScan = true;
        buttonQR.setImageResource(R.drawable.qr_code_enable);
    }

    private void disableQRScan() {
        enableQRScan = false;
        buttonQR.setImageResource(R.drawable.qr_code_disable);
    }

    private boolean getQRScanState() {
        return enableQRScan;
    }

    private void toggleFlash() {

        if(isFlashOn) {
            isFlashOn = false;
            buttonFlash.setImageResource(R.drawable.flash_off);
        }
        else {
            isFlashOn = true;
            buttonFlash.setImageResource(R.drawable.flash_on);
        }

        zXingScannerView.setFlash(isFlashOn);
    }

    private void toggleResultView() {

        if(0 != flexboxLayout.getChildCount()) {
            resultView.setVisibility(View.VISIBLE);
            countTextView.setText(String.valueOf(flexboxLayout.getChildCount()));
            buttonReset.setVisibility(View.VISIBLE);
        }
        else {
            resultView.setVisibility(View.GONE);
            countTextView.setText("0");
            buttonReset.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(resultHandler);
        zXingScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
        zXingScannerView.setFlash(false);
        buttonFlash.setImageResource(R.drawable.flash_off);
    }

    @Override
    protected void onDestroy() {
        zXingScannerView = null;
        super.onDestroy();
    }


}
