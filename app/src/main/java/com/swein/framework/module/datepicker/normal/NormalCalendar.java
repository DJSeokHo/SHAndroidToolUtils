package com.swein.framework.module.datepicker.normal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.swein.framework.tools.util.date.DateUtil;
import com.swein.shandroidtoolutils.R;

import java.util.Calendar;

/**
 * Created by seokho on 09/02/2018.
 */

public class NormalCalendar {

    public interface NormalCalendarDelegate {
        void onDateSet(String date);
    }

    private NormalCalendarDelegate normalCalendarDelegate;

    public NormalCalendar(NormalCalendarDelegate normalCalendarDelegate) {
        this.normalCalendarDelegate = normalCalendarDelegate;
    }

    public DatePickerDialog getCalendar(Context context) {

        return new DatePickerDialog(context, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                normalCalendarDelegate.onDateSet(year + "-" + (DateUtil.dateFormat(month + 1)) + "-" + DateUtil.dateFormat(dayOfMonth));
            }
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

    }

}
