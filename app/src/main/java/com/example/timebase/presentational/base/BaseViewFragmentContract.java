package com.example.timebase.presentational.base;

public interface BaseViewFragmentContract {
    void onMessage(String message);

    void showProgress();

    void hideProgress();
}
