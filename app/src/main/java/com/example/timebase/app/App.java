package com.example.timebase.app;

import android.app.Application;
import com.example.timebase.BuildConfig;
import com.example.timebase.data.storage.SharedPreferencesStorage;
import com.example.timebase.presentational.Route;
import timber.log.Timber;

public class App extends Application {
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Route.getInstance();
        SharedPreferencesStorage.getInstance(this);
    }


}
