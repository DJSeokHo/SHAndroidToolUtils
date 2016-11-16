package com.swein.shandroidtoolutils.tools.util;

import java.util.Calendar;

/**
 * Created by seokho on 13/11/2016.
 */

public class CalendarUtils {

    public String getCurrentYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public String getCurrentMonth() {
        return String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
    }

    public String getCurrentDayOfMonth() {
        return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    public String getCurrentHour() {
        return String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
    }

    public String getCurrentMinute() {
        return String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
    }

    public String getCurrentSecond() {
        return String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
    }

}
