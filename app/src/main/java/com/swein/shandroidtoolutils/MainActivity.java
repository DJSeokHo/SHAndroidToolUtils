package com.swein.shandroidtoolutils;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.swein.framework.tools.util.debug.log.SeokHoTest;
import com.swein.framework.tools.util.other.CountDownTimerTask;
import com.swein.framework.tools.util.time.DateUtil;

import java.util.Calendar;

import static com.swein.framework.tools.util.other.CountDownTimerTask.countdownTimerTask;
import static com.swein.framework.tools.util.popup.PopupWindowUtils.createPopupWindowWithView;


public class MainActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TimePicker timePicker;
    private int plusMinute;
    private TextView selectedDateTitleTV;
    private TextView selectedTimeTitleTV;

    private Button button;



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

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPopupWindowWithView(getApplicationContext(), getLayoutInflater(), R.layout.popup_window_view, 560, 720, Gravity.CENTER, 20, 20, getWindow().getDecorView());

//
            }
        });

//        createNormalProgressDialogWithoutButton(this, "test", "progress");


        countdownTimerTask(Calendar.SECOND, 5, new CountDownTimerTask.RunMethod() {
            @Override
            public void runMethod() {
                SeokHoTest.testSeokHo(MainActivity.class.getName(), "run run run??");
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
