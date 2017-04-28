package com.swein.pattern.singleton;

/**
 * singleton pattern class example
 *
 * Created by seokho on 26/04/2017.
 *
 * mode: hungry, lazy
 */

public class SingletonPatternHungryMode {

    /**
     * hungry mode running time fast than lazy mode and thread safe
     */
    //2. create an only object
    private static SingletonPatternHungryMode instance = new SingletonPatternHungryMode();

    //1. private construct method
    private SingletonPatternHungryMode(){

    }

    //3. get object method
    public static SingletonPatternHungryMode getInstance() {
        return instance;
    }

}
