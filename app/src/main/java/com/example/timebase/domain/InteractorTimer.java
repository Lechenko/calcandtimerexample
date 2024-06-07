package com.example.timebase.domain;

import com.example.timebase.data.Timer;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class InteractorTimer implements ITimer{
    @Override
    public Observable<String> timer() {
        return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .subscribeOn(Schedulers.io())
                .flatMap(v -> Timer.getInstance().run())
                .doOnError(Throwable::printStackTrace);
    }

    @Override
    public Single<String> getTime(boolean isRunning, long startTime) {
        return Observable.just("")
                .subscribeOn(Schedulers.io())
                .flatMap(v -> {
                    Timer.getInstance().setStartTime(startTime);
                    Timer.getInstance().setRunning(isRunning);
                    return Timer.getInstance().run();
                })
                .lastOrError();
    }
}
