package com.swein.framework.template.datepicker;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.swein.framework.module.customcalender.CalendarViewHolder;
import com.swein.framework.tools.util.views.ViewUtil;
import com.swein.shandroidtoolutils.R;

import java.util.Date;

public class PopupDatePickerViewHolder {

    public interface PopupDatePickerViewHolderDelegate {
        void onSelected(Date date);
        void onClose();
    }

    private final static String TAG = "PopupDatePickerViewHolder";

    private View view;

    private CalendarViewHolder calendarViewHolder;
    private TextView textViewTitle;

    private PopupDatePickerViewHolderDelegate popupDateAndLocationViewHolderDelegate;

    public PopupDatePickerViewHolder(Context context, PopupDatePickerViewHolderDelegate popupDateAndLocationViewHolderDelegate) {
        this.popupDateAndLocationViewHolderDelegate = popupDateAndLocationViewHolderDelegate;
        view = ViewUtil.inflateView(context, R.layout.view_holder_popup_date_picker, null);

        findView();
    }

    public void setTitle(String title) {
        textViewTitle.setText(title);
    }

    private void findView() {
        calendarViewHolder = view.findViewById(R.id.calendarViewHolder);
        textViewTitle = view.findViewById(R.id.textViewTitle);

        calendarViewHolder.setDelegate(new CalendarViewHolder.CalendarViewHolderDelegate() {
            @Override
            public void onSelected(Date date) {
                popupDateAndLocationViewHolderDelegate.onSelected(date);
            }
        });


    }

    public View getView() {
        return view;
    }
}
