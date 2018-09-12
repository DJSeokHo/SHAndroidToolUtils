package com.swein.framework.tools.util.email;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class EmailUtil {

    public static void mailTo(Context context, String email, String title, String message, boolean messageInFile) {

        Uri uri = Uri.parse("mailto:" + email);
        String[] emailInfo = {email};
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

        intent.putExtra(Intent.EXTRA_CC, emailInfo);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);

        if(messageInFile) {
            /*
                make file save in temp
                delete file when sent file
             */

//            intent.setType("application/octet-stream");
            /* ex: need temp file real path Uri.fromFile("file:///sdcard/Chrysanthemum.jpg") */
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        }
        else {
            intent.putExtra(Intent.EXTRA_TEXT, message);
        }


        try {
            context.startActivity(Intent.createChooser(intent, "이메일 앱 선택하세요"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
