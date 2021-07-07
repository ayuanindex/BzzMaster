package com.shenkong.bzzmaster.net;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class ObjectLoader {
    public static <T> Observable<T> observeat(Observable<T> observable, LifecycleProvider<ActivityEvent> lifecycleProvider) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(lifecycleProvider.<T>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                ; //指定在主线程中
    }

    public static <T> Observable<T> observefg(Observable<T> observable, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(lifecycleProvider.<T>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread()); //指定在主线程中
    }
}
