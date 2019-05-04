package com.babbel.qa.util;

import java.util.Date;

public class Timer {

    public long startStamp;

    public static long getTimeStamp() {
        return new Date().getTime();
    }

    public static int getDifference(long start, long end) {
        return (int) ((end - start) / 1000);
    }

    public void start() {
        startStamp = getTimeStamp();
    }

    public boolean expired(int seconds) {
        int difference = (int) ((getTimeStamp() - startStamp) / 1000);
        return difference > seconds;
    }
}
