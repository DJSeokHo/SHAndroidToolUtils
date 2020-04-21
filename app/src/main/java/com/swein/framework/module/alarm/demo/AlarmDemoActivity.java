package com.swein.framework.module.alarm.demo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.swein.framework.module.alarm.receiver.AlarmBroadcastReceiver;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import java.util.Calendar;

public class AlarmDemoActivity extends AppCompatActivity {

    private final static String TAG = "AlarmDemoActivity";

    private TextView textView;
    private Switch switchAlarm;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_demo);

        textView = findViewById(R.id.textView);

        textView.setText("闹钟测试");

        switchAlarm = findViewById(R.id.switchAlarm);


        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 45);

                    ILog.iLogDebug(TAG, calendar.getTime().toString());

//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                    }
//                    else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                    }
//                    else {
//                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//                    }


                    // 重复闹钟，4.4以上不精确
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60 * 1000, pendingIntent);
                }
                else {
                    alarmManager.cancel(pendingIntent);
                }
            }
        });

        // 实例化AlarmManager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // 设置闹钟触发动作
        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        intent.setAction("startAlarm");
        pendingIntent = PendingIntent.getBroadcast(this, 110, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        alarmManager.setTimeZone(TimeZone.getDefault().getID()); // Asia/Seoul

        /*
         * AndroidL开始，设置的alarm的触发时间必须大于当前时间 5秒
         *
         * 设置一次闹钟-(5s后启动闹钟)
         *
         * @AlarmType int type：闹钟类型
         * ELAPSED_REALTIME：在指定的延时过后，发送广播，但不唤醒设备（闹钟在睡眠状态下不可用）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒。
         * ELAPSED_REALTIME_WAKEUP：在指定的延时过后，发送广播，并唤醒设备（即使关机也会执行operation所对应的组件）。延时是要把系统启动的时间SystemClock.elapsedRealtime()算进去的。
         * RTC：指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，但不唤醒设备）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒（闹钟在睡眠状态下不可用）。
         * RTC_WAKEUP：指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，并唤醒设备）。即使系统关机也会执行 operation所对应的组件。
         *
         * long triggerAtMillis：触发闹钟的时间。
         * PendingIntent operation：闹钟响应动作（发广播，启动服务等）
         */
//        alarmManager.set(@AlarmType int type, long triggerAtMillis, PendingIntent operation);

        /*
         * AndroidL开始repeat的周期必须大于60秒
         *
         * 设置精准周期闹钟-该方法提供了设置周期闹钟的入口，闹钟执行时间严格按照startTime来处理，使用该方法需要的资源更多，不建议使用。
         *
         * @AlarmType int type：闹钟类型
         * long triggerAtMillis：触发闹钟的时间。
         * long intervalMillis：闹钟两次执行的间隔时间
         * PendingIntent operation：闹钟响应动作（发广播，启动服务等）
         */
//        alarmManager.setRepeating(@AlarmType int type, long triggerAtMillis, long intervalMillis, PendingIntent operation);

        /*
         * AndroidL开始repeat的周期必须大于60秒
         *
         * 设置不精准周期闹钟- 该方法也用于设置重复闹钟，与第二个方法相似，不过其两个闹钟执行的间隔时间不是固定的而已。它相对而言更省电（power-efficient）一些，因为系统可能会将几个差不多的闹钟合并为一个来执行，减少设备的唤醒次数。
         *
         * @AlarmType int type：闹钟类型
         * long triggerAtMillis：触发闹钟的时间。
         * long intervalMillis：闹钟两次执行的间隔时间
         * 内置变量
         * INTERVAL_DAY： 设置闹钟，间隔一天
         * INTERVAL_HALF_DAY： 设置闹钟，间隔半天
         * INTERVAL_FIFTEEN_MINUTES：设置闹钟，间隔15分钟
         * INTERVAL_HALF_HOUR： 设置闹钟，间隔半个小时
         * INTERVAL_HOUR： 设置闹钟，间隔一个小时
         *
         * PendingIntent operation：闹钟响应动作（发广播，启动服务等）
         */
//        alarmManager.setInexactRepeating(@AlarmType int type, long triggerAtMillis, long intervalMillis, PendingIntent operation);

//        alarmManager.setTimeZone(String timeZone);
//        如：东八区
//        alarmManager.setTimeZone("GMT+08:00");
//        <!-- 允许设置时区-->
//        <uses-permission android:name="android.permission.SET_TIME_ZONE" />

//        alarmManager.cancel(PendingIntent operation)



    }

    @Override
    protected void onDestroy() {
        alarmManager.cancel(pendingIntent);
        super.onDestroy();
    }
}
