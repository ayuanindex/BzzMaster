package com.shenkong.bzzmaster.ui.fragment.mine;

import com.blankj.utilcode.util.Utils;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.model.bean.CapitalBean;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.CapitalService;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.List;

import io.reactivex.functions.Consumer;

public class MineViewModel extends BaseViewMode<MineEvent> {
    private static final String TAG = "MineViewModel";
    private LifecycleProvider<FragmentEvent> lifecycleProvider;

    public void setLifecycleProvider(LifecycleProvider<FragmentEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public void requestBalance() {
        LoggerUtils.d(TAG, SpUtil.getString(Utils.getApp(), SpUtil.token, ""), SpUtil.getString(Utils.getApp(), SpUtil.userId, "0"));
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(CapitalService.class).requestBalance(new CapitalBean()), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<CapitalBean>>>() {
                    @Override
                    public void accept(ResultBean<List<CapitalBean>> listResultBean) throws Exception {
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "网络请求出现问题", throwable.getMessage());
                    }
                });
    }

    public void requestAllBalance() {
        LoggerUtils.d(TAG, SpUtil.getString(Utils.getApp(), SpUtil.token, ""), SpUtil.getString(Utils.getApp(), SpUtil.userId, "0"));
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(CapitalService.class).requestAllBalance(new CapitalBean()), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<CapitalBean>>>() {
                    @Override
                    public void accept(ResultBean<List<CapitalBean>> listResultBean) throws Exception {
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, throwable -> LoggerUtils.d(TAG, "网络请求出现错误", throwable.getMessage()));
    }
}
