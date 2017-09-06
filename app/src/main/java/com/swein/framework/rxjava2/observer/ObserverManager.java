package com.swein.framework.rxjava2.observer;

import com.swein.framework.rxjava2.observerinterface.ObserverInterface;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by seokho on 31/08/2017.
 */

public class ObserverManager {

    private ObserverManager() {}

    public static ObserverManager instance = new ObserverManager();

    public static ObserverManager getInstance() {
        return instance;
    }


    public Observer<Object> createObserverWithObserverInterface(final ObserverInterface observerInterface) {
        return new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                observerInterface.onSubscribe(d);
            }

            @Override
            public void onNext(Object value) {
                observerInterface.onNext(value);
            }

            @Override
            public void onError(Throwable e) {
                observerInterface.onError(e);
            }

            @Override
            public void onComplete() {
                observerInterface.onComplete();
            }
        };
    }

}
