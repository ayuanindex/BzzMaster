package com.shenkong.bzzmaster.ui.fragment.home;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.homebanner.HomeBannerFragment;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {
    private ViewPager2 viewPager2;
    private final ArrayList<Fragment> bannerFragments = new ArrayList<>();
    private TabLayout tabLayout;
    private RecyclerView rcHotProduct;
    private BannerAdapter bannerAdapter;
    private WebView webViewChart;
    private final ArrayList<String> productList = new ArrayList<>();
    private ProductAdapter productAdapter;
    public static HomeFragment homeFragment;

    public static HomeFragment getInstance() {
        if (homeFragment == null) {
            synchronized (HomeFragment.class) {
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
            }
        }
        return homeFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View inflate) {
        viewPager2 = inflate.findViewById(R.id.viewPager2);
        tabLayout = inflate.findViewById(R.id.tabLayout);
        rcHotProduct = inflate.findViewById(R.id.rcHotProduct);
        webViewChart = inflate.findViewById(R.id.webViewChart);
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
        initBanner();

        initWebView();

        initProduct();
    }

    private void initBanner() {
        bannerFragments.clear();
        bannerAdapter = new BannerAdapter(requireActivity());
        bannerAdapter.setFragments(bannerFragments);
        viewPager2.setAdapter(bannerAdapter);

        viewPager2AddFragment(HomeBannerFragment.getInstance());

        initTabLayout();
        bannerAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        // 设置图表
        webViewChart.getSettings().setJavaScriptEnabled(true);
        webViewChart.loadUrl("file:///android_asset/web/line-simple.html");
    }

    private void initProduct() {
        productList.clear();
        productAdapter = new ProductAdapter(getContext(), productList);
        rcHotProduct.setLayoutManager(new LinearLayoutManager(getContext()));
        rcHotProduct.setAdapter(productAdapter);

        // 给RecyclerView添加分割线
        rcHotProduct.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) > 0) {
                    outRect.top = 1;
                }
            }
        });

        // 产品列表
        productList.add("Chia早期矿工满存挖矿计划");
        productList.add("深空洪源Swarm BZZ挖矿单节点");

        productAdapter.notifyDataSetChanged();

        initProductAdapterListener(productAdapter);
    }

    private void initProductAdapterListener(ProductAdapter productAdapter) {
        productAdapter.setOnRootViewClickListener((v, holder, position) -> ToastUtil.showToast(getContext(), "点击了条目" + (position + 1)));

        productAdapter.setOnBtnPurchaseClickListener((v, holder, position) -> ToastUtil.showToast(getContext(), "购买了" + holder.tvProductTitle.getText().toString().trim()));
    }

    private void initTabLayout() {
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
        }).attach();
    }

    private void viewPager2AddFragment(HomeBannerFragment e) {
        bannerFragments.add(e);
        TabLayout.Tab tab = tabLayout.newTab();
        tabLayout.addTab(tab);
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onResume() {
        super.onResume();
        /*initWebView();*/
    }
}
