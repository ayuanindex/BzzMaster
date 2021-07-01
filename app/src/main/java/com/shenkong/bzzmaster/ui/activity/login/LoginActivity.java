package com.shenkong.bzzmaster.ui.activity.login;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.model.bean.User;
import com.shenkong.bzzmaster.ui.activity.main.MainActivity;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginEven {
    private static final String TAG = "LoginActivity";
    private TextInputEditText tiePhone;
    private TextInputLayout tilVerificationCode;
    private TextInputEditText tieVerificationCode;
    private MaterialButton btnGetVerificationCode;
    private TextInputLayout tilInvitationCode;
    private TextInputEditText tieInvitationCode;
    private MaterialButton btnLogin;
    private String bizid = "null";
    private TextInputLayout tilPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void initView() {
        tilPhone = findViewById(R.id.tilPhone);
        tiePhone = findViewById(R.id.tiePhone);
        tilVerificationCode = findViewById(R.id.tilVerificationCode);
        tieVerificationCode = findViewById(R.id.tieVerificationCode);
        btnGetVerificationCode = findViewById(R.id.btnGetVerificationCode);
        tilInvitationCode = findViewById(R.id.tilInvitationCode);
        tieInvitationCode = findViewById(R.id.tieInvitationCode);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnGetVerificationCode.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {
        initEven();
    }


    @Override
    protected void initData() {
        // 是否已经登录
        if (SpUtil.getBoolean(this, SpUtil.loginStatus, false)) {
            jumpActivity(MainActivity.class);
            finish();
            return;
        }
        tiePhone.setText(SpUtil.getString(this, SpUtil.phone, ""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                mPresenter.login(this);
                break;
            case R.id.btnGetVerificationCode:
                mPresenter.requestSmsCode(this);
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToastMsg(String msg, int type) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void SendSmsCode(String bizid) {
        this.bizid = bizid;
    }

    @Override
    public void setVerCodeBtnIsEnable(boolean isEnable) {
        uiHandler.post(() -> btnGetVerificationCode.setEnabled(isEnable));
    }

    @Override
    public void setBtnVerCodeText(String text) {
        uiHandler.post(() -> btnGetVerificationCode.setText(text));
    }

    @Override
    public void Login(User user) {
        // 保存用户登录信息到SP中
        SpUtil.putBoolean(this, SpUtil.loginStatus, true);
        SpUtil.putString(this, SpUtil.phone, user.getPhonenumber());
        SpUtil.putString(this, SpUtil.code, user.getCode());
        SpUtil.putString(this, SpUtil.token, user.getToken());
        jumpActivity(MainActivity.class);
        finish();
    }

    @Override
    public String getPhoneNumber() {
        return tiePhone.getEditableText().toString().trim();
    }

    @Override
    public String getInvitationCode() {
        return tieInvitationCode.getEditableText().toString().trim();
    }

    @Override
    public String getVerificationCode() {
        return tieVerificationCode.getEditableText().toString().trim();
    }

    @Override
    public String getBizid() {
        return this.bizid;
    }
}
