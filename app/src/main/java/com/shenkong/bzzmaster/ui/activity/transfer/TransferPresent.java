package com.shenkong.bzzmaster.ui.activity.transfer;

import android.os.Build;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.CapitalBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.CapitalService;
import com.shenkong.bzzmaster.net.api.ProductService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import javax.crypto.interfaces.PBEKey;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.http.POST;

public class TransferPresent extends BasePresenter<TransferEvent> {
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private MutableLiveData<List<ProductBean>> productBeanListLiveData;
    private MutableLiveData<List<CapitalBean>> capitalBeanListLiveData;
    private Disposable productSubscribe;

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<List<ProductBean>> getProductBeanListLiveData() {
        return productBeanListLiveData;
    }

    public void setProductBeanListLiveData(MutableLiveData<List<ProductBean>> productBeanListLiveData) {
        this.productBeanListLiveData = productBeanListLiveData;
    }

    public MutableLiveData<List<CapitalBean>> getCapitalBeanListLiveData() {
        return capitalBeanListLiveData;
    }

    public void setCapitalBeanListLiveData(MutableLiveData<List<CapitalBean>> capitalBeanListLiveData) {
        this.capitalBeanListLiveData = capitalBeanListLiveData;
    }

    public void confirmTransfer(String address, String amountOfMoney) {
        try {
            if (TextUtils.isEmpty(address) || TextUtils.isEmpty(amountOfMoney)) {
                mView.showToastMsg("请输入完整信息", 0);
                return;
            }

            double doubleAmountOfMoney = Double.parseDouble(amountOfMoney);
            if (doubleAmountOfMoney <= 0) {
                mView.showToastMsg("转账金额必须大于0", 0);
                return;
            }

            mView.showConfirmDialog(address, doubleAmountOfMoney);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            mView.showToastMsg("转账金额输入有误", 0);
            LoggerUtils.d(TAG, "转账金额输入有误");
        }
    }

    public void requestAllProduct() {
        if (productSubscribe != null && !productSubscribe.isDisposed()) {
            LoggerUtils.d(TAG, "已经又一个线程在家宅了");
            return;
        }

        productSubscribe = ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(ProductService.class).requestAllProduct(), lifecycleProvider)
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
     * 转出
     *
     * @param address             目标地址
     * @param doubleAmountOfMoney 转出金额
     */
    public void requestTransferOut(String address, double doubleAmountOfMoney, long productId) {
        CapitalBean capitalBean = new CapitalBean();
        capitalBean.setApplyid(Formatter.generateNumberString(new Date()).substring(4));
        capitalBean.setPid(productId);
        capitalBean.setAdress(address);
        capitalBean.setBalance(doubleAmountOfMoney);
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(CapitalService.class).requestTransferOut(capitalBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<Boolean>>() {
                    @Override
                    public void accept(ResultBean<Boolean> booleanResultBean) throws Exception {
                        if (booleanResultBean.getCode() == 200) {
                            mView.transferOutSuccess();
                        }
                        mView.showToastMsg(booleanResultBean.getMsg(), 0);
                        LoggerUtils.d(TAG, booleanResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }

    /**
     * 查询指定币种的余额
     *
     * @param position 选中的币种下标
     */
    public void requestBalance(int position) {
        if (productBeanListLiveData.getValue() == null) {
            return;
        }

        ProductBean productBean = productBeanListLiveData.getValue().get(position);
        CapitalBean capitalBean = new CapitalBean();
        capitalBean.setPid(productBean.getProductid());
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(CapitalService.class).requestBalance(capitalBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<CapitalBean>>>() {
                    @Override
                    public void accept(ResultBean<List<CapitalBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            listResultBean.getDate().get(0).setName(productBean.getCurrency());
                            capitalBeanListLiveData.postValue(listResultBean.getDate());
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
}
