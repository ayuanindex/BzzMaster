package com.shenkong.bzzmaster.ui.activity.transfer;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.CapitalBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.CapitalService;
import com.shenkong.bzzmaster.net.api.ProductService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import io.reactivex.functions.Consumer;
import retrofit2.http.POST;

public class TransferPresent extends BasePresenter<TransferEvent> {
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private MutableLiveData<List<ProductBean>> productBeanListLiveData;
    private MutableLiveData<List<CapitalBean>> capitalBeanListLiveData;

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<List<ProductBean>> getProductBeanListLiveData() {
        return productBeanListLiveData;
    }

    public void setProductBeanListLiveData(MutableLiveData<List<ProductBean>> productBeanListLiveData) {
        this.productBeanListLiveData = productBeanListLiveData;
    }

    public MutableLiveData<List<CapitalBean>> getCapitalBeanListLiveData() {
        return capitalBeanListLiveData;
    }

    public void setCapitalBeanListLiveData(MutableLiveData<List<CapitalBean>> capitalBeanListLiveData) {
        this.capitalBeanListLiveData = capitalBeanListLiveData;
    }

    public void confirmTransfer(String address, String amountOfMoney) {
        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(amountOfMoney)) {
            mView.showToastMsg("请输入完整信息", 0);
            return;
        }

        double doubleAmountOfMoney = Double.parseDouble(amountOfMoney);
        if (doubleAmountOfMoney <= 0) {
            mView.showToastMsg("转账金额必须大于0", 0);
            return;
        }

        mView.showConfirmDialog(address, doubleAmountOfMoney);
    }

    public void requestAllProduct() {
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(ProductService.class).requestAllProduct(), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<ProductBean>>>() {
                    @Override
                    public void accept(ResultBean<List<ProductBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            productBeanListLiveData.postValue(listResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }

    /**
     * 查询指定币种的余额
     *
     * @param position 选中的币种下标
     */
    public void requestBalance(int position) {
        if (productBeanListLiveData.getValue() == null) {
            return;
        }

        ProductBean productBean = productBeanListLiveData.getValue().get(position);
        CapitalBean capitalBean = new CapitalBean();
        capitalBean.setPid(productBean.getProductid());
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(CapitalService.class).requestBalance(capitalBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<CapitalBean>>>() {
                    @Override
                    public void accept(ResultBean<List<CapitalBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            listResultBean.getDate().get(0).setName(productBean.getCurrency());
                            capitalBeanListLiveData.postValue(listResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }
}
