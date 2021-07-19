package com.shenkong.bzzmaster.ui.fragment.home;

import android.os.Build;
import android.view.LayoutInflater;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.AssetsBean;
import com.shenkong.bzzmaster.model.bean.BannerBean;
import com.shenkong.bzzmaster.model.bean.CarouselBean;
import com.shenkong.bzzmaster.model.bean.FrontPage;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.model.bean.ProfitBean;
import com.shenkong.bzzmaster.model.bean.RevenueBean;
import com.shenkong.bzzmaster.model.bean.RevenueListBean;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.AssetsService;
import com.shenkong.bzzmaster.net.api.CarouselService;
import com.shenkong.bzzmaster.net.api.PlanService;
import com.shenkong.bzzmaster.net.api.ProductService;
import com.shenkong.bzzmaster.net.api.RevenueService;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private MutableLiveData<List<ProductPlanBean>> productPlanBeanListLiveData;
    private MutableLiveData<ProfitBean> profitBeanDataLiveData;
    private MutableLiveData<List<AssetsBean>> assetsBeanListLiveData;
    private MutableLiveData<String> webDataLiveData;
    private MutableLiveData<String> webProfitDaysLiveData;
    private MutableLiveData<String> webProfitMoneyLiveData;
    private MutableLiveData<List<MultipleAdapter.LayoutType>> productPlanListLiveData;
    private Disposable profitSubscribe;
    private Disposable productSubscribe;
    private Disposable assetsSubscribe;
    private Disposable productPlanSubscribe;

    public void setLifecycleProvider(LifecycleProvider<FragmentEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<BannerBean> getBannerBeanDataLiveData() {
        return bannerBeanDataLiveData;
    }

    public void setBannerBeanDataLiveData(MutableLiveData<BannerBean> bannerBeanDataLiveData) {
        this.bannerBeanDataLiveData = bannerBeanDataLiveData;
    }

    public MutableLiveData<List<ProductBean>> getProductBeanListLiveData() {
        return productBeanListLiveData;
    }

    public void setProductBeanListLiveData(MutableLiveData<List<ProductBean>> productBeanListLiveData) {
        this.productBeanListLiveData = productBeanListLiveData;
    }

    public MutableLiveData<ProfitBean> getProfitBeanDataLiveData() {
        return profitBeanDataLiveData;
    }

    public void setProfitBeanDataLiveData(MutableLiveData<ProfitBean> profitBeanDataLiveData) {
        this.profitBeanDataLiveData = profitBeanDataLiveData;
    }

    public MutableLiveData<String> getWebDataLiveData() {
        return webDataLiveData;
    }

    public void setWebDataLiveData(MutableLiveData<String> webDataLiveData) {
        this.webDataLiveData = webDataLiveData;
    }

    public MutableLiveData<List<MultipleAdapter.LayoutType>> getProductPlanListLiveData() {
        return productPlanListLiveData;
    }

    public void setProductPlanListLiveData(MutableLiveData<List<MultipleAdapter.LayoutType>> productPlanListLiveData) {
        this.productPlanListLiveData = productPlanListLiveData;
    }

    public MutableLiveData<String> getWebProfitDaysLiveData() {
        return webProfitDaysLiveData;
    }

    public void setWebProfitDaysLiveData(MutableLiveData<String> webProfitDaysLiveData) {
        this.webProfitDaysLiveData = webProfitDaysLiveData;
    }

    public MutableLiveData<String> getWebProfitMoneyLiveData() {
        return webProfitMoneyLiveData;
    }

    public void setWebProfitMoneyLiveData(MutableLiveData<String> webProfitMoneyLiveData) {
        this.webProfitMoneyLiveData = webProfitMoneyLiveData;
    }

    public MutableLiveData<List<AssetsBean>> getAssetsBeanListLiveData() {
        return assetsBeanListLiveData;
    }

    public void setAssetsBeanListLiveData(MutableLiveData<List<AssetsBean>> assetsBeanListLiveData) {
        this.assetsBeanListLiveData = assetsBeanListLiveData;
    }

    public MutableLiveData<List<ProductPlanBean>> getProductPlanBeanListLiveData() {
        return productPlanBeanListLiveData;
    }

    public void setProductPlanBeanListLiveData(MutableLiveData<List<ProductPlanBean>> productPlanBeanListLiveData) {
        this.productPlanBeanListLiveData = productPlanBeanListLiveData;
    }

    /**
     * 查询所有产品
     */
    public synchronized void initProduct() {
        if (productSubscribe != null && !productSubscribe.isDisposed()) {
            return;
        }

        productSubscribe = ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(ProductService.class).requestAllProduct(), lifecycleProvider)
                .map(new Function<ResultBean<List<ProductBean>>, ResultBean<List<ProductBean>>>() {
                    @Override
                    public ResultBean<List<ProductBean>> apply(@NonNull ResultBean<List<ProductBean>> listResultBean) throws Exception {
                        // 只需拿着可以购买计划的产品
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
                            SharedBean.putData(SharedBean.Product, listResultBean.getDate());
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


    /**
     * 查询所有轮播图
     */
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

    /**
     * 收益请求
     */
    public synchronized void initHomeProfitData(long productId) {
        // 如果上一个请求没有完成，那就不让新的请求发生
        if (profitSubscribe != null && !profitSubscribe.isDisposed()) {
            return;
        }

        //LoggerUtils.d(TAG, "收益选择的产品为" + productId);
        // 用户收益记录
        FrontPage frontPage = new FrontPage();
        frontPage.setKeyvalue(productId);
        frontPage.setRows(7);
        frontPage.setPage(1);
        frontPage.setSidx("createtime");
        //排序方式 asc升序  desc降序
        frontPage.setSord("desc");
        profitSubscribe = ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(RevenueService.class).requestRevenueRecord(frontPage), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<RevenueListBean>>() {
                    @Override
                    public void accept(ResultBean<RevenueListBean> revenueListBeanResultBean) throws Exception {
                        if (revenueListBeanResultBean.getCode() == 200) {
                            // 处理好的图表数据
                            ArrayList<List<Object>> jsonLists = getJsonLists(revenueListBeanResultBean);

                            // 计算总收益
                            double money = 0;
                            ArrayList<RevenueBean> allGains = revenueListBeanResultBean.getDate().getAllGains();
                            for (RevenueBean allGain : allGains) {
                                money += allGain.getMoney();
                            }

                            webProfitDaysLiveData.postValue("");
                            webProfitMoneyLiveData.postValue(Formatter.numberFormat(money));
                            webDataLiveData.postValue(new Gson().toJson(jsonLists));
                            //LoggerUtils.d(TAG, money + "-------收益----" + revenueListBeanResultBean.toString());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }

    /**
     * 针对收益图表进行的数据转换
     */
    @NonNull
    private ArrayList<List<Object>> getJsonLists(@NonNull ResultBean<RevenueListBean> revenueListBeanResultBean) {
        // 床架当前日期
        Date currentDate = new Date();

        // 2021-6-15
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<RevenueBean> revenueDayLists = revenueListBeanResultBean.getDate().getRevenueDayLists();

        ArrayList<RevenueBean> revenueListModel = new ArrayList<>();
        RevenueBean bean = new RevenueBean();
        bean.setCreatedate(currentDate);
        bean.setMoney(0);
        revenueListModel.add(bean);
        // 填充6天前的模版数据
        for (int i = 1; i < 7; i++) {
            RevenueBean e = new RevenueBean();
            e.setCreatedate(Formatter.getTheDayBeforeDate(currentDate, i));
            e.setMoney(0);
            revenueListModel.add(0, e);
        }

        // 从拿到的数据匹配模版数据
        for (RevenueBean revenueDayList : revenueDayLists) {
            for (RevenueBean revenueBean : revenueListModel) {
                if (simpleDateFormat.format(revenueDayList.getCreatedate()).equals(simpleDateFormat.format(revenueBean.getCreatedate()))) {
                    revenueBean.setMoney(revenueDayList.getMoney());
                }
            }
        }

        // 包装成eCharts能够使用的数据
        ArrayList<List<Object>> lists = new ArrayList<>();
        for (RevenueBean revenueDayList : revenueListModel) {
            ArrayList<Object> key = new ArrayList<>();
            key.add(simpleDateFormat.format(revenueDayList.getCreatedate()));
            key.add(revenueDayList.getMoney());
            lists.add(key);
        }
        return lists;
    }

    /**
     * 查询热门产品计划
     */
    public void initHomeHotProductData() {
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(PlanService.class).requestHotProductPlan(), lifecycleProvider)
                .map(new Function<ResultBean<List<ProductPlanBean>>, ResultBean<List<MultipleAdapter.LayoutType>>>() {
                    @Override
                    public ResultBean<List<MultipleAdapter.LayoutType>> apply(@NonNull ResultBean<List<ProductPlanBean>> listResultBean) throws Exception {
                        ResultBean<List<MultipleAdapter.LayoutType>> resultBean = new ResultBean<>();
                        if (listResultBean.getDate() != null) {
                            resultBean.setCode(listResultBean.getCode());
                            resultBean.setMsg(listResultBean.getMsg());
                            resultBean.setDate(new ArrayList<>(listResultBean.getDate()));
                        }
                        return resultBean;
                    }
                })
                .subscribe(new Consumer<ResultBean<List<MultipleAdapter.LayoutType>>>() {
                    @Override
                    public void accept(ResultBean<List<MultipleAdapter.LayoutType>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            productPlanListLiveData.postValue(listResultBean.getDate());
                        } else {
                            LoggerUtils.d(TAG, "出现错误");
                            productPlanListLiveData.postValue(new ArrayList<>());
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
     * 根据产品ID获取产品计划
     *
     * @param productId 产品ID
     */
    public synchronized void initProductPlanData(int productId) {
        if (productPlanSubscribe != null && !productPlanSubscribe.isDisposed()) {
            productPlanSubscribe.dispose();
            productPlanSubscribe = null;
        }

        ProductBean productBean = new ProductBean();
        productBean.setProductid(productId);
        productPlanSubscribe = ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(ProductService.class).requestProductPlan(productBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<ProductPlanBean>>>() {
                    @Override
                    public void accept(ResultBean<List<ProductPlanBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            productPlanBeanListLiveData.postValue(listResultBean.getDate());
                        } else {
                            productPlanBeanListLiveData.postValue(new ArrayList<>());
                            uiRefreshCallBack.hideLoading();
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "网络出现问题" + throwable.getMessage());
                        productPlanBeanListLiveData.postValue(new ArrayList<>());
                        uiRefreshCallBack.hideLoading();
                    }
                });
    }


    public void initCalculationPower() {
        // 如果上一个请求没有完成，那就不让新的请求发生
        if (assetsSubscribe != null && !assetsSubscribe.isDisposed()) {
            return;
        }

        assetsSubscribe = ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(AssetsService.class).requestAssets(), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<AssetsBean>>>() {
                    @Override
                    public void accept(ResultBean<List<AssetsBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            assetsBeanListLiveData.postValue(listResultBean.getDate());
                        } else {
                            assetsBeanListLiveData.postValue(new ArrayList<>());
                        }
                        LoggerUtils.d(TAG, "用户购买计划" + listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }
}
