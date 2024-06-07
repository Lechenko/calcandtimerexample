package com.example.timebase.presentational.fragment.fragmentCalc;

import com.example.timebase.common.Constans;
import com.example.timebase.data.model.PairObject;
import com.example.timebase.domain.InteractorCalc;
import com.example.timebase.domain.ICalc;
import com.example.timebase.presentational.Route;
import com.example.timebase.util.Utils;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

import static com.example.timebase.common.Constans.BACK;
import static com.example.timebase.common.Constans.CANCEL;
import static com.example.timebase.common.Constans.DUBBING;
import static com.example.timebase.common.Constans.NOT_ZERO_MESSAGE;
import static com.example.timebase.common.Constans.TAG_CALC;


public class CalcPresenter implements CalcPresenterContract.EventListener {
    private CalcPresenterContract.View view;
    private final CompositeDisposable disposable;
    private ICalc interactor;
    private BehaviorSubject<String> subjectModel;
    private BehaviorSubject<PairObject<PairObject<Boolean, String>, PairObject<String, String>>> subjectView;


    public CalcPresenter() {
        disposable = new CompositeDisposable();
        interactor = new InteractorCalc();
    }

    public void initSubject() {
        subjectModel = BehaviorSubject.create();
        subjectView = BehaviorSubject.create();
    }

    @Override
    public void init() {
        Route.getInstance().setTextAppBar(TAG_CALC);
        Observable<PairObject<PairObject<Boolean, String>, PairObject<String, String>>> view = subjectView.subscribeOn(AndroidSchedulers.mainThread());
        Observable<String> model = subjectModel.subscribeOn(Schedulers.io());
        disposable.add(view.subscribe(v -> {
                    if (!v.getLeft().getLeft() && !v.getLeft().getRight().isEmpty() && v.getLeft().getRight().equals(BACK)) {
                        back();
                    } else if (v.getLeft().getLeft()) {
                        this.view.valueNext(v.getLeft().getRight());
                    }
                }, throwable -> this.view.onMessage(throwable.getMessage())));
        disposable.add(model.flatMap(v -> interactor.calc(v)
                .delay(200, TimeUnit.MILLISECONDS))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::accept));

    }

    private void accept(String v) {
        if (v != null) {
            if (Constans.NOT_ZERO.equals(v)) {
                view.onMessage(NOT_ZERO_MESSAGE);
                view.valueResult("");
                return;
            }
            view.valueResult(v);
        }
    }


    @Override
    public void back() {
        if (!view.getValue().isEmpty()) {
            String val = view.getValue();
            view.valueResult(val.substring(0, val.length() - 1));
        }
    }

    @Override
    public void clear() {
        view.valueResult("");
    }

    @Override
    public void startView(CalcPresenterContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (view != null) view = null;
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        subjectModel.onComplete();
        interactor = null;
    }

    @Override
    public void clickListener(String val) {
        disposable.add(Observable.just(notDubbing(view.getValue(), val))
                .subscribeOn(Schedulers.io())
                .flatMap(v -> v)
                .filter(v -> v.getLeft().getLeft())
                .flatMap(v -> Observable.just(v.getRight().getLeft().concat(v.getRight().getRight())))
                .flatMap(this::checOper)
                .filter(v -> !v.isEmpty())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(v -> subjectModel.onNext(v), throwable -> view.onMessage(throwable.getMessage())));
    }


    private Observable<String> checOper(String val) {
        return Observable.just(Utils.checOper(val) ? val : "");
    }

    private Observable<PairObject<PairObject<Boolean, String>, PairObject<String, String>>> notDubbing(String globalVal, String val) {
        return Observable.just(Utils.notDubbing(globalVal, val))
                .flatMap(v -> {
                    if (v.getLeft().getLeft() && v.getLeft().getRight().equals(CANCEL)) {
                        PairObject<PairObject<Boolean, String>, PairObject<String, String>> pair = new PairObject<>(new PairObject<>(false, ""), null);
                        subjectView.onNext(pair);
                        return Observable.just(pair);
                    }
                    if (v.getLeft().getLeft() && v.getLeft().getRight().equals(DUBBING)) {
                        PairObject<PairObject<Boolean, String>, PairObject<String, String>> pair = new PairObject<>(new PairObject<>(false, BACK), null);
                        subjectView.onNext(pair);
                        return Observable.just(pair);
                    }
                    v.getLeft().setLeft(true);
                    subjectView.onNext(v);
                    return Observable.just(v);
                });

    }


}
