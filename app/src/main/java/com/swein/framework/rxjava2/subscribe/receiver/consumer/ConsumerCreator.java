package com.swein.framework.rxjava2.subscribe.receiver.consumer;

import com.swein.framework.rxjava2.subscribe.receiver.consumer.consumerinterface.IConsumer;

import io.reactivex.functions.Consumer;

/**
 * Created by seokho on 04/12/2017.
 */

public class ConsumerCreator {

    // Consumer only care onNext from Observable
    public static Consumer<Object> createConsumer(final IConsumer iConsumer) {

        return new Consumer<Object>() {
            @Override
            public void accept(Object object) throws Exception {
                iConsumer.accept(object);
            }
        };
    }
}
