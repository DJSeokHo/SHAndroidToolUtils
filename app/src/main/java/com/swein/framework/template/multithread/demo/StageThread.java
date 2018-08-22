package com.swein.framework.template.multithread.demo;

public class StageThread extends Thread {

    @Override
    public void run() {

        ArmyRunnable armyRunnableA = new ArmyRunnable();
        ArmyRunnable armyRunnableB = new ArmyRunnable();

        Thread armyA = new Thread(armyRunnableA, "Army A");
        Thread armyB = new Thread(armyRunnableB, "Army B");

        armyA.start();
        armyB.start();

        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Hero coming");

        Thread hero = new HeroThread();
        hero.setName("SH Hero");

        System.out.println("Attack!!!");

        armyRunnableA.keepRunning = false;
        armyRunnableB.keepRunning = false;

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        hero.start();
        try {
            /*
                all of other thread must wait hero thread
                all of other thread can run only when hero thread finished
             */
            hero.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Hero win");

    }
}
