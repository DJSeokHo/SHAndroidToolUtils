package com.swein.framework.module.noticenotification.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.swein.framework.module.noticenotification.NoticeNotificationManager;
import com.swein.framework.module.noticenotification.constants.NoticeConstants;
import com.swein.framework.tools.util.bitmaps.BitmapUtil;
import com.swein.framework.tools.util.thread.ThreadUtil;
import com.swein.shandroidtoolutils.MainActivity;
import com.swein.shandroidtoolutils.R;

public class NoticeNotificationActivity extends Activity {

    private final static String TAG = "NoticeNotificationActivity";

    private Button button44;
    private Button button50;
    private Button button60;
    private Button button70;
    private Button button80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_notification);

        button44 = findViewById(R.id.button44);
        button50 = findViewById(R.id.button50);
        button60 = findViewById(R.id.button60);
        button70 = findViewById(R.id.button70);
        button80 = findViewById(R.id.button80);

        button44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ThreadUtil.startThread(new Runnable() {
                    @Override
                    public void run() {

                        Bitmap bitmap = BitmapUtil.getBitmapFromUrl("http://www.studynews.net/wp-content/uploads/2017/10/health-club-680.jpg");

                        String longMessage = "A  typical notification provides some useful information to the user, who can then either dismiss it or act on it – usually by tapping the notification to launch the app associated with this notification. For example if you see a ‘You have a new message’ notification, then chances are that tapping it will launch an application where you can view the message you’ve just received.\n" +
                                "\n" +
                                "Most of the time, this is all you need to worry about when you’re creating notifications – but what if you have something more specific in mind, such as a custom layout, or enhanced notifications that deliver extra functionality? Or maybe you’ve just finished testing your app and feel like its notifications are an area where you could improve the user experience.\n" +
                                "\n";

                        NoticeNotificationManager.getInstance().createNoticeNotification4_4Or5_0(NoticeNotificationActivity.this, NoticeConstants.Type.NORMAL,
                                "normal title", "normal message", "coming", true, R.mipmap.ic_launcher, bitmap, null, null,
                                MainActivity.class, 1, 1);

                        NoticeNotificationManager.getInstance().createNoticeNotification4_4Or5_0(NoticeNotificationActivity.this, NoticeConstants.Type.LONG_TEXT,
                                "long title", "long message", "coming", true, R.mipmap.ic_launcher, bitmap, longMessage, null,
                                MainActivity.class, 2, 2);

                        NoticeNotificationManager.getInstance().createNoticeNotification4_4Or5_0(NoticeNotificationActivity.this, NoticeConstants.Type.BIG_IMAGE,
                                "big image title", "big image message", "coming", true, R.mipmap.ic_launcher, bitmap, null, bitmap,
                                MainActivity.class, 3, 3);

                    }
                });

            }
        });

        button50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = BitmapUtil.getBitmapFromUrl("http://www.studynews.net/wp-content/uploads/2017/10/health-club-680.jpg");

                String longMessage = "A  typical notification provides some useful information to the user, who can then either dismiss it or act on it – usually by tapping the notification to launch the app associated with this notification. For example if you see a ‘You have a new message’ notification, then chances are that tapping it will launch an application where you can view the message you’ve just received.\n" +
                        "\n" +
                        "Most of the time, this is all you need to worry about when you’re creating notifications – but what if you have something more specific in mind, such as a custom layout, or enhanced notifications that deliver extra functionality? Or maybe you’ve just finished testing your app and feel like its notifications are an area where you could improve the user experience.\n" +
                        "\n";

                NoticeNotificationManager.getInstance().createNoticeNotification4_4Or5_0(NoticeNotificationActivity.this, NoticeConstants.Type.NORMAL,
                        "normal title", "normal message", null, true, R.mipmap.ic_launcher, bitmap, null, null,
                        MainActivity.class, 1, 1);

                NoticeNotificationManager.getInstance().createNoticeNotification4_4Or5_0(NoticeNotificationActivity.this, NoticeConstants.Type.LONG_TEXT,
                        "long title", "long message", null, true, R.mipmap.ic_launcher, bitmap, longMessage, null,
                        MainActivity.class, 2, 2);

                NoticeNotificationManager.getInstance().createNoticeNotification4_4Or5_0(NoticeNotificationActivity.this, NoticeConstants.Type.BIG_IMAGE,
                        "big image title", "big image message", null, true, R.mipmap.ic_launcher, bitmap, null, bitmap,
                        MainActivity.class, 3, 3);

                NoticeNotificationManager.getInstance().createNoticeNotification4_4Or5_0(NoticeNotificationActivity.this, NoticeConstants.Type.HEADS_UP,
                        "heads up title", "heads up message", null, true, R.mipmap.ic_launcher, bitmap, null, null,
                        MainActivity.class, 4, 4);

            }
        });

        button60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button70.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
