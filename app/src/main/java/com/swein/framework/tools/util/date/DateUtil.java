package com.swein.framework.tools.util.date;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil
{

    public static String getCurrentDateFromFastDateFormat(String DATE_FORMAT) {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance( DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());
        String         date           = fastDateFormat.format( new Date( ) );
        return date;
    }
}
