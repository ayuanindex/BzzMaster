package com.shenkong.bzzmaster.ui.activity.submitOrder.blend;

import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.bean.CapitalBean;

public interface SubmitMixedOrderEvent extends BaseEven {
    void setBalanceText(CapitalBean capitalBean);

    void setAddOrderStatus(boolean date);
}
