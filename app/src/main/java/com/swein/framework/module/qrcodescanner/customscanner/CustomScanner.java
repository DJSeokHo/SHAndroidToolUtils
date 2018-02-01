package com.swein.framework.module.qrcodescanner.customscanner;

import android.content.Context;
import android.graphics.Canvas;

import com.swein.framework.module.qrcodescanner.constants.QRConstants;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * Created by seokho on 31/01/2018.
 */

public class CustomScanner extends ViewFinderView {


    public CustomScanner(Context context) {
        super(context);
        init();
    }

    private void init() {
        setSquareViewFinder(true);
        setBorderAlpha(0.0f);
        setViewFinderOffset(QRConstants.CAMERA_SCANNER_OFFSET);
        setVisibility(INVISIBLE);

//        setupViewFinder();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}
