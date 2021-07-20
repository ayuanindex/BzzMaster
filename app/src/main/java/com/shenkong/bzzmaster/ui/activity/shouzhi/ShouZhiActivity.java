package com.shenkong.bzzmaster.ui.activity.shouzhi;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

/**
 * 充值、提币、收益
 */
public class ShouZhiActivity extends BaseMvpActivity<ShouZhiPresent> implements ShouZhiEvent {
    private RelativeLayout titleLayout;
    private AppCompatImageView ivArrowBack;
    private MaterialTextView tvTitle;
    private TabLayout tabSwitchProduct;
    private SwipeRefreshLayout refreshLayout;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView rcShouZhi;
    private ContentLoadingProgressBar progressLoadingData;
    private AppCompatImageView ivEmptyView;
    private boolean isLoadMore = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shouzhi;
    }

    @Override
    protected void initView() {
        titleLayout = (RelativeLayout) findViewById(R.id.titleLayout);
        ivArrowBack = (AppCompatImageView) findViewById(R.id.ivArrowBack);
        tvTitle = (MaterialTextView) findViewById(R.id.tvTitle);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        smartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.smartRefreshLayout);
        rcShouZhi = (RecyclerView) findViewById(R.id.rcShouZhi);
        progressLoadingData = (ContentLoadingProgressBar) findViewById(R.id.progressLoadingData);
        ivEmptyView = (AppCompatImageView) findViewById(R.id.ivEmptyView);
        tabSwitchProduct = (TabLayout) findViewById(R.id.tabSwitchProduct);

        rcShouZhi.setLayoutManager(new LinearLayoutManager(this));
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(this));
        smartRefreshLayout.setEnableRefresh(false);

        refreshLayout.setColorSchemeResources(
                R.color.blue_primary,
                R.color.red_primary,
                R.color.orange_primary,
                R.color.blue_primary,
                R.color.green_primary,
                R.color.red_primary
        );
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        tabSwitchProduct.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 加载
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            isLoadMore = false;
            mPresenter.setPage(1);
        });

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadMore = true;
                int page = mPresenter.getPage();
                mPresenter.setPage(++page);
                mPresenter.requestShouZhi();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.setLifecycleProvider(this);

        initDataSubscribe();
    }

    /**
     * 设置数据订阅
     */
    private void initDataSubscribe() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {
        // progressLoadingData.setVisibility(View.VISIBLE);
        progressLoadingData.show();
    }

    @Override
    public void hideLoading() {
        // progressLoadingData.setVisibility(View.GONE);
        progressLoadingData.hide();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToastMsg(String msg, int type) {
        ToastUtil.showToast(this, msg);
    }
}
