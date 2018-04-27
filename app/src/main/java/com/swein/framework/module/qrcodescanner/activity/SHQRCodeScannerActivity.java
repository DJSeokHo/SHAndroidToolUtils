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
import com.swein.framework.module.sound.effert.SoundEffect;
import com.swein.framework.tools.util.date.DateUtil;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * reference documentation - https://www.jianshu.com/p/168bef5526ae
 */
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

    private List<ScanResultItemViewHolder> scanResultItemViewHolderList = new ArrayList<>();


    private ScanResultItemViewHolder.ScanResultItemViewHolderDelegate scanResultItemViewHolderDelegate = new ScanResultItemViewHolder.ScanResultItemViewHolderDelegate() {
        @Override
        public void onDeleteClicked(View view, ScanResultItemViewHolder scanResultItemViewHolder) {
            flexboxLayout.removeView(view);
            scanResultItemViewHolderList.remove(scanResultItemViewHolder);
            toggleResultView();
            setResultViewIndex();
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

            flexboxLayout.addView(scanResultItemViewHolder.getItemView(), 0);

            scanResultItemViewHolderList.add(scanResultItemViewHolder);

            toggleResultView();
            setResultViewIndex();

            zXingScannerView.resumeCameraPreview(resultHandler);
            progressBar.setVisibility(View.GONE);

            SoundEffect.getInstance().play(SHQRCodeScannerActivity.this, 1);

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

        SoundEffect.getInstance().initSoundEffect(SHQRCodeScannerActivity.this);
        SoundEffect.getInstance().addResources(1, R.raw.sound_click);
        SoundEffect.getInstance().addResources(2, R.raw.sound_open);


        customScanner = new CustomScanner(this);


        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flexboxLayout.removeAllViews();
                scanResultItemViewHolderList.clear();
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
                    SoundEffect.getInstance().play(SHQRCodeScannerActivity.this, 2);
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

    private void setResultViewIndex() {

        if(0 == flexboxLayout.getChildCount()) {
            return;
        }

        int count = flexboxLayout.getChildCount();
        for(int i = count - 1; i >= 0; i--) {
            scanResultItemViewHolderList.get(i).setIndex(String.valueOf(i + 1));
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
