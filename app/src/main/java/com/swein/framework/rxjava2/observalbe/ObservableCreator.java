package com.swein.framework.rxjava2.observalbe;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 *
 * Created by seokho on 31/08/2017.
 */

public class ObservableCreator {
    /**
     * String data Emitter
     * @param objects any parameters
     * @return
     */
    public static Observable<Object> createObservable(final Object... objects) {

        Observable<Object> observable = Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                for(Object object : objects) {
                    e.onNext(object);
                }
                e.onComplete();
            }
        });

        return observable;
    }


}
