package com.shenkong.bzzmaster.model.presenter;

/**
 * Created by rnd on 2018/3/15.
 *
 */

public class BasePresenter<V> {

    protected V mView;

    public void setMV(V v){
        mView = v;
    }

}
