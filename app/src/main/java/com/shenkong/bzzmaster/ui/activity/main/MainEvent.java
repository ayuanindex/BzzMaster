package com.shenkong.bzzmaster.ui.activity.main;

import androidx.fragment.app.Fragment;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.bean.AppUpdateBean;

import java.util.ArrayList;

public interface MainEvent extends BaseEven {
    void updateViewPagerAdapter(ArrayList<Fragment> fragments);

    /**
     * 显示更新弹窗
     *
     * @param appUpdateBean
     */
    void showUpdateDialog(AppUpdateBean appUpdateBean);

}
