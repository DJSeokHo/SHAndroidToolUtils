package com.swein.framework.rxjava.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.swein.shandroidtoolutils.R;

/**
 * Observable : subscribe()
 * Observer : subscribe()
 * subscribe
 * event
 */
public class RxJavaActivity extends AppCompatActivity {

    final private static String TAG = "RxJavaActivity";



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

//        //observalbe subscribed by observer
//        RxMethod.observableSubscribeWithObserver(ObservalbeManager.getInstance().createObservable(new SendEventInterface() {
//            @Override
//            public void call(Subscriber subscriber) {
//                subscriber.onNext("Hello");
//            }
//        }), ObserverManager.getInstance().createObserver(new GetEventInterface() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                ILog.iLogDebug(TAG, o);
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        }), null, null);
//
//        //observalbe subscribed by subscriber
//        RxMethod.observableSubscribeWithSubscriber(ObservalbeManager.getInstance().createObservable(new SendEventInterface() {
//            @Override
//            public void call(Subscriber subscriber) {
//                subscriber.onNext("Hi");
//            }
//        }), ObserverManager.getInstance().createSubscriber(new GetEventInterface() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                ILog.iLogDebug(TAG, o);
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        }), null, null);
//
//        RxMethod.observableSubscribeWithSubscriber(ObservalbeManager.getInstance().createObservable(new SendEventInterface() {
//            @Override
//            public void call(Subscriber subscriber) {
//                Drawable drawable;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    drawable = getTheme().getDrawable(R.drawable.t1);
//                    subscriber.onNext(drawable);
//                    subscriber.onCompleted();
//                }
//            }
//        }), ObserverManager.getInstance().createSubscriber(new GetEventInterface() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                imageView.setImageDrawable((Drawable) o);
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        }), null, null);
//
//
//        RxMethod.observableFromWithObserver(ObserverManager.getInstance().createObserver(new GetEventInterface() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                ILog.iLogDebug(TAG, o);
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        }), new String[]{"hi", "hello", "seok ho"}, null, null);
//
//
//        RxMethod.observableSubscribeWithAction(ObservalbeManager.getInstance().createObservable(new SendEventInterface() {
//            @Override
//            public void call(Subscriber subscriber) {
//                subscriber.onNext("action test");
//            }
//        }), ActionManager.getInstance().createAction1(new ActionInterface() {
//            @Override
//            public void call() {
//
//            }
//
//            @Override
//            public void call(Object o) {
//                ILog.iLogDebug(TAG, o);
//            }
//        }), null, null);


//        RxMethod.observableSubscribeWithObserver(ObservalbeManager.getInstance().createObservable(new SendEventInterface() {
//            @Override
//            public void call(Subscriber subscriber) {
//                Drawable drawable;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    drawable = getTheme().getDrawable(R.drawable.t2);
//                    subscriber.onNext(drawable);
//                    subscriber.onCompleted();
//                }
//            }
//        }), ObserverManager.getInstance().createObserver(new GetEventInterface() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                imageView.setImageDrawable((Drawable) o);
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        }), Schedulers.io(), AndroidSchedulers.mainThread());

//        RxMethod.observableJustSubscribeWithEventTranslateMap(ObserverManager.getInstance().createObserver(new GetEventInterface() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                imageView.setImageBitmap((Bitmap) o);
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        }), R.drawable.t2, FuncManager.getInstance().createFun1(new FuncEventInterface() {
//            @Override
//            public Object call(Object o) {
//                return BitmapUtils.getBitmapFromDrawableResource(RxJavaActivity.this, (Integer) o);
//            }
//        }), Schedulers.io(), AndroidSchedulers.mainThread());



    }
}
