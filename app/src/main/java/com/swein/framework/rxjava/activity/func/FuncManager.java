package com.swein.framework.rxjava.activity.func;

import com.swein.framework.rxjava.activity.event.FuncEventInterface;

import rx.functions.Func1;

/**
 * Created by seokho on 11/07/2017.
 */

public class FuncManager<T> {

    private FuncManager() {}

    private static FuncManager instance = new FuncManager();

    public static FuncManager getInstance() {

        return instance;
    }

    public Func1<T, T> createFun1(final FuncEventInterface funcEventInterface) {
        return new Func1<T, T>() {
            @Override
            public T call(T t) {
                return (T) funcEventInterface.call(t);
            }
        };
    }

}
