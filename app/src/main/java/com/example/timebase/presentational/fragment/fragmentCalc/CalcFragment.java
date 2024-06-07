package com.example.timebase.presentational.fragment.fragmentCalc;

import android.os.Bundle;

import com.example.timebase.R;
import com.example.timebase.databinding.FragmentCalcBinding;
import com.example.timebase.presentational.base.BaseFragment;
import com.example.timebase.presentational.base.BasePresenter;

import io.reactivex.disposables.CompositeDisposable;


public class CalcFragment extends BaseFragment<FragmentCalcBinding> implements CalcPresenterContract.View {
    private CalcPresenterContract.EventListener presenter;
    private CompositeDisposable clearAllDisposables;

    public CalcFragment() {

    }

    public static CalcFragment newInstance() {
        CalcFragment fragment = new CalcFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_calc;
    }

    @Override
    protected void initFragmentView() {
        presenter = new CalcPresenter();
        getBinding().setEvent(presenter);
        clearAllDisposables = new CompositeDisposable();
        getBinding().back.setOnClickListener(v -> {
            presenter.back();
        });
        getBinding().back.setOnLongClickListener(v -> {
            presenter.clear();
            return false;
        });
    }

    @Override
    protected void attachFragment() {

    }

    @Override
    protected void startFragment() {
        presenter.startView(this);
        presenter.initSubject();
        presenter.init();
//        Callable<Boolean> handled = () -> false;
//        clearAllDisposables.add(RxView.longClicks(getBinding().back,handled)
//                .subscribe( v -> {
//                    presenter.clear();
//                }));
//        clearAllDisposables.add(RxView.clicks(getBinding().back)
//                .subscribe( v -> presenter.back()));
    }

    @Override
    protected void stopFragment() {
        clearAllDisposables.clear();
    }

    @Override
    protected void destroyFragment() {
        clearAllDisposables.dispose();
        clearAllDisposables = null;
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

    @Override
    public void valueResult(String val) {
        getBinding().tvOperation.setText(val);
    }


    @Override
    public void valueNext(String val) {
        String v = getBinding().tvOperation.getText() != null &&
                !getBinding().tvOperation.getText().toString().isEmpty() ?
                getBinding().tvOperation.getText().toString().concat(val) :
                val;
        getBinding().tvOperation.setText(v);
    }

    //TODO not used
    @Override
    public void temporaryValue(String val) {
        getBinding().tvRes.setText(val);
    }

    @Override
    public String getValue() {
        return getBinding().tvOperation.getText() != null &&
                !getBinding().toString().isEmpty() ? getBinding().tvOperation.getText().toString() :
                "";
    }

    //TODO not used
    @Override
    public String getTemporaryValue(String val) {
        return getBinding().tvRes.getText() != null &&
                !getBinding().toString().isEmpty() ? getBinding().tvRes.getText().toString() :
                "";
    }
}
