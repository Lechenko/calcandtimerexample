package com.example.timebase.presentational.fragment.timer_fragment;

import com.example.timebase.R;
import com.example.timebase.data.Timer;
import com.example.timebase.data.storage.SharedPreferencesStorage;
import com.example.timebase.databinding.FragmentTimerBinding;
import com.example.timebase.presentational.base.BaseFragment;
import com.example.timebase.presentational.base.BasePresenter;
import com.jakewharton.rxbinding2.view.RxView;

import static com.example.timebase.common.Constans.IS_RUNNING;
import static com.example.timebase.common.Constans.START_TIME;


public class TimerFragment extends BaseFragment<FragmentTimerBinding> implements TimerPresenterContract.View{
    private TimerPresenterContract.EventListener presenter;

    public static TimerFragment newInstance() {
        return new TimerFragment();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_timer;
    }

    @Override
    protected void initFragmentView() {
        presenter = new TimerPresenter();
        getBinding().setEvent(presenter);
        presenter.init();
    }

    @Override
    protected void attachFragment() {

    }

    @Override
    protected void startFragment() {
        presenter.startView(this);
    }

    @Override
    protected void stopFragment() {

    }

    @Override
    protected void destroyFragment() {

    }

    @Override
    protected void pauseFragment() {
       SharedPreferencesStorage.getInstance().saveDataBoolean(IS_RUNNING, Timer.getInstance().isRunning());
       SharedPreferencesStorage.getInstance().saveDataLong(START_TIME,Timer.getInstance().getStartTime());
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void onMessage(String message) {
        toast(message);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void valueView(String value) {
        getBinding().tvTimer.setText(value);
    }

}
