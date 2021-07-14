package com.shenkong.bzzmaster.ui.activity.orders;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.savedstate.SavedStateRegistryOwner;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.model.bean.OrderBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.ui.activity.orders.adapter.OrderAdapter;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderActivity extends BaseMvpActivity<OrderPresenter> implements OrderEvent {
    private RelativeLayout titleLayout;
    private AppCompatImageView ivArrowBack;
    private MaterialTextView tvTitle;
    private LinearLayoutCompat llTop;
    private TabLayout tabSwitchProduct;
    private RecyclerView rcOrders;
    private ContentLoadingProgressBar progressLoadingData;
    private AppCompatImageView ivEmptyView;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout refreshLayout;
    private OrderAdapter orderAdapter;
    private SmartRefreshLayout smartRefreshLayout;
    private int type;
    private int productid = 0;
    private boolean isLoadMore = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_orders;
    }

    @Override
    protected void initView() {
        titleLayout = findViewById(R.id.titleLayout);
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tvTitle = findViewById(R.id.tvTitle);
        llTop = findViewById(R.id.llTop);
        tabSwitchProduct = findViewById(R.id.tabSwitchProduct);
        rcOrders = findViewById(R.id.rcOrders);
        progressLoadingData = findViewById(R.id.progressLoadingData);
        ivEmptyView = findViewById(R.id.ivEmptyView);
        refreshLayout = findViewById(R.id.refreshLayout);
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);

        rcOrders.setLayoutManager(new LinearLayoutManager(this));
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(this));
        smartRefreshLayout.setEnableRefresh(false);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        refreshLayout.setOnRefreshListener(() -> {
            isLoadMore = false;
            mPresenter.setPage(1);
            mPresenter.requestAllProductPlan(productid);
        });

        tabSwitchProduct.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                load(tab);
            }

            private void load(TabLayout.Tab tab) {
                if (type == 0) {
                    if (mPresenter.getProductList().getValue() != null) {
                        showLoading();
                        mPresenter.setPage(1);
                        isLoadMore = false;
                        productid = mPresenter.getProductList().getValue().get(tab.getPosition()).getProductid();
                        mPresenter.requestAllProductPlan(productid);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                load(tab);
            }
        });

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isLoadMore = true;
                int page = mPresenter.getPage();
                mPresenter.setPage(++page);
                mPresenter.requestAllProductPlan(productid);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        mPresenter.setLifecycleProvider(this);

        initDataSubscribe();

        mPresenter.initProductCategory();

        if (type == 0) {
            orderAdapter = new OrderAdapter(this);
            rcOrders.setAdapter(orderAdapter);
        }
    }

    private void initDataSubscribe() {
        // 所有产品
        mPresenter.setProductList(new MutableLiveData<>());
        mPresenter.getProductList().observe(this, productBeanList -> {
            tabSwitchProduct.removeAllTabs();
            for (ProductBean productBean : productBeanList) {
                tabSwitchProduct.addTab(tabSwitchProduct.newTab().setText(productBean.getName()));
            }
        });

        // 产品计划
        mPresenter.setProductPlanBeanListLiveData(new MutableLiveData<>());
        mPresenter.getProductPlanBeanListLiveData().observe(this, new Observer<List<ProductPlanBean>>() {
            @Override
            public void onChanged(List<ProductPlanBean> productPlanBeans) {
                mPresenter.requestOrders(productPlanBeans, productid);
            }
        });

        // 订单
        mPresenter.setOrderBeanListLiveData(new MutableLiveData<>());
        mPresenter.getOrderBeanListLiveData().observe(this, new Observer<List<OrderBean>>() {
            @Override
            public void onChanged(List<OrderBean> orderBeans) {
                if (isLoadMore) {
                    // 加载更多是切换到了下一页没有数据是，将页数返回到上一页
                    if (orderBeans.size() < 1) {
                        int page = mPresenter.getPage();
                        mPresenter.setPage(--page);
                    }
                    orderAdapter.addData(orderBeans);
                } else {
                    if (orderBeans.size() == 0) {
                        ivEmptyView.setVisibility(View.VISIBLE);
                    } else {
                        ivEmptyView.setVisibility(View.GONE);
                    }
                    orderAdapter.resetData(orderBeans);
                }
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {
        progressLoadingData.setVisibility(View.VISIBLE);
        progressLoadingData.show();
    }

    @Override
    public void hideLoading() {
        progressLoadingData.setVisibility(View.GONE);
        progressLoadingData.hide();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToastMsg(String msg, int type) {
        ToastUtil.showToast(this, msg);
    }
}
