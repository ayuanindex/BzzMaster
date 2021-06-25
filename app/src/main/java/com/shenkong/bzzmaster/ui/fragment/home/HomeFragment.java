package com.shenkong.bzzmaster.ui.fragment.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.bean.BannerBean;
import com.shenkong.bzzmaster.bean.ProductBean;
import com.shenkong.bzzmaster.bean.ProfitBean;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.BannerViewHolder;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.ProductViewHolder;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.ProfitViewHolder;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {
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
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<MultipleAdapter.LayoutType> listBeans = new ArrayList<>();

        // 添加广告栏数据
        listBeans.add(new BannerBean(
                R.drawable.img_banner_1,
                R.drawable.img_banner_1,
                R.drawable.img_banner_swarm
        ));

        // 添加收益数据
        listBeans.add(new ProfitBean());

        // 添加推荐产品数据
        for (int i = 0; i < 10; i++) {
            listBeans.add(new ProductBean("Chia早期矿工满存挖矿计划" + i));
        }

        recyclerView.setAdapter(new MultipleAdapter(getActivity(), listBeans) {
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
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onResume() {
        super.onResume();
    }
}
