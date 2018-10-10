package com.swein.framework.tools.util.date;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String getCurrentDateTimeString() {
        Calendar calendar = Calendar.getInstance();

        String s = String.format("%d-%02d-%02d %02d:%02d:%02d",
                calendar.get(Calendar.YEAR),
                (calendar.get(Calendar.MONTH) + 1),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND)
        );

        return s;

    }

    public static String dateFormat(int date) {
        if(10 > date) {
            return "0" + date;
        }

        return date + "";
    }

    public static String getDayOfWeekOfDateTime(String date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0) {
                w = 0;
            }

            return weekDays[w];
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

}
