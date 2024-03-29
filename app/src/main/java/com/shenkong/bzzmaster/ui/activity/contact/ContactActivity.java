package com.shenkong.bzzmaster.ui.activity.contact;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.BuildConfig;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.model.bean.ChatBean;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends BaseMvpActivity<ContactPresenter> implements ContactEvent {
    private android.widget.RelativeLayout titleLayout;
    private androidx.appcompat.widget.AppCompatImageView ivArrowBack;
    private com.google.android.material.textview.MaterialTextView tvTitle;
    private androidx.appcompat.widget.AppCompatImageView ivIcon;
    private com.google.android.material.textview.MaterialTextView tvTip;
    private com.google.android.material.textview.MaterialTextView tvContactTip;
    private com.google.android.material.textview.MaterialTextView tvEmail;
    private com.google.android.material.textview.MaterialTextView tvPhone;
    private com.google.android.material.textview.MaterialTextView tvVersionName;

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
        tvVersionName = findViewById(R.id.tvVersionName);
        tvTip = findViewById(R.id.tvTip);
        tvContactTip = findViewById(R.id.tvContactTip);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);

        tvVersionName.setText("版本V" + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {
        initDataSubscribe();

        mPresenter.setLifecycleProvider(this);
        switch (BuildConfig.firm) {
            case "mifan":
                tvContactTip.setText(BuildConfig.appName + "技术团队");
                tvEmail.setText("邮箱:lzy2118053626@hotmail.com");
                tvPhone.setText("联系电话:18175009828");
                break;
            default:
                mPresenter.requestChat();
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void initDataSubscribe() {
        MutableLiveData<List<ChatBean>> chatBeanListLiveData = new MutableLiveData<>();
        mPresenter.setChatBeanListLiveData(chatBeanListLiveData);
        mPresenter.getChatBeanListLiveData().observe(this, chatBeans -> {
            if (chatBeans.size() > 0) {
                ChatBean chatBean = chatBeans.get(0);
                tvContactTip.setText(chatBean.getTitle());
                tvPhone.setText("联系电话:" + chatBean.getQrcode());
            }
        });
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
