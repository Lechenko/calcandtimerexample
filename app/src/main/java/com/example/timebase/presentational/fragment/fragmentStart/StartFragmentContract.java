package com.example.timebase.presentational.fragment.fragmentStart;

import com.example.timebase.presentational.base.BasePresenter;
import com.example.timebase.presentational.base.BaseViewFragmentContract;

public interface StartFragmentContract {

    interface Listener extends BasePresenter<StartFragmentContract.View> {
        void eventClick(String cmd);

        void init();
    }

    interface View extends BaseViewFragmentContract {

    }
}
