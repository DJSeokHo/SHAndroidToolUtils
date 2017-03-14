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

}
