package com.swein.framework.rxjava.activity.subscribe;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 *
 * Created by seokho on 11/07/2017.
 */

public class RxMethod<T> {

    public static void observableSubscribe(Observable observable, Observer observer, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            observable.subscribeOn(from).observeOn(to).subscribe(observer);
        }
        else {
            observable.subscribe(observer);
        }
    }

    public static void observableSubscribe(Observable observable, Subscriber subscriber, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            observable.subscribeOn(from).observeOn(to).subscribe(subscriber);
        }
        else {
            observable.subscribe(subscriber);
        }
    }

    public static void observableJust(Observer observer, Object object, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            Observable.just(object).subscribeOn(from).observeOn(to).subscribe(observer);
        }
        else {
            Observable.just(object).subscribe(observer);
        }
    }

    public static void observableJustWith(Subscriber subscriber, Object object, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            Observable.just(object).subscribeOn(from).observeOn(to).subscribe(subscriber);
        }
        else {
            Observable.just(object).subscribe(subscriber);
        }
    }

    public static void observableFrom(Observer observer, Object[] objects, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            Observable.from(objects).subscribeOn(from).observeOn(to).subscribe(observer);
        }
        else {
            Observable.from(objects).subscribe(observer);
        }
    }

    public static void observableFrom(Subscriber subscriber, Object[] objects, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            Observable.from(objects).subscribeOn(from).observeOn(to).subscribe(subscriber);
        }
        else {
            Observable.from(objects).subscribe(subscriber);
        }
    }

    public static void observableSubscribeWithAction(Observable observable, Action1 next, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            observable.subscribeOn(from).observeOn(to).subscribe(next);
        }
        else {
            observable.subscribe(next);
        }
    }

    public static void observableSubscribeWithAction(Observable observable, Action1 next, Action1 error, Action0 completed, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            observable.subscribeOn(from).observeOn(to).subscribe(next, error, completed);
        }
        else {
            observable.subscribe(next, error, completed);
        }
    }

    public static void observableSubscribeWithAction(Observable observable, Action1 next, Action1 error, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            observable.subscribeOn(from).observeOn(to).subscribe(next, error);
        }
        else {
            observable.subscribe(next, error);
        }
    }

    public static void observableJustSubscribeWithEventTranslateMap(Observer observer, Object fromData, Func1 func1, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            Observable.just(fromData).map(func1).subscribeOn(from).observeOn(to).subscribe(observer);
        }
        else {
            Observable.just(fromData).map(func1).subscribe(observer);
        }
    }

    public static void observableJustSubscribeWithEventTranslateMap(Subscriber subscriber, Object fromData, Func1 func1, Scheduler from, Scheduler to) {
        if(from != null && to != null) {
            Observable.just(fromData).map(func1).subscribeOn(from).observeOn(to).subscribe(subscriber);
        }
        else {
            Observable.just(fromData).map(func1).subscribe(subscriber);
        }
    }

}
