package com.shenkong.bzzmaster.ui.activity.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.AsyncDifferConfig;

import com.shenkong.bzzmaster.BuildConfig;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.AppUpdateBean;
import com.shenkong.bzzmaster.model.bean.EditionDTO;
import com.shenkong.bzzmaster.model.bean.NoticeBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.AppService;
import com.shenkong.bzzmaster.net.api.NoticeService;
import com.shenkong.bzzmaster.ui.fragment.home.HomeFragment;
import com.shenkong.bzzmaster.ui.fragment.mine.MineFragment;
import com.shenkong.bzzmaster.ui.fragment.product.ProductFragment;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainPresenter extends BasePresenter<MainEvent> {

    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private MutableLiveData<List<NoticeBean>> noticeListLiveData;
    private Disposable noticeSubscribe;

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<List<NoticeBean>> getNoticeListLiveData() {
        return noticeListLiveData;
    }

    public void setNoticeListLiveData(MutableLiveData<List<NoticeBean>> noticeListLiveData) {
        this.noticeListLiveData = noticeListLiveData;
    }

    public void checkAppWhetherUpdate(int versionCode) {
        LoggerUtils.d(TAG, "更细接口：" + versionCode);
        EditionDTO editionDTO = new EditionDTO();
        editionDTO.setEdition(versionCode);
        editionDTO.setFirm(BuildConfig.firm);
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(AppService.class).resultCheckAppUpdate(editionDTO), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<AppUpdateBean>>() {
                    @Override
                    public void accept(ResultBean<AppUpdateBean> appUpdateBeanResultBean) throws Exception {
                        if (appUpdateBeanResultBean.getCode() == 200) {
                            if (appUpdateBeanResultBean.getDate() != null) {
                                mView.showUpdateDialog(appUpdateBeanResultBean.getDate());
                            }
                        }
                        LoggerUtils.d(TAG, "更细接口", appUpdateBeanResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }

    public void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>(4);
        fragments.add(HomeFragment.getInstance());
        fragments.add(ProductFragment.getInstance());
        // fragments.add(InvitationFragment.getInstance());
        fragments.add(MineFragment.getInstance());

        mView.updateViewPagerAdapter(fragments);
    }

    public void requestNotice() {
        if (noticeSubscribe != null && !noticeSubscribe.isDisposed()) {
            return;
        }

        noticeSubscribe = ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(NoticeService.class).requestNotices(), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<NoticeBean>>>() {
                    @Override
                    public void accept(ResultBean<List<NoticeBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            noticeListLiveData.postValue(listResultBean.getDate());
                        } else {
                            noticeListLiveData.postValue(new ArrayList<>());
                        }
                        LoggerUtils.d(TAG, "公告" + listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }
}
