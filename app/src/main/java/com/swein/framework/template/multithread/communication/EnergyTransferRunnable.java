package com.swein.framework.template.multithread.communication;

public class EnergyTransferRunnable implements Runnable {

    private EnergySystem energySystem;
    private int fromBox;
    private double maxAmount;

    private static int DELAY = 30;

    public EnergyTransferRunnable(EnergySystem energySystem, int fromBox, double maxAmount) {
        this.energySystem = energySystem;
        this.fromBox = fromBox;
        this.maxAmount = maxAmount;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int toBox = (int)(energySystem.getBoxAmount() * Math.random());
                double amount = maxAmount * Math.random();
                energySystem.transfer(fromBox, toBox, amount);
                Thread.sleep((int)(DELAY * Math.random()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
