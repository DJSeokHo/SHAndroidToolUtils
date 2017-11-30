package com.swein.pattern.strategy.otaku;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.pattern.strategy.otaku.live.play.impl.OtakuPlay;
import com.swein.pattern.strategy.otaku.live.shopping.impl.OtakuShopping;
import com.swein.pattern.strategy.otaku.live.study.impl.OtakuStudy;
import com.swein.pattern.strategy.otaku.otaku.OTAKU;

/**
 * Created by seokho on 30/11/2017.
 */

public class DirtyBoy extends OTAKU {

    private String name;

    public DirtyBoy(String name){
        ILog.iLogDebug(name, "大家好，我是 --> " + name);
        ILog.iLogDebug(name, "我经常做以下几件事");
        iotakuStudy = new OtakuStudy();
        iotakuPlay = new OtakuPlay();
        iotakuShopping = new OtakuShopping();

        this.name = name;
    }

    @Override
    public void work() {
        ILog.iLogDebug(name, "周一到周五，我会学习");
        iotakuStudy.studyAndroid(name);
    }

    @Override
    public void goShopping() {
        ILog.iLogDebug(name, "周六，我会逛街");
        iotakuShopping.oldMamsClothesShopNearHouse(name);
    }

    @Override
    public void play() {
        ILog.iLogDebug(name, "周日，我会在家玩一天");

        iotakuPlay.playWOW(name);
        iotakuPlay.playD3(name);
        iotakuPlay.playGloryOfTheKing(name);
        iotakuPlay.playLOL(name);
        iotakuPlay.playEatChicken(name);
        iotakuPlay.watchAV(name);
        iotakuPlay.beAKeyboardMan(name);
    }

}
