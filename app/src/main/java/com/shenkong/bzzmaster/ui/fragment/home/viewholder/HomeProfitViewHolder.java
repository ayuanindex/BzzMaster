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
import com.shenkong.bzzmaster.common.utils.CurrencyUtil;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.AssetsBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
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
    private boolean isNew = true;
    private List<ProductPlanBean> productPlanBeanList;

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

    private void initEvent() {
        tabSwitchProduct.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                loadProduct();
            }

            private void loadProduct() {
                if (productBeanList != null) {
                    ProductBean productBean = productBeanList.get(position);
                    productId = productBean.getProductid();
                    tvProductName.setText(productBean.getName());
                    tvProductNodeCount.setText(CurrencyUtil.getProfitUnit(productBean.getCurrency()));
                    homeViewModel.initProductPlanData(productId);
                    // 获取收益数据
                    homeViewModel.initHomeProfitData(productId);
                    // 开启查询收益的定时器
                    //startTimer();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                position = tab.getPosition();
                loadProduct();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void load(MultipleAdapter multipleAdapter, int position) {
        if (isNew) {
            isNew = false;
            webViewChart.getSettings().setJavaScriptEnabled(true);
            webViewChart.loadUrl("file:///android_asset/web/line-simple.html");
            LoggerUtils.d(TAG, "正在加载网页");

            homeViewModel = new ViewModelProvider(fragmentActivity).get(HomeViewModel.class);
            initDataSubscribe();

            initEvent();

            // 请求产品列表
            homeViewModel.initProduct();
        }
    }

    /**
     * 数据订阅
     */
    private void initDataSubscribe() {
        // 产品
        homeViewModel.getProductBeanListLiveData().observe(fragmentActivity, productBeanList -> {
            HomeProfitViewHolder.this.productBeanList = productBeanList;

            tabSwitchProduct.removeAllTabs();
            for (ProductBean productBean : productBeanList) {
                tabSwitchProduct.addTab(tabSwitchProduct.newTab().setText(productBean.getName()));
            }
            tabSwitchProduct.setVisibility(View.VISIBLE);
            cardSwarm.setVisibility(View.VISIBLE);
        });

        // 产品计划数据
        homeViewModel.getProductPlanBeanListLiveData().observe(fragmentActivity, new Observer<List<ProductPlanBean>>() {
            @Override
            public void onChanged(List<ProductPlanBean> productPlanBeans) {
                HomeProfitViewHolder.this.productPlanBeanList = productPlanBeans;
                // 请求用户产品数据
                homeViewModel.initCalculationPower();
            }
        });

        // 算力数据
        homeViewModel.getAssetsBeanListLiveData().observe(fragmentActivity, new Observer<List<AssetsBean>>() {
            @Override
            public void onChanged(List<AssetsBean> assetsBeans) {
                long a = 0;
                for (AssetsBean assetsBean : assetsBeans) {
                    for (ProductPlanBean productPlanBean : productPlanBeanList) {
                        if (productPlanBean.getPlanid() == assetsBean.getPid()) {
                            a += assetsBean.getNumber();
                        }
                    }
                }

                String calculationPower = a + tvProductNodeCount.getText().toString().trim();
                tvProductNodeCount.setText(calculationPower);
            }
        });

        // 图表数据
        homeViewModel.getWebDataLiveData().observe(fragmentActivity, s -> {
            webViewChart.loadUrl("javascript:refreshData(" + s + ")");
        });

        // 天数
        homeViewModel.getWebProfitDaysLiveData().observe(fragmentActivity, s -> {
            webViewChart.loadUrl("javascript:setProfitDay(" + s + ")");
        });

        // 收益总金额
        homeViewModel.getWebProfitMoneyLiveData().observe(fragmentActivity, s -> {
            webViewChart.loadUrl("javascript:setProfitCount(" + s + ")");
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
