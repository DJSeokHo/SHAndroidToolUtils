package com.swein.framework.template.multithread.communication;

public class EnergySystem {

    private final double[] energyBoxes;

    private final Object lockObj = new Object();

    EnergySystem(int n, double initialEnergy) {
        this.energyBoxes = new double[n];
        for(int i = 0; i < energyBoxes.length; i++) {
            energyBoxes[i] = initialEnergy;
        }
    }

    public void transfer(int from, int to, double amount) {

        // add lock
        synchronized (lockObj) {

//            if(energyBoxes[from] < amount) {
//                return;
//            }

            /* keep this runnable waiting when condition no matched
             * otherwise this runnable will keep request CUP resource
             * that can make app slowly */
            while (energyBoxes[from] < amount) {

                try {
                    /* waiting in wait set */
                    lockObj.wait();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            System.out.print(Thread.currentThread().getName());
            energyBoxes[from] -= amount;
            System.out.printf("from %d trans %10.2f energy to %d", from, amount, to);

            energyBoxes[to] += amount;
            System.out.printf("total energy is : %10.2f%n", getTotalEnergies());

            /* tell other waiting runnable to access me */
            lockObj.notifyAll();
        }

    }

    private double getTotalEnergies() {
        double sum = 0;
        for(double amount: energyBoxes) {
            sum += amount;
        }

        return sum;
    }

    public int getBoxAmount() {
        return energyBoxes.length;
    }
}
