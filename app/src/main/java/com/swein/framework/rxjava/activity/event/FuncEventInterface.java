package com.swein.framework.rxjava.activity.event;

/**
 * Created by seokho on 11/07/2017.
 */

public interface FuncEventInterface<T> {

    T call(T t);

}
