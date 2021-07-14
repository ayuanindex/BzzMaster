package com.shenkong.bzzmaster.ui.activity.web;

import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.exifinterface.media.ExifInterface;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

public class WebViewActivity extends BaseMvpActivity<WebViewPresenter> {
    public static final String URL = "url";
    public static final String TITLE = "title";
    private RelativeLayout titleLayout;
    private AppCompatImageView ivArrowBack;
    private MaterialTextView tvTitle;
    private ContentLoadingProgressBar progressLoading;
    private WebView webView;
    private SwipeRefreshLayout refreshLayout;
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {
        titleLayout = findViewById(R.id.titleLayout);
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tvTitle = findViewById(R.id.tvTitle);
        progressLoading = findViewById(R.id.progressLoading);
        webView = findViewById(R.id.webView);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressLoading.show();
                webView.loadUrl(url);
            }
        });
    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra(URL);
        String title = getIntent().getStringExtra(TITLE);

        tvTitle.setText(title);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressLoading.setProgress(newProgress, true);
                } else {
                    progressLoading.setProgress(newProgress);
                }
                if (newProgress == 100) {
                    refreshLayout.setRefreshing(false);
                    uiHandler.postDelayed(() -> progressLoading.hide(), 200);
                }
            }


        });

        webView.loadUrl(url);
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
