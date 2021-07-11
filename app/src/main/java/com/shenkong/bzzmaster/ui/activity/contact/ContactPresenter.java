package com.shenkong.bzzmaster.ui.activity.contact;

import androidx.lifecycle.MutableLiveData;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.ChatBean;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.ChatService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;

import io.reactivex.functions.Consumer;

public class ContactPresenter extends BasePresenter<ContactEvent> {
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private MutableLiveData<List<ChatBean>> chatBeanListLiveData;

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public MutableLiveData<List<ChatBean>> getChatBeanListLiveData() {
        return chatBeanListLiveData;
    }

    public void setChatBeanListLiveData(MutableLiveData<List<ChatBean>> chatBeanListLiveData) {
        this.chatBeanListLiveData = chatBeanListLiveData;
    }

    public void requestChat() {
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(ChatService.class).requestChat(), lifecycleProvider)
                .subscribe(new Consumer<ResultBean<List<ChatBean>>>() {
                    @Override
                    public void accept(ResultBean<List<ChatBean>> chatBeanResultBean) throws Exception {
                        if (chatBeanResultBean.getCode() == 200) {
                            chatBeanListLiveData.postValue(chatBeanResultBean.getDate());
                        }
                        LoggerUtils.d(TAG, chatBeanResultBean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoggerUtils.d(TAG, "请求出错", throwable.getMessage());
                    }
                });
    }
}
