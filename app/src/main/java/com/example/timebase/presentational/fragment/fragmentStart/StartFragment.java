package com.example.timebase.presentational.fragment.fragmentStart;

import android.os.Bundle;
import com.example.timebase.R;
import com.example.timebase.databinding.FragmentStartBinding;
import com.example.timebase.presentational.base.BaseFragment;
import com.example.timebase.presentational.base.BasePresenter;

public class StartFragment extends BaseFragment<FragmentStartBinding> implements StartFragmentContract.View {
    private StartFragmentContract.Listener presenter;

    public StartFragment() {
    }

    public static StartFragment newInstance() {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_start;
    }

    @Override
    protected void initFragmentView() {
        presenter = new StartFragmentPresenter();
        getBinding().setEvent(presenter);
    }

    @Override
    protected void attachFragment() {

    }

    @Override
    protected void startFragment() {
        presenter.startView(this);
        presenter.init();
    }

    @Override
    protected void stopFragment() {

    }

    @Override
    protected void destroyFragment() {
        presenter = null;
    }

    @Override
    protected void pauseFragment() {

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

}
