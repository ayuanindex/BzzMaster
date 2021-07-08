package com.shenkong.bzzmaster.ui.fragment.home.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.model.bean.BannerBean;
import com.shenkong.bzzmaster.ui.fragment.home.HomeBannerFragment;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeBannerViewHolder extends MultipleAdapter.MultipleBaseViewHolder {
    public View rootView;
    public ViewPager2 bannerPager;
    public TabLayout tabLayout;
    private MultipleAdapter multipleAdapter;
    private BannerBean bannerBean;
    private static final MutableLiveData<Integer> itemPosition = new MutableLiveData<>(0);
    private static Timer bannerTimer;
    private static TimerTask bannerTimerTask;

    public HomeBannerViewHolder(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.bannerPager = rootView.findViewById(R.id.bannerPager);
        this.tabLayout = rootView.findViewById(R.id.tabLayout);
    }

    @Override
    public void load(MultipleAdapter multipleAdapter, int position) {
        this.multipleAdapter = multipleAdapter;
        bannerBean = (BannerBean) multipleAdapter.getBean(position);

        ArrayList<Fragment> fragments = new ArrayList<>();
        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(multipleAdapter.getFragmentActivity(), fragments);
        bannerPager.setAdapter(bannerPagerAdapter);

        for (int imgResource : bannerBean.getImgResources()) {
            fragments.add(new HomeBannerFragment(imgResource));
        }

        bannerPagerAdapter.notifyDataSetChanged();
        new TabLayoutMediator(tabLayout, bannerPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            }
        }).attach();

        // 订阅数据变化
        itemPosition.observe(multipleAdapter.getFragmentActivity(), (Integer item) -> {
            bannerPager.setCurrentItem(item);
        });

        startBannerLooper(bannerPagerAdapter.getItemCount());
    }


    /**
     * 开启广告循环切换定时器
     *
     * @param itemCount 广告总条数
     */
    private void startBannerLooper(int itemCount) {
        if (bannerTimer != null) {
            bannerTimer.cancel();
            bannerTimer = null;
        }

        if (bannerTimerTask != null) {
            bannerTimerTask.cancel();
            bannerTimerTask = null;
        }

        bannerTimer = new Timer();
        bannerTimerTask = new TimerTask() {
            @Override
            public void run() {
                Integer value = itemPosition.getValue();
                value += 1;

                if (value > itemCount) {
                    itemPosition.postValue(0);
                } else {
                    itemPosition.postValue(value);
                }
            }
        };
        bannerTimer.schedule(bannerTimerTask, 5000, 5000);
    }

    static class BannerPagerAdapter extends FragmentStateAdapter {

        private final List<Fragment> fragments;

        public BannerPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
            super(fragmentActivity);
            this.fragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }

    }
}
