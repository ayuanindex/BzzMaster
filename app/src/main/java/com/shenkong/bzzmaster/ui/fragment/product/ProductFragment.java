package com.shenkong.bzzmaster.ui.fragment.product;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.HomeProductPlanViewHolder;

import java.util.ArrayList;

public class ProductFragment extends BaseFragment<ProductViewModel, ProductEvent> implements ProductEvent {
    public static ProductFragment productFragment;
    private TabLayout tabSwitchProduct;
    private RecyclerView rcProduct;
    // private ProductAdapter productAdapter;
    private ContentLoadingProgressBar progressLoadingData;
    private SwipeRefreshLayout refreshLayout;
    private AppCompatImageView ivEmptyView;
    private int position;
    private MultipleAdapter multipleAdapter;

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
        refreshLayout = inflate.findViewById(R.id.refreshLayout);

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
                load(tab);
            }

            private void load(TabLayout.Tab tab) {
                if (customerViewModel != null) {
                    showLoading();
                    position = tab.getPosition();
                    customerViewModel.initProductPlanData(position);
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

        refreshLayout.setOnRefreshListener(() -> {
            if (customerViewModel.getProductList().getValue() == null) {
                customerViewModel.initProduct();
            }
            customerViewModel.initProductPlanData(position);
        });

        /*productAdapter.setOnItemClickListener((view, productPlanBean, position) -> {
            SharedBean.remove(SharedBean.ProductPlanBean);
            SharedBean.putData(SharedBean.ProductPlanBean, productPlanBean);
            Intent intent = new Intent(getContext(), ProductInfoActivity.class);
            startActivity(intent);
        });*/
    }

    @Override
    protected void initData() {
        // 设置ViewModel和接口
        initViewModel(ProductViewModel.class);
        customerViewModel.setUiRefreshCallBack(this);
        customerViewModel.setLifecycleProvider(this);

        // 数据订阅
        initDataSubscribe();


        // productAdapter = new ProductAdapter(requireActivity());
        multipleAdapter = new MultipleAdapter(requireActivity()) {
            @NonNull
            @Override
            public MultipleBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate = LayoutInflater.from(requireContext()).inflate(R.layout.item_product, parent, false);
                return new HomeProductPlanViewHolder(inflate, requireActivity());
            }
        };
        rcProduct.setAdapter(multipleAdapter);

        customerViewModel.initProduct();
    }

    /**
     * 数据订阅
     */
    private void initDataSubscribe() {
        customerViewModel.setProductList(new MutableLiveData<>());
        customerViewModel.getProductList().observe(this, productBeanList -> {
            tabSwitchProduct.removeAllTabs();
            for (ProductBean productBean : productBeanList) {
                tabSwitchProduct.addTab(tabSwitchProduct.newTab().setText(productBean.getName()));
            }
        });

        customerViewModel.setProductPlan(new MutableLiveData<>());
        customerViewModel.getProductPlan().observe(this, productPlanBeans -> {
            isShowEmptyView(productPlanBeans.isEmpty());
            // productAdapter.updateDataList(productPlanBeans);
            multipleAdapter.addAllData(new ArrayList<>(productPlanBeans));
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
        refreshLayout.setRefreshing(false);
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
