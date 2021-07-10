package com.shenkong.bzzmaster.ui.activity.main;

import androidx.fragment.app.Fragment;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.ApkVersionInfoUtil;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.AppUpdateBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.AppService;
import com.shenkong.bzzmaster.ui.fragment.home.HomeFragment;
import com.shenkong.bzzmaster.ui.fragment.invitation.InvitationFragment;
import com.shenkong.bzzmaster.ui.fragment.mine.MineFragment;
import com.shenkong.bzzmaster.ui.fragment.product.ProductFragment;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainPresenter extends BasePresenter<MainEvent> {

    private LifecycleProvider<ActivityEvent> lifecycleProvider;

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public void checkAppWhetherUpdate(int versionCode) {
        AppUpdateBean appUpdateBean = new AppUpdateBean();
        appUpdateBean.setEdition(versionCode);
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(AppService.class).resultCheckAppUpdate(appUpdateBean), lifecycleProvider)
                .subscribe(new Observer<ResultBean<AppUpdateBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResultBean<AppUpdateBean> appUpdateBeanResultBean) {
                        if (appUpdateBeanResultBean.getCode() == 401) {
                            mView.showUpdateDialog(appUpdateBeanResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, appUpdateBeanResultBean.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LoggerUtils.d(TAG, "出现错误", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>(4);
        fragments.add(HomeFragment.getInstance());
        fragments.add(ProductFragment.getInstance());
        fragments.add(InvitationFragment.getInstance());
        fragments.add(MineFragment.getInstance());

        mView.updateViewPagerAdapter(fragments);
    }
}
