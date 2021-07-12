package com.shenkong.bzzmaster.ui.fragment.home;

import android.os.Build;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.BannerBean;
import com.shenkong.bzzmaster.model.bean.CarouselBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProfitBean;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.CarouselService;
import com.shenkong.bzzmaster.net.api.PlanService;
import com.shenkong.bzzmaster.net.api.ProductService;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class HomeViewModel extends BaseViewMode<HomeEvent> {
    private static final String TAG = "HomeViewModel";
    private LifecycleProvider<FragmentEvent> lifecycleProvider;
    private MutableLiveData<BannerBean> bannerBeanDataLiveData;
    private MutableLiveData<List<ProductBean>> productBeanListLiveData;
    private MutableLiveData<ProfitBean> profitBeanDataLiveData;
    private MutableLiveData<List<MultipleAdapter.LayoutType>> productPlanListLiveData;

    public void setLifecycleProvider(LifecycleProvider<FragmentEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<List<ProductBean>> getProductBeanListLiveData() {
        return productBeanListLiveData;
    }

    public void setProductBeanListLiveData(MutableLiveData<List<ProductBean>> productBeanListLiveData) {
        this.productBeanListLiveData = productBeanListLiveData;
    }

    public void setProductPlanListLiveData(MutableLiveData<List<MultipleAdapter.LayoutType>> productPlanListLiveData) {
        this.productPlanListLiveData = productPlanListLiveData;
    }

    public MutableLiveData<List<MultipleAdapter.LayoutType>> getProductPlanListLiveData() {
        return productPlanListLiveData;
    }

    public MutableLiveData<BannerBean> getBannerBeanDataLiveData() {
        return bannerBeanDataLiveData;
    }

    public void setBannerBeanDataLiveData(MutableLiveData<BannerBean> bannerBeanDataLiveData) {
        this.bannerBeanDataLiveData = bannerBeanDataLiveData;
    }

    public MutableLiveData<ProfitBean> getProfitBeanDataLiveData() {
        return profitBeanDataLiveData;
    }

    public void setProfitBeanDataLiveData(MutableLiveData<ProfitBean> profitBeanDataLiveData) {
        this.profitBeanDataLiveData = profitBeanDataLiveData;
    }

    public void initProduct() {
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(ProductService.class).requestAllProduct(), lifecycleProvider)
                .map(new Function<ResultBean<List<ProductBean>>, ResultBean<List<ProductBean>>>() {
                    @Override
                    public ResultBean<List<ProductBean>> apply(@NonNull ResultBean<List<ProductBean>> listResultBean) throws Exception {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            listResultBean.getDate().removeIf(new Predicate<ProductBean>() {
                                @Override
                                public boolean test(ProductBean productBean) {
                                    return productBean.getStaues() == 1;
                                }
                            });
                        } else {
                            List<ProductBean> date = listResultBean.getDate();
                            for (int i = 0; i < date.size(); i++) {
                                if (date.get(i).getStaues() == 1) {
                                    date.remove(date.get(i));
                                    i--;
                                }
                            }
                        }
                        return listResultBean;
                    }
                })
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
                        LoggerUtils.d(TAG, "网络请求发生错误，请检查网络链接是否正常", throwable.getMessage());
                    }
                });
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
                            if (bannerBeanDataLiveData.getValue() != null) {
                                bannerBeanDataLiveData.getValue().setCarouselBeanList(listResultBean.getDate());
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
                            productPlanListLiveData.postValue(listResultBean.getDate());
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
