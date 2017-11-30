package com.swein.pattern.strategy.otaku.otaku;

import com.swein.pattern.strategy.otaku.live.drink.IotakuDrink;
import com.swein.pattern.strategy.otaku.live.play.IotakuPlay;
import com.swein.pattern.strategy.otaku.live.shopping.IotakuShopping;
import com.swein.pattern.strategy.otaku.live.study.IotakuStudy;

/**
 * Created by seokho on 30/11/2017.
 */

public abstract class OTAKU {

    protected IotakuStudy iotakuStudy;
    protected IotakuPlay iotakuPlay;
    protected IotakuShopping iotakuShopping;

    protected IotakuDrink iotakuDrink;

    public abstract void work();

    public abstract void goShopping();

    public abstract void play();

}
