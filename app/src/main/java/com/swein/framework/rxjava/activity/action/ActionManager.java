package com.swein.framework.rxjava.activity.action;

import com.swein.framework.rxjava.activity.event.ActionInterface;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by seokho on 11/07/2017.
 */

public class ActionManager<T> {

    private ActionManager() {}

    private static ActionManager instance = new ActionManager();

    public static ActionManager getInstance() {

        return instance;
    }

    public Action0 createAction0(final ActionInterface actionInterface) {
        return new Action0() {
            // onCompleted()
            @Override
            public void call() {
                actionInterface.call();
            }
        };
    }

    public Action1 createAction1(final ActionInterface actionInterface) {
        return new Action1<T>() {
            @Override
            public void call(T t) {
                actionInterface.call(t);
            }
        };
    }

}
