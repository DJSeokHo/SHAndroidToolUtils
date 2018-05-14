package com.swein.framework.tools.eventsplitshot.observer;


import com.swein.framework.tools.eventsplitshot.observer.runnable.IESSObserver;

/**
 * ESSObserver: observer, take event, observer class and data
 * ESSObserver: 观察者类，携带事件，事件接受方，需要传送的数据(可以为空)
 *
 * organization: SWein
 * author: seok ho
 * Created by seokho on 01/12/2017.
 */
public class ESSObserver {

    private IESSObserver iessObserver;
    private Object classObject;

    public ESSObserver(IESSObserver iessObserver, Object classObject) {
        this.iessObserver = iessObserver;
        this.classObject = classObject;
    }

    public IESSObserver getShooter() {
        return this.iessObserver;
    }

    public Object getClassObject() {
        return this.classObject;
    }
}
