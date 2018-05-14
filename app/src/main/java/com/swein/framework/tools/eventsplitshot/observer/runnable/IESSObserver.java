package com.swein.framework.tools.eventsplitshot.observer.runnable;

/**
 * Created by seokho on 01/12/2017.
 *
 * organization: SWein
 * author: seok ho
 */

public interface IESSObserver {

    // run method(with data), data can be null
    // 收到消息(带数据)，执行方法, 数据可以为空
    void shot(Object object, Object data);

}
