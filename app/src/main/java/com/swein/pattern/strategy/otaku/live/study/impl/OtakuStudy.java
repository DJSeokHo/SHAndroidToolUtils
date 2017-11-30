package com.swein.pattern.strategy.otaku.live.study.impl;

import com.swein.framework.tools.util.debug.log.ILog;
import com.swein.pattern.strategy.otaku.live.study.IotakuStudy;

/**
 * Created by seokho on 30/11/2017.
 */

public class OtakuStudy implements IotakuStudy {


    @Override
    public void studyAndroid(String name) {
        ILog.iLogDebug(name, "studyAndroid");
    }

    @Override
    public void studyPython(String name) {
        ILog.iLogDebug(name, "studyPython");
    }

    @Override
    public void studyIOS(String name) {
        ILog.iLogDebug(name, "studyIOS");
    }

    @Override
    public void studyCSharp(String name) {
        ILog.iLogDebug(name, "studyCSharp");
    }

    @Override
    public void studyPhotoShop(String name) {
        ILog.iLogDebug(name, "studyPhotoShop");
    }
}
