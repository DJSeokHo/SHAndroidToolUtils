package com.swein.framework.module.qrcodescannerlite.scannerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.client.android.camera.CameraManager;
import com.swein.shandroidtoolutils.R;

public class AutoScannerView extends View {

    private Paint maskPaint;
    private Paint linePaint;
    private Paint traAnglePaint;
    private Paint textPaint;
    private CameraManager cameraManager;

    private final int maskColor = Color.parseColor("#60000000");
    private final int triAngleColor = Color.parseColor("#76EE00");
    private final int lineColor = Color.parseColor("#FF0000");
    private final int textColor = Color.parseColor("#CCCCCC");

    private final int triAngleLength = dp2px(20);
    private final int triAngleWidth = dp2px(1);
    private final int textMarinTop = dp2px(30);
    private int lineOffsetCount = 0;

    public AutoScannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskPaint.setColor(maskColor);

        traAnglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        traAnglePaint.setColor(triAngleColor);
        traAnglePaint.setStrokeWidth(triAngleWidth);
        traAnglePaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(lineColor);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(dp2px(14));
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;

        // make sure refreshed
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (cameraManager == null)
            return;
        Rect frame = cameraManager.getFramingRect();
        Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null || previewFrame == null) {
            return;
        }

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.drawRect(0, 0, width, frame.top, maskPaint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, maskPaint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, maskPaint);
        canvas.drawRect(0, frame.bottom + 1, width, height, maskPaint);

        String text = "QR Code Scanner";
        canvas.drawText(text, (width - textPaint.measureText(text)) / 2, frame.bottom + textMarinTop, textPaint);


        Path leftTopPath = new Path();
        leftTopPath.moveTo(frame.left + triAngleLength, frame.top + triAngleWidth / 2);
        leftTopPath.lineTo(frame.left + triAngleWidth / 2, frame.top + triAngleWidth / 2);
        leftTopPath.lineTo(frame.left + triAngleWidth / 2, frame.top + triAngleLength);
        canvas.drawPath(leftTopPath, traAnglePaint);

        Path rightTopPath = new Path();
        rightTopPath.moveTo(frame.right - triAngleLength, frame.top + triAngleWidth / 2);
        rightTopPath.lineTo(frame.right - triAngleWidth / 2, frame.top + triAngleWidth / 2);
        rightTopPath.lineTo(frame.right - triAngleWidth / 2, frame.top + triAngleLength);
        canvas.drawPath(rightTopPath, traAnglePaint);

        Path leftBottomPath = new Path();
        leftBottomPath.moveTo(frame.left + triAngleWidth / 2, frame.bottom - triAngleLength);
        leftBottomPath.lineTo(frame.left + triAngleWidth / 2, frame.bottom - triAngleWidth / 2);
        leftBottomPath.lineTo(frame.left + triAngleLength, frame.bottom - triAngleWidth / 2);
        canvas.drawPath(leftBottomPath, traAnglePaint);

        Path rightBottomPath = new Path();
        rightBottomPath.moveTo(frame.right - triAngleLength, frame.bottom - triAngleWidth / 2);
        rightBottomPath.lineTo(frame.right - triAngleWidth / 2, frame.bottom - triAngleWidth / 2);
        rightBottomPath.lineTo(frame.right - triAngleWidth / 2, frame.bottom - triAngleLength);
        canvas.drawPath(rightBottomPath, traAnglePaint);

        // scanner line
        if (lineOffsetCount > frame.bottom - frame.top - dp2px(10)) {
            lineOffsetCount = 0;
        }
        else {
            lineOffsetCount = lineOffsetCount + 6;
//            canvas.drawLine(frame.left, frame.top + lineOffsetCount, frame.right, frame.top + lineOffsetCount, linePaint);    //draw a red line
            Rect lineRect = new Rect();
            lineRect.left = frame.left;
            lineRect.top = frame.top + lineOffsetCount;
            lineRect.right = frame.right;
            lineRect.bottom = frame.top + dp2px(10) + lineOffsetCount;
            canvas.drawBitmap(((BitmapDrawable)(getResources().getDrawable(R.drawable.scanline))).getBitmap(), null, lineRect, linePaint);
        }

        postInvalidateDelayed(10L, frame.left, frame.top, frame.right, frame.bottom);
    }

    private int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }


}
