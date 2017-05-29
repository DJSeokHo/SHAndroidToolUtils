package com.swein.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.swein.framework.tools.util.toast.ToastUtils;
import com.swein.shandroidtoolutils.R;

public class HandlerExampleActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private Button button;


    private Handler callbackHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            ToastUtils.showShortToastNormal(getApplicationContext(), ""+1);
            return false;
        }
    }){
        @Override
        public void handleMessage(Message msg) {
            ToastUtils.showShortToastNormal(getApplicationContext(), ""+2);
        }
    };

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

//            textView.setText(""+msg.arg1 + " - " + msg.arg2);
            textView.setText(""+msg.obj);

        }
    };

    private int images[] = {R.drawable.t1, R.drawable.t2, R.drawable.t3};
    private int index;

    class Person{
        private int age;
        private String name;

        @Override
        public String toString() {
            return "name=" + name + " age=" + age;
        }
    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            index++;
            index = index%3;
            imageView.setImageResource(images[index]);
            handler.postDelayed(new MyRunnable(), 1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_example);


        //ActivityThread will create main(UI) thread.
        //main(UI) thread will create Looper and message queue

        //threadlocal set(), get()  will input, output param of thread



        textView = (TextView)findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                handler.removeCallbacks(myRunnable);
                callbackHandler.sendEmptyMessage(1);
            }
        });

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
//                    Message message = new Message();

                    Message message = handler.obtainMessage();

                    Person person = new Person();
                    person.age = 30;
                    person.name = "seok ho";
                    message.obj = person;
                    message.sendToTarget();
//                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        handler.postDelayed(new MyRunnable(), 1000);
//
//        new Thread() {
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    //will throw exception:Only the original thread that created a view hierarchy can touch its views.
////                    textView.setText("update thread");
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setText("update thread");
//                        }
//                    });
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

    }
}
