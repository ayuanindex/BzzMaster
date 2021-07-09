package com.shenkong.bzzmaster.ui.activity.orders;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.OrderBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.activity.orders.adapter.OrderAdapter;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.Date;

public class OrdersActivity extends BaseMvpActivity<OrderPresenter> implements OrderEvent {
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

        rcOrders.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        refreshLayout.setOnRefreshListener(() -> {
            uiHandler.postDelayed(() -> {
                refreshLayout.setRefreshing(false);
            }, 1000);
        });

        tabSwitchProduct.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LoggerUtils.d(TAG, orderAdapter.getOrderBeanList().get(tab.getPosition()).toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {
        int type = getIntent().getIntExtra("type", 0);
        mPresenter.setLifecycleProvider(this);

        initDataSubscribe();

        mPresenter.initProductCategory();

        if (type == 0) {
            orderAdapter = new OrderAdapter(this);
            rcOrders.setAdapter(orderAdapter);

            ArrayList<OrderBean> orderBeans = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                OrderBean e = new OrderBean();
                e.setCreatetime(new Date());
                e.setMessage("测试订单" + i);
                orderBeans.add(e);
            }
            orderAdapter.resetData(orderBeans);
            ivEmptyView.setVisibility(View.GONE);
        }
    }

    private void initDataSubscribe() {
        mPresenter.setProductList(new MutableLiveData<>());
        mPresenter.getProductList().observe(this, productBeanList -> {
            for (ProductBean productBean : productBeanList) {
                tabSwitchProduct.addTab(tabSwitchProduct.newTab().setText(productBean.getName()));
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
