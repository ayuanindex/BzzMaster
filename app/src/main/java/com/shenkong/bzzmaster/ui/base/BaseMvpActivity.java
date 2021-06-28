package com.shenkong.bzzmaster.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.shenkong.bzzmaster.common.utils.PMUtil;
import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;


/**
 * Created by rnd on 2018/3/15.
 */

public abstract class BaseMvpActivity<P extends BasePresenter> extends RxAppCompatActivity implements BaseEven, View.OnClickListener {
    public final String TAG = this.getClass().getSimpleName();
    public static Handler uiHandler = uiHandler = new Handler(Looper.getMainLooper());
    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        // 状态栏和导航栏透明设置
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        initView();
        initEven();
        initData();
        initEvent();
    }

    public abstract @LayoutRes
    int getLayoutId();

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract void initData();

    protected void initEven() {
        mPresenter = PMUtil.getT(this, 0);
        if (this instanceof BaseEven) {
            mPresenter.setMV(this);
        }
    }

    /**
     * 简单页面跳转
     *
     * @param activityClass 目标activity
     */
    public void jumpActivity(Class<? extends Activity> activityClass) {
        startActivity(new Intent(this, activityClass));
    }
}
