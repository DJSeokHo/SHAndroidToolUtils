package com.swein.framework.tools.util.ps;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by seokho on 16/05/2017.
 */

public class PSUtils {

    /**
     * Three Primary Colors
     * @param originalBitmap original image
     * @param hue I don't know how to explain. you should study photo shop
     * @param saturation I don't know how to explain. you should study photo shop
     * @param lum I don't know how to explain. you should study photo shop
     * @return new image
     */
    public static Bitmap handleImageEffect(Bitmap originalBitmap, float hue, float saturation, float lum) {

        //ARGB_8888 best
        Bitmap bitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


        ColorMatrix hueMatrix = createHueMatrix(hue);
        ColorMatrix saturationMatrix = createSaturationMatrix(saturation);
        ColorMatrix lumMatrix = createLumMatrix(lum);


        paint.setColorFilter(new ColorMatrixColorFilter(mergingColorMatrix(hueMatrix, saturationMatrix, lumMatrix)));
        canvas.drawBitmap(originalBitmap, 0, 0, paint);
//        canvas.drawBitmap(bitmap, 0, 0, paint);

        return bitmap;
    }



    private static ColorMatrix mergingColorMatrix(ColorMatrix... colorMatrices) {

        ColorMatrix mergingMatrix = new ColorMatrix();

        for(ColorMatrix colorMatrix : colorMatrices) {
            mergingMatrix.postConcat(colorMatrix);
        }

        return mergingMatrix;
    }


    private static ColorMatrix createLumMatrix(float lum) {
        ColorMatrix lumMatrix = new ColorMatrix();
        //float rScale, float gScale, float bScale, float aScale
        lumMatrix.setScale(lum, lum, lum, 1);

        return lumMatrix;
    }

    private static ColorMatrix createSaturationMatrix(float saturation) {
        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);
        return saturationMatrix;
    }

    private static ColorMatrix createHueMatrix(float hue) {
        ColorMatrix hueMatrix = new ColorMatrix();

        hueMatrix.setRotate(0, hue);    //R
        hueMatrix.setRotate(1, hue);    //G
        hueMatrix.setRotate(2, hue);    //B

        return hueMatrix;
    }

}
