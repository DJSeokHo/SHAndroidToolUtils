package com.swein.data.global.file;

import android.os.Environment;

import com.swein.data.global.symbol.Symbol;

/**
 * Created by seokho on 14/01/2017.
 */

public class FileStorageData {

    public final static String FILE_STORAGE_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public final static String FILE_STROAGE_ROOT_FOLDER = Symbol.VIRGULE + "SeokHo" + Symbol.VIRGULE;
}
