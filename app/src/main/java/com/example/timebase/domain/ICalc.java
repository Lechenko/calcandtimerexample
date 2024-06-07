package com.example.timebase.domain;


import io.reactivex.Observable;


public interface ICalc {


    Observable<String> calc(String val);
}
