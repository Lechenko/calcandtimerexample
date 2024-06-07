package com.example.timebase.presentational.activity;

import com.example.timebase.presentational.Route;

public class MainPresenter implements IMainPresenter.EventListener{
    private IMainPresenter.View view;

    @Override
    public void init() {
        Route.getInstance().transactionSelectFragment();
    }

    @Override
    public void startView(IMainPresenter.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view != null) view = null;
    }
}
