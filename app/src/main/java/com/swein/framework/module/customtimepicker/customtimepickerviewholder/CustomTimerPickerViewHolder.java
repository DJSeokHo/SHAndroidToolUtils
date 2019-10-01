package com.swein.framework.module.customtimepicker.customtimepickerviewholder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.swein.framework.module.customtimepicker.customnumberpicker.CustomNumberPicker;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.framework.tools.util.views.ViewUtil;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

public class CustomTimerPickerViewHolder {

    private final static String TAG = "CustomTimerPickerViewHolder";

    public interface CustomTimerPickerViewHolderDelegate {

    }

    private View view;

    private TextView textViewConfirm;
    private TextView textViewCancel;

    private CustomNumberPicker customNumberPickerLocal;
    private CustomNumberPicker customNumberPickerHour;
    private CustomNumberPicker customNumberPickerMinute;

    private CustomTimerPickerViewHolderDelegate customTimerPickerViewHolderDelegate;

    public CustomTimerPickerViewHolder(Context context, CustomTimerPickerViewHolderDelegate customTimerPickerViewHolderDelegate) {
        this.customTimerPickerViewHolderDelegate = customTimerPickerViewHolderDelegate;
        view = ViewUtil.inflateView(context, R.layout.view_holder_custom_timer_picker, null);
        findView();
        setListener();
        initNumberPicker();
    }

    private void findView() {
        textViewConfirm = view.findViewById(R.id.textViewConfirm);
        textViewCancel = view.findViewById(R.id.textViewCancel);

        customNumberPickerLocal = view.findViewById(R.id.customNumberPickerLocal);
        customNumberPickerHour = view.findViewById(R.id.customNumberPickerHour);
        customNumberPickerMinute = view.findViewById(R.id.customNumberPickerMinute);
    }

    private void setListener() {
        textViewConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ILog.iLogDebug(TAG,
                        customNumberPickerHour.getDisplayedValues()[customNumberPickerHour.getValue()] + " " +
                                customNumberPickerMinute.getDisplayedValues()[customNumberPickerMinute.getValue()] + " " +
                                customNumberPickerLocal.getDisplayedValues()[customNumberPickerLocal.getValue()]
                );
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initNumberPicker() {

        List<String> hourList = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            hourList.add(String.valueOf(i));
        }

        customNumberPickerHour.setDisplayedValues(hourList.toArray(new String[hourList.size()]));
        customNumberPickerHour.setMaxValue(hourList.size() - 1);
        customNumberPickerHour.setMinValue(0);
        customNumberPickerHour.setValue(0);
        customNumberPickerHour.setBackgroundColor(Color.WHITE);
        customNumberPickerHour.setNumberPickerDividerColor(Color.BLACK);

        List<String> minuteList = new ArrayList<>();
        for(int i = 0; i <= 59; i++) {
            minuteList.add(String.valueOf(i));
        }

        customNumberPickerMinute.setDisplayedValues(minuteList.toArray(new String[minuteList.size()]));
        customNumberPickerMinute.setMaxValue(minuteList.size() - 1);
        customNumberPickerMinute.setMinValue(0);
        customNumberPickerMinute.setValue(0);
        customNumberPickerMinute.setBackgroundColor(Color.WHITE);
        customNumberPickerMinute.setNumberPickerDividerColor(Color.BLACK);

        List<String> localList = new ArrayList<>();
        localList.add("AM");
        localList.add("PM");

        customNumberPickerLocal.setDisplayedValues(localList.toArray(new String[localList.size()]));
        customNumberPickerLocal.setMinValue(0);
        customNumberPickerLocal.setMaxValue(localList.size() - 1);
        customNumberPickerLocal.setBackgroundColor(Color.WHITE);
        customNumberPickerLocal.setNumberPickerDividerColor(Color.BLACK);

    }

    public View getView() {
        return view;
    }

}
