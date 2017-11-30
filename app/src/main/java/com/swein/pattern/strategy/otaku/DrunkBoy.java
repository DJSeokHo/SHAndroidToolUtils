package com.swein.pattern.strategy.otaku;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.pattern.strategy.otaku.live.drink.impl.OtakuDrink;
import com.swein.pattern.strategy.otaku.live.play.impl.OtakuPlay;
import com.swein.pattern.strategy.otaku.otaku.OTAKU;

/**
 * Created by seokho on 30/11/2017.
 */

public class DrunkBoy extends OTAKU {

    private String name;

    public DrunkBoy(String name){
        ILog.iLogDebug(name, "大家好，我是 --> " + name);
        ILog.iLogDebug(name, "我经常做以下几件事");
        iotakuPlay = new OtakuPlay();

        this.name = name;
    }

    @Override
    public void work() {
        ILog.iLogDebug(name, "工作就是喝喝喝...");
    }

    @Override
    public void goShopping() {
        ILog.iLogDebug(name, "买酒去...");
    }

    public void wantDrunk(OtakuDrink otakuDrink) {
        iotakuDrink = otakuDrink;
    }

    @Override
    public void play() {
        ILog.iLogDebug(name, "不醉乌龟...");
        iotakuDrink.drink(name);
    }
}
