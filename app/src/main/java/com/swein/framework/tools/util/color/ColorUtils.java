package com.swein.framework.tools.util.color;

import com.swein.framework.tools.util.debug.log.ILog;

import java.util.Random;

/**
 * Created by seokho on 14/03/2017.
 */

public class ColorUtils {

    public static String createRandomColorString() {

        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r ;
        g = g.length() == 1 ? "0" + g : g ;
        b = b.length() == 1 ? "0" + b : b ;

        ILog.iLogDebug( ColorUtils.class.getSimpleName(), r + g + b );

        return "#" + r + g + b;
    }

    /**
     * 0% ~ 100% input
     * @return
     */
    public static String colorAlphaToHexString(double alpha) {

        if( 0 > alpha || 1 < alpha ) {
            return "";
        }

        alpha = Math.round(alpha * 100) /  100.0d;
        int a = (int) Math.round(alpha * 255);
        String hex = Integer.toHexString(a).toUpperCase();

        if (hex.length() == 1) {
            hex = "0" + hex;
        }

        return hex;

    }

}
