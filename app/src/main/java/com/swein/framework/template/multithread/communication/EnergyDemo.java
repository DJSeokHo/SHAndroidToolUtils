package com.swein.framework.template.multithread.communication;

public class EnergyDemo {

    private final static int BOX_AMOUNT = 100;
    private final static double INITIAL_ENERGY = 1000;

    public static void main(String[] args) {

        EnergySystem energySystem = new EnergySystem(BOX_AMOUNT, INITIAL_ENERGY);

        for(int i = 0; i < BOX_AMOUNT; i++) {

            EnergyTransferRunnable energyTransferRunnable = new EnergyTransferRunnable(energySystem, i, INITIAL_ENERGY);
            Thread t = new Thread(energyTransferRunnable, "Transfer " + i);
            t.start();

        }
    }
}
