package com.swein.framework.tools.util.endec;

import android.text.TextUtils;
import android.util.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * encode
 * decode
 * util
 *
 * Created by seokho on 2018/4/26.
 */
public class EndecUtil {

    public static String fileToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        InputStream inputStream = null;
        byte[] data;
        String result = null;

        try {
            inputStream = new FileInputStream(path);

            data = new byte[inputStream.available()];

            inputStream.read(data);

            result = Base64.encodeToString(data, Base64.DEFAULT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
