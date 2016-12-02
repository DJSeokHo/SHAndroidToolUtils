package com.swein.shandroidtoolutils;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.swein.framework.tools.util.time.DateUtil;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private DatePicker datePicker;
    private TimePicker timePicker;
    private int plusMinute;
    private TextView selectedDateTitleTV;
    private TextView selectedTimeTitleTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ToastUtils.showCustomLongToastNormal(this, "text");
//        ToastUtils.showCustomLongToastWithImageResourceId(this, "image and text", R.mipmap.ic_launcher);

        datePicker = (DatePicker)findViewById(R.id.datePicker);
        timePicker = (TimePicker)findViewById(R.id.time_picker);
        selectedDateTitleTV = (TextView)findViewById( R.id.selectedDateTitleTV );
        selectedTimeTitleTV = (TextView)findViewById( R.id.selectedTimeTitleTV );

        plusMinute = 1;

        // 날짜 선택기 초기화
        Calendar now = Calendar.getInstance();
        int minute = now.get(Calendar.MINUTE);
        int mod = minute % 10;
        if(mod == 0){
            //minute += 10;
            now.add(Calendar.MINUTE, 10);
        }
        else if(mod < 5){
            //minute += 10 - mod;
            now.add(Calendar.MINUTE, 10 - mod);
        }
        else if(mod >= 5){
            //minute += 20 - mod;
            now.add(Calendar.MINUTE, 20 - mod);
        }
        // now.set(Calendar.MINUTE, minute);
        // 추가 시간 셋팅
        if(plusMinute > 0){
            now.add(Calendar.MINUTE, plusMinute);
        }

        datePicker.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener()
                {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear,int dayOfMonth)
                    {

                    }
                }
        );

        // 시간 선택기 초기화
        if(Build.VERSION.SDK_INT < 23) {
            timePicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(now.get(Calendar.MINUTE)/10);
        }
        else {
            timePicker.setHour(now.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(now.get(Calendar.MINUTE)/10);
        }

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
            {
                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
                {

                }
            }
        );

        // 날짜/시간 뷰 초기화
        selectedDateTitleTV.setText(DateUtil.toMonthDateLongWeekDay(now));
        selectedTimeTitleTV.setText(DateUtil.toHourMinute(now));

    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
