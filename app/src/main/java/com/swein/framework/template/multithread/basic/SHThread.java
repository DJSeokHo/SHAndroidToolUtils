package com.swein.framework.template.multithread.basic;

public class SHThread extends Thread {

    @Override
    public void run() {
        System.out.println(getName() + " start");

        int count = 0;
        boolean keepRunning = true;

        while (keepRunning) {

            System.out.println(getName() + " running " + (++count));

            if(count == 100) {
                keepRunning = false;
            }

            if(count % 10 == 0) {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(getName() + " end");
    }
}
