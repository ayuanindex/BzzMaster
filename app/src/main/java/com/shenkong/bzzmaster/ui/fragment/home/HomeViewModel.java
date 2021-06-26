package com.shenkong.bzzmaster.ui.fragment.home;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.bean.BannerBean;
import com.shenkong.bzzmaster.bean.ProductBean;
import com.shenkong.bzzmaster.bean.ProfitBean;
import com.shenkong.bzzmaster.ui.base.BaseViewMode;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.util.ArrayList;

public class HomeViewModel extends BaseViewMode<HomeEvent> {

    public void initRecyclerView() {
        new Thread(() -> {
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

            uiRefreshCallBack.setRecyclerViewAdapter(listBeans);
        }).start();
    }
}
