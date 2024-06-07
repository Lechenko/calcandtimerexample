package com.example.timebase.presentational.base;

public interface BasePresenter<V> {

    void startView(V view);

    void detachView();
}
