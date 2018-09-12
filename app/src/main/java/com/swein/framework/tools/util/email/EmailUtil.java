package com.swein.framework.tools.util.email;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class EmailUtil {

    public static void mailTo(Context context, String email, String title, String message) {

        Uri uri = Uri.parse("mailto:" + email);
        String[] emailInfo = {email};
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

        intent.putExtra(Intent.EXTRA_CC, emailInfo);
        intent.putExtra(Intent.EXTRA_SUBJECT, title);

        intent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            context.startActivity(Intent.createChooser(intent, "이메일 앱 선택하세요"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
