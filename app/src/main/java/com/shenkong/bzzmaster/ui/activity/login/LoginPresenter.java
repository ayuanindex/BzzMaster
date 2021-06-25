package com.shenkong.bzzmaster.ui.activity.login;

import android.text.TextUtils;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.model.bean.User;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.SmsService;
import com.shenkong.bzzmaster.net.api.UserService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


public class LoginPresenter extends BasePresenter<LoginEven> {

    public void sendSmsCode(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        String phoneNumber = mView.getPhoneNumber();
        User user = new User();
        user.setPhonenumber(phoneNumber);
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(SmsService.class).LoginCode(user), lifecycleProvider).map(new Function<ResultBean<String>, ResultBean<String>>() {
            @Override
            public ResultBean<String> apply(ResultBean<String> userResultBean) throws Exception {
                return userResultBean;
            }
        }).subscribe(new Observer<ResultBean<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResultBean<String> userResultBean) {
                if (userResultBean.getCode() == 200) {
                    mView.showToastMsg("发送成功", 0);
                    mView.SendSmsCode(userResultBean.getDate());
                } else {
                    mView.showToastMsg(userResultBean.getMsg(), userResultBean.getCode());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.showToastMsg("发送失败，请稍后重试", 0);
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void login(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        String verificationCode = mView.getVerificationCode();
        String code = mView.getCode();
        String phoneNumber = mView.getPhoneNumber();
        String bizid = mView.getBizid();
        HashMap<String, String> map = new HashMap<>();
        map.put("phoneNumber", phoneNumber);
        map.put("verificationCode", verificationCode);
        if (!TextUtils.isEmpty(code))
            map.put("code", code);
        if (!TextUtils.isEmpty(bizid))
            map.put("bizid", bizid);
        ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(UserService.class).LoginCode(map), lifecycleProvider).map(new Function<ResultBean<User>, ResultBean<User>>() {
            @Override
            public ResultBean<User> apply(ResultBean<User> userResultBean) throws Exception {
                return userResultBean;
            }
        }).subscribe(new Observer<ResultBean<User>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResultBean<User> userResultBean) {
                if (userResultBean.getCode() == 200) {
                    mView.showToastMsg("登录成功", 0);
                    mView.Login(userResultBean.getDate());
                } else {
                    mView.showToastMsg(userResultBean.getMsg(), userResultBean.getCode());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.showToastMsg("登录失败，请稍后重试", 0);
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
