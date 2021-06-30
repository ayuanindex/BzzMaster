package com.shenkong.bzzmaster.ui.fragment.mine;

import android.view.View;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseFragment;

public class MineFragment extends BaseFragment<MineViewModel, MineEvent> implements MineEvent{
    public static MineFragment mineFragment;

    public static MineFragment getInstance() {
        if (mineFragment == null) {
            synchronized (MineFragment.class) {
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
            }
        }
        return mineFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View inflate) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        initViewModel(MineViewModel.class);
        customerViewModel.setUiRefreshCallBack(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToastMsg(String msg, int type) {

    }
}
