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
     *
     * @return
     */
    String getPhoneNumber();

    /**
     * 邀请码
     *
     * @return
     */
    String getInvitationCode();

    /**
     * 验证码
     *
     * @return
     */
    String getVerificationCode();

    /**
     * 验证码
     *
     * @return
     */
    String getBizid();

    /**
     * 设置发送验证码按钮是否可用
     *
     * @param isEnable 是否可用的标志true可用false不可用
     */
    void setVerCodeBtnIsEnable(boolean isEnable);

    /**
     * 设置获取验证码按钮的文字
     *
     * @param text 需要设置的文字
     */
    void setBtnVerCodeText(String text);
}
