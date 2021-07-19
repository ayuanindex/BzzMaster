package com.shenkong.bzzmaster.ui.fragment.mine;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.CapitalBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.CapitalService;
import com.shenkong.bzzmaster.net.api.ProductService;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MineViewModel extends BaseViewMode<MineEvent> {
    private static final String TAG = "MineViewModel";
    private LifecycleProvider<FragmentEvent> lifecycleProvider;
    private MutableLiveData<List<CapitalBean>> capitalBeanListLiveData;
    private MutableLiveData<List<ProductBean>> productBeanListLiveData;

    public void setLifecycleProvider(LifecycleProvider<FragmentEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<List<CapitalBean>> getCapitalBeanListLiveData() {
        return capitalBeanListLiveData;
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

    public synchronized void requestAllProduct() {
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(ProductService.class).requestAllProduct(), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<ProductBean>>>() {
                    @Override
                    public void accept(ResultBean<List<ProductBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            productBeanListLiveData.postValue(listResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, throwable -> LoggerUtils.d(TAG, "请求出现问题", throwable.getMessage()));
    }

    public synchronized void requestAllBalance(List<ProductBean> productBeanList) {
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(CapitalService.class).requestAllBalance(new CapitalBean()), lifecycleProvider)
                .map(new Function<ResultBean<List<CapitalBean>>, ResultBean<List<CapitalBean>>>() {
                    @Override
                    public ResultBean<List<CapitalBean>> apply(@NonNull ResultBean<List<CapitalBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            for (CapitalBean capitalBean : listResultBean.getDate()) {
                                for (ProductBean productBean : productBeanList) {
                                    if (productBean.getProductid() == capitalBean.getPid()) {
                                        capitalBean.setName(productBean.getName());
                                        capitalBean.setCurrency(productBean.getCurrency());
                                    }
                                }
                            }
                        }
                        return listResultBean;
                    }
                })
                .subscribe(new Consumer<ResultBean<List<CapitalBean>>>() {
                    @Override
                    public void accept(ResultBean<List<CapitalBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            capitalBeanListLiveData.postValue(listResultBean.getDate());
                        }/* else {
                            capitalBeanListLiveData.postValue(new ArrayList<>());
                        }*/
                        LoggerUtils.d(TAG, "余额", listResultBean.toString());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    LoggerUtils.d(TAG, "网络请求出现错误", throwable.getMessage());
                });
    }
}
