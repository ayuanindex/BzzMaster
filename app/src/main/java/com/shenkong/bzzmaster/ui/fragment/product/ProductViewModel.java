package com.shenkong.bzzmaster.ui.fragment.product;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.ProductService;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ProductViewModel extends BaseViewMode<ProductEvent> {
    private static final String TAG = "ProductViewModel";
    private final HashMap<String, List<ProductBean>> productMap = new HashMap<>();

    private MutableLiveData<List<ProductBean>> productList;
    private MutableLiveData<List<ProductPlanBean>> productPlan;
    private Timer timer;
    private TimerTask timerTask;
    private Disposable productPlanSubscribe;

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
                .subscribe(new Consumer<ResultBean<List<ProductBean>>>() {
                    @Override
                    public void accept(ResultBean<List<ProductBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            productList.postValue(listResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "网络请求发生错误，请检查网络链接是否正常", throwable.getMessage());
                    }
                });
    }

    public void initProductPlanData(ProductBean productBean, LifecycleProvider<FragmentEvent> lifecycleProvider) {
        if (productPlanSubscribe != null && !productPlanSubscribe.isDisposed()) {
            productPlanSubscribe.dispose();
            productPlanSubscribe = null;
        }

        productPlanSubscribe = ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(ProductService.class).requestProductPlan(productBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<ProductPlanBean>>>() {
                    @Override
                    public void accept(ResultBean<List<ProductPlanBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            productPlan.postValue(listResultBean.getDate());
                        } else {
                            productPlan.postValue(new ArrayList<>());
                            uiRefreshCallBack.hideLoading();
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "网络出现问题" + throwable.getMessage());
                        productPlan.postValue(new ArrayList<>());
                        uiRefreshCallBack.hideLoading();
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
