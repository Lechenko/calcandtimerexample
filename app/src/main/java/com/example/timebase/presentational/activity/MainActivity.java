package com.example.timebase.presentational.activity;


import android.support.v4.app.Fragment;
import com.example.timebase.R;
import com.example.timebase.databinding.ActivityMainBinding;
import com.example.timebase.presentational.IRoute;
import com.example.timebase.presentational.Route;
import com.example.timebase.presentational.base.BaseActivity;
import com.example.timebase.presentational.base.BasePresenter;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements IMainPresenter.View, IRoute {
    private IMainPresenter.EventListener presenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        presenter = new MainPresenter();
        getBinding().setEvent(presenter);
        getBinding().setView(this);
        Route.getInstance().startView(this);
        presenter.init();
    }

    @Override
    protected void onStartView() {
        presenter.startView(this);
    }

    @Override
    protected void onDestroyView() {
        Route.getInstance().stopView();

    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void transitionFragment(Fragment fragment, int contener) {
        transactionFragmentWithBackStack(fragment,contener);
    }

    @Override
    public void setTextAppBar(String val) {
        getBinding().tvAppBar.setText(val);
    }

    @Override
    public void backStack() {
        onBackPressed();
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
