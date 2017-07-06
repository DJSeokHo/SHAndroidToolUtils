package com.swein.framework.rxjava.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Observable : subscribe()
 * Observer : subscribe()
 * subscribe
 * event
 */
public class RxJavaActivity extends AppCompatActivity {

    final private static String TAG = "RxJavaActivity";

    // observer - wait event
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {
            ILog.iLogDebug(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            ILog.iLogDebug(TAG, "onError " + e.toString());
        }

        @Override
        public void onNext(String s) {
            ILog.iLogDebug(TAG, "onNext " + s);
        }
    };

    // subscriber - base on observer, wait event
    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            ILog.iLogDebug(TAG, "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            ILog.iLogDebug(TAG, "onError " + e);
        }

        @Override
        public void onNext(String s) {
            ILog.iLogDebug(TAG, "onNext " + s);
        }

        // before event send. can clear or reset here
        @Override
        public void onStart() {
            super.onStart();
            ILog.iLogDebug(TAG, "onStart ");
        }
    };

    //observable - send event
    Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Seok Ho");
            subscriber.onCompleted();
        }

    });

    //quick send event
//    Observable observable = Observable.just("Hello", "Hi", "Seok Ho");

    //quick send event from T[] or Iterable<? extends T>
//    String[] words = {"Hello", "Hi", "Aloha"};
//    Observable observable = Observable.from(words);

    private ImageView imageView;


//    subscribeOn() and observeOn() can operate thread
//    Schedulers.immediate()    this thread
//    Schedulers.newThread()    new thread
//    Schedulers.io()           file IO / DB IO / Network IO
//    Schedulers.computation()  computation
//    AndroidSchedulers.mainThread()    for android main thread only


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        imageView = (ImageView)findViewById(R.id.imageView);

        //observalbe subscribed by observer
        observable.subscribe(observer);

        //observalbe subscribed by subscriber
        observable.subscribe(subscriber);

        Observable.create(new Observable.OnSubscribe<Object>() {

            @Override
            public void call(Subscriber<? super Object> subscriber) {
                Drawable drawable = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    drawable = getTheme().getDrawable(R.drawable.t1);
                    subscriber.onNext(drawable);
                    subscriber.onCompleted();
                }
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                imageView.setImageDrawable((Drawable) o);
            }
        });



        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())   //subscribe on IO thread
                .observeOn(AndroidSchedulers.mainThread())  // call back on main thread
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        ILog.iLogDebug(TAG, "number:" + number);
                    }
                });

        Observable.create(new Observable.OnSubscribe<Object>() {

            @Override
            public void call(Subscriber<? super Object> subscriber) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Drawable drawable = getTheme().getDrawable(R.drawable.t2);
                    subscriber.onNext(drawable);
                    subscriber.onCompleted();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        imageView.setImageDrawable((Drawable) o);
                    }
                });


    }
}
