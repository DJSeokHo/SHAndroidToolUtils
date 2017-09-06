package com.swein.framework.rxjava2;

import com.swein.framework.rxjava2.observalbe.ObservableManager;
import com.swein.framework.rxjava2.observer.ObserverManager;
import com.swein.framework.rxjava2.observerinterface.ObserverInterface;

import org.junit.Test;

import io.reactivex.disposables.Disposable;

import static junit.framework.Assert.assertEquals;

/**
 * Created by seokho on 01/09/2017.
 */

public class RxJava2Test {

    @Test
    public void testString() {

        ObservableManager.getInstance().createObservableWithT("1", "2", "3")
                .subscribe(
                        ObserverManager.getInstance().createObserverWithObserverInterface(new ObserverInterface() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Object value) {
                                assertEquals("1", value.toString());
                                assertEquals("2", value.toString());
                                assertEquals("3", value.toString());
                                System.out.println(value.toString());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                System.out.println("onComplete");
                            }
                        }));

    }

}
