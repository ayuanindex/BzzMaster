package com.shenkong.bzzmaster.ui.activity.productinfo;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

public class ProductInfoActivity extends BaseMvpActivity<ProductInfoPresenter> implements ProductInfoEvent {
    private android.widget.RelativeLayout titleLayout;
    private androidx.appcompat.widget.AppCompatImageView ivArrowBack;
    private com.google.android.material.textview.MaterialTextView tvTitle;
    private android.webkit.WebView webViewProductInfo;
    private com.google.android.material.button.MaterialButton btnPurchase;

    @Override
    public int getLayoutId() {
        return R.layout.activity_productinfo;
    }

    @Override
    protected void initView() {
        ivArrowBack = (AppCompatImageView) findViewById(R.id.ivArrowBack);
        tvTitle = (MaterialTextView) findViewById(R.id.tvTitle);
        webViewProductInfo = (WebView) findViewById(R.id.webViewProductInfo);
        btnPurchase = (MaterialButton) findViewById(R.id.btnPurchase);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> {
            finish();
        });

        btnPurchase.setOnClickListener(v -> {
            // TODO: 2021/6/28 购买产品按钮功能
        });
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    @JavascriptInterface
    protected void initData() {
        WebSettings webSettings = webViewProductInfo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webViewProductInfo.setVisibility(View.INVISIBLE);
        webViewProductInfo.loadUrl("file:///android_asset/web/productinfo.html");
        uiHandler.postDelayed(() -> {
            webViewProductInfo.loadUrl("javascript:setImgUrl('https://pool-chia.oss-cn-hangzhou.aliyuncs.com/img/chia1.png')");
            webViewProductInfo.setVisibility(View.VISIBLE);
        }, 1000);
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
