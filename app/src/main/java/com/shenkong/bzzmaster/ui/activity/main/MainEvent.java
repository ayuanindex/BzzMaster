package com.shenkong.bzzmaster.ui.activity.main;

import androidx.fragment.app.Fragment;

import com.shenkong.bzzmaster.event.BaseEven;

import java.util.ArrayList;

public interface MainEvent extends BaseEven {
    void updateViewPagerAdapter(ArrayList<Fragment> fragments);
}
