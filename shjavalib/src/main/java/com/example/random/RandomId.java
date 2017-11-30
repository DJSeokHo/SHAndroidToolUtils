package com.example.random;

import java.util.Random;

/**
 * Created by seokho on 15/09/2017.
 */
public class RandomId {
    private Random random;
    private String table;

    public RandomId() {
        random = new Random();
        table = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    }

    public String randomId(int id) {
        String ret = null,
                num = String.format("%05d", id);
        int key = random.nextInt(10),
                seed = random.nextInt(100);
        Caesar caesar = new Caesar(table, seed);
        num = caesar.encode(key, num);
        ret = num
                + String.format("%01d", key)
                + String.format("%02d", seed);

        return ret;
    }
}
