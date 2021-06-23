package com.shenkong.bzzmaster.ui.fragment.product;

import android.view.View;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseFragment;

public class ProductFragment extends BaseFragment {
    public static ProductFragment productFragment;

    public static ProductFragment getInstance() {
        if (productFragment == null) {
            synchronized (ProductFragment.class) {
                if (productFragment == null) {
                    productFragment = new ProductFragment();
                }
            }
        }
        return productFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_product;
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
