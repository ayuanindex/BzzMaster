package com.shenkong.bzzmaster.ui.fragment.wallet;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.CapitalBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.CapitalService;
import com.shenkong.bzzmaster.net.api.ProductService;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class WalletViewModel extends BaseViewMode<WalletEvent> {
    private WalletFragment walletFragment;
    private final MutableLiveData<List<CapitalBean>> capitalListLiveData;
    private final MutableLiveData<List<ProductBean>> productListLiveData;

    public WalletViewModel() {
        capitalListLiveData = new MutableLiveData<>();
        productListLiveData = new MutableLiveData<>();
    }

    public void bind(WalletFragment walletFragment) {
        this.walletFragment = walletFragment;
        setUiRefreshCallBack(walletFragment);

        initDataSubscribe();
    }

    /**
     * 设置数据订阅
     */
    private void initDataSubscribe() {
        this.capitalListLiveData.observe(walletFragment, capitalBeanList -> requestProducts());

        this.productListLiveData.observe(walletFragment, productBeanList -> {
            uiRefreshCallBack.setRecyclerViewData(new ArrayList<>(productBeanList));
        });
    }

    /**
     * 请求所有余额
     */
    public void requestAllBalance() {
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(CapitalService.class).requestAllBalance(new CapitalBean()), walletFragment)
                .subscribe(new Consumer<ResultBean<List<CapitalBean>>>() {
                    @Override
                    public void accept(ResultBean<List<CapitalBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            capitalListLiveData.postValue(listResultBean.getDate());
                        } else {
                            uiRefreshCallBack.hideLoading();
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        uiRefreshCallBack.hideLoading();
                        throwable.printStackTrace();
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }

    /**
     * 查询所有产品
     */
    public void requestProducts() {
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(ProductService.class).requestAllProduct(), walletFragment)
                .map(new Function<ResultBean<List<ProductBean>>, ResultBean<List<ProductBean>>>() {
                    @Override
                    public ResultBean<List<ProductBean>> apply(@NonNull ResultBean<List<ProductBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            for (ProductBean productBean : listResultBean.getDate()) {
                                for (CapitalBean capitalBean : capitalListLiveData.getValue()) {
                                    if (capitalBean.getPid() == productBean.getProductid()) {
                                        productBean.setCapitalBean(capitalBean);
                                    }
                                }
                            }

                            Collections.sort(listResultBean.getDate(), new Comparator<ProductBean>() {
                                @Override
                                public int compare(ProductBean o1, ProductBean o2) {
                                    return (int) (o1.getTop() - o2.getTop());
                                }
                            });
                        }
                        return listResultBean;
                    }
                })
                .subscribe(new Consumer<ResultBean<List<ProductBean>>>() {
                    @Override
                    public void accept(ResultBean<List<ProductBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            productListLiveData.postValue(listResultBean.getDate());
                        } else {
                            uiRefreshCallBack.hideLoading();
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        uiRefreshCallBack.hideLoading();
                        throwable.printStackTrace();
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }
}
