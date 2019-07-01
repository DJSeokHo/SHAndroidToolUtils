package com.swein.framework.template.handlerthread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.TextView;

import com.swein.shandroidtoolutils.R;

public class HandlerThreadTemplateActivity extends Activity {

    private final static String TAG = "HandlerThreadTemplateActivity";

    private TextView textViewUpdateUI;

    private HandlerThread backgroundHandlerThread;

    private Handler uiHandler;
    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread_template);

        textViewUpdateUI = findViewById(R.id.textViewUpdateUI);

        // create ui handler
        uiHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what) {
                    case 1:

                        textViewUpdateUI.setText("haha " + msg.obj);

                        break;
                }
                return false;
            }
        });

        // create background handler thread
        backgroundHandlerThread = new HandlerThread("HandlerThread");
        backgroundHandlerThread.start();

        // create background handler
        // and set looper of handlerThread
        // into background handler
        backgroundHandler = new Handler(backgroundHandlerThread.getLooper()) {

            int count = 0;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // get the message from background handler
                // and
                // send message to uiHandler
                Message.obtain(uiHandler, 1, ++count).sendToTarget();
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i = 0; i < 10; i++) {

                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // send message to looper of background handler in handlerThread
                    backgroundHandler.sendEmptyMessage(1);
                }

            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        backgroundHandlerThread.quitSafely();
        super.onDestroy();
    }

}
