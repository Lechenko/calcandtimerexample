package com.example.timebase.presentational.fragment.fragmentCalc;

import com.example.timebase.presentational.base.BasePresenter;
import com.example.timebase.presentational.base.BaseViewFragmentContract;

public interface CalcPresenterContract {
    interface View extends BaseViewFragmentContract {
        void valueResult(String val);

        void valueNext(String val);

        void temporaryValue(String val);

        String getValue();

        String getTemporaryValue(String val);
    }

    interface EventListener extends BasePresenter<View> {
        void init();

        void initSubject();

        void back();

        void clear();

        void clickListener(String val);
    }
}
