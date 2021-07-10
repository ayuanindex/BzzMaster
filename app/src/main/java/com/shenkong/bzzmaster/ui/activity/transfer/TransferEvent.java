package com.shenkong.bzzmaster.ui.activity.transfer;

import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.bean.CapitalBean;

public interface TransferEvent extends BaseEven {
    void showConfirmDialog(String address, double doubleAmountOfMoney);

    void setBalanceText(CapitalBean capitalBean);
}
