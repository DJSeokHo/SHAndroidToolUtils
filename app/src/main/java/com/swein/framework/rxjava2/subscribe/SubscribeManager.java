package com.swein.framework.rxjava2.subscribe;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by seokho on 04/12/2017.
 */

public class SubscribeManager {

    private SubscribeManager() {}

    public static SubscribeManager instance = new SubscribeManager();

    public static SubscribeManager getInstance() {
        return instance;
    }

    public void subscribeOnMainThread(Observable<Object> observable, Observer<Object> observer) {

        observable.subscribe(observer);
    }

    public void subscribeOnNewThread(Observable<Object> observable, Observer<Object> observer) {

        // send on thread, get on UI thread
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void subscribeOnIOThread(Observable<Object> observable, Observer<Object> observer) {

        // send on thread, get on UI thread
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

    public void subscribeOnComputationThread(Observable<Object> observable, Observer<Object> observer) {

        // send on thread, get on UI thread
        observable.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }



    public void subscribeOnMainThread(Observable<Object> observable, Consumer<Object> consumer) {

        observable.subscribe(consumer);
    }

    public void subscribeOnNewThread(Observable<Object> observable, Consumer<Object> consumer) {

        // send on thread, get on UI thread
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
    }

    public void subscribeOnIOThread(Observable<Object> observable, Consumer<Object> consumer) {

        // send on thread, get on UI thread
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);

    }

    public void subscribeOnComputationThread(Observable<Object> observable, Consumer<Object> consumer) {

        // send on thread, get on UI thread
        observable.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);

    }

}
