package com.swein.pattern.strategy.otaku.live.shopping.impl;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.pattern.strategy.otaku.live.shopping.IotakuShopping;

/**
 * Created by seokho on 30/11/2017.
 */

public class OtakuShopping implements IotakuShopping {

    @Override
    public void inUniqlo(String name) {
        ILog.iLogDebug(name, "在优衣库闲逛");
    }

    @Override
    public void localMarket(String name) {
        ILog.iLogDebug(name, "在市场小摊徘徊");
    }

    @Override
    public void oldMamsClothesShopNearHouse(String name) {
        ILog.iLogDebug(name, "在家附近大娘衣服店");
    }
}
