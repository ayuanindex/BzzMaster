package com.shenkong.bzzmaster.ui.activity.productinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.ui.activity.submit.SubmitOrderActivity;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

public class ProductInfoActivity extends BaseMvpActivity<ProductInfoPresenter> implements ProductInfoEvent {
    private android.widget.RelativeLayout titleLayout;
    private androidx.appcompat.widget.AppCompatImageView ivArrowBack;
    private com.google.android.material.textview.MaterialTextView tvTitle;
    private android.webkit.WebView webViewProductInfo;
    private com.google.android.material.button.MaterialButton btnPurchase;
    private ProductPlanBean data;
    private androidx.core.widget.NestedScrollView scrollContent;

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
        scrollContent = (NestedScrollView) findViewById(R.id.scrollContent);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> {
            finish();
        });

        btnPurchase.setOnClickListener(v -> {
            SharedBean.putData(SharedBean.ProductPlanBean, data);
            Intent intent = new Intent(this, SubmitOrderActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    @JavascriptInterface
    protected void initData() {
        data = (ProductPlanBean) SharedBean.getData(SharedBean.ProductPlanBean);

        if (data != null) {
            WebSettings webSettings = webViewProductInfo.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webViewProductInfo.loadUrl("file:///android_asset/web/productinfo.html");
            if (data.getDetailslink() != null) {
                uiHandler.postDelayed(() -> {
                    webViewProductInfo.loadUrl("javascript:setImgUrl('" + data.getPic() + "')");
                    webViewProductInfo.setVisibility(View.VISIBLE);
                    scrollContent.setVisibility(View.VISIBLE);
                }, 1000);
            }
        }
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
