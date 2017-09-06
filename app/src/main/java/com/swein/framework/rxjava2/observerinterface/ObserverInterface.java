package com.swein.framework.rxjava2.observerinterface;

import io.reactivex.disposables.Disposable;

/**
 * Created by seokho on 31/08/2017.
 */

public interface ObserverInterface {

    void onSubscribe(Disposable d);
    void onNext(Object value);
    void onError(Throwable e);
    void onComplete();
}
