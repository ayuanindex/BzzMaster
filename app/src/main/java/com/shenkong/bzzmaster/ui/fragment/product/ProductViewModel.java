package com.shenkong.bzzmaster.ui.fragment.product;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.shenkong.bzzmaster.App;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.ProductService;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.http.POST;

public class ProductViewModel extends BaseViewMode<ProductEvent> {
    private static final String TAG = "ProductViewModel";
    private final HashMap<String, List<ProductBean>> productMap = new HashMap<>();
    private MutableLiveData<List<ProductBean>> productList;
    private MutableLiveData<List<ProductPlanBean>> productPlan;
    private Timer timer;
    private TimerTask timerTask;

    /**
     * @return 产品列表集合
     */
    public MutableLiveData<List<ProductBean>> getProductList() {
        return productList;
    }

    /**
     * @param productList 设置数据
     */
    public void setProductList(MutableLiveData<List<ProductBean>> productList) {
        this.productList = productList;
    }

    /**
     * @return 返回产品计划列表
     */
    public MutableLiveData<List<ProductPlanBean>> getProductPlan() {
        return productPlan;
    }

    /**
     * @param productPlan 初始化产品计划列表
     */
    public void setProductPlan(MutableLiveData<List<ProductPlanBean>> productPlan) {
        this.productPlan = productPlan;
    }

    public void initProduct(LifecycleProvider<FragmentEvent> lifecycleProvider) {
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(ProductService.class).requestAllProduct(), lifecycleProvider)
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

    public void initProductData(ProductBean productBean, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        Observable<ResultBean<List<ProductPlanBean>>> observefg = ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(ProductService.class).requestProductPlan(productBean), lifecycleProvider);
        observefg.subscribe(new Observer<ResultBean<List<ProductPlanBean>>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ResultBean<List<ProductPlanBean>> listResultBean) {
                if (listResultBean.getCode() == 200) {
                    productPlan.postValue(listResultBean.getDate());
                } else {
                    uiRefreshCallBack.hideLoading();
                }
                LoggerUtils.d(TAG, listResultBean.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LoggerUtils.d(TAG, "网络出现问题" + e.getMessage());
                uiRefreshCallBack.hideLoading();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void startSingleTimerTask(TimerTask task) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        timer = new Timer();
        this.timerTask = task;
        timer.schedule(timerTask, 1000);
    }
}
