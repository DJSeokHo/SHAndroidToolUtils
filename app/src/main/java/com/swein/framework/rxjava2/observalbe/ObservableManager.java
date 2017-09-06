package com.swein.framework.rxjava2.observalbe;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by seokho on 31/08/2017.
 */

public class ObservableManager {

    private ObservableManager() {}

    public static ObservableManager instance = new ObservableManager();

    public static ObservableManager getInstance() {
        return instance;
    }

    /**
     * String data Emitter
     * @param objects any parameters
     * @return
     */
    public Observable<Object> createObservableWithT(final Object... objects) {

        return Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                for(Object object : objects) {
                    e.onNext(object);
                }
                e.onComplete();
            }
        });
    }

}
