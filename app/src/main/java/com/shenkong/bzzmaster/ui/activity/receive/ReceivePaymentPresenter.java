package com.shenkong.bzzmaster.ui.activity.receive;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.king.zxing.util.CodeUtils;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.CapitalBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.CapitalService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;
import java.util.Set;

import io.reactivex.functions.Consumer;

public class ReceivePaymentPresenter extends BasePresenter<ReceivePaymentEvent> {
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private MutableLiveData<List<CapitalBean>> capitalBeanListLiveData;

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<List<CapitalBean>> getCapitalBeanListLiveData() {
        return capitalBeanListLiveData;
    }

    public void setCapitalBeanListLiveData(MutableLiveData<List<CapitalBean>> capitalBeanListLiveData) {
        this.capitalBeanListLiveData = capitalBeanListLiveData;
    }

    public void createQRCodeBitmap(String content, int size) {
        Bitmap qrCodeBitmap = CodeUtils.createQRCode(content, size);
        mView.setQrCodeToView(qrCodeBitmap);
    }

    /**
     * 查询USDT的余额
     * 服务器端接收到参数PID为0的时候会自动查询USDT的余额
     */
    public void requestBalance() {
        CapitalBean capitalBean = new CapitalBean();
        capitalBean.setPid(0);
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(CapitalService.class).requestBalance(capitalBean), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<CapitalBean>>>() {
                    @Override
                    public void accept(ResultBean<List<CapitalBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            capitalBeanListLiveData.postValue(listResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出现问题", throwable.getMessage());
                    }
                });
    }
}
