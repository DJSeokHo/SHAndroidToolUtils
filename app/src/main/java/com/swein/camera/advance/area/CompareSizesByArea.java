package com.swein.camera.advance.area;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Size;

import java.util.Comparator;

/**
 * Created by seokho on 15/05/2017.
 */

public class CompareSizesByArea implements Comparator<Size> {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int compare(Size lhs, Size rhs) {
        // We cast here to ensure the multiplications won't overflow
        return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                (long) rhs.getWidth() * rhs.getHeight());
    }

}
