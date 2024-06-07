package com.example.timebase.presentational;


import com.example.timebase.R;
import com.example.timebase.presentational.fragment.fragmentCalc.CalcFragment;
import com.example.timebase.presentational.fragment.fragmentStart.StartFragment;
import com.example.timebase.presentational.fragment.timer_fragment.TimerFragment;

public class Route{
    private static Route instance;
    private IRoute view;

    private Route() {
    }

    public static synchronized Route getInstance() {
        if (instance == null) {
            return instance = new Route();
        } else {
            return instance;
        }
    }

    public void setTextAppBar(String val){
        view.setTextAppBar(val);
    }

    public void transactionSelectFragment(){
        view.transitionFragment(StartFragment.newInstance(), R.id.content_main);
    }

    public void transactionTimer(){
        view.transitionFragment(TimerFragment.newInstance(), R.id.content_main);
    }

    public void transactionCalc(){
        view.transitionFragment(CalcFragment.newInstance(), R.id.content_main);
    }


    public void startView(IRoute view) {
        this.view = view;
    }

    public void stopView(){
        if (view != null) view = null;
    }
}
