package com.shenkong.bzzmaster.ui.activity.login;

import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shenkong.bzzmaster.R;
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    protected void initView() {
        tiePhone = findViewById(R.id.tiePhone);
        tilVerificationCode = findViewById(R.id.tilVerificationCode);
        tieVerificationCode = findViewById(R.id.tieVerificationCode);
        btnGetVerificationCode = findViewById(R.id.btnGetVerificationCode);
        tilInvitationCode = findViewById(R.id.tilInvitationCode);
        tieInvitationCode = findViewById(R.id.tieInvitationCode);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this::onClick);
        btnGetVerificationCode.setOnClickListener(this::onClick);
    }

    @Override
    protected void initEvent() {
        initEven();
    }


    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                mPresenter.login(this);
                break;
            case R.id.btnGetVerificationCode:
                mPresenter.sendSmsCode(this);
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
    public void Login(User user) {

    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getVerificationCode() {
        return null;
    }

    @Override
    public String getBizid() {
        return this.bizid;
    }
}
