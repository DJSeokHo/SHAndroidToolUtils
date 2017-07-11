package com.swein.framework.rxjava.activity.event;

/**
 * Created by seokho on 07/07/2017.
 */

public interface GetEventInterface<T> {

    void onCompleted();
    void onError(Throwable e);
    void onNext(T t);
    void onStart();
}
