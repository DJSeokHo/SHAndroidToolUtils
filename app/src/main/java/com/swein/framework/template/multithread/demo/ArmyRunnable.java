package com.swein.framework.template.multithread.demo;

public class ArmyRunnable implements Runnable {

    // volatile can make sure this thread can read some value from other thread
    public volatile boolean keepRunning = true;

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName() + " start attack");

        while (keepRunning) {
            // attack 10 times
            for(int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " attack " + i);

                // let other army to attack
                Thread.yield();
            }
        }

        System.out.println(Thread.currentThread().getName() + " finish attack");
    }
}
