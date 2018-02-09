package com.swein.framework.module.datepicker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swein.framework.module.datepicker.normal.NormalCalendar;
import com.swein.framework.module.datepicker.yearmonth.YearMonthPicker;
import com.swein.shandroidtoolutils.R;

import java.util.Calendar;

public class DatePickerActivity extends Activity {


    private Button buttonCalendar;
    private Button buttonYMCalendar;

    private TextView textViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        buttonCalendar = (Button) findViewById(R.id.buttonCalendar);
        buttonYMCalendar = (Button) findViewById(R.id.buttonYMCalendar);

        textViewDate = (TextView) findViewById(R.id.textViewDate);

        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NormalCalendar normalCalendar = new NormalCalendar(new NormalCalendar.NormalCalendarDelegate() {
                    @Override
                    public void onDateSet(String date) {
                        textViewDate.setText(date);
                    }
                });

                normalCalendar.getCalendar(DatePickerActivity.this).show();
            }
        });

        buttonYMCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                YearMonthPicker yearMonthPicker = new YearMonthPicker(DatePickerActivity.this, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                        String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1), new YearMonthPicker.YearMonthPickerDelegate() {
                    @Override
                    public void onDateSelected(String year, String month) {
                        textViewDate.setText(year + "-" + month);
                    }
                });
                yearMonthPicker.show();

            }
        });


    }
}
