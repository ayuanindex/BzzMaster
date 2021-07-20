package com.shenkong.bzzmaster.ui.activity.shouzhi;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.DetailBean;
import com.shenkong.bzzmaster.model.bean.DetailVo;
import com.shenkong.bzzmaster.model.bean.FrontPage;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.ShouZhiDetailService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ShouZhiPresent extends BasePresenter<ShouZhiEvent> {
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private MutableLiveData<DetailBean> detailBeanLiveData;
    private Disposable shouZhiSubscribe;
    private int page = 1;
    private int transactionPage = 1;
    private int applyPage = 1;
    private int revenuesPage = 1;
    private DetailVo t = new DetailVo();

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public int getTransactionPage() {
        return transactionPage;
    }

    public void setTransactionPage(int transactionPage) {
        this.transactionPage = transactionPage;
    }

    public int getApplyPage() {
        return applyPage;
    }

    public void setApplyPage(int applyPage) {
        this.applyPage = applyPage;
    }

    public int getRevenuesPage() {
        return revenuesPage;
    }

    public void setRevenuesPage(int revenuesPage) {
        this.revenuesPage = revenuesPage;
    }

    public void setDetailVoType(int type) {
        t.setType(type);
    }

    public MutableLiveData<DetailBean> getDetailBeanLiveData() {
        return detailBeanLiveData;
    }

    public void setDetailBeanLiveData(MutableLiveData<DetailBean> detailBeanLiveData) {
        this.detailBeanLiveData = detailBeanLiveData;
    }

    /**
     * 查询收支明细
     */
    public void requestShouZhi() {
        if (shouZhiSubscribe != null && !shouZhiSubscribe.isDisposed()) {
            shouZhiSubscribe.dispose();
            shouZhiSubscribe = null;
        }

        int type = t.getType();
        LoggerUtils.d(TAG, "记载了" + (type == 1 ? "充值" : (type == 2 ? "提币" : "收益")) + "的第" + page + "页数据");

        FrontPage<DetailVo> frontPage = new FrontPage<DetailVo>();
        frontPage.setPage(page);
        frontPage.setRows(2);
        frontPage.setT(t);
        shouZhiSubscribe = ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(ShouZhiDetailService.class).requestShouZhiDetail(frontPage), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<DetailBean>>() {
                    @Override
                    public void accept(ResultBean<DetailBean> detailBeanResultBean) throws Exception {
                        if (detailBeanResultBean.getCode() == 200) {
                            // TODO: 2021/7/20
                            detailBeanLiveData.postValue(detailBeanResultBean.getDate());
                        } else {
                            detailBeanLiveData.postValue(new DetailBean());
                            mView.hideLoading();
                        }
                        LoggerUtils.d(TAG, "收支:" + detailBeanResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                        throwable.printStackTrace();
                    }
                });
    }
}
