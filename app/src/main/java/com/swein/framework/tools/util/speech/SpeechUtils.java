package com.swein.framework.tools.util.speech;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.swein.data.global.symbol.Symbol;
import com.swein.framework.tools.util.toast.ToastUtils;

import java.io.File;
import java.util.Locale;

/**
 * Created by seokho on 18/01/2017.
 */

public class SpeechUtils {

    public static void TextToSpeechByQueueAddWithoutRecord(final Context context, TextToSpeech textToSpeech, final Locale locale, String string) {


        final TextToSpeech finalTextToSpeech = textToSpeech;
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    int result = finalTextToSpeech.setLanguage(locale.US);
                    if(result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE) {
                        ToastUtils.showShortToastNormal(context, "have no language");
                    }
                }
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(string, TextToSpeech.QUEUE_ADD, null, "speech");
        }
    }

    public static void TextToSpeechByQueueFLUSHWithoutRecord(final Context context, TextToSpeech textToSpeech, final Locale locale, String string) {

        final TextToSpeech finalTextToSpeech = textToSpeech;
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    int result = finalTextToSpeech.setLanguage(locale.US);
                    if(result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE) {
                        ToastUtils.showShortToastNormal(context, "have no language");
                    }
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(string, TextToSpeech.QUEUE_FLUSH, null, "speech");
        }
    }

    public static void TextToSpeechWithRecord(final Context context, TextToSpeech textToSpeech, final Locale locale, String string, String recordPath, String recordFileName) {


        final TextToSpeech finalTextToSpeech = textToSpeech;
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    int result = finalTextToSpeech.setLanguage(locale.US);
                    if(result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE) {
                        ToastUtils.showShortToastNormal(context, "have no language");
                    }
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.synthesizeToFile(string, null, new File(recordPath + Symbol.VIRGULE + recordFileName), "record");
        }
    }
}
