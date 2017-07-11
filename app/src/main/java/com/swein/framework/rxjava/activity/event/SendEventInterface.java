package com.swein.framework.rxjava.activity.event;

import rx.Subscriber;

/**
 * Created by seokho on 07/07/2017.
 */

public interface SendEventInterface<T> {

    void call(Subscriber<T> subscriber);
}
