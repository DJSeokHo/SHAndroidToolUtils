package com.swein.framework.tools.util.date;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016-11-15.
 */

public class DateUtil
{

    public static String getCurrentDateFromFastDateFormat(String DATE_FORMAT) {
        FastDateFormat fastDateFormat = FastDateFormat.getInstance( DATE_FORMAT, TimeZone.getDefault(), Locale.getDefault());
        String         date           = fastDateFormat.format( new Date( ) );
        return date;
    }
//    public static String toApiFormat(DateTimePickerViewHolder holder) {
//        if (Build.VERSION.SDK_INT < 23) {
//            return String.format("%04d%02d%02d%02d%02d"
//                    , holder.datePicker.getYear()
//                    , holder.datePicker.getMonth() + 1
//                    , holder.datePicker.getDayOfMonth()
//                    , holder.timePicker.getCurrentHour()
//                    , holder.timePicker.getCurrentMinute() * 10);
//
//        } else {
//            return String.format("%04d%02d%02d%02d%02d"
//                    , holder.datePicker.getYear()
//                    , holder.datePicker.getMonth() + 1
//                    , holder.datePicker.getDayOfMonth()
//                    , holder.timePicker.getHour()
//                    , holder.timePicker.getMinute() * 10);
//        }
//    }

    public static String toApiFormat(Calendar calendar) {
            return String.format("%04d%02d%02d%02d%02d"
                    , calendar.get(Calendar.YEAR)
                    , calendar.get(Calendar.MONTH) + 1
                    , calendar.get(Calendar.DAY_OF_MONTH)
                    , calendar.get(Calendar.HOUR_OF_DAY)
                    , calendar.get(Calendar.MINUTE));
    }

    public static Calendar fromApiFormat(String datetime) {
        int year = Integer.parseInt(datetime.substring(0, 4));
        int month = Integer.parseInt(datetime.substring(4, 6))-1;
        int date = Integer.parseInt(datetime.substring(6, 8));
        int hour = Integer.parseInt(datetime.substring(8, 10));
        int minute = Integer.parseInt(datetime.substring(10, 12));

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, date, hour, minute, 0);

        return calendar;
    }

    public static String toYearMonthDateLongWeekDayHourMinute(Calendar calendar) {

        return String.format("%04d.%02d.%02d(%s) %2d:%02d"
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH) + 1
                , calendar.get(Calendar.DAY_OF_MONTH)
                , calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.KOREA)
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE));
    }

    public static String toMonthDateLongWeekDay(Calendar calendar) {

        return String.format("%02d/%02d(%s)"
                , calendar.get(Calendar.MONTH) + 1
                , calendar.get(Calendar.DAY_OF_MONTH)
                , calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.KOREA));
    }

    public static String toMonthDate(Calendar calendar) {

        return String.format("%02d/%02d"
                , calendar.get(Calendar.MONTH) + 1
                , calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String toHourMinute(Calendar calendar) {

        return String.format("%02d:%02d"
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE));
    }

    public static String toDisplayDifference(Calendar calendarFrom, Calendar calendarTo)
    {
        Calendar calendar1 = (Calendar) calendarFrom.clone();
        Calendar calendar2 = (Calendar) calendarTo.clone();

        // 초 제거
        calendar1.clear(Calendar.SECOND);
        calendar2.clear(Calendar.SECOND);

        // 밀리초 제거
        calendar1.clear(Calendar.MILLISECOND);
        calendar2.clear(Calendar.MILLISECOND);

        long stamp = Math.abs(calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / 1000;
        //Log.d(TAG, "!!!Math.abs(calendarTo.getTimeInMillis() - calendarFrom.getTimeInMillis()) % 1000 == " + (Math.abs(calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) % 1000));
        long nDays = stamp / (60 * 60 * 24);
        //Log.d(TAG, "!!!stamp / (60 * 60 * 24) == " + (stamp / (60 * 60 * 24)));
        long nHours = (stamp / (60 * 60)) % 24;
        //Log.d(TAG, "!!!(stamp / (60 * 60)) % 24 == " + ((stamp / (60 * 60)) % 24));
        long nMinutes = (stamp / 60) % 60;
        //Log.d(TAG, "!!!(stamp / 60) % 60 == " + ((stamp / 60) % 60));

        String ret = (nDays > 0 ? nDays + "일 " : "");
        ret += (nHours > 0 ? nHours + "시 " : "");
        ret += (nMinutes > 0 ? nMinutes + "분" : "0분");

        return ret;
    }

    public static Long TimeStringToTimeStamp(String string) {

        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long tempLong = 0;

        try {
            Date date = format.parse(string);
            tempLong = date.getTime();

        }
        catch ( ParseException e ) {
            e.printStackTrace();
        }

        return tempLong;
    }

    public static Date TimeStampToDate(long timeStamp) {

        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timeLong = new Long(timeStamp);
        String d = simpleDateFormat.format(timeLong);
        Date date = null;

        try {
            date = simpleDateFormat.parse(d);
        }
        catch ( ParseException e ) {
            e.printStackTrace();
        }

       return date;

    }

    public static String TimeStampToTimeString(long timeStamp) {

        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timeLong = new Long(timeStamp);
        String d = simpleDateFormat.format(timeLong);

        return d;

    }

    public static String getCurrentYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public static String getCurrentMonth() {
        return String.valueOf(Calendar.getInstance().get(Calendar.MONTH));
    }

    public static String getCurrentDayOfMonth() {
        return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    public static String getCurrentHour() {
        return String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
    }

    public static String getCurrentMinute() {
        return String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
    }

    public static String getCurrentSecond() {
        return String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
    }



}
