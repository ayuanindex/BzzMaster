package com.shenkong.bzzmaster.ui.fragment.home;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.BannerBean;
import com.shenkong.bzzmaster.model.bean.CarouselBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.model.bean.ProfitBean;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.CarouselService;
import com.shenkong.bzzmaster.net.api.PlanService;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewMode<HomeEvent> {
    private static final String TAG = "HomeViewModel";
    private LifecycleProvider<FragmentEvent> lifecycleProvider;
    private MutableLiveData<BannerBean> bannerBeanData;
    private MutableLiveData<ProfitBean> profitBeanData;
    private MutableLiveData<List<MultipleAdapter.LayoutType>> productPlanList;

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

    public MutableLiveData<ProfitBean> getProfitBeanData() {
        return profitBeanData;
    }

    public void setProfitBeanData(MutableLiveData<ProfitBean> profitBeanData) {
        this.profitBeanData = profitBeanData;
    }

    public void initHomeBannerData() {
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(CarouselService.class).requestCarousel(), lifecycleProvider)
                .subscribe(new Observer<ResultBean<List<CarouselBean>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResultBean<List<CarouselBean>> listResultBean) {
                        if (listResultBean.getCode() == 200) {
                            if (bannerBeanData.getValue() != null) {
                                bannerBeanData.getValue().setCarouselBeanList(listResultBean.getDate());
                            }
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LoggerUtils.d(TAG, "请求出错", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void initHomeProfitData() {
        /*ProfitBean profitBean = new ProfitBean();
        uiRefreshCallBack.initProfitData(profitBean);*/
    }

    public void initHomeHotProductData() {
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(PlanService.class).requestHotProductPlan(), lifecycleProvider)
                .retry(3)
                .map(listResultBean -> {
                    ResultBean<List<MultipleAdapter.LayoutType>> resultBean = new ResultBean<>();
                    resultBean.setCode(listResultBean.getCode());
                    resultBean.setMsg(listResultBean.getMsg());
                    resultBean.setDate(new ArrayList<>(listResultBean.getDate()));
                    return resultBean;
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
