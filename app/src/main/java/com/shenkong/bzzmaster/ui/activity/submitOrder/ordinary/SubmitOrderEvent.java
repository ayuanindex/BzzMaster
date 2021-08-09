package com.shenkong.bzzmaster.ui.activity.submitOrder.ordinary;

import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.bean.CapitalBean;

public interface SubmitOrderEvent extends BaseEven {
    void setBalanceText(CapitalBean capitalBean);

    void setAddOrderStatus(boolean date);
}
