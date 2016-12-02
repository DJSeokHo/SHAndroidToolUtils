package com.swein.framework.tools.util.time;

import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Administrator on 2016-11-15.
 */

public class DateUtil
{
    private static String TAG = DateUtil.class.getName();

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

//    public static void fromApiFormat(String str, ApiDateTime dateTime) {
//        dateTime.year = Integer.parseInt(str.substring(0, 4));
//        dateTime.month = Integer.parseInt(str.substring(4, 6))-1;
//        dateTime.date = Integer.parseInt(str.substring(6, 8));
//        dateTime.hour = Integer.parseInt(str.substring(8, 10));
//        dateTime.minute = Integer.parseInt(str.substring(10, 12));
//    }
//
//    public static Calendar fromApiDateTime(ApiDateTime dateTime) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.clear();
//        calendar.set(dateTime.year, dateTime.month, dateTime.date, dateTime.hour, dateTime.minute);
//
//        return calendar;
//    }

//    public static String toYearMonthDateLongWeekDayHourMinute(DateTimePickerViewHolder holder) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.clear();
//        calendar.set(holder.datePicker.getYear(), holder.datePicker.getMonth(), holder.datePicker.getDayOfMonth(), 1, 1, 1);
//
//        if (Build.VERSION.SDK_INT < 23) {
//            return String.format("%04d.%02d.%02d(%s) %2d:%02d"
//                    , holder.datePicker.getYear()
//                    , holder.datePicker.getMonth() + 1
//                    , holder.datePicker.getDayOfMonth()
//                    , calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.KOREA)
//                    , holder.timePicker.getCurrentHour()
//                    , holder.timePicker.getCurrentMinute() * 10);
//        }
//        else{
//            return String.format("%04d.%02d.%02d(%s) %2d:%02d"
//                    , holder.datePicker.getYear()
//                    , holder.datePicker.getMonth() + 1
//                    , holder.datePicker.getDayOfMonth()
//                    , calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.KOREA)
//                    , holder.timePicker.getHour()
//                    , holder.timePicker.getMinute() * 10);
//        }
//    }

    public static String toYearMonthDateLongWeekDayHourMinute(Calendar calendar) {

        return String.format("%04d.%02d.%02d(%s) %2d:%02d"
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH) + 1
                , calendar.get(Calendar.DAY_OF_MONTH)
                , calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.KOREA)
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE));
    }

//    public static String toMonthDateLongWeekDay(DateTimePickerViewHolder holder) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.clear();
//        calendar.set(holder.datePicker.getYear(), holder.datePicker.getMonth(), holder.datePicker.getDayOfMonth(), 1, 1, 1);
//
//        return String.format("%02d/%02d(%s)"
//                , holder.datePicker.getMonth() + 1
//                , holder.datePicker.getDayOfMonth()
//                , calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.KOREA));
//    }

    public static String toMonthDateLongWeekDay(Calendar calendar) {

        return String.format("%02d/%02d(%s)"
                , calendar.get(Calendar.MONTH) + 1
                , calendar.get(Calendar.DAY_OF_MONTH)
                , calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.KOREA));
    }

//    public static String toMonthDate(DateTimePickerViewHolder holder) {
//
//        return String.format("%02d/%02d"
//                , holder.datePicker.getMonth() + 1
//                , holder.datePicker.getDayOfMonth());
//    }

    public static String toMonthDate(Calendar calendar) {

        return String.format("%02d/%02d"
                , calendar.get(Calendar.MONTH) + 1
                , calendar.get(Calendar.DAY_OF_MONTH));
    }

//    public static String toHourMinute(DateTimePickerViewHolder holder) {
//
//        if (Build.VERSION.SDK_INT < 23) {
//            return String.format("%02d:%02d"
//                    , holder.timePicker.getCurrentHour()
//                    , holder.timePicker.getCurrentMinute() * 10);
//        }
//        else{
//            return String.format("%02d:%02d"
//                    , holder.timePicker.getHour()
//                    , holder.timePicker.getMinute() * 10);
//        }
//    }

    public static String toHourMinute(Calendar calendar) {

        return String.format("%02d:%02d"
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE));
    }

    public static String toDisplayDifference(Calendar calendarFrom, Calendar calendarTo)
    {
        Log.d(TAG, "toDisplayDifference("+calendarFrom+", "+calendarTo+"):");

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

//    public static String toDisplayDifferenceBigDecimal(Calendar calendarFrom, Calendar calendarTo)
//    {
//        Log.d(TAG, "toDisplayDifference("+calendarFrom+", "+calendarTo+"):");
//
//        Calendar calendar1 = (Calendar) calendarFrom.clone();
//        Calendar calendar2 = (Calendar) calendarTo.clone();
//
//        // 초 제거
//        calendar1.clear(Calendar.SECOND);
//        calendar2.clear(Calendar.SECOND);
//
//        // 밀리초 제거
//        calendar1.clear(Calendar.MILLISECOND);
//        calendar2.clear(Calendar.MILLISECOND);
//
//        long calMillis_two;
//        long calMillis_one;
//        long stamp = 0;
//        String ret = "";
//
//        calMillis_two = calendar2.getTimeInMillis();
//        calMillis_one = calendar1.getTimeInMillis();
//
//
//        try {
//            stamp = Arith.div(Math.abs(Arith.sub(calMillis_two, calMillis_one)), 1000, 1);
//            long nDays = Arith.div(stamp, Arith.mullong(Arith.mulint(60, 60), 24), 1);
//            long nHours = Arith.div(stamp, Arith.mullong(60, 60), 1) % 24;
//            long nMinutes = Arith.div(stamp, 60, 1) % 60;
//
//            Log.d("seokho ", nDays + " " + nHours + " " + nMinutes);
//
//            ret = (nDays > 0 ? nDays + "일 " : "");
//            ret += (nHours > 0 ? nHours + "시 " : "");
//            ret += (nMinutes > 0 ? nMinutes + "분" : "0분");
//
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        return ret;
//    }
}
