package com.shenkong.bzzmaster.ui.activity.currencyinfo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.DetailBean;
import com.shenkong.bzzmaster.model.bean.DetailVo;
import com.shenkong.bzzmaster.model.bean.FrontPage;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.ShouZhiDetailService;
import com.shenkong.bzzmaster.ui.fragment.mine.MineViewModel;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CurrencyInfoPresenter extends BasePresenter<CurrencyInfoEvent> {

    private CurrencyInfoActivity currencyInfoActivity;
    private MutableLiveData<DetailBean> detailLiveData;
    private Disposable shouZhiSubscribe;

    public CurrencyInfoPresenter() {
    }

    public void bind(CurrencyInfoActivity currencyInfoActivity) {
        this.currencyInfoActivity = currencyInfoActivity;

        detailLiveData = new MutableLiveData<>();

        initDataSubscribe();
    }

    /**
     * 数据订阅
     */
    private void initDataSubscribe() {
        detailLiveData.observe(currencyInfoActivity, detailBeans -> {
            mView.updateRecycerViewDatas(detailBeans);
        });
    }

    public void requestProductShouZhiDetail(long productid, int type) {
        if (shouZhiSubscribe != null && !shouZhiSubscribe.isDisposed()) {
            shouZhiSubscribe.dispose();
            shouZhiSubscribe = null;
        }

        DetailVo t = new DetailVo();
        t.setProductid((int) productid);
        t.setType(type);

        FrontPage<DetailVo> frontPage = new FrontPage<>();
        frontPage.setRows(20);
        frontPage.setPage(1);
        frontPage.setT(t);
        shouZhiSubscribe = ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(ShouZhiDetailService.class).requestShouZhiDetailByProduct(frontPage), currencyInfoActivity)
                .subscribe(new Consumer<ResultBean<DetailBean>>() {
                    @Override
                    public void accept(ResultBean<DetailBean> detailBeanResultBean) throws Exception {
                        if (detailBeanResultBean.getCode() == 200) {
                            detailLiveData.postValue(detailBeanResultBean.getDate());
                        } else {
                            detailLiveData.postValue(new DetailBean());
                        }
                        mView.hideLoading();
                        LoggerUtils.d(TAG, detailBeanResultBean.toString());
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
}
