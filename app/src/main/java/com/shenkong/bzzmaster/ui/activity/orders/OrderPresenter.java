package com.shenkong.bzzmaster.ui.activity.orders;

import android.os.Build;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.FrontPage;
import com.shenkong.bzzmaster.model.bean.OrderBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.OrderService;
import com.shenkong.bzzmaster.net.api.ProductService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class OrderPresenter extends BasePresenter<OrderEvent> {
    private static final String TAG = "OrderPresenter";
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private int page = 1;
    private MutableLiveData<List<ProductBean>> productList;
    private MutableLiveData<List<ProductPlanBean>> productPlanBeanListLiveData;
    private MutableLiveData<List<OrderBean>> orderBeanListLiveData;
    private Disposable productPlanSubscribe;
    private Disposable orderSubscribe;

    public MutableLiveData<List<ProductBean>> getProductList() {
        return productList;
    }

    public void setProductList(MutableLiveData<List<ProductBean>> productList) {
        this.productList = productList;
    }

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<List<ProductPlanBean>> getProductPlanBeanListLiveData() {
        return productPlanBeanListLiveData;
    }

    public void setProductPlanBeanListLiveData(MutableLiveData<List<ProductPlanBean>> productPlanBeanListLiveData) {
        this.productPlanBeanListLiveData = productPlanBeanListLiveData;
    }

    public MutableLiveData<List<OrderBean>> getOrderBeanListLiveData() {
        return orderBeanListLiveData;
    }

    public void setOrderBeanListLiveData(MutableLiveData<List<OrderBean>> orderBeanListLiveData) {
        this.orderBeanListLiveData = orderBeanListLiveData;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void initProductCategory() {
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(ProductService.class).requestAllProduct(), lifecycleProvider)
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

    /**
     * 获取当前产品下的所有计划
     *
     * @param productId 需要查询的产品
     */
    public void requestAllProductPlan(long productId) {
        if (orderSubscribe != null && !orderSubscribe.isDisposed()) {
            orderSubscribe.dispose();
            orderSubscribe = null;
        }

        if (productPlanSubscribe != null && !productPlanSubscribe.isDisposed()) {
            productPlanSubscribe.dispose();
            productPlanSubscribe = null;
        }

        ProductBean productBean = new ProductBean();
        productBean.setProductid(productId);
        productPlanSubscribe = ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(ProductService.class).requestProductPlan(productBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<ProductPlanBean>>>() {
                    @Override
                    public void accept(ResultBean<List<ProductPlanBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            productPlanBeanListLiveData.postValue(listResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                        //mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                        mView.hideLoading();
                    }
                });
    }

    /**
     * 获取产品当前币种的所有订单
     *
     * @param productPlanBeans 匹配订单名称所用的参数
     * @param productid
     */
    public void requestOrders(List<ProductPlanBean> productPlanBeans, long productid) {
        LoggerUtils.d(TAG, "page" + page);
        FrontPage frontPage = new FrontPage();
        frontPage.setRows(20);
        frontPage.setPage(page);
        frontPage.setSidx("createtime");
        frontPage.setKeyvalue(productid);
        //排序方式 asc升序  desc降序
        frontPage.setSord("desc");
        orderSubscribe = ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(OrderService.class).requestOrders(frontPage), lifecycleProvider)
                .map(new Function<ResultBean<FrontPage<List<OrderBean>>>, ResultBean<FrontPage<List<OrderBean>>>>() {
                    @Override
                    public ResultBean<FrontPage<List<OrderBean>>> apply(@NonNull ResultBean<FrontPage<List<OrderBean>>> frontPageResultBean) throws Exception {
                        if (frontPageResultBean.getCode() == 200) {
                            for (OrderBean orderBean : frontPageResultBean.getDate().getT()) {
                                for (ProductPlanBean productPlanBean : productPlanBeans) {
                                    if (orderBean.getPid() == productPlanBean.getProductid()) {
                                        orderBean.setPname(productPlanBean.getName());
                                    }
                                }
                            }
                        }
                        return frontPageResultBean;
                    }
                })
                .subscribe(new Consumer<ResultBean<FrontPage<List<OrderBean>>>>() {
                    @Override
                    public void accept(ResultBean<FrontPage<List<OrderBean>>> frontPageResultBean) throws Exception {
                        if (frontPageResultBean.getCode() == 200) {
                            orderBeanListLiveData.postValue(frontPageResultBean.getDate().getT());
                        } else {
                            orderBeanListLiveData.postValue(new ArrayList<>());
                        }
                        mView.hideLoading();
                        LoggerUtils.d(TAG, frontPageResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                        orderBeanListLiveData.postValue(new ArrayList<>());
                        mView.hideLoading();
                    }
                });
    }
}
