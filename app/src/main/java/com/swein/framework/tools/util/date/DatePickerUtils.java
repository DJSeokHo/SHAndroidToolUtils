package com.swein.framework.tools.util.date;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

/**
 * Created by nsp on 10/16/16.
 */


public class DatePickerUtils {



    private static void setNumberPickerProperties(DatePicker dp, /*Typeface typeface,*/ int color, float textSize)
    {
        for(int x = 0 ; x < dp.getChildCount() ; x++) {
            LinearLayout l = (LinearLayout) dp.getChildAt(x);
            l = (LinearLayout) l.getChildAt(x);
            if (l != null) {
                for (int i = 0; i < l.getChildCount(); i++) {
                    NumberPicker np = (NumberPicker) l.getChildAt(i);
                    np.setClipChildren(false);
                    np.setClipToPadding(false);
                    np.setDividerPadding(0);

                    if(i == 0) {
                        np.setFormatter(new NumberPicker.Formatter() {
                            @Override
                            public String format(int value) {
                                return value + "년";
                            }
                        });

                        // setNumberPickerTextColor(np, typeface, color,textSize, "년");
                        setNumberPickerTextColor(np, color,textSize, "년");
                    }
                    else if (i == 1) {
                        np.setFormatter(new NumberPicker.Formatter() {
                            @Override
                            public String format(int value) {
                                return value + "월";
                            }
                        });

                        // setNumberPickerTextColor(np, typeface, color,textSize, "월");
                        setNumberPickerTextColor(np, color,textSize, "월");
                    }
                    else if (i == 2) {
                        np.setFormatter(new NumberPicker.Formatter() {
                            @Override
                            public String format(int value) {
                                return value + "일";
                            }
                        });

                        // setNumberPickerTextColor(np, typeface, color,textSize, "일");
                        setNumberPickerTextColor(np, color,textSize, "일");
                    }
                }
            }
        }
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, /*Typeface typeface,*/ int color, float textSize, String postfixString)
    {
        // numberPicker.setWrapSelectorWheel(false);
        try {
            //* divider color
            Field dividerField = numberPicker.getClass().getDeclaredField("mSelectionDivider");
            dividerField.setAccessible(true);
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#446E6F71"));
            dividerField.set(numberPicker,colorDrawable);

            numberPicker.invalidate();

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);

            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);

                    Paint paint =  (Paint)selectorWheelPaintField.get(numberPicker);
                    paint.setColor(color);
                    // paint.setTypeface(typeface);
                    paint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, textSize, numberPicker.getResources().getDisplayMetrics()));

                    EditText editText = (EditText) child;

                    // editText.setTypeface(typeface);
                    if(numberPicker.isSelected()){
                        editText.setTextColor(Color.parseColor("#4884C6"));
                    }
                    else {
                        editText.setTextColor(color);
                    }
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                    String str = editText.getText().toString() + postfixString;
                    editText.setText(str);
                    editText.setFilters(new InputFilter[0]);
                    editText.invalidate();

                    numberPicker.invalidate();

                    return true;
                }
                catch(NoSuchFieldException e){
                } catch(IllegalArgumentException e){
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private final static String[] DISPLAYED_MINS = { "00", "10", "20", "30", "40", "50" };

    public static void setTimePickerTextColor(TimePicker timePicker, /*Typeface typeface,*/ int color, float textSize) {
        Resources system = Resources.getSystem();
        int hour_numberpicker_id = system.getIdentifier("hour", "id", "android");
        int minute_numberpicker_id = system.getIdentifier("minute", "id", "android");
        int ampm_numberpicker_id = system.getIdentifier("amPm", "id", "android");

        NumberPicker hour_numberpicker = (NumberPicker) timePicker.findViewById(hour_numberpicker_id);
        NumberPicker minute_numberpicker = (NumberPicker) timePicker.findViewById(minute_numberpicker_id);
        //10분단위 디스플레이[[
        minute_numberpicker.setMinValue(0);
        minute_numberpicker.setMaxValue(DISPLAYED_MINS.length - 1);
        minute_numberpicker.setWrapSelectorWheel(true);
        minute_numberpicker.setDisplayedValues(DISPLAYED_MINS);
        //]]10분단위 디스플레이

        NumberPicker ampm_numberpicker = (NumberPicker) timePicker.findViewById(ampm_numberpicker_id);


        setNumberPickerTextColor(hour_numberpicker, color, textSize,"");
        setNumberPickerTextColor(minute_numberpicker, color, textSize,"");
        setNumberPickerTextColor(ampm_numberpicker, color, textSize,"");

//        setNumberPickerTextColor(hour_numberpicker, color, textSize,"");
//        setNumberPickerTextColor(minute_numberpicker, color, textSize,"");
//        setNumberPickerTextColor(ampm_numberpicker, color, textSize,"");
    }

    public static void setTextColor(/*Typeface typeface,*/ int color, float textSize, DatePicker datepicker ) {
        // setNumberPickerProperties(datepicker, typeface, color, textSize);
        setNumberPickerProperties(datepicker, color, textSize);
    }


}