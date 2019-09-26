package com.swein.framework.module.customcalender;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.swein.shandroidtoolutils.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarViewHolder extends LinearLayout {

    public interface CalendarViewHolderDelegate {
        void onSelected(Date date);
    }

    private ImageView btnPrev;
    private ImageView btnNext;

    private TextView txtDate;
    private GridView grid;

    private Calendar curDate = Calendar.getInstance();

    private List<View> cellList = new ArrayList<>();

    private CalendarViewHolderDelegate calendarViewHolderDelegate;

    public CalendarViewHolder(Context context) {
        super(context);
    }

    public CalendarViewHolder(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public CalendarViewHolder(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }

    private void initControl(Context context) {
        bindControl(context);
        bindControlEvent();
        renderCalendar();
    }

    private void bindControl(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_holder_calendar, this);

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        txtDate = findViewById(R.id.txtDate);

        grid = findViewById(R.id.calendar_grid);
    }

    private void bindControlEvent() {
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, -1);
                renderCalendar();
            }
        });

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH, 1);
                renderCalendar();
            }
        });
    }

    private void renderCalendar() {

        cellList.clear();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yyyy");
        txtDate.setText(simpleDateFormat.format(curDate.getTime()));

        ArrayList<Date> cells = new ArrayList<>();

        Calendar calendar = (Calendar) curDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int prevDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -prevDays);

        int maxCellCount = 6 * 7;
        while (cells.size() < maxCellCount) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
//            ILog.iLogDebug("???", calendar.getTime());
        }

        grid.setAdapter(new CalendarAdapter(getContext(), cells));
    }

    public void setDelegate(CalendarViewHolderDelegate calendarViewHolderDelegate) {
        this.calendarViewHolderDelegate = calendarViewHolderDelegate;
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {

        LayoutInflater inflater;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        SimpleDateFormat simpleDateFormatFull = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("MM");

        public CalendarAdapter(@NonNull Context context, ArrayList<Date> days) {
            super(context, R.layout.view_holder_calendar_text_day, days);
            inflater = LayoutInflater.from(context);

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Date date = getItem(position);
//            ILog.iLogDebug("??? ", date);
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.view_holder_calendar_text_day, parent, false);
            }

            String day = simpleDateFormat.format(date);
            if(day.startsWith("0")) {
                day = day.replace("0", "");
            }
            ((TextView)convertView).setText(day);

            cellList.add(convertView);

            Date now = new Date();
            boolean isSameMonth = false;
            if(simpleDateFormatMonth.format(now).equals(simpleDateFormatMonth.format(date))) {
                isSameMonth = true;
            }

            if (isSameMonth) {
                ((TextView)convertView).setTextColor(Color.BLACK);
            }
            else {
                ((TextView)convertView).setTextColor(Color.GRAY);
            }


            if(simpleDateFormatFull.format(now).equals(simpleDateFormatFull.format(date))) {
                ((TextView)convertView).setTextColor(Color.RED);
            }

            View finalConvertView = convertView;
            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(View view : cellList) {
                        view.setBackgroundColor(Color.TRANSPARENT);
                    }

                    finalConvertView.setBackgroundResource(R.drawable.calendar_circle_bg);

                    calendarViewHolderDelegate.onSelected(date);

                }
            });

            return convertView;
        }
    }

}
