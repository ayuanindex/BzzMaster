package com.shenkong.bzzmaster.ui.fragment.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.HomeBannerViewHolder;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.HomeHotProductViewHolder;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.HomeProfitViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<HomeViewModel, HomeEvent> implements HomeEvent {
    public static HomeFragment homeFragment;
    private RecyclerView recyclerView;
    private MultipleAdapter multipleAdapter;

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
        recyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
        initViewModel(HomeViewModel.class);
        customerViewModel.setUiRefreshCallBack(this);
        customerViewModel.setLifecycleProvider(this);

        multipleAdapter = new MultipleAdapter(requireActivity()) {
            @NonNull
            @Override
            public MultipleBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate;
                switch (viewType) {
                    case Types.BANNER_LAYOUT:
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_banner, parent, false);
                        return new HomeBannerViewHolder(inflate);
                    case Types.PROFIT_LAYOUT:
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_profit, parent, false);
                        return new HomeProfitViewHolder(inflate);
                    case Types.PRODUCT_LAYOUT:
                    default:
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_hot_product, parent, false);
                        return new HomeHotProductViewHolder(inflate);
                }
            }
        };
        recyclerView.setAdapter(multipleAdapter);

        initDataSubscribe();

        customerViewModel.initHomeBannerData();
        customerViewModel.initHomeProfitData();
        customerViewModel.initHomeHotProductData();
    }

    private void initDataSubscribe() {
        customerViewModel.setProductPlanList(new MutableLiveData<>());
        customerViewModel.getProductPlanList().observe(this, (List<MultipleAdapter.LayoutType> productPlanBeanList) -> {
            multipleAdapter.resetDataList(productPlanBeanList);
        });
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

    @Override
    public void initHomeBannerData(MultipleAdapter.LayoutType layoutType) {
        multipleAdapter.addData(layoutType, 0);
    }

    @Override
    public void initProfitData(MultipleAdapter.LayoutType layoutType) {
        multipleAdapter.addData(layoutType, 1);
    }

    @Override
    public void initHotProductData(ArrayList<MultipleAdapter.LayoutType> productPlanBeanList) {
        uiHandler.post(() -> {
            multipleAdapter.resetDataList(productPlanBeanList);
        });
    }
}
