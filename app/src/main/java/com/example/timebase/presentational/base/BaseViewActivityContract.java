package com.example.timebase.presentational.base;


public interface BaseViewActivityContract {
    void onMessage(String message);

    void showProgress();

    void hideProgress();
}
