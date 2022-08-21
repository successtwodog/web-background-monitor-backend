package com.cg.proxyim.Utils;

import java.time.LocalDate;
import java.util.Random;

public class MockData {
    public int[] get7int() {
        Random r = new Random();
        int[] nums = new int[7];
        for (int i = 0; i < 7; i++) {
            nums[i] = r.nextInt(500)+200;
        }
        return nums;
    }

    //    public double[] get7double() {
//        Random r = new Random();
//
//    }
    public String[] get7date() {
        Random random = new Random();
        int minDay = (int) LocalDate.of(2022, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2022, 8, 20).toEpochDay();
        String[] dates = new String[7];
        for (int i = 0; i < 7; i++) {
            long randomDay = minDay + random.nextInt(maxDay - minDay);

            LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
            dates[i] = randomBirthDate.toString();
        }
        return dates;

    }

    public int get1int() {
        Random r = new Random();
        return r.nextInt(500)+200;
    }

}
