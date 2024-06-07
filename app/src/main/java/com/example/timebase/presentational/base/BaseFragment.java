package com.example.timebase.presentational.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Objects;


public abstract class BaseFragment<Binding extends ViewDataBinding> extends Fragment {
    private Binding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attachFragment();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        startFragment();
    }

    @Override
    public void onPause() {
        pauseFragment();
        super.onPause();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        initFragmentView();
        return binding.getRoot();
    }

    protected Binding getBinding() {
        return binding;
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract void initFragmentView();

    protected abstract void attachFragment();

    protected abstract void startFragment();

    protected abstract void stopFragment();

    protected abstract void destroyFragment();

    protected abstract void pauseFragment();

    @Override
    public void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().detachView();
            destroyFragment();
        }
        super.onDestroy();
    }

    protected void toast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    protected abstract BasePresenter getPresenter();

    public void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) Objects.requireNonNull(this.getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(this.getActivity().getWindow().getDecorView().getWindowToken(), 0);

    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(this.getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).showSoftInput(this.getActivity().getWindow().getDecorView(), InputMethodManager.SHOW_IMPLICIT);
    }
}
