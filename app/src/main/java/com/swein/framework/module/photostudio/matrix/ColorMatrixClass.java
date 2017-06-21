package com.swein.framework.module.photostudio.matrix;

import android.graphics.ColorMatrix;

/**
 * Created by seokho on 21/06/2017.
 */

public class ColorMatrixClass {



    private ColorMatrixClass() {}

    private static ColorMatrixClass instance = new ColorMatrixClass();

    public static ColorMatrixClass getInstance() {

        return instance;
    }

    /**
     * 0:red 1:green 2:blue
     */
    private ColorMatrix hueMatrix = new ColorMatrix();
    private ColorMatrix saturationMatrix = new ColorMatrix();
    private ColorMatrix lunMatrix = new ColorMatrix();

    public void setRedHue(float degree) {
        hueMatrix.setRotate(0, degree);
    }

    public void setGreenHue(float degree) {
        hueMatrix.setRotate(1, degree);
    }

    public void setBlueHue(float degree) {
        hueMatrix.setRotate(2, degree);
    }

    /**
     *
     *
     * @param saturation from 0 to 1 and 0: gray
     */
    public void setSaturation(float saturation) {
        saturationMatrix.setSaturation(saturation);
    }

    /**

     * @param r
     * @param g
     * @param b
     * @param a default value : 1
     */
    public void setLunMatrix(float r, float g, float b, float a) {
        lunMatrix.setScale(r, g, b, a);
    }

    public ColorMatrix getConcatMatrix(ColorMatrix... colorMatrices) {
        ColorMatrix colorMatrix = new ColorMatrix();
        for(ColorMatrix c : colorMatrices) {
            colorMatrix.postConcat(c);
        }

        return colorMatrix;
    }
}
