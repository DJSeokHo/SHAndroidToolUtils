package com.swein.framework.template.multithread.basic;

public class MultiThreadBase {

    public static void main(String[] args) {

        Thread shThread = new SHThread();
        shThread.setName("SHThread");
        shThread.start();

        Thread shRunnableThread = new Thread(new SHRunnable(), "SHRunnable");
        shRunnableThread.start();
    }

}
