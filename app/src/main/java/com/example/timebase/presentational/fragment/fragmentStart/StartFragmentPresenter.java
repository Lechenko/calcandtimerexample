package com.example.timebase.presentational.fragment.fragmentStart;


import com.example.timebase.presentational.Route;
import static com.example.timebase.common.Constans.TAG_CALC;
import static com.example.timebase.common.Constans.TAG_EXCEPTION;
import static com.example.timebase.common.Constans.TAG_TIMER;

public class StartFragmentPresenter implements StartFragmentContract.Listener {

    private StartFragmentContract.View view;

    @Override
    public void eventClick(String cmd) {
        switch (cmd) {
            case TAG_TIMER:
                Route.getInstance().transactionTimer();
                break;
            case TAG_CALC:
                Route.getInstance().transactionCalc();
                break;
            default:
                view.onMessage(TAG_EXCEPTION);
        }
    }

    @Override
    public void init() {
        Route.getInstance().setTextAppBar("");
    }

    @Override
    public void startView(StartFragmentContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view != null) view = null;
    }
}
