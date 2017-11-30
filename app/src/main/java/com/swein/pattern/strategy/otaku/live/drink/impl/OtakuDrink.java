package com.swein.pattern.strategy.otaku.live.drink.impl;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.pattern.strategy.otaku.live.drink.IotakuDrink;

/**
 * Created by seokho on 30/11/2017.
 */

public class OtakuDrink implements IotakuDrink {

    @Override
    public void drink(String name) {
        ILog.iLogDebug(name, "酒是粮食精，越喝越年轻...");
    }
}
