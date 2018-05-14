package com.swein.framework.tools.eventsplitshot.eventcenter;


import com.swein.framework.tools.eventsplitshot.observer.ESSObserver;
import com.swein.framework.tools.eventsplitshot.observer.runnable.IESSObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event Split Shot: can help you use subject - event - observer pattern in easy way
 * Event Split Shot: 简单易用的观察者模式API, 可以提供多对多事件发送
 *
 * organization: SWein
 * Created by seokho on 01/12/2017.
 */
public class ESSCenter {

    private ESSCenter() {
    }

    private static ESSCenter instance = new ESSCenter();

    public static ESSCenter getInstance() {
        return instance;
    }

    private Map<String, List<ESSObserver>> map = new HashMap<>();

    public void sendEventMessage(String arrows, Object object, Object data) {

        if (map.get(arrows) == null || 0 == map.get(arrows).size()) {
            return;
        }

        for (ESSObserver myObserver : map.get(arrows)) {
            myObserver.getShooter().shot(object, data);
        }
    }


    public void addESSObserver(String arrows, Object classObject, IESSObserver iessObserver) {

        if (map.get(arrows) == null) {
            map.put(arrows, new ArrayList<ESSObserver>());
        }

        ESSObserver essObserver = new ESSObserver(iessObserver, classObject);

        for(ESSObserver observer : map.get(arrows)){
            if(observer.getClassObject() == essObserver.getClassObject()){
                return;
            }
        }

        map.get(arrows).add(essObserver);
    }

    public void removeAllESSObserverInThis(Object object) {
        List<ESSObserver> essObservers = new ArrayList<>();

        for (List<ESSObserver> essObserverArrayList : map.values()) {
            essObservers.clear();
            for (ESSObserver observer : essObserverArrayList) {
                if (observer.getClassObject() == object) {
                    essObservers.add(observer);
                }
            }
            essObserverArrayList.removeAll(essObservers);
        }

    }

}
