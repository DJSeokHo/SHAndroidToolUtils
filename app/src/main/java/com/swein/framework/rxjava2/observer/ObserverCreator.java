package com.swein.framework.rxjava2.observer;

import com.swein.framework.rxjava2.observer.observerinterface.IObserver;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *
 * Created by seokho on 31/08/2017.
 */

public class ObserverCreator {

    public static Observer<Object> createObserver(final IObserver IObserver) {

        return new Observer<Object>() {

            @Override
            public void onSubscribe(Disposable d) {
                IObserver.onSubscribe(d);
            }

            @Override
            public void onNext(Object value) {
                IObserver.onNext(value);
            }

            @Override
            public void onError(Throwable e) {
                IObserver.onError(e);
            }

            @Override
            public void onComplete() {
                IObserver.onComplete();
            }
        };
    }

}
