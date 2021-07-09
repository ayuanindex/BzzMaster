package com.shenkong.bzzmaster.ui.activity.settings;

import android.content.Intent;
import android.view.View;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.ui.activity.login.LoginActivity;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

public class SettingsActivity extends BaseMvpActivity<SettingsPresenter> implements SettingsEvent {
    private android.widget.RelativeLayout titleLayout;
    private androidx.appcompat.widget.AppCompatImageView ivArrowBack;
    private com.google.android.material.textview.MaterialTextView tvTitle;
    private com.google.android.material.button.MaterialButton btnLogOut;

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
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        btnLogOut.setOnClickListener(v -> {
            SpUtil.removeTag(this, SpUtil.loginStatus);
            SpUtil.removeTag(this, SpUtil.token);
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
