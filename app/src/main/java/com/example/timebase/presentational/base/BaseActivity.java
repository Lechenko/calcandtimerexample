package com.example.timebase.presentational.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Objects;

public abstract class BaseActivity<Binding extends ViewDataBinding> extends AppCompatActivity {
    private Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        initView();
    }

    protected void transactionFragmentNoBackStack(Fragment fragment, int container){
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(container,fragment,fragment.getClass().getSimpleName())
                .commit();
    }


    protected void transactionFragmentWithBackStack(Fragment fragment,int container) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            FragmentManager manager = this.getSupportFragmentManager();
            if (manager.getBackStackEntryCount() == 0) {
                      super.finish();
            }
    }

    @Override
    protected void onStart() {
        super.onStart();
        onStartView();
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected Binding getBinding(){
        return binding;
    }

    protected abstract void initView();


    @Override
    public void onDestroy() {
        if (getPresenter() != null) {
            getPresenter().detachView();
            onDestroyView();
        }
        super.onDestroy();
    }

    protected abstract void onStartView();

    protected abstract void onDestroyView();

    protected abstract BasePresenter getPresenter();

    protected void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);

    }

    protected void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).showSoftInput(this.getWindow().getDecorView(), InputMethodManager.SHOW_IMPLICIT);
    }

    protected void toast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
