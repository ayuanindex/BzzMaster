package com.shenkong.bzzmaster.ui.activity.plan;

import android.app.Notification;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.AssetsBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.AssetsService;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class PlanPresenter extends BasePresenter<PlanEvent> {
    private static final String TAG = "PlanPresenter";
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private MutableLiveData<Boolean> booleanLiveData;
    private Disposable assetsSubscribe;

    public PlanPresenter() {
        booleanLiveData = new MutableLiveData<>();
    }

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<Boolean> getBooleanLiveData() {
        return booleanLiveData;
    }

    public void requestAssets() {
        if (assetsSubscribe != null && !assetsSubscribe.isDisposed()) {
            return;
        }
        assetsSubscribe = ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(AssetsService.class).requestAssets(), lifecycleProvider)
                .map(new Function<ResultBean<List<AssetsBean>>, ResultBean<List<MultiLayoutAdapter.LayoutType>>>() {
                    @Override
                    public ResultBean<List<MultiLayoutAdapter.LayoutType>> apply(@NonNull ResultBean<List<AssetsBean>> listResultBean) throws Exception {
                        return new ResultBean<>(listResultBean.getCode(), listResultBean.getMsg(), new ArrayList<>(listResultBean.getDate()));
                    }
                })
                .subscribe(new Consumer<ResultBean<List<MultiLayoutAdapter.LayoutType>>>() {
                    @Override
                    public void accept(ResultBean<List<MultiLayoutAdapter.LayoutType>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            mView.updatePlanList(listResultBean.getDate());
                        } else {
                            mView.updatePlanList(new ArrayList<>());
                            mView.hideLoading();
                            mView.showEmptyView();
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        throwable.printStackTrace();
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }

    /**
     * 申请取消质押计划
     *
     * @param assetsBean 质押计划信息
     */
    public void requestCancelPledge(AssetsBean assetsBean) {
        mView.showLoading();
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(AssetsService.class).requestCancel(assetsBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<Boolean>>() {
                    @Override
                    public void accept(ResultBean<Boolean> booleanResultBean) throws Exception {
                        if (booleanResultBean.getCode() == 200) {
                            getBooleanLiveData().postValue(true);
                        }
                        mView.showToastMsg(booleanResultBean.getMsg(), 0);
                        mView.hideLoading();
                        LoggerUtils.d(TAG, booleanResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showToastMsg("请求出错", 0);
                        mView.hideLoading();
                        throwable.printStackTrace();
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }
}
