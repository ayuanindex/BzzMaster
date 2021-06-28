package com.shenkong.bzzmaster.ui.activity.login;

import android.text.TextUtils;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.User;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.SmsService;
import com.shenkong.bzzmaster.net.api.UserService;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;


public class LoginPresenter extends BasePresenter<LoginEven> {
    private static String bizid = "";
    private int unLockingVerCodeBtnTime = 60;
    private Timer timer;
    private TimerTask loopTask;
    private TimerTask cancelTast;

    public void requestSmsCode(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        String phoneNumber = mView.getPhoneNumber();
        if (TextUtils.isEmpty(phoneNumber)) {
            mView.showToastMsg("请输入手机号码", 0);
        } else {
            mView.setVerCodeBtnIsEnable(false);
            User user = new User();
            user.setPhonenumber(phoneNumber);
            ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(SmsService.class).requestSmsCode(user), lifecycleProvider)
                    .map(stringResultBean -> stringResultBean)
                    .subscribe(new Observer<ResultBean<String>>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull ResultBean<String> stringResultBean) {
                            int code = stringResultBean.getCode();
                            if (code == 200) {
                                // 开启按钮定时
                                startVerificationCodeBtnTimer();
                                mView.showToastMsg("发送成功，请注意查收短信", 0);
                                bizid = stringResultBean.getDate();
                            } else {
                                mView.setVerCodeBtnIsEnable(true);
                                mView.showToastMsg("验证码获取失败", 0);
                            }

                            LoggerUtils.d(TAG, stringResultBean.toString());
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mView.showToastMsg("发送失败，请重试", 0);
                            mView.setVerCodeBtnIsEnable(true);
                            LoggerUtils.d(TAG, "出现错误", e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void startVerificationCodeBtnTimer() {
        timer = new Timer();
        loopTask = new TimerTask() {
            @Override
            public void run() {
                mView.setBtnVerCodeText("(" + (unLockingVerCodeBtnTime--) + "s后)获取验证码");
            }
        };

        cancelTast = new TimerTask() {
            @Override
            public void run() {
                unLockingVerCodeBtnTime = 60;

                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }

                if (loopTask != null) {
                    loopTask.cancel();
                    loopTask = null;
                }

                mView.setBtnVerCodeText("获取验证码");
                mView.setVerCodeBtnIsEnable(true);
            }
        };
        timer.schedule(loopTask, 0, 1000);
        timer.schedule(cancelTast, 60000);
    }

    public void login(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        String verificationCode = mView.getVerificationCode();
        String invitationCode = mView.getInvitationCode();
        String phoneNumber = mView.getPhoneNumber();
        //String bizid = mView.getBizid();

        HashMap<String, String> map = new HashMap<>();
        map.put("phonenumber", phoneNumber);
        map.put("verificationCode", verificationCode);
        if (!TextUtils.isEmpty(invitationCode))
            map.put("code", invitationCode);
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
