package com.swein.framework.template.multithread.demo;

public class HeroThread extends Thread {

    @Override
    public void run() {

        System.out.println(getName() + " start attack");
        for(int i = 0; i < 10; i++) {
            System.out.println(getName() + " start attack " + i);
        }


        System.out.println(getName() + " finish attack");
    }
}
