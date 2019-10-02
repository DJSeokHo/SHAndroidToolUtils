package com.swein.framework.module.customtimepicker.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.swein.framework.module.customtimepicker.customnumberpicker.CustomNumberPicker;
import com.swein.framework.module.customtimepicker.customtimepickerviewholder.CustomTimerPickerViewHolder;
import com.swein.shandroidtoolutils.R;

public class CustomTimePickerDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_time_picker_demo);

        CustomTimerPickerViewHolder customTimerPickerViewHolder = new CustomTimerPickerViewHolder(this, new CustomTimerPickerViewHolder.CustomTimerPickerViewHolderDelegate() {
        });

        ((FrameLayout)findViewById(R.id.container)).addView(customTimerPickerViewHolder.getView());
    }
}
