package com.shenkong.bzzmaster.ui.activity.submitOrder.blend;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.Utils;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.model.bean.CapitalBean;
import com.shenkong.bzzmaster.model.bean.OrderBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.CapitalService;
import com.shenkong.bzzmaster.net.api.OrderService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;

public class SubmitMixedOrderPresenter extends BasePresenter<SubmitMixedOrderEvent> {
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private MutableLiveData<List<CapitalBean>> capitalBeanListLiveData;
    private MutableLiveData<List<CapitalBean>> capitalBeanUSDTListLiveData;
    private MutableLiveData<List<ProductBean>> productBeanListLiveData;

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<List<CapitalBean>> getCapitalBeanListLiveData() {
        return capitalBeanListLiveData;
    }

    public CapitalBean getCapitalBean() {
        if (capitalBeanListLiveData.getValue() != null) {
            return capitalBeanListLiveData.getValue().get(0);
        }
        return null;
    }

    public CapitalBean getUSDTCapitalBean() {
        if (capitalBeanUSDTListLiveData.getValue() != null) {
            return capitalBeanUSDTListLiveData.getValue().get(0);
        }
        return null;
    }

    public void setCapitalBeanListLiveData(MutableLiveData<List<CapitalBean>> capitalBeanListLiveData) {
        this.capitalBeanListLiveData = capitalBeanListLiveData;
    }

    public MutableLiveData<List<ProductBean>> getProductBeanListLiveData() {
        return productBeanListLiveData;
    }

    public void setProductBeanListLiveData(MutableLiveData<List<ProductBean>> productBeanListLiveData) {
        this.productBeanListLiveData = productBeanListLiveData;
    }

    public MutableLiveData<List<CapitalBean>> getCapitalBeanUSDTListLiveData() {
        return capitalBeanUSDTListLiveData;
    }

    public void setCapitalBeanUSDTListLiveData(MutableLiveData<List<CapitalBean>> capitalBeanUSDTListLiveData) {
        this.capitalBeanUSDTListLiveData = capitalBeanUSDTListLiveData;
    }

    public void selectUSDTBalance() {
        CapitalBean capitalBean = new CapitalBean();
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(CapitalService.class).requestBalance(capitalBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<CapitalBean>>>() {
                    @Override
                    public void accept(ResultBean<List<CapitalBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            capitalBeanUSDTListLiveData.postValue(listResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }

    public void selectBalanceByProductId(Long productId) {
        CapitalBean capitalBean = new CapitalBean();
        capitalBean.setPid(productId);
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(CapitalService.class).requestBalance(capitalBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<CapitalBean>>>() {
                    @Override
                    public void accept(ResultBean<List<CapitalBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            capitalBeanListLiveData.postValue(listResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }

    public void requestAddOrder(ProductPlanBean productPlanBean, double needPrice, long count) {
        OrderBean orderBean = new OrderBean();
        long uid = Long.parseLong(SpUtil.getString(Utils.getApp(), SpUtil.userId, "0"));
        LoggerUtils.d(TAG, "用户ID" + uid);
        orderBean.setOrderid(Formatter.generateNumberString(new Date()));
        orderBean.setUid(uid);
        orderBean.setPid(productPlanBean.getPlanid());
        orderBean.setNumber(count);
        orderBean.setAmount(needPrice);
        orderBean.setPname(productPlanBean.getName());
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(OrderService.class).requestAddOrder(orderBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<Boolean>>() {
                    @Override
                    public void accept(ResultBean<Boolean> booleanResultBean) throws Exception {
                        if (booleanResultBean.getCode() == 200) {
                            mView.setAddOrderStatus(booleanResultBean.getDate());
                        } else {
                            mView.setAddOrderStatus(false);
                        }
                        mView.showToastMsg(booleanResultBean.getMsg(), 0);
                        LoggerUtils.d(TAG, booleanResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                        mView.setAddOrderStatus(false);
                    }
                });
    }
}
