package com.shenkong.bzzmaster.ui.fragment.home;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.model.bean.BannerBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProfitBean;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.util.ArrayList;

public class HomeViewModel extends BaseViewMode<HomeEvent> {
    public void initHomeBannerData() {
        BannerBean bannerBean = new BannerBean(R.drawable.img_banner_1,
                R.drawable.img_banner_1,
                R.drawable.img_banner_swarm
        );
        uiRefreshCallBack.initHomeBannerData(bannerBean);
    }

    public void initHomeProfitData() {
        ProfitBean profitBean = new ProfitBean();
        uiRefreshCallBack.initProfitData(profitBean);
    }

    public void initHomeHotProductData() {
        new Thread(() -> {
            ArrayList<MultipleAdapter.LayoutType> productBeanList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                productBeanList.add(new ProductBean("Chia早期矿工满存挖矿计划" + i));
            }
            uiRefreshCallBack.initHotProductData(productBeanList);
        }).start();
    }
}
