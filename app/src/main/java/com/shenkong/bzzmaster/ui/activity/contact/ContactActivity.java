package com.shenkong.bzzmaster.ui.activity.contact;

import android.view.View;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

public class ContactActivity extends BaseMvpActivity<ContactPresenter> implements ContactEvent {
    private android.widget.RelativeLayout titleLayout;
    private androidx.appcompat.widget.AppCompatImageView ivArrowBack;
    private com.google.android.material.textview.MaterialTextView tvTitle;
    private androidx.appcompat.widget.AppCompatImageView ivIcon;
    private com.google.android.material.textview.MaterialTextView tvTip;
    private com.google.android.material.textview.MaterialTextView tvContactTip;
    private com.google.android.material.textview.MaterialTextView tvEmail;
    private com.google.android.material.textview.MaterialTextView tvPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    protected void initView() {
        titleLayout = findViewById(R.id.titleLayout);
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tvTitle = findViewById(R.id.tvTitle);
        ivIcon = findViewById(R.id.ivIcon);
        tvTip = findViewById(R.id.tvTip);
        tvContactTip = findViewById(R.id.tvContactTip);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());
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
