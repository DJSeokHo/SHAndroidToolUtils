package com.swein.framework.rxjava.activity.observalbe;

import com.swein.framework.rxjava.activity.event.SendEventInterface;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by seokho on 07/07/2017.
 */

public class ObservalbeManager<T> {

    private ObservalbeManager() {}

    private static ObservalbeManager instance = new ObservalbeManager();

    public static ObservalbeManager getInstance() {

        return instance;
    }

    public Observable<T> createObservable(final SendEventInterface sendEventInterface) {

        //observable - send event
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {

                sendEventInterface.call(subscriber);
            }

        });
    }
}
