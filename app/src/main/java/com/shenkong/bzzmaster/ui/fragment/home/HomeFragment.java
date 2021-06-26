package com.shenkong.bzzmaster.ui.fragment.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.BannerViewHolder;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.ProductViewHolder;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.ProfitViewHolder;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment<HomeViewModel> implements HomeEvent {
    public static HomeFragment homeFragment;
    private RecyclerView recyclerView;

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

        customerViewModel.initRecyclerView();
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
    public void setRecyclerViewAdapter(ArrayList<MultipleAdapter.LayoutType> listBeans) {
        uiHandler.post(() -> recyclerView.setAdapter(new MultipleAdapter(getActivity(), listBeans) {
            @NonNull
            @Override
            public MultipleBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate;
                switch (viewType) {
                    case Types.BANNER_LAYOUT:
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_banner, parent, false);
                        return new BannerViewHolder(inflate);
                    case Types.PROFIT_LAYOUT:
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_profit, parent, false);
                        return new ProfitViewHolder(inflate);
                    case Types.PRODUCT_LAYOUT:
                    default:
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_hot_product, parent, false);
                        return new ProductViewHolder(inflate);
                }
            }
        }));
    }
}
