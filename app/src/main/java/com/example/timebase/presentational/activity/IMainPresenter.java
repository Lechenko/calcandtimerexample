package com.example.timebase.presentational.activity;

import com.example.timebase.presentational.base.BasePresenter;
import com.example.timebase.presentational.base.BaseViewActivityContract;

public interface IMainPresenter {

    interface View extends BaseViewActivityContract {
        void backStack();
    }

    interface EventListener extends BasePresenter<View> {
        void init();
    }
}