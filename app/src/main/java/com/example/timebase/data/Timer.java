package com.example.timebase.data;

import android.annotation.SuppressLint;

import io.reactivex.Observable;

public class Timer {
    private static Timer instance;
    private boolean running = false;
    private long startTime = 0L;
    private long hour = 0L;
    private long min = 0L;
    private long sec = 0L;

    private Timer() {

    }

    public static synchronized Timer getInstance() {
        if (instance == null) {
            instance = new Timer();

        }
        return instance;
    }

    @SuppressLint("DefaultLocale")
    public Observable<String> run() {
        hour = getElapsedTimeHour();
        min = getElapsedTimeMin();
        sec = getElapsedTimeSecs();
        return Observable.just(String.format("%02d:%02d:%02d", hour, min, sec));
    }


    private long getElapsedTimeSecs() {
        if (running ) {
            return ((System.currentTimeMillis() - startTime) / 1000L) % 60L;
        } else {
            return sec;
        }
    }

    private long getElapsedTimeMin() {
        if (running) {
            return (((System.currentTimeMillis() - startTime) / 1000L) / 60L) % 60L;
        } else {
            return min;
        }
    }

    private long getElapsedTimeHour() {
        if (running) {
            return ((((System.currentTimeMillis() - startTime) / 1000L) / 60L) / 60L);
        } else {
            return hour;
        }
    }

    public void clearTimer() {
        startTime = 0L;
        hour = 0L;
        min = 0L;
        sec = 0L;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isRunning() {
        return running;
    }

    public long getStartTime() {
        return startTime;
    }
}
