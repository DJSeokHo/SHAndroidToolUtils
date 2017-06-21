package com.swein.framework.module.photostudio.matrix;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by seokho on 21/06/2017.
 */

public class ColorMatrixClass {



    private ColorMatrixClass() {}

    private static ColorMatrixClass instance = new ColorMatrixClass();

    public static ColorMatrixClass getInstance() {

        return instance;
    }


    private ColorMatrix hueMatrix = new ColorMatrix();
    private ColorMatrix saturationMatrix = new ColorMatrix();
    private ColorMatrix lunMatrix = new ColorMatrix();

    /**
     * 0:red 1:green 2:blue
     * @param redDegree 0
     * @param greenDegree 1
     * @param blueDegree 2
     */
    public ColorMatrix setHue(float redDegree, float greenDegree, float blueDegree) {
        hueMatrix.reset();
        hueMatrix.setRotate(0, redDegree);
        hueMatrix.setRotate(1, greenDegree);
        hueMatrix.setRotate(2, blueDegree);

        return hueMatrix;
    }

    /**
     *
     *
     * @param saturation from 0 to 1 and 0: gray
     */
    public ColorMatrix setSaturation(float saturation) {
        saturationMatrix.reset();
        saturationMatrix.setSaturation(saturation);
        return saturationMatrix;
    }

    /**

     * @param r
     * @param g
     * @param b
     * @param a default value : 1
     */
    public ColorMatrix setLunMatrix(float r, float g, float b, float a) {
        lunMatrix.reset();
        lunMatrix.setScale(r, g, b, a);
        return lunMatrix;
    }

    public Bitmap getConcatMatrix(Bitmap bitmap, ColorMatrix... colorMatrices) {

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        ColorMatrix colorMatrix = new ColorMatrix();
        for(ColorMatrix c : colorMatrices) {
            colorMatrix.postConcat(c);
        }

        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bitmap;

    }

}
