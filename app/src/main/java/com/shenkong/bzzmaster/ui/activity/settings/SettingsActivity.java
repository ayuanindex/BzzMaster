package com.shenkong.bzzmaster.ui.activity.settings;

import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.Utils;
import com.google.android.material.button.MaterialButton;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.ui.activity.login.LoginActivity;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

public class SettingsActivity extends BaseMvpActivity<SettingsPresenter> implements SettingsEvent {
    private android.widget.RelativeLayout titleLayout;
    private androidx.appcompat.widget.AppCompatImageView ivArrowBack;
    private com.google.android.material.textview.MaterialTextView tvTitle;
    private com.google.android.material.button.MaterialButton btnLogOut;
    private com.google.android.material.button.MaterialButton btnRealNameAuthentication;

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initView() {
        titleLayout = findViewById(R.id.titleLayout);
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tvTitle = findViewById(R.id.tvTitle);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnRealNameAuthentication = findViewById(R.id.btnRealNameAuthentication);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        // 实名认证
        btnRealNameAuthentication.setOnClickListener(v -> {
            // TODO: 2021/7/11 实名认证功能 待实现
        });

        // 退出登录
        btnLogOut.setOnClickListener(v -> {
            SpUtil.removeTag(Utils.getApp(), SpUtil.loginStatus);
            SpUtil.removeTag(Utils.getApp(), SpUtil.token);
            SpUtil.removeTag(Utils.getApp(), SpUtil.userId);
            startActivity(new Intent(this, LoginActivity.class));
            sendBroadcast(new Intent("logOut"));
            finish();
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToastMsg(String msg, int type) {

    }
}
