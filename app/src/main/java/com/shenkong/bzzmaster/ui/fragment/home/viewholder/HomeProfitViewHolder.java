package com.shenkong.bzzmaster.ui.fragment.home.viewholder;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.fragment.home.HomeViewModel;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeProfitViewHolder extends MultipleAdapter.MultipleBaseViewHolder {
    private static final String TAG = "HomeProfitViewHolder";
    private final FragmentActivity fragmentActivity;
    public View rootView;
    public MaterialCardView cardSwarm;
    public MaterialTextView tvProfitName;
    public MaterialTextView tvProductName;
    public MaterialTextView tvProductNodeCount;
    public ConstraintLayout chartLayout;
    public TabLayout tabSwitchProduct;
    public WebView webViewChart;
    private HomeViewModel homeViewModel;
    private List<ProductBean> productBeanList;
    private int position;
    private int productId;
    private Timer timer;
    private TimerTask task;

    public HomeProfitViewHolder(View rootView, FragmentActivity fragmentActivity) {
        super(rootView);
        this.rootView = rootView;
        this.fragmentActivity = fragmentActivity;
        this.cardSwarm = rootView.findViewById(R.id.cardSwarm);
        this.tvProfitName = rootView.findViewById(R.id.tvProfitName);
        this.tabSwitchProduct = rootView.findViewById(R.id.tabSwitchProduct);
        this.tvProductName = rootView.findViewById(R.id.tvProductName);
        this.tvProductNodeCount = rootView.findViewById(R.id.tvProductNodeCount);
        this.chartLayout = rootView.findViewById(R.id.chartLayout);
        this.webViewChart = rootView.findViewById(R.id.webViewChart);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void load(MultipleAdapter multipleAdapter, int position) {
/*
        this.cardSwarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragmentActivity, OrderActivity.class);
                intent.putExtra("type", 0);
                fragmentActivity.startActivity(intent);
            }
        });
*/
        webViewChart.getSettings().setJavaScriptEnabled(true);
        webViewChart.loadUrl("file:///android_asset/web/line-simple.html");
        initEvent();

        homeViewModel = new ViewModelProvider(fragmentActivity).get(HomeViewModel.class);

        // 请求产品列表
        homeViewModel.initProduct();
        homeViewModel.getProductBeanListLiveData().observe(fragmentActivity, new Observer<List<ProductBean>>() {
            @Override
            public void onChanged(List<ProductBean> productBeanList) {
                tabSwitchProduct.removeAllTabs();
                for (ProductBean productBean : productBeanList) {
                    tabSwitchProduct.addTab(tabSwitchProduct.newTab().setText(productBean.getName()));
                }
                HomeProfitViewHolder.this.productBeanList = productBeanList;
                tabSwitchProduct.setVisibility(View.VISIBLE);
            }
        });

        homeViewModel.getWebDataLiveData().observe(fragmentActivity, s -> {
            webViewChart.loadUrl("javascript:refreshData(" + s + ")");
        });
    }

    private void initEvent() {
        tabSwitchProduct.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                if (productBeanList != null) {
                    cardSwarm.setVisibility(View.GONE);
                    chartLayout.setVisibility(View.GONE);

                    ProductBean productBean = productBeanList.get(position);
                    productId = productBean.getProductid();
                    tvProductName.setText(productBean.getName());

                    cardSwarm.setVisibility(View.VISIBLE);
                    chartLayout.setVisibility(View.VISIBLE);

                    // 开启查询收益的定时器
                    startTimer();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (task != null) {
            task.cancel();
            task = null;
        }

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                homeViewModel.initHomeProfitData(productId);
            }
        };
        timer.schedule(task, 0, 5000);
    }
}
