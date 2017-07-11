package com.swein.framework.rxjava.activity.observer;

import com.swein.framework.rxjava.activity.event.GetEventInterface;

import rx.Observer;
import rx.Subscriber;

/**
 * Created by seokho on 07/07/2017.
 */

public class ObserverManager <T> {

    private ObserverManager() {}

    private static ObserverManager instance = new ObserverManager();

    public static ObserverManager getInstance() {

        return instance;
    }

    public Observer<T> createObserver(final GetEventInterface eventInterface) {

        // observer - wait event
        return new Observer<T>() {
            @Override
            public void onCompleted() {
                eventInterface.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                eventInterface.onError(e);
            }

            @Override
            public void onNext(T t) {
                eventInterface.onNext(t);
            }
        };
    }

    public Subscriber<T> createSubscriber(final GetEventInterface eventInterface) {

        // subscriber - base on observer, wait event
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                eventInterface.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                eventInterface.onError(e);
            }

            @Override
            public void onNext(T t) {
                eventInterface.onNext(t);
            }

            @Override
            public void onStart() {
                eventInterface.onStart();
            }
        };
    }
}
