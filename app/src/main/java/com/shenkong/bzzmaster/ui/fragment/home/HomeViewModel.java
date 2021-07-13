package com.shenkong.bzzmaster.ui.fragment.home;

import android.os.Build;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.BannerBean;
import com.shenkong.bzzmaster.model.bean.CarouselBean;
import com.shenkong.bzzmaster.model.bean.FrontPage;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProfitBean;
import com.shenkong.bzzmaster.model.bean.RevenueBean;
import com.shenkong.bzzmaster.model.bean.RevenueListBean;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
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
import java.util.Collections;
import java.util.Comparator;
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
    private MutableLiveData<ProfitBean> profitBeanDataLiveData;
    private MutableLiveData<String> webDataLiveData;
    private MutableLiveData<List<MultipleAdapter.LayoutType>> productPlanListLiveData;

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

    /**
     * 收益请求
     */
    public void initHomeProfitData(long productId) {
        // 用户收益记录
        FrontPage frontPage = new FrontPage();
        frontPage.setKeyvalue(productId);
        frontPage.setRows(7);
        frontPage.setPage(1);
        frontPage.setSidx("createtime");
        //排序方式 asc升序  desc降序
        frontPage.setSord("desc");
        ObjectLoader.observefg(NetManager.getInstance().getRetrofit().create(RevenueService.class).requestRevenueRecord(frontPage), lifecycleProvider)
                .map(new Function<ResultBean<RevenueListBean>, ArrayList<List>>() {
                    @Override
                    public ArrayList<List> apply(@NonNull ResultBean<RevenueListBean> revenueListBeanResultBean) throws Exception {
                        if (revenueListBeanResultBean.getCode() != 200) {
                            return null;
                        }

                        // 2021-6-15
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        ArrayList<RevenueBean> revenueDayLists = revenueListBeanResultBean.getDate().getRevenueDayLists();

                        // 如果没有收益记录，则添加一行空数据，方便生成
                        if (revenueDayLists.size() == 0) {
                            RevenueBean e = new RevenueBean();
                            e.setCreatedate(new Date());
                            e.setMoney(0);
                            revenueDayLists.add(e);
                        }

                        // 给数据按照date排序，从小到大
                        Collections.sort(revenueDayLists, new Comparator<RevenueBean>() {
                            @Override
                            public int compare(RevenueBean o1, RevenueBean o2) {
                                return o1.getCreatedate().compareTo(o2.getCreatedate());
                            }
                        });

                        // 根据缺少的天数，不全7天的数据
                        if (revenueDayLists.size() < 7) {
                            RevenueBean revenueBean = revenueDayLists.get(0);
                            Date createdate = revenueBean.getCreatedate();
                            int theDayBefore = 7 - revenueDayLists.size();
                            // 填充前N天的数据
                            for (int i = 1; i <= theDayBefore; i++) {
                                RevenueBean e = new RevenueBean();
                                e.setCreatedate(Formatter.getTheDayBeforeDate(createdate, i));
                                e.setMoney(0.00);
                                revenueDayLists.add(0, e);
                            }
                        }

                        ArrayList<List> lists = new ArrayList<>();
                        for (RevenueBean revenueDayList : revenueDayLists) {
                            ArrayList<Object> key = new ArrayList<>();
                            key.add(simpleDateFormat.format(revenueDayList.getCreatedate()));
                            key.add(revenueDayList.getMoney());
                            lists.add(key);
                        }
                        return lists;
                    }
                })
                .subscribe(new Consumer<ArrayList<List>>() {
                    @Override
                    public void accept(ArrayList<List> lists) throws Exception {
                        webDataLiveData.postValue(new Gson().toJson(lists));
                        LoggerUtils.d(TAG, "收益" + new Gson().toJson(lists));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
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
