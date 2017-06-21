package com.swein.framework.rxjava.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Observable : subscribe()
 * Observer : subscribe()
 * subscribe
 * event
 */
public class RxJavaActivity extends AppCompatActivity {

    final private static String TAG = "RxJavaActivity";

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            ILog.iLogDebug(TAG, "onNext " + s);
        }

        @Override
        public void onCompleted() {
            ILog.iLogDebug(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            ILog.iLogDebug(TAG, "onError " + e.toString());
        }
    };

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            ILog.iLogDebug(TAG, "onNext " + s);
        }

        @Override
        public void onCompleted() {
            ILog.iLogDebug(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            ILog.iLogDebug(TAG, "onError " + e.toString());
        }
    };

    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Seok Ho");
            subscriber.onCompleted();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        observable.subscribe(observer);

//        observable.subscribe(subscriber);

//        Observable observable = Observable.just("Hello", "Hi", "Aloha");
//        observable.subscribe(observer);

//        String[] words = {"Hello", "Hi", "Seok Ho"};
//        Observable observable = Observable.from(words);
//        observable.subscribe(observer);




    }
}
