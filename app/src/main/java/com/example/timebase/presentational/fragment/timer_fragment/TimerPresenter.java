package com.example.timebase.presentational.fragment.timer_fragment;


import com.example.timebase.common.Constans;
import com.example.timebase.data.Timer;
import com.example.timebase.data.storage.SharedPreferencesStorage;
import com.example.timebase.domain.ITimer;
import com.example.timebase.domain.InteractorTimer;
import com.example.timebase.presentational.Route;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import timber.log.Timber;
import static com.example.timebase.common.Constans.DEFAULT_TIMER;
import static com.example.timebase.common.Constans.IS_RUNNING;
import static com.example.timebase.common.Constans.START_TIME;
import static com.example.timebase.common.Constans.TAG_TIMER;

public class TimerPresenter implements TimerPresenterContract.EventListener{
    private TimerPresenterContract.View view;
    private ITimer interactor;
    private CompositeDisposable disposable;

    @Override
    public void startView(TimerPresenterContract.View view) {
        disposable = new CompositeDisposable();
        interactor = new InteractorTimer();
        this.view = view;
        boolean isRunning = SharedPreferencesStorage.getInstance().loadDataBoolean(IS_RUNNING);
        long startTime = SharedPreferencesStorage.getInstance().loadDataLong(START_TIME);
        if(isRunning && startTime != 0L) {
            interactor.getTime(isRunning,startTime)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<String>() {
                        @Override
                        public void onSuccess(String s) {
                            view.valueView(s);
                            dispose();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e("getTime %s",e.getMessage());
                            view.onMessage(Constans.TAG_EXCEPTION);
                        }
                    });
            timerWork(isRunning, startTime);
        }else {
            view.valueView(DEFAULT_TIMER);
        }

    }

    @Override
    public void detachView() {
        if (view != null) view = null;
        if(disposable != null){
            if(!disposable.isDisposed())disposable.dispose();
            disposable = null;
        }
        if(interactor != null)interactor = null;
    }

    @Override
    public void init() {
        Route.getInstance().setTextAppBar(TAG_TIMER);
    }

    @Override
    public void startTimer() {
        timerWork(true, System.currentTimeMillis());
    }

    private void timerWork(boolean running, long time){
        pauseCheck(running,time);
        disposable.add(interactor.timer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> view.valueView(v),throwable -> view.onMessage(Constans.TAG_EXCEPTION)));
    }

    private void pauseCheck(boolean running,long time){
        if (!running){
            Timer.getInstance().setRunning(running);
            Timer.getInstance().setStartTime(time);
        }else {
            long pauseTime =  SharedPreferencesStorage.getInstance().loadDataLong(START_TIME);
            long startTime = pauseTime != 0L ? pauseTime - time : time;
            Timer.getInstance().setRunning(running);
            Timer.getInstance().setStartTime(startTime);
        }
        SharedPreferencesStorage.getInstance().saveDataBoolean(IS_RUNNING, Timer.getInstance().isRunning());
        SharedPreferencesStorage.getInstance().saveDataLong(START_TIME,time);
    }

    @Override
    public void stopTimer() {
        view.valueView(DEFAULT_TIMER);
        Timer.getInstance().clearTimer();
        Timer.getInstance().setRunning(false);
        SharedPreferencesStorage.getInstance().saveDataBoolean(IS_RUNNING, false);
        SharedPreferencesStorage.getInstance().saveDataLong(START_TIME,0L);
        disposable.clear();
    }

    @Override
    public void pauseTimer() {
        pauseCheck(false,System.currentTimeMillis());
    }

}
