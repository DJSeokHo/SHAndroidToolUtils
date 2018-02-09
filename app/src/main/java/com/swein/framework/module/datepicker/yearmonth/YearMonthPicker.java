package com.swein.framework.module.datepicker.yearmonth;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.swein.framework.tools.util.date.DateUtil;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Custom year and month only date picker
 * Created by seokho on 05/02/2018.
 */

public class YearMonthPicker implements View.OnClickListener {

    public interface YearMonthPickerDelegate {
        void onDateSelected(String year, String month);
    }

    private View rootView;

    private Dialog dialog;

    private Button buttonJan;
    private Button buttonFeb;
    private Button buttonMar;
    private Button buttonApr;
    private Button buttonMay;
    private Button buttonJun;
    private Button buttonJul;
    private Button buttonAug;
    private Button buttonSep;
    private Button buttonOct;
    private Button buttonNov;
    private Button buttonDec;

    private String year = "";
    private String month = "";

    private List<String> yearList = new ArrayList<>();
    private YearMonthPickerDelegate yearMonthPickerDelegate;

    public YearMonthPicker(Context context, String year, String month, YearMonthPickerDelegate yearMonthPickerDelegate) {

        dialog = new Dialog(context, R.style.SHYearMonthPickerTheme);

        rootView = LayoutInflater.from(context).inflate(R.layout.dialog_month_picker, null);

        dialog.setContentView(rootView, new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        this.year = year;
        this.month = month;
        this.yearMonthPickerDelegate = yearMonthPickerDelegate;

        for(int i = 1950; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            yearList.add(String.valueOf(i));
        }

        findView();
        initDate();
    }


    private void findView() {

        Spinner spinnerYear = (Spinner) rootView.findViewById(R.id.spinnerYear);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_spinner_item, yearList);
        adapter.setDropDownViewResource(R.layout.view_holder_spinner_drop_item);
        spinnerYear.setAdapter(adapter);

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerYear.setSelection(yearList.indexOf(year), true);

        rootView.findViewById(R.id.buttonPick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yearMonthPickerDelegate.onDateSelected(year, DateUtil.dateFormat(Integer.parseInt(month)));
                dialog.dismiss();
            }
        });

        rootView.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        buttonJan = (Button) rootView.findViewById(R.id.buttonJan);
        buttonJan.setOnClickListener(this);

        buttonFeb = (Button) rootView.findViewById(R.id.buttonFeb);
        buttonFeb.setOnClickListener(this);

        buttonMar = (Button) rootView.findViewById(R.id.buttonMar);
        buttonMar.setOnClickListener(this);

        buttonApr = (Button) rootView.findViewById(R.id.buttonApr);
        buttonApr.setOnClickListener(this);

        buttonMay = (Button) rootView.findViewById(R.id.buttonMay);
        buttonMay.setOnClickListener(this);

        buttonJun = (Button) rootView.findViewById(R.id.buttonJun);
        buttonJun.setOnClickListener(this);

        buttonJul = (Button) rootView.findViewById(R.id.buttonJul);
        buttonJul.setOnClickListener(this);

        buttonAug = (Button) rootView.findViewById(R.id.buttonAug);
        buttonAug.setOnClickListener(this);

        buttonSep = (Button) rootView.findViewById(R.id.buttonSep);
        buttonSep.setOnClickListener(this);

        buttonOct = (Button) rootView.findViewById(R.id.buttonOct);
        buttonOct.setOnClickListener(this);

        buttonNov = (Button) rootView.findViewById(R.id.buttonNov);
        buttonNov.setOnClickListener(this);

        buttonDec = (Button) rootView.findViewById(R.id.buttonDec);
        buttonDec.setOnClickListener(this);

    }

    public void show() {
        dialog.show();
    }

    @Override
    public void onClick(View view) {

        setButtonBackground(view);

        switch (view.getId()) {

            case R.id.buttonJan:
                month = "1";
                break;
            case R.id.buttonFeb:
                month = "2";
                break;
            case R.id.buttonMar:
                month = "3";
                break;

            case R.id.buttonApr:
                month = "4";
                break;

            case R.id.buttonMay:
                month = "5";
                break;

            case R.id.buttonJun:
                month = "6";
                break;

            case R.id.buttonJul:
                month = "7";
                break;

            case R.id.buttonAug:
                month = "8";
                break;

            case R.id.buttonSep:
                month = "9";
                break;

            case R.id.buttonOct:
                month = "10";
                break;

            case R.id.buttonNov:
                month = "11";
                break;

            case R.id.buttonDec:
                month = "12";
                break;
        }
    }

    private void setButtonBackground(View view) {

        buttonJan.setBackgroundColor(Color.TRANSPARENT);
        buttonFeb.setBackgroundColor(Color.TRANSPARENT);
        buttonMar.setBackgroundColor(Color.TRANSPARENT);
        buttonApr.setBackgroundColor(Color.TRANSPARENT);
        buttonMay.setBackgroundColor(Color.TRANSPARENT);
        buttonJun.setBackgroundColor(Color.TRANSPARENT);
        buttonJul.setBackgroundColor(Color.TRANSPARENT);
        buttonAug.setBackgroundColor(Color.TRANSPARENT);
        buttonSep.setBackgroundColor(Color.TRANSPARENT);
        buttonOct.setBackgroundColor(Color.TRANSPARENT);
        buttonNov.setBackgroundColor(Color.TRANSPARENT);
        buttonDec.setBackgroundColor(Color.TRANSPARENT);

        view.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

    }

    private void initDate() {

        switch (month) {

            case "1":
                buttonJan.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "2":
                buttonFeb.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "3":
                buttonMar.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "4":
                buttonApr.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "5":
                buttonMay.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "6":
                buttonJun.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "7":
                buttonJul.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "8":
                buttonAug.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "9":
                buttonSep.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "10":
                buttonOct.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "11":
                buttonNov.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);

                break;
            case "12":
                buttonDec.setBackgroundResource(R.drawable.year_month_picker_button_selected_circle_bg);
                break;

        }

    }
}
