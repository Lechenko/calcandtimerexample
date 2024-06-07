package com.example.timebase.domain;

import com.example.timebase.data.Calc;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class InteractorCalc implements ICalc {
    @Override
    public Observable<String> calc(String val) {
        return Observable.just(val)
                .subscribeOn(Schedulers.io())
                .flatMap(v -> Calc.getInstance().calc(v));
    }
    
}
