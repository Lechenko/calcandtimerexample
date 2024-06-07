package com.example.timebase.presentational.fragment.timer_fragment;

import com.example.timebase.presentational.base.BasePresenter;
import com.example.timebase.presentational.base.BaseViewFragmentContract;

public interface TimerPresenterContract {
    interface View extends BaseViewFragmentContract {
        void valueView(String value);
    }

    interface EventListener extends BasePresenter<View> {
        void init();

        void startTimer();

        void stopTimer();


        void pauseTimer();
    }
}
