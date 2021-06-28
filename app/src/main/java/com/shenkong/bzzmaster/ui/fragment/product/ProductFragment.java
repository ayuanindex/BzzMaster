package com.shenkong.bzzmaster.ui.fragment.product;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.activity.productinfo.ProductInfoActivity;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.product.adapter.ProductAdapter;

import java.util.List;
import java.util.Objects;

public class ProductFragment extends BaseFragment<ProductViewModel, ProductEvent> implements ProductEvent {
    public static ProductFragment productFragment;
    private TabLayout tabSwitchProduct;
    private RecyclerView rcProduct;
    private ProductAdapter productAdapter;
    private ContentLoadingProgressBar progressLoadingData;

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
        progressLoadingData = (ContentLoadingProgressBar) inflate.findViewById(R.id.progressLoadingData);

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
                    customerViewModel.initProductData(Objects.requireNonNull(tab.getText()).toString().trim());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ProductBean productBean, int position) {
                ToastUtil.showToast(getContext(), productAdapter.getItemBean(position).getTitle());
                Intent intent = new Intent(getContext(), ProductInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        // 设置ViewModel和接口
        initViewModel(ProductViewModel.class);
        customerViewModel.setUiRefreshCallBack(this);

        tabSwitchProduct.addTab(tabSwitchProduct.newTab().setText("XCH"));
        tabSwitchProduct.addTab(tabSwitchProduct.newTab().setText("BZZ"));

        productAdapter = new ProductAdapter(requireContext());
        rcProduct.setAdapter(productAdapter);
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

    @Override
    public void showToastMsg(String msg, int type) {

    }

    @Override
    public void updateProductAdapter(List<ProductBean> productBeanList) {
        uiHandler.post(() -> {
            productAdapter.updateDataList(productBeanList);
            hideLoading();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // 加载数据
        showLoading();
        customerViewModel.initProductData(Objects.requireNonNull(tabSwitchProduct.getTabAt(0)).getText().toString().trim());
    }
}
