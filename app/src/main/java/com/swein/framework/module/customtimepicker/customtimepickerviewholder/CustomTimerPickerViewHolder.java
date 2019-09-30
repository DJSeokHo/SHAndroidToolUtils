package com.swein.framework.module.customtimepicker.customtimepickerviewholder;

import android.content.Context;
import android.view.View;

import com.swein.framework.tools.util.views.ViewUtil;
import com.swein.shandroidtoolutils.R;

public class CustomTimerPickerViewHolder {

    private View view;

    public CustomTimerPickerViewHolder(Context context) {
        view = ViewUtil.inflateView(context, R.layout.view_holder_custom_timer_picker, null);
    }

}
