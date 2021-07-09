package com.shenkong.bzzmaster.ui.activity.orders;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.ProductService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class OrderPresenter extends BasePresenter<OrderEvent> {
    private static final String TAG = "OrderPresenter";
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private MutableLiveData<List<ProductBean>> productList;

    public MutableLiveData<List<ProductBean>> getProductList() {
        return productList;
    }

    public void setProductList(MutableLiveData<List<ProductBean>> productList) {
        this.productList = productList;
    }

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public void initProductCategory() {
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(ProductService.class).requestAllProduct(), lifecycleProvider)
                .retry(3)
                .subscribe(new Observer<ResultBean<List<ProductBean>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResultBean<List<ProductBean>> listResultBean) {
                        if (listResultBean.getCode() == 200) {
                            productList.postValue(listResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LoggerUtils.d(TAG, "网络请求发生错误，请检查网络链接是否正常");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
