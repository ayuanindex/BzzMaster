package com.shenkong.bzzmaster.ui.fragment.home;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.model.bean.BannerBean;
import com.shenkong.bzzmaster.model.bean.ProfitBean;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.HomeBannerViewHolder;
import com.shenkong.bzzmaster.ui.fragment.home.viewholder.HomeProductPlanViewHolder;
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
        recyclerView = inflate.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setRcProductStyle();
    }

    private void setRcProductStyle() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) > 1) {
                    outRect.bottom = 35;
                }

                /*outRect.top = 0;
                outRect.bottom = 45;

                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = 45;
                }*/
            }
        });
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void initData() {
        initViewModel(HomeViewModel.class);
        customerViewModel.setUiRefreshCallBack(this);
        customerViewModel.setLifecycleProvider(this);

        initDataSubscribe();

        multipleAdapter = new MultipleAdapter(requireActivity()) {
            @NonNull
            @Override
            public MultipleBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate;
                MultipleBaseViewHolder multipleBaseViewHolder;
                switch (viewType) {
                    case Types.BANNER_LAYOUT:
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_banner, parent, false);
                        multipleBaseViewHolder = new HomeBannerViewHolder(inflate, requireActivity());
                        break;
                    case Types.PROFIT_LAYOUT:
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_profit, parent, false);
                        multipleBaseViewHolder = new HomeProfitViewHolder(inflate, requireActivity());
                        break;
                    case Types.PRODUCT_LAYOUT:
                    default:
                        inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
                        multipleBaseViewHolder = new HomeProductPlanViewHolder(inflate, requireActivity());
                        break;
                }

                return multipleBaseViewHolder;
            }
        };
        recyclerView.setAdapter(multipleAdapter);

        customerViewModel.getBannerBeanDataLiveData().setValue(new BannerBean(R.drawable.img_banner_1, null));
        customerViewModel.getProfitBeanDataLiveData().setValue(new ProfitBean());

        multipleAdapter.addData(customerViewModel.getBannerBeanDataLiveData().getValue(), 0);
        multipleAdapter.addData(customerViewModel.getProfitBeanDataLiveData().getValue(), 1);
    }

    private void initDataSubscribe() {
        // 产品
        customerViewModel.setProductBeanListLiveData(new MutableLiveData<>());

        // 给网页端的收益数据
        customerViewModel.setWebDataLiveData(new MutableLiveData<>());
        customerViewModel.setWebProfitDaysLiveData(new MutableLiveData<>());
        customerViewModel.setWebProfitMoneyLiveData(new MutableLiveData<>());

        // 广告
        customerViewModel.setBannerBeanDataLiveData(new MutableLiveData<>());
        customerViewModel.getBannerBeanDataLiveData().observe(this, bannerBean -> {
            multipleAdapter.notifyDataSetChanged();
        });

        // 收益
        customerViewModel.setProfitBeanDataLiveData(new MutableLiveData<>());
        customerViewModel.getProfitBeanDataLiveData().observe(this, profitBean -> {
            multipleAdapter.notifyDataSetChanged();
        });

        // 产品计划
        customerViewModel.setProductPlanListLiveData(new MutableLiveData<>());
        customerViewModel.getProductPlanListLiveData().observe(this, (List<MultipleAdapter.LayoutType> productPlanBeanList) -> {
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

    @Override
    public void onResume() {
        super.onResume();
        customerViewModel.initHomeBannerData();
        customerViewModel.initHomeHotProductData();
    }
}
