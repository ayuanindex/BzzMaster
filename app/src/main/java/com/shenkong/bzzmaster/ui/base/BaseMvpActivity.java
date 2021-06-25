package com.shenkong.bzzmaster.ui.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.shenkong.bzzmaster.common.utils.PMUtil;
import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;


/**
 * Created by rnd on 2018/3/15.
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseEven,
        View.OnClickListener {

    public P mPresenter;


    protected void initEven() {
        mPresenter = PMUtil.getT(this, 0);
        if (this instanceof BaseEven) {
            mPresenter.setMV(this);
        }
    }




}
