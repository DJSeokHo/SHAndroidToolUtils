package com.swein.framework.rxjava2.demo.console;

import com.swein.framework.rxjava2.observalbe.ObservableCreator;

import io.reactivex.Observable;

/**
 *
 * Created by seokho on 04/12/2017.
 */

public class RequestData {

    public static Observable<Object> requestData(Object... objects){
        return ObservableCreator.createObservable(objects);
    }

}
