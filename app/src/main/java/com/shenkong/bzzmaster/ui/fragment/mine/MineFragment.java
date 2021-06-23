package com.shenkong.bzzmaster.ui.fragment.mine;

import android.view.View;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.home.HomeFragment;

public class MineFragment extends BaseFragment {
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

    }
}
