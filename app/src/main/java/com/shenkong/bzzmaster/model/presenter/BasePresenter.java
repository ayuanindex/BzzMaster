package com.shenkong.bzzmaster.model.presenter;

/**
 * Created by rnd on 2018/3/15.
 */

public class BasePresenter<V> {
    public final String TAG = this.getClass().getSimpleName();

    protected V mView;

    public void setMV(V v) {
        mView = v;
    }

}
