package com.swein.pattern.strategy.otaku.live.play.impl;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.pattern.strategy.otaku.live.play.IotakuPlay;

/**
 * Created by seokho on 30/11/2017.
 */

public class OtakuPlay implements IotakuPlay {

    @Override
    public void watchAV(String name) {
        ILog.iLogDebug(name, "看AV?? 三精一毒...");
    }

    @Override
    public void playWOW(String name) {
        ILog.iLogDebug(name, "魔兽...到底冲不冲月卡？");
    }

    @Override
    public void playD3(String name) {
        ILog.iLogDebug(name, "暗黑3 20层进去就挂");
    }

    @Override
    public void playLOL(String name) {
        ILog.iLogDebug(name, "LOL 嗯嗯嗯...");
    }

    @Override
    public void playEatChicken(String name) {
        ILog.iLogDebug(name, "吃鸡？？ 腾讯的光荣使命听说不错，貌似已经上线了，赶紧看看手机配置够不够...");
    }

    @Override
    public void playGloryOfTheKing(String name) {
        ILog.iLogDebug(name, "敌军来不了战场了...");
    }

    @Override
    public void beAKeyboardMan(String name) {
        ILog.iLogDebug(name, "进入微博热搜, 点开评论, 写评论:我认为这个事是这样...反映了一些XX问题...");
    }
}
