package com.shenkong.bzzmaster.ui.activity.notice;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.NoticeBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.NoticeService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NoticePresenter extends BasePresenter<NoticeEvent> {
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private MutableLiveData<List<NoticeBean>> noticeListLiveData;
    private Disposable noticeSubscribe;

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<List<NoticeBean>> getNoticeListLiveData() {
        return noticeListLiveData;
    }

    public void setNoticeListLiveData(MutableLiveData<List<NoticeBean>> noticeListLiveData) {
        this.noticeListLiveData = noticeListLiveData;
    }

    public void requestNotice() {
        if (noticeSubscribe != null && !noticeSubscribe.isDisposed()) {
            return;
        }

        noticeSubscribe = ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(NoticeService.class).requestNotices(), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<NoticeBean>>>() {
                    @Override
                    public void accept(ResultBean<List<NoticeBean>> listResultBean) throws Exception {
                        if (listResultBean.getCode() == 200) {
                            noticeListLiveData.postValue(listResultBean.getDate());
                        } else {
                            noticeListLiveData.postValue(new ArrayList<>());
                        }
                        mView.refreshLayoutCancel(false);
                        LoggerUtils.d(TAG, "公告" + listResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.refreshLayoutCancel(false);
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }

}
