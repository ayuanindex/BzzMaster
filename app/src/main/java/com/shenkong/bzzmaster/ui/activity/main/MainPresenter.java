package com.shenkong.bzzmaster.ui.activity.main;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.ui.fragment.home.HomeFragment;
import com.shenkong.bzzmaster.ui.fragment.invitation.InvitationFragment;
import com.shenkong.bzzmaster.ui.fragment.mine.MineFragment;
import com.shenkong.bzzmaster.ui.fragment.product.ProductFragment;

import java.util.ArrayList;

public class MainPresenter extends BasePresenter<MainEvent> {

    public void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>(4);
        fragments.add(HomeFragment.getInstance());
        fragments.add(ProductFragment.getInstance());
        fragments.add(InvitationFragment.getInstance());
        fragments.add(MineFragment.getInstance());

        mView.updateViewPagerAdapter(fragments);
    }
}
