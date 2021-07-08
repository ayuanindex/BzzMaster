package com.shenkong.bzzmaster.ui.fragment.home;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.BannerBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.model.bean.ProfitBean;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.PlanService;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class HomeViewModel extends BaseViewMode<HomeEvent> {
    private static final String TAG = "HomeViewModel";
    private LifecycleProvider<FragmentEvent> lifecycleProvider;
    private MutableLiveData<List<MultipleAdapter.LayoutType>> productPlanList;

    private MutableLiveData<BannerBean> bannerBeanData;

    public void setLifecycleProvider(LifecycleProvider<FragmentEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public void setProductPlanList(MutableLiveData<List<MultipleAdapter.LayoutType>> productPlanList) {
        this.productPlanList = productPlanList;
    }

    public MutableLiveData<List<MultipleAdapter.LayoutType>> getProductPlanList() {
        return productPlanList;
    }

    public MutableLiveData<BannerBean> getBannerBeanData() {
        return bannerBeanData;
    }

    public void setBannerBeanData(MutableLiveData<BannerBean> bannerBeanData) {
        this.bannerBeanData = bannerBeanData;
    }

    public void initHomeBannerData() {
        BannerBean bannerBean = new BannerBean(R.drawable.img_banner_1,
                R.drawable.img_banner_1,
                R.drawable.img_banner_swarm
        );
        uiRefreshCallBack.initHomeBannerData(bannerBean);
    }

    public void initHomeProfitData() {
        ProfitBean profitBean = new ProfitBean();
        uiRefreshCallBack.initProfitData(profitBean);
    }

    public void initHomeHotProductData() {
        new Thread(() -> {
            ArrayList<MultipleAdapter.LayoutType> productPlanBeanList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                ProductPlanBean e = new ProductPlanBean();
                e.setName("Chia早期矿工满存挖矿计划" + i);
                productPlanBeanList.add(e);
            }
            uiRefreshCallBack.initHotProductData(productPlanBeanList);
        }).start();

        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(PlanService.class).requestHotProductPlan(), lifecycleProvider)
                .retry(3)
                .map(new Function<ResultBean<List<ProductPlanBean>>, ResultBean<List<MultipleAdapter.LayoutType>>>() {
                    @Override
                    public ResultBean<List<MultipleAdapter.LayoutType>> apply(@NonNull ResultBean<List<ProductPlanBean>> listResultBean) throws Exception {
                        ResultBean<List<MultipleAdapter.LayoutType>> resultBean = new ResultBean<>();
                        resultBean.setCode(listResultBean.getCode());
                        resultBean.setMsg(listResultBean.getMsg());
                        resultBean.setDate(new ArrayList<>(listResultBean.getDate()));
                        return resultBean;
                    }
                })
                .subscribe(new Observer<ResultBean<List<MultipleAdapter.LayoutType>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResultBean<List<MultipleAdapter.LayoutType>> listResultBean) {
                        if (listResultBean.getCode() == 200) {
                            productPlanList.postValue(listResultBean.getDate());
                        } else {
                            LoggerUtils.d(TAG, "出现错误");
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LoggerUtils.d(TAG, "网络请求出现未知错误" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
