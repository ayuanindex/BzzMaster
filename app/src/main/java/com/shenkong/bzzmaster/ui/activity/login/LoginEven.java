package com.shenkong.bzzmaster.ui.activity.login;

import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.bean.User;

public interface LoginEven extends BaseEven {
    /**
     * 发送验证码
     */
    void SendSmsCode(String bizid);

    /**
     * 登录
     */
    void Login(User user);

    /**
     * 手机号
     * @return
     */
    String getPhoneNumber();

    /**
     * 邀请码
     * @return
     */
    String getCode();

    /**
     * 验证码
     * @return
     */
    String getVerificationCode();
    /**
     * 验证码
     * @return
     */
    String getBizid();
}
