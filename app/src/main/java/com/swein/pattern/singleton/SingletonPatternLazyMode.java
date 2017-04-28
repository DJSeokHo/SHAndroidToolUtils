package com.swein.pattern.singleton;

/**
 * singleton pattern class example
 *
 * Created by seokho on 26/04/2017.
 *
 * mode: hungry, lazy
 */

public class SingletonPatternLazyMode {

    /**
     * lazy mode
     */
    //2. create an only object
    private static SingletonPatternLazyMode instance;

    //1. private construct method
    private SingletonPatternLazyMode(){

    }

    //3. get object method
    public static SingletonPatternLazyMode getInstance() {

        if(instance == null) {
            instance = new SingletonPatternLazyMode();
        }

        return instance;
    }

}
