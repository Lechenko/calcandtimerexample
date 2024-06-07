package com.example.timebase.domain;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface ITimer {
    Observable<String> timer();

    Single<String> getTime(boolean isRunning, long startTime);
}
