package com.swein.framework.rxjava2.demo.console;

import com.swein.framework.rxjava2.observalbe.ObservableCreator;
import com.swein.framework.rxjava2.observer.ObserverCreator;
import com.swein.framework.rxjava2.observer.observerinterface.IObserver;
import com.swein.framework.rxjava2.subscribe.SubscribeManager;

import io.reactivex.disposables.Disposable;

/**
 *
 * Created by seokho on 04/12/2017.
 */
public class RxJava2Main {

    public static void main(String[] args)
    {

        SubscribeManager.getInstance().subscribeOnMainThread(
                ObservableCreator.createObservable("1", "2", "3"),
                ObserverCreator.createObserver(new IObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        System.out.println("??? " + value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("??? onComplete");
                    }
                })
        );

        SubscribeManager.getInstance().subscribeOnMainThread(
                RequestData.requestData("4", "5", "6"),
                ObserverCreator.createObserver(new IObserver() {

                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Object value) {
                        if(value.toString().equals("5")){
                            disposable.dispose();
                        }

                        System.out.println("??? " + value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("??? onComplete");
                    }
                })
        );

    }

}
