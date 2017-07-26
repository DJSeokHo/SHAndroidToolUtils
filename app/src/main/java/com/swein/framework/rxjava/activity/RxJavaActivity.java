package com.swein.framework.rxjava.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.swein.framework.rxjava.activity.event.GetEventInterface;
import com.swein.framework.rxjava.activity.observer.ObserverManager;
import com.swein.framework.rxjava.activity.subject.SubJectManager;
import com.swein.framework.rxjava.activity.subscribe.RxMethod;
import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.shandroidtoolutils.R;

import java.util.ArrayList;
import java.util.List;

import rx.subjects.ReplaySubject;

/**
 * Observable : subscribe()
 * Observer : subscribe()
 * subscribe
 * event
 */
public class RxJavaActivity extends AppCompatActivity {

    final private static String TAG = "RxJavaActivity";

    class Student{

        public String name;
        public List<String> courseList;

        public Student(String name, List<String> courseList) {
            this.name = name;
            this.courseList = courseList;
        }

    }


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

//        List<String> list1 = new ArrayList<>();
//        list1.add("C");
//        list1.add("C++");
//        list1.add("JAVA");
//
//        List<String> list2 = new ArrayList<>();
//        list1.add("Android");
//        list1.add("iOS");
//
//        List<String> list3 = new ArrayList<>();
//        list1.add("XML");
//        list1.add("HTML");
//        list1.add("Spring");
//
//        Student student1 = new Student("A", list1);
//        Student student2 = new Student("B", list2);
//        Student student3 = new Student("C", list3);
//
//        Student[] students = {student1, student2, student3};
//
//        // output course list one by one that from 3 students
//        RxMethod.observableFromSubscribeWithEventTranslateFlatMap(ObserverManager.getInstance().createObserver(new GetEventInterface() {
//            @Override
//            public void onCompleted() {
//                ILog.iLogDebug(TAG, "\n");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//
//                ILog.iLogDebug(TAG, o);
//            }
//
//            @Override
//            public void onStart() {
//
//            }
//        }), students, FuncManager.getInstance().createFun1(new FuncEventInterface() {
//            @Override
//            public Object call(Object o) {
//                Student student = (Student) o;
//                return Observable.from(student.courseList);
//            }
//        }), Schedulers.io(), AndroidSchedulers.mainThread());


//        List<String> list = new ArrayList<>();
//        list.add("asyncSubject1");
//        list.add("asyncSubject2");
//        list.add("asyncSubject3");

//        RxMethod.asyncSubjectFromSubscribe(SubJectManager.getInstance().createAsyncSubject(list), ObserverManager.getInstance().createSubscriber(new GetEventInterface() {
//            @Override
//            public void onCompleted() {
//                ILog.iLogDebug(TAG, "asyncSubject onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                ILog.iLogDebug(TAG, "asyncSubject onError");
//            }
//
//            @Override
//            public void onNext(Object o) {
//                ILog.iLogDebug(TAG, "asyncSubject : " + o);
//            }
//
//            @Override
//            public void onStart() {
//                ILog.iLogDebug(TAG, "asyncSubject onStart");
//            }
//        }));

//        List<String> list = new ArrayList<>();
//        list.add("behaviorSubject1");
//        list.add("behaviorSubject2");
//
////        BehaviorSubject behaviorSubject = SubJectManager.getInstance().createBehaviorSubject(null, "default");
//        BehaviorSubject behaviorSubject = SubJectManager.getInstance().createBehaviorSubject(list, null);
//
//        RxMethod.behaviorSubjectFromSubscribe(behaviorSubject, ObserverManager.getInstance().createSubscriber(new GetEventInterface() {
//            @Override
//            public void onCompleted() {
//                ILog.iLogDebug(TAG, "behaviorSubject onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                ILog.iLogDebug(TAG, "behaviorSubject onError");
//            }
//
//            @Override
//            public void onNext(Object o) {
//                ILog.iLogDebug(TAG, "behaviorSubject onNext : " + o);
//            }
//
//            @Override
//            public void onStart() {
//                ILog.iLogDebug(TAG, "behaviorSubject onStart");
//            }
//        }));
//
//        behaviorSubject.onNext("behaviorSubject3");
//        behaviorSubject.onNext("behaviorSubject4");

//        List<String> list = new ArrayList<>();
//        list.add("publishSubject1");
//        list.add("publishSubject2");
////
//        PublishSubject publishSubject = SubJectManager.getInstance().createPublishSubject(list);
//        RxMethod.publishSubjectFromSubscribe(publishSubject, ObserverManager.getInstance().createSubscriber(new GetEventInterface() {
//            @Override
//            public void onCompleted() {
//                ILog.iLogDebug(TAG, "publishSubject onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                ILog.iLogDebug(TAG, "publishSubject onError");
//            }
//
//            @Override
//            public void onNext(Object o) {
//                ILog.iLogDebug(TAG, "publishSubject onNext : " + o);
//            }
//
//            @Override
//            public void onStart() {
//                ILog.iLogDebug(TAG, "publishSubject onStart");
//            }
//        }));
//        publishSubject.onNext("publishSubject3");
//        publishSubject.onNext("publishSubject4");

        List<String> list = new ArrayList<>();
        list.add("replaySubject1");
        list.add("replaySubject2");

        ReplaySubject replaySubject = SubJectManager.getInstance().createReplaySubject(list);

        RxMethod.replaySubjectFromSubscribe(replaySubject, ObserverManager.getInstance().createSubscriber(new GetEventInterface() {
            @Override
            public void onCompleted() {
                ILog.iLogDebug(TAG, "replaySubject onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                ILog.iLogDebug(TAG, "replaySubject onError");
            }

            @Override
            public void onNext(Object o) {
                ILog.iLogDebug(TAG, "replaySubject onNext : " + o);
            }

            @Override
            public void onStart() {
                ILog.iLogDebug(TAG, "replaySubject onStart");
            }
        }));

        replaySubject.onNext("replaySubject3");
        replaySubject.onNext("replaySubject4");
        replaySubject.onNext("replaySubject5");

    }
}
