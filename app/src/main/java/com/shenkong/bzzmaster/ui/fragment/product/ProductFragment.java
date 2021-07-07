package com.shenkong.bzzmaster.ui.fragment.product;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.activity.productinfo.ProductInfoActivity;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.product.adapter.ProductAdapter;

public class ProductFragment extends BaseFragment<ProductViewModel, ProductEvent> implements ProductEvent {
    public static ProductFragment productFragment;
    private TabLayout tabSwitchProduct;
    private RecyclerView rcProduct;
    private ProductAdapter productAdapter;
    private ContentLoadingProgressBar progressLoadingData;
    private AppCompatImageView viewById;
    private AppCompatImageView ivEmptyView;

    public static ProductFragment getInstance() {
        if (productFragment == null) {
            synchronized (ProductFragment.class) {
                if (productFragment == null) {
                    productFragment = new ProductFragment();
                }
            }
        }
        return productFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_product;
    }

    @Override
    protected void initView(View inflate) {
        tabSwitchProduct = inflate.findViewById(R.id.tabSwitchProduct);
        rcProduct = inflate.findViewById(R.id.rcProduct);
        progressLoadingData = inflate.findViewById(R.id.progressLoadingData);
        ivEmptyView = inflate.findViewById(R.id.ivEmptyView);

        setRcProductStyle();
    }

    private void setRcProductStyle() {
        rcProduct.setLayoutManager(new LinearLayoutManager(requireContext()));
        rcProduct.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 0;
                outRect.bottom = 45;

                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = 45;
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        tabSwitchProduct.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (customerViewModel != null) {
                    showLoading();
                    if (customerViewModel.getProductList().getValue() != null) {
                        ProductBean productBean = customerViewModel.getProductList().getValue().get(tab.getPosition());
                        customerViewModel.initProductData(productBean, ProductFragment.this);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        productAdapter.setOnItemClickListener((view, productPlanBean, position) -> {
            ToastUtil.showToast(getContext(), productAdapter.getItemBean(position).getName());
            Intent intent = new Intent(getContext(), ProductInfoActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        // 设置ViewModel和接口
        initViewModel(ProductViewModel.class);
        customerViewModel.setUiRefreshCallBack(this);

        // 数据订阅
        initDataSubscribe();

        customerViewModel.initProduct(this);

        productAdapter = new ProductAdapter(requireContext());
        rcProduct.setAdapter(productAdapter);
    }

    /**
     * 数据订阅
     */
    private void initDataSubscribe() {
        customerViewModel.setProductList(new MutableLiveData<>());
        customerViewModel.getProductList().observe(this, productBeanList -> {
            for (ProductBean productBean : productBeanList) {
                tabSwitchProduct.addTab(tabSwitchProduct.newTab().setText(productBean.getName()));
            }
        });

        customerViewModel.setProductPlan(new MutableLiveData<>());
        customerViewModel.getProductPlan().observe(this, productPlanBeans -> {
            if (productPlanBeans.isEmpty()) {
                isShowEmptyView(true);
            } else {
                isShowEmptyView(false);
            }
            productAdapter.updateDataList(productPlanBeans);
            hideLoading();
        });
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
    }

    public void isShowEmptyView(boolean isShow) {
        ivEmptyView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showToastMsg(String msg, int type) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
