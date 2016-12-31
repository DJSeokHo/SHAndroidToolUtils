package com.swein.framework.tools.util.random;

import java.util.Random;

/**
 * Created by seokho on 30/12/2016.
 */

public class RandomNumberUtils {

    public static int getRandomIntegerNumber(int from, int nextFrom) {

        Random rand = new Random();
        int randNumber = rand.nextInt(from) + nextFrom;
        return randNumber;
    }

}
