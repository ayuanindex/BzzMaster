package com.shenkong.bzzmaster.ui.fragment.home;

import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.util.ArrayList;
import java.util.List;

public interface HomeEvent extends BaseEven {
    void initHomeBannerData(MultipleAdapter.LayoutType layoutType);

    void initProfitData(MultipleAdapter.LayoutType layoutType);

    void initHotProductData(ArrayList<MultipleAdapter.LayoutType> productPlanBeanList);
}
