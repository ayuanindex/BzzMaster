package com.shenkong.bzzmaster.ui.activity.plan;

import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;

import java.util.List;

public interface PlanEvent extends BaseEven {
    void updatePlanList(List<MultiLayoutAdapter.LayoutType> date);

    void showEmptyView();
}
