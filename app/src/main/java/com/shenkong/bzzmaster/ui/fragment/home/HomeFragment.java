package com.shenkong.bzzmaster.ui.fragment.home;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.homebanner.HomeBannerFragment;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {
    private ViewPager2 viewPager2;
    private ArrayList<Fragment> fragments;
    private TabLayout tabLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View inflate) {
        viewPager2 = (ViewPager2) inflate.findViewById(R.id.viewPager2);
        tabLayout = (TabLayout) inflate.findViewById(R.id.tabLayout);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
        // testCode
        fragments = new ArrayList<>();
        viewPager2AddFragment(new HomeBannerFragment(R.drawable.img_banner_1));
        viewPager2AddFragment(new HomeBannerFragment(R.drawable.img_banner_swarm));

        BannerAdapter bannerAdapter = new BannerAdapter(requireActivity());
        bannerAdapter.setFragments(fragments);
        viewPager2.setAdapter(bannerAdapter);

        initTabLayout();
    }

    private void initTabLayout() {
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
        }).attach();
    }

    private void viewPager2AddFragment(HomeBannerFragment e) {
        fragments.add(e);
        TabLayout.Tab tab = tabLayout.newTab();
        tabLayout.addTab(tab);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
