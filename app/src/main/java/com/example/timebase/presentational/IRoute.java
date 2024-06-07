package com.example.timebase.presentational;

import android.support.v4.app.Fragment;

public interface IRoute {
    void transitionFragment(Fragment fragment, int contener);

    void setTextAppBar(String val);
}
